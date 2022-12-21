from typing import List

from pydantic import BaseModel


class TeamModel(BaseModel):
    team_name: str  # will act as a unique id in the teams collection
    team_lead_name: str
    employees: List[str]

