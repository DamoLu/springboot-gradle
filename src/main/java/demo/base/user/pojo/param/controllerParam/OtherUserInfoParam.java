package demo.base.user.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class OtherUserInfoParam implements CommonControllerParam {

	private String nickName;
	
	private String pk;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "OtherUserInfoParam [nickName=" + nickName + ", pk=" + pk + "]";
	}

	@Override
	public OtherUserInfoParam fromJson(JSONObject json) {
		OtherUserInfoParam param = new OtherUserInfoParam();
		if(json.containsKey("nickName")) {
			param.setNickName(json.getString("nickName").replaceAll("\\s", ""));
		}
		if(json.containsKey("pk")) {
			param.setPk(json.getString("pk"));
		}

		return param;
	}

}
