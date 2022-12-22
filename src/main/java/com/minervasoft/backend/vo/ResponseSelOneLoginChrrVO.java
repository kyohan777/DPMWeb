package com.minervasoft.backend.vo;

public class ResponseSelOneLoginChrrVO extends AbstractResponseVO {

    private LoginChrrVO selOne = null;
    private String pwdYn="Y";

    public LoginChrrVO getSelOne() {
        return selOne;
    }

    public void setSelOne(LoginChrrVO selOne) {
        this.selOne = selOne;
    }

	public String getPwdYn() {
		return pwdYn;
	}

	public void setPwdYn(String pwdYn) {
		this.pwdYn = pwdYn;
	}
}
