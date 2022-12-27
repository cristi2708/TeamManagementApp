import asyncio
import typing

import bson
import motor.motor_asyncio
import pymongo.results

from models import user_model, team_model, task_model, exceptions

client = motor.motor_asyncio.AsyncIOMotorClient('mongodb://localhost:27017')
db = client.app_database


async def do_find_user(username: str) -> typing.Optional[user_model.UserModel]:
    document = await db.users.find_one({'_id': username})

    if document is not None:
        # replace _id key with username
        username = document['_id']
        del document['_id']
        document['username'] = username
        return user_model.UserModel(**document)
    return None


async def do_find_team_lead(username: str) -> bool:
    document = await db.users.find_one({'_id': username, 'role': 'lead'})
    if document is not None:
        return True
    else:
        return False


async def do_find_employee(username: str) -> bool:
    document = await db.users.find_one({'_id': username, 'role': 'employee'})
    if document is not None:
        return True
    else:
        return False


async def insert_user(user: user_model.UserModel):
    user_dict: dict = user.dict()
    username = user_dict['username']
    del user_dict['username']
    user_dict['_id'] = username
    result = await db.users.insert_one(user_dict)
    return result


async def insert_team(team: team_model.TeamModel):
    team_dict: dict = team.dict()
    team_name = team_dict['team_name']
    del team_dict['team_name']
    team_dict['_id'] = team_name
    result = await db.teams.insert_one(team_dict)
    return result


async def insert_task(task: task_model.TaskModel):
    try:
        result = await db.tasks.insert_one(task.dict())
        return result
    except pymongo.errors.PyMongoError as e:
        raise exceptions.DatabaseOperationFailed("unable to write document to db") from e


async def do_find_team(team_name: str) -> team_model.TeamModel or None:
    document = await db.teams.find_one({'_id': team_name})
    if document is not None:
        # replace _id key with team_name
        team_name = document['_id']
        del document['_id']
        document['team_name'] = team_name
        return team_model.TeamModel(**document)
    return None


async def find_tasks(assignee: str) -> typing.List[task_model.TaskModel]:
    task_list = []
    try:
        cursor = db.tasks.find({"assignee": assignee})
        document_list = await cursor.to_list(length=100)
        for document in document_list:
            idd = str(document['_id'])
            document.pop('_id', None)
            document['id'] = idd
            task_list.append(task_model.TaskModelRead(**document))
        return task_list
    except pymongo.errors.PyMongoError as e:
        raise exceptions.DatabaseOperationFailed("unable to retrieve tasks") from e


async def is_team_lead_free(lead_name: str) -> bool:
    """ returns true if the team lead does not have any teams under his name """
    document = await db.teams.find_one({'team_lead_name': lead_name})
    if not document:
        return True
    else:
        return False


async def add_employee_to_team(team_name: str, employee: str) -> bool:
    """ update the employees list of a specific team """
    update_result: pymongo.results.UpdateResult = await db.teams.update_one({'_id': team_name},
                                                                            {'$push': {'employees': employee}})
    return update_result.modified_count > 0


async def remove_employee_from_team(team_name: str, employee: str) -> bool:
    """ update the employees list of a specific team """
    update_result: pymongo.results.UpdateResult = await db.teams.update_one({'_id': team_name},
                                                                            {'$pull': {'employees': employee}})
    return update_result.modified_count > 0


async def complete_task(_id: bson.ObjectId) -> bool:
    update_result: pymongo.results.UpdateResult = await db.tasks.update_one({'_id': _id},
                                                                            {'$set': {'completed': True}})
    return update_result.modified_count > 0


async def find_employees() -> typing.List[user_model.UserModel]:
    user_list = []
    try:
        cursor = db.users.find({"role": "employee"})
        document_list = await cursor.to_list(length=100)
        for document in document_list:
            username = str(document['_id'])
            document.pop('_id', None)
            document['username'] = username
            user_list.append(user_model.UserModel(**document))
        return user_list
    except pymongo.errors.PyMongoError as e:
        raise exceptions.DatabaseOperationFailed("unable to retrieve employees") from e


if __name__ == '__main__':
    loop = asyncio.get_event_loop()
    asyncio.ensure_future(remove_employee_from_team("t5", "string"))
    loop.run_forever()
