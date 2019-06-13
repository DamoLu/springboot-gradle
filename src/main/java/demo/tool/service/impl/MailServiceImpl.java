package demo.tool.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.pojo.constant.ResourceConstant;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.tool.pojo.type.MailType;
import demo.tool.service.MailService;
import emailHandle.mailService.send.SendEmail;
import ioHandle.FileUtilCustom;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private SystemConstantService systemConstantService;

//	@Autowired
//	private MailRecordMapper mailRecordMapper;

	private boolean isMailReady() {
		if (systemConstantService.hasKey(SystemConstantStore.adminMailName)
				&& systemConstantService.hasKey(SystemConstantStore.adminMailPwd)) {
			return true;
		} else {
			systemConstantService
					.getValsByName(Arrays.asList(SystemConstantStore.adminMailName, SystemConstantStore.adminMailPwd));
			if (systemConstantService.hasKey(SystemConstantStore.adminMailName)
					&& systemConstantService.hasKey(SystemConstantStore.adminMailPwd)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public CommonResult sendSimpleMail(Long userId, String sendTo, String title, String content, String mailKey,
			MailType mailType) {
		CommonResult result = new CommonResult();
		if (userId == null || mailType == null || mailType.getValue() == null) {
			result.failWithMessage(ResultType.nullParam.getName());
			return result;
		}
		if (!isMailReady()) {
			result.failWithMessage(ResultType.mailBaseOptionError.getName());
			return result;
		}

		FileUtilCustom ioUtil = new FileUtilCustom();
		Resource resource = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
		Properties properties = null;
		try {
			properties = ioUtil.getPropertiesFromFile(resource.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
			result.failWithMessage(ResultType.mailPropertiesError.getName());
			return result;
		}

		SendEmail sm = new SendEmail();
		sm.sendMail(systemConstantService.getValByName(SystemConstantStore.adminMailName),
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), Arrays.asList(sendTo), null,
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)), title, content,
				null, properties);

		result.successWithMessage(mailKey);
		return result;
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, List<String> attachmentPathList,
			Properties properties) {
		if (!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(systemConstantService.getValByName(SystemConstantStore.adminMailName),
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), Arrays.asList(sendTo), null,
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)), title, content,
				attachmentPathList, properties);
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, String attachmentPath,
			Properties properties) {
		if (!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(systemConstantService.getValByName(SystemConstantStore.adminMailName),
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), Arrays.asList(sendTo), null,
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)), title, content,
				Arrays.asList(attachmentPath), properties);
	}

	@Override
	public void sendErrorMail(String errorMessage) {
		if (!isMailReady()) {
			return;
		}

		FileUtilCustom ioUtil = new FileUtilCustom();
		Resource resource = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
		Properties properties = null;
		try {
			properties = ioUtil.getPropertiesFromFile(resource.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		SendEmail sm = new SendEmail();
		sm.sendMail(systemConstantService.getValByName(SystemConstantStore.adminMailName),
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd),
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)), null, null,
				("error : " + LocalDateTime.now().toString()), errorMessage, null, properties);

	}

}
