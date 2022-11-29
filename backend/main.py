import uvicorn

from fastapi import Depends, FastAPI, HTTPException
from fastapi.security import HTTPBasic, HTTPBasicCredentials

import mongo
from user_model import UserModel

app = FastAPI(docs_url="/")
security = HTTPBasic()


@app.get("/auth/test")
def read_current_user(credentials: HTTPBasicCredentials = Depends(security)):
    return {"username": credentials.username, "password": credentials.password}


@app.get("/hello")
async def root():
    return {"message": "Hello World"}


@app.post("/register")
async def register_user(user: UserModel):
    user_db = await mongo.do_find_user(user.username)
    if user_db:
        raise HTTPException(400, f"user with username {user.username} already exists")
    else:
        await mongo.insert_user(user)
        return {"message": "user registered"}


if __name__ == '__main__':
    uvicorn.run(app, host="0.0.0.0", port=8000)
