package com.xdaocloud.futurechain.dto.resp.football;


import com.xdaocloud.futurechain.common.IBaseRequest;

import java.util.List;

public class TeamMsgResponse implements IBaseRequest {

    private List<CoachDTO> coach;

    private List<CaptainDTO> captain;

    private List<TeamDTO> team;

    private List<SubstitutionDTO> substitution;


    public List<CaptainDTO> getCaptain() {
        return captain;
    }

    public void setCaptain(List<CaptainDTO> captain) {
        this.captain = captain;
    }

    public List<TeamDTO> getTeam() {
        return team;
    }

    public void setTeam(List<TeamDTO> team) {
        this.team = team;
    }

    public List<SubstitutionDTO> getSubstitution() {
        return substitution;
    }

    public void setSubstitution(List<SubstitutionDTO> substitution) {
        this.substitution = substitution;
    }

    public List<CoachDTO> getCoach() {
        return coach;
    }

    public void setCoach(List<CoachDTO> coach) {
        this.coach = coach;
    }
}
