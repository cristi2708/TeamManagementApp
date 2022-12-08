from typing import List

from fastapi import APIRouter, HTTPException

import mongo
from models.team_model import TeamModel

router = APIRouter(prefix="/teams", tags=["teams"])


@router.post("/create")
async def add_team(team: TeamModel):
    team_db = await mongo.do_find_team(team.team_name)
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
