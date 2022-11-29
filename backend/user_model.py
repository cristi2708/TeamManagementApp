from pydantic import BaseModel


class UserModel(BaseModel):
    full_name: str
    username: str
    email: str
    password_hash: str
    phone_number: str