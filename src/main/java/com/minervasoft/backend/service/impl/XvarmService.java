package com.minervasoft.backend.service.impl;

import com.windfire.apis.asys.asysUsrElement;
import com.windfire.apis.asysConnectData;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service("xvarmService")
@Configuration
public class XvarmService {
	
	
    private asysConnectData connection = null;
   
    
    @Value("${xvarm.clientName}")
    private String clientName;
    
    @Value("${xvarm.userName}")
    private String userName;
    
    @Value("${xvarm.password}")
    private String password;
    
    @Value("${xvarm.serverIp}")
    private String serverIp;
    
    @Value("${xvarm.serverPort}")
    private int serverPort;

    

    public void createConnection() throws Exception {
        try {
            if (connection == null)
                connection = new asysConnectData(serverIp, serverPort, clientName, userName, password);
        } catch (Exception e) {

            throw e;
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }

        } catch (Exception e) {
            connection = null;
        }
    }

    /**
     * XTORM으로부터 이미지를 다운로드
     * @param elementID 다운로드 대상 ElementId
     * @param fileNameExt 다운로드 받을 때 사용 할 파일명.확장자명
     * @param dest 다운로드 받을 경로
     * @throws Exception
     */
    public void downloadFile(String elementID, String fileNameExt, String dest) throws Exception {
        if(connection == null) {
            try {
                createConnection();
            } catch (Exception e) {
                throw e;
            }
        }
        try {
            asysUsrElement downloadFile = new asysUsrElement(connection);
            downloadFile.m_elementId = "XVARM_MAIN::" + elementID + "::IMAGE";

            int ret = downloadFile.getContent(dest + File.separator + fileNameExt, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        disconnect();
    }

    public static void main(String[] args) {
        String s = "XVARM_MAIN::2019102316515801::IMAGE";
        XvarmService xv = new XvarmService();
        String test [] = {"C:\\Go\\CONTRIBUTING.md"};

        try {
            xv.downloadFile("202107061322321t","test.jpg", "C:\\Users\\hangmok\\Desktop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
