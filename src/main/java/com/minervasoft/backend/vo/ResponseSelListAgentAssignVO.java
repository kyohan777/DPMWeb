package com.minervasoft.backend.vo;

import java.util.List;

public class ResponseSelListAgentAssignVO extends AbstractResponseVO {

    private List<AgentAssignVO> selList = null;

    public List<AgentAssignVO> getSelList() {
        return this.selList;
    }

    public void setSelList(List<AgentAssignVO> selList) {
        this.selList = selList;
    }
}
