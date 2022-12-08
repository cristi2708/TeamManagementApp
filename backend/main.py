import uvicorn
from fastapi import Depends, FastAPI
from fastapi.security import HTTPBasic, HTTPBasicCredentials

import team_api
import user_api

app = FastAPI(docs_url="/")
security = HTTPBasic()


@app.get("/auth/test")
def read_current_user(credentials: HTTPBasicCredentials = Depends(security)):
    return {"username": credentials.username, "password": credentials.password}


@app.get("/hello")
async def root():
    return {"message": "Hello World"}


app.include_router(user_api.router)
app.include_router(team_api.router)


if __name__ == '__main__':
    uvicorn.run(app, host="0.0.0.0", port=8000)
