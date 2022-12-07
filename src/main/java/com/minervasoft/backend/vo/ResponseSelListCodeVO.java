package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListCodeVO extends AbstractResponseVO {

    private List<CodeVO> selList = null;

    public List<CodeVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<CodeVO> selList) {
        this.selList = selList;
    }
}
