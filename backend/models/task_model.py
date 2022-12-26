import datetime

import bson
from pydantic import BaseModel


# body-ul request-ului nu are _id field-ul in el cand fac get tasks
class TaskModel(BaseModel):
    _id: bson.ObjectId = bson.ObjectId()
    description: str
    due_date: datetime.datetime
    reporter: str  # a team lead
    assignee: str  # a team member
    completed: bool = False


# used for the get routes
class TaskModelRead(BaseModel):
    id: str
    description: str
    due_date: datetime.datetime
    reporter: str  # a team lead
    assignee: str  # a team member
    completed: bool = False
