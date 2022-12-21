from logging import getLogger
from typing import List

from fastapi import APIRouter, HTTPException

import mongo
from models.task_model import TaskModel
from models.exceptions import DatabaseOperationFailed

router = APIRouter(prefix="/tasks", tags=["tasks"])

logger = getLogger("task_api")


@router.post("/create")
async def add_task(task: TaskModel):
    try:
        await mongo.insert_task(task)
    except DatabaseOperationFailed as e:
        logger.exception(e)
        raise HTTPException(400, "unable to create task")
    return {"message": "task created"}


@router.get("/find/{username}")
async def get_user_tasks(username: str):
    # try:
    return await mongo.find_tasks(username)
    # except:


