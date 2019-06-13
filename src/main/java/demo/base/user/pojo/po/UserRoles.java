package demo.base.user.pojo.po;

import java.util.Date;

public class UserRoles {
	private Long id;

	private Long userId;

	private Integer roleId;

	private Date createTime;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRole() {
		return id;
	}

	public void setRole(Long userRoleId) {
		this.id = userRoleId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UserRoles [id=" + id + ", userId=" + userId + ", roleId=" + roleId + ", createTime=" + createTime + "]";
	}
}