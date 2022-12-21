import datetime
import uuid

import bson
from pydantic import BaseModel


class TaskModel(BaseModel):
    _id: bson.ObjectId = bson.ObjectId()    # will act as a unique id in the tasks collection
    description: str
    due_date: datetime.datetime
    reporter: str   # a team lead
    assignee: str   # a team member
