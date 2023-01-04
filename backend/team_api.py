from typing import List, Union

from fastapi import APIRouter, HTTPException, Header

import mongo
from models.team_model import TeamModel
from fastapi.responses import JSONResponse

from models.user_model import Role

router = APIRouter(prefix="/teams", tags=["teams"])


@router.post("/create")
async def add_team(team: TeamModel):
    team_db = await mongo.find_team_by_name(team.team_name)
    if team_db:
        raise HTTPException(400, f"team with the name {team.team_name} already exists")

    # team lead validation
    lead_exists: bool = await mongo.do_find_team_lead(team.team_lead_name)
    if not lead_exists:
        raise HTTPException(400, f"team leader named {team.team_lead_name} cannot be found")

    is_team_lead_free: bool = await mongo.is_team_lead_free(team.team_lead_name)
    if not is_team_lead_free:
        raise HTTPException(400, f"team leader named {team.team_lead_name} has already a team assigned")

    # employee validation
    employee_list: List[str] = team.employees
    for employee in employee_list:
        if not await mongo.do_find_employee(employee):
            raise HTTPException(400, f"employee named {employee} cannot be found")

    await mongo.insert_team(team)
    return {"message": f"team named {team.team_name} created"}


@router.put("/add_member/{team_name}")
async def add_team_member(team_name: str, employee: str):
    updated = False
    is_valid_employee: bool = await mongo.do_find_employee(employee)
    team_db: TeamModel = await mongo.find_team_by_name(team_name)

    # check if the team does not have that employee already
    if is_valid_employee and team_db is not None and employee not in team_db.employees:
        updated: bool = await mongo.add_employee_to_team(team_name, employee)
    if not updated:
        raise HTTPException(400, f"unable to add employee {employee} to team {team_name}")

    return {"message": f"employee: {employee} added to team: {team_name}"}


@router.put("/delete_member/{team_name}")
async def remove_team_member(team_name: str, employee: str):
    updated: bool = await mongo.remove_employee_from_team(team_name, employee)
    if not updated:
        raise HTTPException(400, f"unable to remove employee {employee} from team {team_name}")

    return {"message": f"employee: {employee} removed from team: {team_name}"}


from models import user_model, team_model


@router.get("/me")
async def get_my_team(x_username: Union[str, None] = Header(default=None)):
    """returns the team that is associated with the logged-in user"""
    user: user_model = await mongo.find_user(x_username)
    if not user:
        raise HTTPException(400, f"{x_username} not found in the database")
    elif user.role == Role.LEAD:
        team: team_model = await mongo.find_team_by_lead(x_username)
        if not team:
            raise HTTPException(400, f"{x_username} does not belong to any team")
        else:
            return team
    elif user.role == Role.EMPLOYEE:
        try:
            team: team_model = await mongo.find_team_of_employee(x_username)
            return team
        except Exception as e:
            raise HTTPException(400, f"{x_username} does not belong to any team or it belongs to more than 1 teams") from e

