package demo.tool.service;

import java.util.List;
import java.util.Properties;

public interface MailService {

	void sendMailWithAttachment(String sendTo, String title, String context, List<String> attachmentPathList, Properties properties);

	void sendMailWithAttachment(String sendTo, String title, String context, String attachmentPath,
			Properties properties);

	void sendErrorMail(String errorMessage);


}
