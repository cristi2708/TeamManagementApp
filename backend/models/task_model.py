import datetime
import uuid

from pydantic import BaseModel


class TaskModel(BaseModel):
    task_id: uuid = uuid.UUID()     # will act as a unique id in the tasks collection
    description: str
    due_date: datetime.date
    reporter: str
    assignee: str
