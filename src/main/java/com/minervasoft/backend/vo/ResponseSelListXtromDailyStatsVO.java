package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListXtromDailyStatsVO extends AbstractResponseVO {

    private List<XtromDailyStatsVO> selList = null;

    public List<XtromDailyStatsVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<XtromDailyStatsVO> selList) {
        this.selList = selList;
    }
}
