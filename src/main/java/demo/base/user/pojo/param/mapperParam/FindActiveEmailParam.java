package demo.base.user.pojo.param.mapperParam;

import demo.base.user.pojo.type.RolesType;

public class FindActiveEmailParam {

	private String email;

	private Long roleId = RolesType.ROLE_USER.getId();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
