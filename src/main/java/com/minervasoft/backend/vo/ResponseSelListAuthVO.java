package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListAuthVO extends AbstractResponseVO {

    private List<GroupAuthVO> selList = null;

    public List<GroupAuthVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<GroupAuthVO> selList) {
        this.selList = selList;
    }
}
