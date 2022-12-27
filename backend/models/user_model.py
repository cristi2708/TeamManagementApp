from pydantic import BaseModel
from enum import Enum


class Role(str, Enum):
    EMPLOYEE = "employee"
    LEAD = "lead"


class UserModel(BaseModel):
    first_name: str
    last_name: str
    username: str  # _id in the users collection
    password_hash: str  # the body of the request will contain the password hash not the actual password
    email: str  # the data validation will be performed at the java level
    phone_number: str
    role: Role


class UserLoginModel(BaseModel):
    username: str
    password: str
