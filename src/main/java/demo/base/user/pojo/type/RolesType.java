package demo.base.user.pojo.type;

public enum RolesType {

	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", -4L),
	ROLE_DEV("ROLE_DEV", -3L),
	ROLE_DBA("ROLE_DBA", -2L),
	ROLE_ADMIN("ROLE_ADMIN", -1L),
	ROLE_USER("ROLE_USER", 1L),
	;
	
	private String roleName;
	private Long roleId;
	
	RolesType(String roleName, Long roleId) {
		this.roleName = roleName;
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public Long getId() {
		return roleId;
	}
	
	public static RolesType getRole(Long id) {
		for(RolesType role : RolesType.values()) {
			if(role.getId() == id) {
				return role;
			}
		}
		return null;
	}
	
	public static RolesType getRole(String roleName) {
		for(RolesType role : RolesType.values()) {
			if(role.getRoleName().equals(roleName)) {
				return role;
			}
		}
		return null;
	}
}
