from typing import Union

import uvicorn

from fastapi import Depends, FastAPI, Header
from fastapi.security import HTTPBasic, HTTPBasicCredentials

import mongo
import user_api

app = FastAPI(docs_url="/")
security = HTTPBasic()


@app.get("/auth/test")
def read_current_user(credentials: HTTPBasicCredentials = Depends(security)):
    return {"username": credentials.username, "password": credentials.password}


@app.get("/hello")
async def root():
    return {"message": "Hello World"}


@app.get("/profile/me")
async def get_my_profile(x_username: Union[str, None] = Header(default=None)):
    return await mongo.do_find_user(x_username)


@app.get("/profile/{username}")
async def get_user_profile(username: str):
    return await mongo.do_find_user(username)


app.include_router(user_api.router)


if __name__ == '__main__':
    uvicorn.run(app, host="0.0.0.0", port=8000)
