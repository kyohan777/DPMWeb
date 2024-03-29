package com.minervasoft.backend.controller;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.type.TypeException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.minervasoft.backend.service.DpmService;
import com.minervasoft.backend.vo.CalibVerifiVo;
import com.minervasoft.backend.vo.CommonVO;
import com.minervasoft.backend.vo.LoginChrrVO;
import com.minervasoft.backend.vo.ResponseCalibVerifiVo;
import com.minervasoft.backend.vo.ResponseSelOneLoginChrrVO;
import com.minervasoft.backend.vo.ResponseStatisticsVo;
import com.minervasoft.backend.vo.ResponseUserManageVo;
import com.minervasoft.backend.vo.StatisticsVO;
import com.minervasoft.backend.vo.UserManageVo;

//import SafeSignOn.SSO;
//import SafeSignOn.SsoAuthInfo;

@Controller
public class DpmController {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource(name = "DpmService")
    private DpmService dpmService;
    
    
    /********************************************* 
     * 로그인 및 공통  
     *********************************************/
    
    /**
     * 로그인ID 검사
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/login/loginCheck.do")
    @ResponseBody
    public ResponseSelOneLoginChrrVO selectLoginChkInfo(LoginChrrVO paramVO) {
        
    	ResponseSelOneLoginChrrVO response = new ResponseSelOneLoginChrrVO();
        
        try {
        	LoginChrrVO one = dpmService.selOneLoginChrr(paramVO);
        	if(one != null) {//아이디 존재여부 확인
        		response.setSelOne(one);
        			String password = paramVO.getChrrPwd();
        			String hex = "";
        			MessageDigest md = MessageDigest.getInstance("SHA-256");
            		// 평문 암호화
            		md.update(password.getBytes());
            		hex = String.format("%064x", new BigInteger(1, md.digest()));
            		if(!hex.equals(one.getChrrPwd())) {
            			one.setPwdYn("N");
            		}
        	}else {
        		response.setSelOne(one);
        	}
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    //초기 비번 : admin - 8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
    
//    @RequestMapping(value = "/login/getUserInfoSSO.do")
//    public String getUserInfoSSO(ChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
//    	
//        try {
//        	
//
//        	String sRemoteAddr = request.getRemoteAddr();
//        	String sApiKey = "368B184727E89AB69FAF";
//        	InetAddress inetAddress = InetAddress.getLocalHost();
//        	String ip = inetAddress.toString();
//        	String sToken = "";
//        	int nResult = -1;
//        	logger.debug("ip : " + ip);
//        	if (ip.contains("10.254.12.106")) { //ip.contains("10.254.10.124")||
//        		SSO sso = new SSO(sApiKey)	;	
//        		SsoAuthInfo authInfo = new SsoAuthInfo();
//        		Cookie[] cs = request.getCookies();
//				if(cs != null) {
//					for(int i=0; i<cs.length; i++) {
//						logger.debug("cs[i].getName() : " + cs[i].getName());
//						if(cs[i].getName().equals("ssotoken")) {
//							if(cs[i].getValue()!="") {
//								logger.debug("cs[i].getValue() : " + cs[i].getValue());
//								nResult = sso.verifyToken(cs[i].getValue());
//								logger.debug("nResult: " + nResult);
//								if(nResult >= 0) {
//									sToken = cs[i].getValue();
//								}
//							}
//						}
//					}
//				}	
//				if (!sToken.equals("")) {
//					authInfo = sso.userView(sToken, sRemoteAddr);
//					
//					String sUserName = authInfo.getUserName();
//				}
//        		
//        	}
////        		String sID = "";
////        		String sPwd = "";
////        		String sApiKey = "368B184727E89AB69FAF";
////        		int nResult = -1;
////        		
////        		
////        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
////        		sID = paramVO.getChrrId();
////        		sPwd = paramVO.getChrrPwd();
////        		
////        		authInfo = sso.authID(sID, sPwd, true, sRemoteAddr);
////        		nResult = sso.getLastError();
////        		if (nResult >= 0) {
////        			//세션정보저장후 메인페이지로 이동
////                	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
////                		request.getSession().setAttribute("loginInfo", loginInfoVO);
////                		request.getSession().setMaxInactiveInterval(60*300);
////                		
////                		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
////                		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
////                		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
////                		modelMap.addAttribute("contentPage", "firstView.jsp");
////                		
////                		returnPage = "dpm/main";
////                	} else {
////                		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
////                	}
////        		}
////        		else {
////        			modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
////        		}
////        	}else {
//        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
//
//            	//세션정보저장후 메인페이지로 이동
//            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
//            		request.getSession().setAttribute("loginInfo", loginInfoVO);
//            		request.getSession().setMaxInactiveInterval(60*300);
//            		
//            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
//            		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
//            		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
//            		modelMap.addAttribute("contentPage", "firstView.jsp");
//            		
//            		returnPage = "dpm/main";
//            	} else {
//            		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
//            	}
//        		
//        	//}
//        	
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        
//        return returnPage;
//        
//    } 
    
    /**
     * 로그인처리
     * @param paramVO
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/login/login.do")
    public String login(LoginChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
    	String returnPage = "dpm/login";
    	
        try {
        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);
            	//세션정보저장후 메인페이지로 이동
            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
            		request.getSession().setAttribute("loginInfo", loginInfoVO);
            		request.getSession().setMaxInactiveInterval(60*300);
            		
            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
            		returnPage = "dpm/firstView";
            	} else {
            		modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
            	}
        	
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return returnPage;
        
    } 
    
    
    @RequestMapping(value = "/login/loginSSO.do")
    public String login2(LoginChrrVO paramVO, HttpServletRequest request, ModelMap modelMap) {
    	String returnPage = "dpm/login";
    	
        try {
        	

        	String sRemoteAddr = request.getRemoteAddr();
        	InetAddress inetAddress = InetAddress.getLocalHost();
        	String ip = inetAddress.toString();
        	logger.debug("ip : " + ip);
        	
        		LoginChrrVO loginInfoVO = dpmService.selOneLoginChrr(paramVO);

            	//세션정보저장후 메인페이지로 이동
            	if(loginInfoVO != null && !"".equals(loginInfoVO.getChrrId())) {
            		request.getSession().setAttribute("loginInfo", loginInfoVO);
            		request.getSession().setMaxInactiveInterval(60*300);
            		
            		modelMap.addAttribute("loginResult", "로그인에 성공하였습니다.");
            		modelMap.addAttribute("chrrId", loginInfoVO.getChrrId());
            		modelMap.addAttribute("chrrNm", loginInfoVO.getChrrNm());
            		
            		returnPage = "dpm/dpmDayPro";
            	} else {
            		//modelMap.addAttribute("loginResult", "로그인에 실패하였습니다.");
            	}
        		
        	
        	
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return returnPage;
        
    } 
    
   
    
    /**
     * 서버일자, 일시 조회
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getServerDateTime.do")
    @ResponseBody
    public CommonVO getServerDateTime() {
        
    	CommonVO response = new CommonVO();
        
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMddHHmmss");
    	
    	String date = formatDate.format(System.currentTimeMillis());
    	String time = formatTime.format(System.currentTimeMillis());
    	
    	response.setServerDate(date);
    	response.setServerTime(time);
        
        return response;
    }

    /**
     * 브라우저명 리턴
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {

        String header =request.getHeader("User-Agent");
        if (header.contains("MSIE")) {
               return "MSIE";

        } else if(header.contains("Chrome")) {
               return "Chrome";

        } else if(header.contains("Opera")) {
               return "Opera";
        }

        return "Firefox";
    }
    
    /**
     * 엑셀출력 요청 header정보 set
     * @param request
     * @param response
     * @param fileName
     * @throws Exception
     */
    private void setExcelDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
    	 String header = getBrowser(request);

    	 if (header.contains("MSIE")) {

  	        String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");

  	        response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");

  	 } else if (header.contains("Firefox")) {

  	        //String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

  	        //response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");
  		 	String docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");

	        response.setHeader("Content-Disposition", "attachment;filename=" + docName + ";");

  	 } else if (header.contains("Opera")) {

  	        String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

  	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");

  	 } else if (header.contains("Chrome")) {

  	        String docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

  	        response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + "\"");

  	 }
       response.setCharacterEncoding("UTF-8");
       response.setContentType("application/vnd.ms-excel");
       response.setHeader("Pragma","public");
       response.setHeader("Expires","0");
    }
    
    
          

    
    

    
    


    /******************************************************************************************
     * 
     * 
     * 분리보관
     * 
     * 
     ******************************************************************************************/
    
    
    
    /**
     *  [IMR] 일일 처리 현황:: 일일 처리 현황 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDailyProInfoTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDailyProInfoTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmDailyProInfoTotRowCnt(paramVO);
        	if(one != null) {
        		response.setTotRowCnt(one.getTotRowCnt());
        	}
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 일일 처리 현황:: 일일 처리 현황 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDailyProInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDailyProInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmDailyProInfo(paramVO);
            for(StatisticsVO vo : list) {
                if(vo.getIntvisionImr() != null && !vo.getIntvisionImr().isEmpty()) {
                	//json string data 파싱하기
                	String json = vo.getIntvisionImr(); 
                	JSONParser parser = new JSONParser();
                	Object obj = parser.parse(json);
                	JSONObject jsonObj = (JSONObject) obj;
                	System.out.println(jsonObj);
                	
                	vo.setAyn((String)jsonObj.get("A"));
                	vo.setByn((String)jsonObj.get("B"));
                	vo.setCyn((String)jsonObj.get("C"));
                	vo.setDyn((String)jsonObj.get("D"));
                	vo.setEyn((String)jsonObj.get("E"));
                	vo.setTmRecvYn((String)jsonObj.get("TM_RECV_YN"));
                	vo.setSmsRecvYn((String)jsonObj.get("SMS_RECV_YN"));
                	vo.setDmRecvYn((String)jsonObj.get("DM_RECV_YN"));
                	vo.setEmailRecvYn((String)jsonObj.get("EMAIL_RECV_YN"));
                	vo.setTmOfferYn((String)jsonObj.get("TM_OFFER_YN"));
                	vo.setDmOfferYn((String)jsonObj.get("DM_OFFER_YN"));
                	vo.setEmailOfferYn((String)jsonObj.get("EMAIL_OFFER_YN"));
                }
            }
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StatisticsVO>());
        }
        
        return response;
    }
    
    /**
     *  [IMR] 일별 통계:: 일별 통계 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDayProInfoTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDayProInfoTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmDayProInfoTotRowCnt(paramVO);
        	if(one != null) {
        		response.setTotRowCnt(one.getTotRowCnt());
        	}
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 일별 통계:: 일별 통계 현황 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmDayProInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmDayProInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmDayProInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StatisticsVO>());
        }
        
        return response;
    }
    
    /**
     *  [IMR] 월별 통계:: 월별 통계 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmMonthProInfoTotRowCnt.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmMonthProInfoTotRowCnt(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
        	StatisticsVO one = dpmService.getDpmMonthProInfoTotRowCnt(paramVO);
        	if(one != null) {
        		response.setTotRowCnt(one.getTotRowCnt());
        	}
        	
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 월별 통계:: 일별 통계 현황 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getDpmMonthProInfo.do")
    @ResponseBody
    public ResponseStatisticsVo getDpmMonthProInfo(StatisticsVO paramVO) {
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
        
        try {
            List<StatisticsVO> list = dpmService.getDpmMonthProInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<StatisticsVO>());
        }
        
        return response;
    }
    
    
    
    /**
     *  [IMR] 월별 통계:: 월별 통계 전체 cnt 조회
     *  2022.12.08 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmImrResViewerInfoTotRowCnt.do")
    @ResponseBody
    public ResponseCalibVerifiVo getdpmImrResViewerInfoTotRowCnt(CalibVerifiVo paramVO) {
    	ResponseCalibVerifiVo response = new ResponseCalibVerifiVo();
        
        try {
        	CalibVerifiVo one = dpmService.getdpmImrResViewerInfoTotRowCnt(paramVO);
        	if(one != null) {
        		response.setTotRowCnt(one.getTotRowCnt());
        	}
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  [IMR] 결과 열람자 이력 조회 화면
     *  2022.12.08 신규 개발 
     * @param CalibVerifiVo
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmImrResViewerInfo.do")
    @ResponseBody
    public ResponseCalibVerifiVo getdpmImrResViewerInfo(CalibVerifiVo paramVO) {
    	ResponseCalibVerifiVo response = new ResponseCalibVerifiVo();
        
        try {
            List<CalibVerifiVo> list = dpmService.getdpmImrResViewerInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<CalibVerifiVo>());
        }
        
        return response;
    }
    
    
    /**
     *  사용자 관리  전체 cnt 조회
     *  2022.12.12 신규 개발 
     * @param CalibVerifiVo
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmUserManageInfoTotRowCnt.do")
    @ResponseBody
    public ResponseUserManageVo getdpmUserManageInfoTotRowCnt(UserManageVo paramVO) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
        
        try {
        	UserManageVo one = dpmService.getdpmUserManageInfoTotRowCnt(paramVO);
        	if(one != null) {
        		response.setTotRowCnt(one.getTotRowCnt());
        	}
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     *  사용자 관리 조회
     *  2022.12.12 신규 개발 
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/getdpmUserManageInfo.do")
    @ResponseBody
    public ResponseUserManageVo getdpmUserManageInfo(UserManageVo paramVO) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
        
        try {
            List<UserManageVo> list = dpmService.getdpmUserManageInfo(paramVO);
            response.setSelList(list);
            response.setPageNumber(paramVO.getPageNumber());
            response.setTotPageCnt(paramVO.getTotPageCnt());
            response.setTotRowCnt(paramVO.getTotRowCnt());
            
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
            response.setSelList(new ArrayList<UserManageVo>());
        }
        
        return response;
    }
    
    /**
     * 사용자 정보 등록
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/insertUserInfo.do")
    @ResponseBody
    public ResponseUserManageVo insertUserInfo(UserManageVo paramVO,HttpServletRequest request) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
    	HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
        paramVO.setRgId(loginVO.getChrrId());
        try {
        	// 비밀번호 평문
        	String password = paramVO.getChrrPwd();
    		String hex = "";
    		
//    		// "SHA1PRNG"은 알고리즘 이름
//    		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//    		byte[] bytes = new byte[16];
//    		random.nextBytes(bytes);
//    		// SALT 생성
//    		String salt = new String(Base64.getEncoder().encode(bytes));
//    		String rawAndSalt = password+salt;
//    		
//    		System.out.println("raw : "+password);
//    		System.out.println("salt : "+salt);
    		
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
    		// 평문 암호화
    		md.update(password.getBytes());
    		hex = String.format("%064x", new BigInteger(1, md.digest()));
    		
//    		// 평문+salt 암호화
//    		md.update(rawAndSalt.getBytes());
//    		hex = String.format("%064x", new BigInteger(1, md.digest()));
//    		System.out.println("raw+salt의 해시값 : "+hex);
            
            paramVO.setChrrPwd(hex);
        	dpmService.insertUserInfo(paramVO);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     * 사용자 정보 수정
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/updateUserInfo.do")
    @ResponseBody
    public ResponseUserManageVo updateUserInfo(UserManageVo paramVO,HttpServletRequest request) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
    	HttpSession session = request.getSession();
        LoginChrrVO loginVO = (LoginChrrVO) session.getAttribute("loginInfo");
        paramVO.setRgId(loginVO.getChrrId());
        try {
        	if(paramVO.getChrrPwd() != null && paramVO.getChrrPwd() !="") {
        		// 비밀번호 평문
            	String password = paramVO.getChrrPwd();
        		String hex = "";
        		MessageDigest md = MessageDigest.getInstance("SHA-256");
        		// 평문 암호화
        		md.update(password.getBytes());
        		hex = String.format("%064x", new BigInteger(1, md.digest()));
        		 paramVO.setChrrPwd(hex);
        	}
        	dpmService.updateUserInfo(paramVO);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }    
    
    /**
     * 사용자 정보 삭제
     * @param paramVO
     * @return
     */
    @RequestMapping(value = "/dpm/deleteUserInfo.do")
    @ResponseBody
    public ResponseUserManageVo deleteUserInfo(UserManageVo paramVO,HttpServletRequest request) {
    	ResponseUserManageVo response = new ResponseUserManageVo();
        try {
        	dpmService.deleteUserInfo(paramVO);
        } catch(Exception e) {
            e.printStackTrace();
            response.setRsYn("N");
        }
        
        return response;
    }
    
    
    /**
     * 일별 통계 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDpmDayProExcel.do")
    public void selListDpmDayProExcel(StatisticsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<StatisticsVO> list  = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_일별 통계.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	 String title = "년/월/일,대상,처리현황,'','','',처리율,검증현황,'','',금융안내,'',금융이외,'',보험제공,'',딜러제공,'',KB제공,'',수집-전화,'',수집-문자,'',수집-DM,'',수집-메일,'',제공-전화,'',제공-DM,'',제공-메일,'',제공-문자,''";
    	StatisticsVO one = dpmService.getDpmDayProInfoTotRowCnt(paramVO);
    	
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<StatisticsVO> listPage = dpmService.getDpmDayProInfo(paramVO);
    		list.addAll(listPage);
    	}

    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames",  paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","Y");
    	modelMap.put("mergeTitle", title);
    	modelMap.put("VO", "StatisticsVO");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
        
    }
    
    
    
    /**
     * 사용자 관리 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListUserInfoExcel.do")
    public void selListUserInfoExcel(UserManageVo paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	List<UserManageVo> list = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_사용자 정보.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	UserManageVo one = dpmService.getdpmUserManageInfoTotRowCnt(paramVO);
    	
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;	    	
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<UserManageVo> listPage = dpmService.getdpmUserManageInfo(paramVO);
    		list.addAll(listPage);
    	}
    	
    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames", paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","N");
    	modelMap.put("VO", "UserManageVo");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
        
    }
    
    
    /**
     * 월별 통계 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDpmMonthProExcel.do")
    public void selListDpmMonthProExcel(StatisticsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<StatisticsVO> list  = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_월별 통계.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	StatisticsVO one = dpmService.getDpmMonthProInfoTotRowCnt(paramVO);
    	String title = "년/월,대상,처리현황,'','','',처리율,검증현황,'','',금융안내,'',금융이외,'',보험제공,'',딜러제공,'',KB제공,'',수집-전화,'',수집-문자,'',수집-DM,'',수집-메일,'',제공-전화,'',제공-DM,'',제공-메일,'',제공-문자,''";
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<StatisticsVO> listPage = dpmService.getDpmMonthProInfo(paramVO);
    		list.addAll(listPage);
    	}
    	
    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames",  paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","Y");
    	modelMap.put("mergeTitle", title);
    	modelMap.put("VO", "StatisticsVO");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
    	
        
    }
    
    
    /**
     * 일일 처리 현황 > 엑셀 출력
     * @param paramVO
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/selListDpmDailyProExcel.do")
    public void selListDpmDailyProExcel(StatisticsVO paramVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {    	
    	
    	List<StatisticsVO> list  = new ArrayList<>();    	
    	CommonVO commonVO 		 = getServerDateTime();
    	String filename 		 = commonVO.getServerTime().concat("_일일 처리 현황.xlsx");    	
    	setExcelDownloadHeader(request, response, filename);
    	StatisticsVO one = dpmService.getDpmDailyProInfoTotRowCnt(paramVO);
    	int pageSize   = 10000;
    	int totRowCnt  = one.getTotRowCnt() ;
    	int totPageCnt = (int) Math.floor(totRowCnt/pageSize)+1;
    	paramVO.setPageSize(pageSize);
    	
    	for(int pageNumber = 1; pageNumber <= totPageCnt; pageNumber++) {
    		paramVO.setPageNumber(pageNumber);
    		List<StatisticsVO> listPage = dpmService.getDpmDailyProInfo(paramVO);
    		 for(StatisticsVO vo : listPage) {
                 if(vo.getIntvisionImr() != null && !vo.getIntvisionImr().isEmpty()) {
                 	//json string data 파싱하기
                 	String json = vo.getIntvisionImr(); 
                 	JSONParser parser = new JSONParser();
                 	Object obj = parser.parse(json);
                 	JSONObject jsonObj = (JSONObject) obj;
                 	System.out.println(jsonObj);
                 	
                 	vo.setAyn((String)jsonObj.get("A"));
                 	vo.setByn((String)jsonObj.get("B"));
                 	vo.setCyn((String)jsonObj.get("C"));
                 	vo.setDyn((String)jsonObj.get("D"));
                 	vo.setEyn((String)jsonObj.get("E"));
                 	vo.setTmRecvYn((String)jsonObj.get("TM_RECV_YN"));
                 	vo.setSmsRecvYn((String)jsonObj.get("SMS_RECV_YN"));
                 	vo.setDmRecvYn((String)jsonObj.get("DM_RECV_YN"));
                 	vo.setEmailRecvYn((String)jsonObj.get("EMAIL_RECV_YN"));
                 	vo.setTmOfferYn((String)jsonObj.get("TM_OFFER_YN"));
                 	vo.setDmOfferYn((String)jsonObj.get("DM_OFFER_YN"));
                 	vo.setEmailOfferYn((String)jsonObj.get("EMAIL_OFFER_YN"));
                 	vo.setSmsOfferYn((String)jsonObj.get("SMS_OFFER_YN"));
                 }
             }
    		list.addAll(listPage);
    	}
    	
    	modelMap.put("gridLabels", paramVO.getGridLabels());
    	modelMap.put("gridNames",  paramVO.getGridNames());
    	modelMap.put("gridWidths", paramVO.getGridWidths());
    	modelMap.put("headerMergeYn","N");
    	modelMap.put("VO", "StatisticsVO");
    	modelMap.put("excelList", list);
    	
    	excelDownload(modelMap,request,response);
    	
        
    }
    
    
    @SuppressWarnings("unchecked")
	protected final void excelDownload(Map<String,Object> model, HttpServletRequest request , HttpServletResponse response) throws Exception {
    	logger.debug("excelDownload start!!!!");
    	List<Object> rowList  = new ArrayList<>();    	
    	String gridLabels    = (String) model.get("gridLabels");
        String gridNames     = (String) model.get("gridNames");
        String gridWidths    = (String) model.get("gridWidths");
        String headerMergeYn = (String) model.get("headerMergeYn");
        rowList = (ArrayList<Object>) model.get("excelList");
        String vo =  (String) model.get("VO");
        Class<?> voClass = Class.forName("com.minervasoft.backend.vo." + vo);                        
        List<Method> methodList = new ArrayList<Method>();            
        String[] nameList = gridNames.split(",");
        
        for(String name : nameList) {
        	String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        		methodList.add(voClass.getMethod(methodName,null));
        }
        
        try(Workbook workbook = new XSSFWorkbook()) {
        	
            Sheet sheet = workbook.createSheet();
            String[] widthList = gridWidths.split(",");
            for(int i=0; i<widthList.length; i++) {
            	sheet.setColumnWidth(i, Integer.parseInt(widthList[i]) * 50);
            }
            
            int rowNo = 0;
            Row headerRow;
            
            if(headerMergeYn =="Y") {
            	 headerRow= sheet.createRow(rowNo++);
            	 String title = (String) model.get("mergeTitle");
            	 String[] titleList = title.split(",");
            	 //셀 병합
                sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));  //일자
                sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));  //대상
                sheet.addMergedRegion(new CellRangeAddress(0,0,2,5));  //처리현황
                sheet.addMergedRegion(new CellRangeAddress(0,1,6,6));  //처리율
                sheet.addMergedRegion(new CellRangeAddress(0,0,7,9));  //검증현황
                sheet.addMergedRegion(new CellRangeAddress(0,0,10,11));  //금융안내
                sheet.addMergedRegion(new CellRangeAddress(0,0,12,13));//금융이외
                sheet.addMergedRegion(new CellRangeAddress(0,0,14,15));//보험제공
                sheet.addMergedRegion(new CellRangeAddress(0,0,16,17));//딜러제공
                sheet.addMergedRegion(new CellRangeAddress(0,0,18,19));//KB제공
                sheet.addMergedRegion(new CellRangeAddress(0,0,20,21));//수집-전화
                sheet.addMergedRegion(new CellRangeAddress(0,0,22,23));//수집-문자
                sheet.addMergedRegion(new CellRangeAddress(0,0,24,25));//수집-DM
                sheet.addMergedRegion(new CellRangeAddress(0,0,26,27));//수집-메일
                sheet.addMergedRegion(new CellRangeAddress(0,0,28,29));//제공-전화
                sheet.addMergedRegion(new CellRangeAddress(0,0,30,31));//제공-DM
                sheet.addMergedRegion(new CellRangeAddress(0,0,32,33));//제공-메일
                sheet.addMergedRegion(new CellRangeAddress(0,0,34,35));//제공-문자
                int headNo= 0;
                //merge header 생성
                for(String name : titleList) {
                	if(name != "") {
                		headerRow.createCell(headNo).setCellValue(name);
                	}else {
                		headerRow.createCell(headNo);
                	}
                	headNo++;
                }
            }
            
            String[] labelList = gridLabels.split(",");
            headerRow = sheet.createRow(rowNo++);
	    	if(labelList != null) {
	    		for(int i=0; i<labelList.length; i++) {
	    			Cell cell = headerRow.createCell(i);
	    			cell.setCellValue(labelList[i]);
	    		}
	    	}
	    	
	    	if(rowList != null) {
            	for(int i = 0; i < rowList.size(); i++) {
            		Row aRow = (Row) sheet.createRow(rowNo++);
            		setEachRow(aRow, rowList.get(i), methodList);
            	}
            		
            }
     
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch(TypeException e) {
        	e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 행 데이터 생성
     * @param aRow
     * @param vo
     * @param methodList
     * @throws Exception
     */
    private void setEachRow(Row aRow, Object vo, List<Method> methodList) throws Exception {
    	for(int i=0; i<methodList.size(); i++) {
    		Cell cell = aRow.createCell(i);
    		if(!methodList.get(i).invoke(vo).equals(null)) {
    			if(methodList.get(i).getGenericReturnType().getTypeName().contains("String")) {
    				String val = methodList.get(i).invoke(vo).toString();
        			cell.setCellValue(val);
    			}else {
    				Integer val = Integer.parseInt(methodList.get(i).invoke(vo).toString());
    				cell.setCellValue(val);
    			}
    		}
    	}
    }	
    
    
    
    /**
     * 일일 처리 통계 배치
     * @param paramVO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/dpmBatchStart.do")
    public ResponseStatisticsVo dpmBatchStart(StatisticsVO paramVO, ModelMap modelMap) throws Exception {    	
    	logger.debug("일일 처리 통계 배치 시작");
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
    	try {
			StatisticsVO info = dpmService.getDpmBatchInfo();
			if(info == null) {
				response.setRsYn("N");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
    
    /**
     * 일일 처리 통계 배치 건수 조회
     * @param paramVO
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dpm/getBatchTotCnt.do")
    public ResponseStatisticsVo getBatchTotCnt(StatisticsVO paramVO, ModelMap modelMap) throws Exception {    	
    	ResponseStatisticsVo response = new ResponseStatisticsVo();
    	try {
    		StatisticsVO one = dpmService.getBatchTotCnt();
			if(one.getTotRowCnt() <= 0) {
				response.setRsYn("N");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
    
    
     
}
