import asyncio
import typing

import motor.motor_asyncio

from models import user_model, team_model

client = motor.motor_asyncio.AsyncIOMotorClient('mongodb://localhost:27017')
db = client.app_database


async def do_find_user(username: str) -> typing.Optional[user_model.UserModel]:
    document = await db.users.find_one({'username': username})
    if document is not None:
        return user_model.UserModel(**document)
    return None


async def do_find_team_lead(username: str) -> bool:
    document = await db.users.find_one({'username': username, 'role': 'lead'})
    if document is not None:
        return True
    else:
        return False


async def do_find_employee(username: str) -> bool:
    document = await db.users.find_one({'username': username, 'role': 'employee'})
    if document is not None:
        return True
    else:
        return False


async def insert_user(user: user_model.UserModel):
    result = await db.users.insert_one(user.dict())
    return result


async def insert_team(team: team_model.TeamModel):
    result = await db.teams.insert_one(team.dict())
    return result


async def do_find_team(team_name: str):
    document = await db.teams.find_one({'team_name': team_name})
    if document is not None:
        return team_model.TeamModel(**document)
    return None


async def is_team_lead_free(lead_name: str) -> bool:
    """ returns true if the team lead does not have any teams under his name """
    document = await db.teams.find_one({'team_lead_name': lead_name})
    if not document:
        return True
    else:
        return False

# if __name__ == '__main__':
#     loop = asyncio.get_event_loop()
#     asyncio.ensure_future(is_team_lead_free("sst"))
#     loop.run_forever()
