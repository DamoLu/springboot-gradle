package demo.task.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.user.mapper.UsersMapper;
import demo.task.service.TaskToolService;
import demo.tool.mapper.MailRecordMapper;

@Component
public class TaskToolServiceImpl implements TaskToolService {

	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
	
	/** 清理无效的错误登录记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanAttempts() {
		usersMapper.cleanAttempts(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 15)));
	}
	
	/** 清理过期或已读的邮件记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanMailRecord() {
		mailRecordMapper.cleanMailRecord(null);
	}
	
}
