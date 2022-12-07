package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListMenuVO extends AbstractResponseVO {

    private List<MenuVO> selList = null;

    public List<MenuVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<MenuVO> selList) {
        this.selList = selList;
    }
}
