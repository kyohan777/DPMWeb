package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListStepStatsVO extends AbstractResponseVO {

    private List<StepStatsVO> selList = null;

    public List<StepStatsVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<StepStatsVO> selList) {
        this.selList = selList;
    }
}
