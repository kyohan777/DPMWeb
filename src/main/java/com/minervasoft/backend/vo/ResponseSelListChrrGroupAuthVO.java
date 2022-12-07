package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListChrrGroupAuthVO extends AbstractResponseVO {

    private List<ChrrGroupAuthVO> selList = null;

    public List<ChrrGroupAuthVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<ChrrGroupAuthVO> selList) {
        this.selList = selList;
    }
}
