package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListBizStatsVO extends AbstractResponseVO {

    private List<BizStatsVO> selList = null;

    public List<BizStatsVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<BizStatsVO> selList) {
        this.selList = selList;
    }
}
