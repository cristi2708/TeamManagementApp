from logging import getLogger

import bson
from fastapi import APIRouter, HTTPException

import mongo
from models.exceptions import DatabaseOperationFailed
from models.task_model import TaskModel

router = APIRouter(prefix="/tasks", tags=["tasks"])

logger = getLogger("task_api")


@router.post("/create")
async def add_task(task: TaskModel):
    try:
        await mongo.insert_task(TaskModel(**task.dict()))
    except DatabaseOperationFailed as e:
        logger.exception(e)
        raise HTTPException(400, "unable to create task")
    return {"message": "task created"}


@router.get("/find/{username}")
async def get_user_tasks(username: str):
    # try:
    return await mongo.find_tasks(username)
    # except:


@router.put("/complete/{_id}")
async def complete_user_task(_id: str):
    object_id = bson.ObjectId(_id)
    return await mongo.complete_task(object_id)

