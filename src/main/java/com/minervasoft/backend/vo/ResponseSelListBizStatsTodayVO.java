package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListBizStatsTodayVO extends AbstractResponseVO {

    private List<BizStatsTodayVO> selList = null;

    public List<BizStatsTodayVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<BizStatsTodayVO> selList) {
        this.selList = selList;
    }
}
