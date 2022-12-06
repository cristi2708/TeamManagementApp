import typing

import motor.motor_asyncio

import user_model

client = motor.motor_asyncio.AsyncIOMotorClient('mongodb://localhost:27017')
db = client.app_database


async def do_find_user(username: str) -> typing.Optional[user_model.UserModel]:
    document = await db.users.find_one({'username': username})
    if document is not None:
        return user_model.UserModel(**document)
    return None


async def insert_user(user: user_model.UserModel):
    result = await db.users.insert_one(user.dict())
    return result
