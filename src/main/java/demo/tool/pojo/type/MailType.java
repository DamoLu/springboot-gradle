package demo.tool.pojo.type;

public enum MailType {
	/** 注册激活 */
	registActivation("registActivation", 1),
	/** 重置密码 */
	forgotPassword("forgotPassword", 2),
	/** 忘记用户名 */
	forgotUsername("forgotUsername", 3),
	;

	private String mailType;
	private Integer mailCode;

	private MailType(String mailType, Integer mailCode) {
		this.mailType = mailType;
		this.mailCode = mailCode;
	}

	public String getName() {
		return mailType;
	}

	public Integer getValue() {
		return mailCode;
	}
}
