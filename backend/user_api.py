from typing import Union

from fastapi import APIRouter, HTTPException, Header

import mongo
from models.user_model import UserModel, UserLoginModel

router = APIRouter(prefix="/users", tags=["users"])


@router.post("/register")
async def register_user(user: UserModel):
    user_db = await mongo.do_find_user(user.username)
    if user_db:
        raise HTTPException(400, f"user with username {user.username} already exists")
    else:
        await mongo.insert_user(user)
        return {"message": "user registered"}


@router.post("/login")
async def login_user(user: UserLoginModel):
    user_from_db = await mongo.do_find_user(user.username)
    if not user_from_db or user_from_db.password_hash != user.password:
        raise HTTPException(401, f"invalid credentials")
    else:
        return {"message": "user logged in successfully!"}


@router.get("/profile/me")
async def get_my_profile(x_username: Union[str, None] = Header(default=None)):
    return await mongo.do_find_user(x_username)


@router.get("/profile/{username}")
async def get_user_profile(username: str):
    return await mongo.do_find_user(username)


@router.get("/employees")
async def get_employees():
    return await mongo.find_employees()

