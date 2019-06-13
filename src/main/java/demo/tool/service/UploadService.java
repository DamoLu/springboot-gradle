package demo.tool.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import demo.tool.pojo.result.UploadResult;

public interface UploadService {

	public UploadResult saveFiles(Map<String, MultipartFile> fileMap, String storePath);

}
