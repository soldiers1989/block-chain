package com.xdaocloud.futurechain.dto.resp.football;


import com.xdaocloud.futurechain.common.IBaseRequest;

import java.util.List;
import java.util.Map;

public class GetMaiMatchListResponse implements IBaseRequest {


    private Map<String ,List<MatchDTO>> matchDTOs;


    public Map<String, List<MatchDTO>> getMatchDTOs() {
        return matchDTOs;
    }

    public void setMatchDTOs(Map<String, List<MatchDTO>> matchDTOs) {
        this.matchDTOs = matchDTOs;
    }
}
