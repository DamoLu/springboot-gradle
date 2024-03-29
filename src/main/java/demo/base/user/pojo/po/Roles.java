package demo.base.user.pojo.po;

public class Roles {

	private Integer roleId;
	private String role;
	private Boolean isDelete;

	@Override
	public String toString() {
		return "Roles [roleId=" + roleId + ", role=" + role + ", isDelete=" + isDelete + "]";
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role == null ? null : role.trim();
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
