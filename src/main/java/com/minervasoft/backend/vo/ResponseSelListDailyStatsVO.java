package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListDailyStatsVO extends AbstractResponseVO {

    private List<DailyStatsVO> selList = null;

    public List<DailyStatsVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<DailyStatsVO> selList) {
        this.selList = selList;
    }
}
