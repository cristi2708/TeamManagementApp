from fastapi import APIRouter, HTTPException

import mongo
from user_model import UserModel, UserLoginModel

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
    # 200 if password from request matches with password from db
    # 401 if password don't match
    # 401 if user not found

    user_from_db = await mongo.do_find_user(user.username)
    if not user_from_db or user_from_db.password_hash != user.password:
        raise HTTPException(401, f"invalid credentials")
    else:
        return {"message": "user logged in successfully!"}

