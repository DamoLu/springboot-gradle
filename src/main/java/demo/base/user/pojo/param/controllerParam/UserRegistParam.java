package demo.base.user.pojo.param.controllerParam;

import demo.base.user.pojo.po.UserConstant;
import demo.baseCommon.pojo.param.CommonControllerParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

@ApiModel("用户注册参数")
public class UserRegistParam implements CommonControllerParam {

	@ApiModelProperty(name = "用户名", dataType = "string")
	private String userName;
	@ApiModelProperty(name = "用户昵称", dataType = "string")
	private String nickName;
	private String email;
	private String pwd;
	private String pwdRepeat;
	private Integer gender;
	private String qq;
	private String mobile;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdRepeat() {
		return pwdRepeat;
	}

	public void setPwdRepeat(String pwdRepeat) {
		this.pwdRepeat = pwdRepeat;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public UserRegistParam fromJson(JSONObject json) {
		UserRegistParam param = new UserRegistParam();
		if (json.containsKey("userName")) {
			param.setUserName(json.getString("userName").replaceAll("\\s", ""));
		}
		if (json.containsKey("nickName")) {
			param.setNickName(json.getString("nickName").replaceAll("\\s", ""));
		}
		if (json.containsKey("gender")) {
			if (UserConstant.male.toString().equals(json.getString("gender"))) {
				param.setGender(UserConstant.male);
			} else if (UserConstant.female.toString().equals(json.getString("gender"))) {
				param.setGender(UserConstant.female);
			} else {
				param.setGender(UserConstant.secret);
			}
		}
		if (json.containsKey("pwd")) {
			param.setPwd(json.getString("pwd"));
		}
		if (json.containsKey("pwdRepeat")) {
			param.setPwdRepeat(json.getString("pwdRepeat"));
		}
		if (json.containsKey("email")) {
			param.setEmail(json.getString("email").replaceAll("\\s", ""));
		}
		if (json.containsKey("qq")) {
			param.setQq(json.getString("qq").replaceAll("\\s", ""));
		}
		if (json.containsKey("mobile")) {
			param.setMobile(json.getString("mobile").replaceAll("\\s", ""));
		}

		return param;
	}

}
