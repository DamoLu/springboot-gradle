package demo.base.user.pojo.vo;

import java.util.Date;

public class UsersDetailVO {

	private String nickName;

	private String gender;

	private Long qq;

	private String email;

	private Long mobile;

	private Date lastLoginTime;

	private String headImage;

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getQq() {
		return qq;
	}

	public void setQq(Long qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "UsersDetailVO [nickName=" + nickName + ", gender=" + gender + ", qq=" + qq + ", email=" + email
				+ ", mobile=" + mobile + ", lastLoginTime=" + lastLoginTime + ", headImage=" + headImage + "]";
	}

}
