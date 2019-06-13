package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.user.mapper.RolesMapper;
import demo.base.user.pojo.po.Roles;
import demo.base.user.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RolesMapper rolesMapper;
	
	@Override
	public List<Roles> getRoleList() {
		
		List<Roles> roleList = rolesMapper.getRoleList();
		
		if(roleList == null || roleList.isEmpty()) {
			roleList = new ArrayList<Roles>();
		}
		
		return roleList;
		
	}
	
}
