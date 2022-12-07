package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListChrrVO extends AbstractResponseVO {

    private List<ChrrVO> selList = null;

    public List<ChrrVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<ChrrVO> selList) {
        this.selList = selList;
    }
}
