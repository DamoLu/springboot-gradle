package demo.tool.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import demo.tool.pojo.result.UploadResult;
import demo.tool.service.UploadService;
import ioHandle.FileUtilCustom;

@Service
public class UploadServiceImpl implements UploadService {

	@Override
	public UploadResult saveFiles(Map<String, MultipartFile> fileMap, String storePath) {
		FileUtilCustom io = new FileUtilCustom();
		MultipartFile tmpFile = null;
		UploadResult result = new UploadResult();
		List<String> uploadSuccessFileNameList = new ArrayList<String>();
		List<String> uploadFailFileNameList = new ArrayList<String>();
		
		boolean flag = true;
		
		for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
			flag = true;
			tmpFile = entry.getValue();
			
			try {
				io.byteToFile(tmpFile.getBytes(), storePath + tmpFile.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
				flag = false;
				uploadFailFileNameList.add(tmpFile.getOriginalFilename());
			}
			
			if(flag) {
				uploadSuccessFileNameList.add(tmpFile.getOriginalFilename());
			}
		}
		result.setUploadSuccessFileNameList(uploadSuccessFileNameList);
		result.setUploadFailFileNameList(uploadFailFileNameList);
		result.setUploadTime(new Date());
		result.setIsSuccess();
		return result;
	}
	
	

}
