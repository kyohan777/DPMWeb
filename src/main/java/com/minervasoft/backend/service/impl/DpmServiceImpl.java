package com.minervasoft.backend.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.minervasoft.backend.dao.DpmDAO;
import com.minervasoft.backend.service.DpmService;
import com.minervasoft.backend.vo.AgentAssignVO;
import com.minervasoft.backend.vo.BizStatsTodayVO;
import com.minervasoft.backend.vo.BizStatsVO;
import com.minervasoft.backend.vo.CalibVerifiVo;
import com.minervasoft.backend.vo.ChrrGroupAuthVO;
import com.minervasoft.backend.vo.ChrrVO;
import com.minervasoft.backend.vo.CodeVO;
import com.minervasoft.backend.vo.DailyStatsVO;
import com.minervasoft.backend.vo.GroupAuthVO;
import com.minervasoft.backend.vo.ImageVerifyVO;
import com.minervasoft.backend.vo.LoginChrrVO;
import com.minervasoft.backend.vo.MaskingHistoryVO;
import com.minervasoft.backend.vo.MenuAuthVO;
import com.minervasoft.backend.vo.MenuVO;
import com.minervasoft.backend.vo.MonthlyStatsVO;
import com.minervasoft.backend.vo.StatisticsVO;
import com.minervasoft.backend.vo.StepStatsVO;
import com.minervasoft.backend.vo.UserManageVo;
import com.minervasoft.backend.vo.XtromDailyStatsVO;

@Service("DpmService")
public class DpmServiceImpl implements DpmService {
    /********************************************* 
     * 로그인 및 공통  
     *********************************************/	
    @Resource(name = "DpmDAO")
    private DpmDAO dpmDao;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public LoginChrrVO selOneLoginChrr(LoginChrrVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selOneLoginChrr(paramVO);
    }    
    
    /*
    @Override
    public List<MenuAuthVO> selListMenuAuth(MenuAuthVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMenuAuth(paramVO);
    }

    @Override
    public List<CodeVO> selListCode(CodeVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListCode(paramVO);
    }    
    */
    
    /********************************************* 
     * 마스킹검증 
     *********************************************/
    /*
    @Override
    public ImageVerifyVO selOneImageVerifyTotRowCnt(ImageVerifyVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selOneImageVerifyTotRowCnt(paramVO);
    }    
    
    @Override
    public List<ImageVerifyVO> selListImageVerify(ImageVerifyVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListImageVerify(paramVO);
    }
    
    @Override
    public int updFpMaskObj(ImageVerifyVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.updFpMaskObj(paramVO);
    }
    
    @Override
    public int insFpMaskHis(MaskingHistoryVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.insFpMaskHis(paramVO);
    }     
    
    @Override
    public List<AgentAssignVO> selListAgentAssign(AgentAssignVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListAgentAssign(paramVO);
    }
    
    @Override
    public int updAgentAssign(AgentAssignVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.updAgentAssign(paramVO);
    }
    
    @Override
    public int saveChrrForAgent(AgentAssignVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveChrrForAgent(paramVO);
    }
    
	@Override
	public int deleteAgent(AgentAssignVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.deleteAgent(paramVO);
	}
    */
    /********************************************* 
     * 통계  
     *********************************************/
    /*
    @Override
    public List<BizStatsVO> selListBizStats() throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListBizStats();
    }

    @Override
    public List<BizStatsTodayVO> selListBizStatsToday() throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListBizStatsToday();
    }
    
    @Override
    public List<DailyStatsVO> selListDailyStats(DailyStatsVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListDailyStats(paramVO);
    }

    @Override
    public List<MonthlyStatsVO> selListMonthlyStats(MonthlyStatsVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMonthlyStats(paramVO);
    }
    
    @Override
    public MaskingHistoryVO selOneMaskingHistoryTotRowCnt(MaskingHistoryVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selOneMaskingHistoryTotRowCnt(paramVO);
    }    
    
    @Override
    public List<MaskingHistoryVO> selListMaskingHistory(MaskingHistoryVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMaskingHistory(paramVO);
    }
    
    @Override
    public List<MaskingHistoryVO> selListMaskingHistoryDetail(MaskingHistoryVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMaskingHistoryDetail(paramVO);
    }    
    */
    
    /********************************************* 
     * 관리
     *********************************************/
    /*
    @Override
    public List<CodeVO> selListCodeForManagement(CodeVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListCodeForManagement(paramVO);
    }

    @Override
    public List<CodeVO> selListCodeDetailForManagement(CodeVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListCodeDetailForManagement(paramVO);
    }
    
    @Override
    public int saveCode(CodeVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveCode(paramVO);
    }
    
	@Override
	public int deleteCode(CodeVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.deleteCode(paramVO);
	}
    
    @Override
    public int saveDetailCode(CodeVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveDetailCode(paramVO);
    }
    
	@Override
	public int deleteDetailCode(CodeVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.deleteDetailCode(paramVO);
	}
    
    @Override
    public List<ChrrVO> selListChrrForManagement(ChrrVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListChrrForManagement(paramVO);
    }
    
    @Override
    public int saveChrrForMng(ChrrVO paramVO) throws Exception {
        // TODO Auto-generated method stub
    	return dpmDao.saveChrrForMng(paramVO);
    	
    }
    
    @Override
    public List<ChrrVO> selListChrr() throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListChrr();
    }    
    
    @Override
    public List<GroupAuthVO> selListGroupAuth() throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListGroupAuth();
    }

    @Override
    public List<ChrrGroupAuthVO> selListChrrGroupAuth(ChrrGroupAuthVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListChrrGroupAuth(paramVO);
    }
    
    @Override
    public int saveGroupAuth(GroupAuthVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveGroupAuth(paramVO);
    }
    
    @Override
    public int saveChrrGroupAuth(ChrrGroupAuthVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveChrrGroupAuth(paramVO);
    }
    
    @Override
    public List<GroupAuthVO> selListAuth(GroupAuthVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListAuth(paramVO);
    }    
    
    @Override
    public List<MenuAuthVO> selListMenuAuthForManagement(MenuAuthVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMenuAuthForManagement(paramVO);
    }
    
    @Override
    public int saveMenuAuth(MenuAuthVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveMenuAuth(paramVO);
    }     
    
    @Override
    public List<MenuVO> selListMenu(MenuVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMenu(paramVO);
    }
    
    @Override
    public List<MenuVO> selListMenuForManagement(MenuVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListMenuForManagement(paramVO);
    }
    
    @Override
    public int saveMenu(MenuVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.saveMenu(paramVO);
    } 
    
    @Override
    public int deleteMenu(MenuVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.deleteMenu(paramVO);
    } 
    
    @Override
    public int deleteChrrForMng(ChrrVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.deleteChrrForMng(paramVO);
    } 
    
    @Override
    public int deleteChrrGroupAuth(ChrrGroupAuthVO paramVO) throws Exception {
    	// TODO Auto-generated method stub
        return dpmDao.deleteChrrGroupAuth(paramVO);
    } 
    */
    /********************************************* 
     * 기타
     *********************************************/         
    /*
    @Override
    public ChrrVO selOneChrr(ChrrVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selOneChrr(paramVO);
    }

    @Override
    public List<MenuVO> selListChrrMenu(ChrrVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListChrrMenu(paramVO);
    }

    @Override
    public List<StepStatsVO> selListStepStats() throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListStepStats();
    }

    @Override
    public List<XtromDailyStatsVO> selListXtormDailyStats(XtromDailyStatsVO paramVO) throws Exception {
        // TODO Auto-generated method stub
        return dpmDao.selListXtormDailyStats(paramVO);
    }
	*/

    /********************************************* 
     * 분리보관
     *********************************************/
    
    
    /************************************************
     * 2022.12 신규 개발 KSM
     ************************************************/
    //일별 통계 조회
    @Override
	public List<StatisticsVO> getDpmDayProInfo(StatisticsVO paramVO) throws Exception {
		  // TODO Auto-generated method stub
        return dpmDao.getDpmDayProInfo(paramVO);
	}
    //일별 통계 전체 cnt 조회
	@Override
	public StatisticsVO getDpmDayProInfoTotRowCnt(StatisticsVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.getDpmDayProInfoTotRowCnt(paramVO);
	}

	@Override
	public List<StatisticsVO> getDpmMonthProInfo(StatisticsVO paramVO) throws Exception {
		 // TODO Auto-generated method stub
         return dpmDao.getDpmMonthProInfo(paramVO);
	}

	@Override
	public StatisticsVO getDpmMonthProInfoTotRowCnt(StatisticsVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.getDpmMonthProInfoTotRowCnt(paramVO);
	}

	@Override
	public List<CalibVerifiVo> getdpmImrResViewerInfo(CalibVerifiVo paramVO) throws Exception {
		// TODO Auto-generated method stub
				return dpmDao.getdpmImrResViewerInfo(paramVO);
	}

	@Override
	public CalibVerifiVo getdpmImrResViewerInfoTotRowCnt(CalibVerifiVo paramVO) throws Exception {
		// TODO Auto-generated method stub
				return dpmDao.getdpmImrResViewerInfoTotRowCnt(paramVO);
	}
	
	@Override
	public List<UserManageVo> getdpmUserManageInfo(UserManageVo paramVO) throws Exception {
		// TODO Auto-generated method stub
				return dpmDao.getdpmUserManageInfo(paramVO);
	}

	@Override
	public UserManageVo getdpmUserManageInfoTotRowCnt(UserManageVo paramVO) throws Exception {
		// TODO Auto-generated method stub
				return dpmDao.getdpmUserManageInfoTotRowCnt(paramVO);
	}

	@Override
	public int insertUserInfo(UserManageVo paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.insertUserInfo(paramVO);
	}

	@Override
	public int updateUserInfo(UserManageVo paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.updateUserInfo(paramVO);
	}

	@Override
	public int deleteUserInfo(UserManageVo paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.deleteUserInfo(paramVO);
	}

	@Override
	public List<StatisticsVO> getDpmDailyProInfo(StatisticsVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.getDpmDailyProInfo(paramVO);
	}

	@Override
	public StatisticsVO getDpmDailyProInfoTotRowCnt(StatisticsVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.getDpmDailyProInfoTotRowCnt(paramVO);
	}
	
	
	@Override
	public List<StatisticsVO> getDpmCalibVerifiInfo(StatisticsVO paramVO) throws Exception {
		// TODO Auto-generated method stub
		return dpmDao.getDpmDailyProInfo(paramVO);
	}
	
	//일일 처리 통계 배치 
	@Override
	public StatisticsVO getDpmBatchInfo() throws Exception {
		List<StatisticsVO> list = dpmDao.getDpmBatchInfo();
		StatisticsVO statisticInfo = new StatisticsVO(); 
		if(list.size()>0) {
			for(StatisticsVO vo : list) {
	            if(vo.getIntvisionImr() != null) {
	            	logger.debug("INTVISION_IMR : "+vo.getIntvisionImr());
	            	//json string data 파싱하기
	            	String json = vo.getIntvisionImr(); 
	            	JSONParser parser = new JSONParser();
	            	Object obj = parser.parse(json);
	            	JSONObject jsonObj = (JSONObject) obj;
	            	statisticInfo.setPrcDt(vo.getPrcDt());
	            	statisticInfo.setPrcDtCnt(statisticInfo.getPrcDtCnt()+1);  					//대상건수
	            	statisticInfo.setPrcCn(statisticInfo.getPrcCn()+(Integer) vo.getPrcCn());	//처리건수
	            	statisticInfo.setErrCn(statisticInfo.getErrCn()+(Integer) vo.getErrCn());	//오류건수
	            	statisticInfo.setVerifyCn(statisticInfo.getVerifyCn()+(Integer) vo.getVerifyCn());//검증건수
	            	statisticInfo.setVerifyUpdateCn(statisticInfo.getVerifyUpdateCn()+(Integer) vo.getVerifyUpdateCn());//수정건수
	            	if(jsonObj.get("A").equals("Y")) {
	            		statisticInfo.setAy(statisticInfo.getAy()+1);
	            	}
	            	if(jsonObj.get("B").equals("Y")) {
	            		statisticInfo.setBy(statisticInfo.getBy()+1);
	            	}
	            	if(jsonObj.get("C").equals("Y")) {
	            		statisticInfo.setCy(statisticInfo.getCy()+1);
	            	}
	            	if(jsonObj.get("D").equals("Y")) {
	            		statisticInfo.setDy(statisticInfo.getDy()+1);
	            	}
	            	if(jsonObj.get("E").equals("Y")) {
	            		statisticInfo.setEy(statisticInfo.getEy()+1);
	            	}
	            	if(jsonObj.get("TM_RECV_YN").equals("Y")) {
	            		statisticInfo.setTmRecvY(statisticInfo.getTmRecvY()+1);
	            	}
	            	if(jsonObj.get("SMS_RECV_YN").equals("Y")) {
	            		statisticInfo.setSmsRecvY(statisticInfo.getSmsRecvY()+1);	
	            	}
	            	if(jsonObj.get("DM_RECV_YN").equals("Y")) {
	            		statisticInfo.setDmRecvY(statisticInfo.getDmRecvY()+1);	
	            	}
	            	if(jsonObj.get("EMAIL_RECV_YN").equals("Y")) {
	            		statisticInfo.setEmailRecvY(statisticInfo.getEmailRecvY()+1);	
	            	}
	            	if(jsonObj.get("TM_OFFER_YN").equals("Y")) {
	            		statisticInfo.setTmOfferY(statisticInfo.getTmOfferY()+1);
	            	}
	            	if(jsonObj.get("DM_OFFER_YN").equals("Y")) {
	            		statisticInfo.setDmOfferY(statisticInfo.getDmOfferY()+1);	
	            	}
	            	if(jsonObj.get("EMAIL_OFFER_YN").equals("Y")) {
	            		statisticInfo.setEmailOfferY(statisticInfo.getEmailOfferY()+1);	
	            	}
	            	if(jsonObj.get("SMS_OFFER_YN").equals("Y")) {
	            		statisticInfo.setSmsOfferY(statisticInfo.getSmsOfferY()+1);	
	            	}
	            }
	        }
		}else {
			statisticInfo = null;
		}
		return statisticInfo;
	}

	@Override
	public int insertDailyStatics(StatisticsVO paramVO) throws Exception {
		return dpmDao.insertDailyStatics(paramVO);
	}
	
	
	// 교정/검증 확정 처리
	@Override
    public int updImrConfirm(StatisticsVO paramVO) throws Exception {
		return dpmDao.updImrConfirm(paramVO);
    }
	
	@Override
	public int insertViewReason(CalibVerifiVo paramVO) throws Exception {
		return dpmDao.insertViewReason(paramVO);
	}
	

}
