package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListMonthlyStatsVO extends AbstractResponseVO {

    private List<MonthlyStatsVO> selList = null;

    public List<MonthlyStatsVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<MonthlyStatsVO> selList) {
        this.selList = selList;
    }
}
