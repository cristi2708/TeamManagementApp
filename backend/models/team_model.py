from typing import List

from pydantic import BaseModel


class TeamModel(BaseModel):
    _id: str  # will act as a unique id in the teams collection   => team_name
    team_lead_name: str
    employees: List[str]

