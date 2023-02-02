package com.minervasoft.backend.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.minerva.crypto.mCryptoApi;

import lombok.extern.slf4j.Slf4j;


/**
 * <b>암호화 모듈</b>
 * <p>
 * 파일암호화 D'amo 관련 모률  
 * 파일 암호화 및 암화화 하제 * 
 * </p>
 */
@Service
@Slf4j
public class MagicCryptoService {
	
		
	
    @Value("${crypto.magick}")
    private String magick;
	
    @Value("${crypto.magici}")
    private String magici;
    
    @Value("${crypto.magick}")
    private String magicp;
	
	public boolean DecryptFile(String sfile,String sDestFile)
	{	
		
   
		try {	
			if(sfile==sDestFile)throw new Exception("Decrypt Target Same With Origin ="+ sDestFile);
			Path srcPath = Paths.get(sfile);
			if (Files.notExists(srcPath, LinkOption.NOFOLLOW_LINKS))
			{
				log.error("Can't Find File :{} ",srcPath);
				return false;
			}			
			//아웃풋 폴더에 복사할 서브디렉토리 경로가 존재하지 않으면 생성 후, 복사
			Path tParent = Paths.get(sDestFile).getParent();
			if (Files.notExists(tParent, LinkOption.NOFOLLOW_LINKS)) 
			{			
				Files.createDirectories(tParent);		
			}			
			
			mCryptoApi m1 = new mCryptoApi();
			int error_code = 0;
			error_code = m1.MvCrypto_CreateObject(magici, magicp);
			if(error_code == 0)
			System.out.println("MvCrypto_CreateObject Error");
			
			error_code = m1.MvCrypto_SetKey(magick);
			if(error_code != 0)
			System.out.println("MvCrypto_SetKey error");
			
			error_code = m1.MvCrypto_DecFile2File(sfile, sDestFile);
			if(error_code != 0) {
				System.out.println("MvCrypto_DecFile2File error");
			}
			m1.MvCrypto_CloseObject();
			
			
			log.debug("DecryptFilePath {}->{}", sfile, sDestFile);
			if(error_code != 0) {
				log.error("MvCrypto_DecFile2File error {}", Files.size(srcPath));
				return false;
			}
			return true;
		    
		} 
		catch (Exception e) 
		{
			log.warn("Decrypt Error :{} ",e);			
		}
		
		return false;		 
	}
	
	public boolean EncryptFile(String sfile,String sDestFile)
	{	
		try {
			Path srcPath = Paths.get(sfile);
			if (Files.notExists(srcPath, LinkOption.NOFOLLOW_LINKS))
			{
				log.debug("Can't Find File :{} ",srcPath);				
				return false;
			}
			Path tParent = Paths.get(sDestFile).getParent();
			//아웃풋 폴더에 복사할 서브디렉토리 경로가 존재하지 않으면 생성 후, 복사
			if (Files.notExists(tParent, LinkOption.NOFOLLOW_LINKS)) 
			{
				Files.createDirectories(tParent);
			}
			
			mCryptoApi m1 = new mCryptoApi();
			int error_code = 0;
			error_code = m1.MvCrypto_CreateObject(magici, magicp);
			if(error_code == 0)
			System.out.println("MvCrypto_CreateObject Error");
			
			error_code = m1.MvCrypto_SetKey(magick);
			if(error_code != 0)
			System.out.println("MvCrypto_SetKey error");
			
			error_code = m1.MvCrypto_EncFile2File(sfile, sDestFile, 0);
			if(error_code != 0)
			System.out.println("MvCrypto_EncFile2File error");
			
			m1.MvCrypto_CloseObject();
						
			log.debug("[{}] AgentCipherEncryptFilePath {}->{}", sfile,sDestFile);
			if(error_code != 0) {
				log.error("MvCrypto_EncFile2File error {} -> {}", sfile,sDestFile);
				return false;
			}
			return true;
			
		}
		catch (Exception e) 
		{
			log.warn("Encrypt Error:{} ",e);	
		}
		
		return false;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		//ID : magicLock
		//PW : !@magicLock!@
		
		mCryptoApi m1 = new mCryptoApi();
		int error_code = 0;
		error_code = m1.MvCrypto_CreateObject("magicLock", "!@magicLock!@");
		if(error_code == 0)
		System.out.println("MvCrypto_CreateObject Error");
		
		error_code = m1.MvCrypto_SetKey("1234");
		if(error_code != 0)
		System.out.println("MvCrypto_SetKey error");
		
		
		File dir = new File("D:/KB캐피탈/법인세 자료/");
		File files[] = dir.listFiles();
		String[] filenames = dir.list();

		for (int i = 0; i < files.length; i++) {
		    System.out.println("file: " + files[i] + ", " + filenames[i]);
		    
		    error_code = m1.MvCrypto_EncFile2File(files[i] +"", "D:/KB캐피탈/법인세 자료_암호화/" + filenames[i], 0);
			if(error_code != 0)
			System.out.println("MvCrypto_EncFile2File error");
		}
		
		
		//error_code = m1.MvCrypto_DecFile2File("D:\\KB캐피탈\\법인세 자료\\adsdf_0_enc.jpg", "D:\\KB캐피탈\\법인세 자료_암호화\\adsdf_0_dec.jpg");
		//if(error_code != 0)
		//System.out.println("MvCrypto_DecFile2File error");
		m1.MvCrypto_CloseObject();
		
	}
	
}
