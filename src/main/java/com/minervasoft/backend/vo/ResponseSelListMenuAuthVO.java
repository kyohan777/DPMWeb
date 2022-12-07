package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListMenuAuthVO extends AbstractResponseVO {

    private List<MenuAuthVO> selList = null;

    public List<MenuAuthVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<MenuAuthVO> selList) {
        this.selList = selList;
    }
}
