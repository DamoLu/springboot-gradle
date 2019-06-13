package demo.base.user.mapper;

import java.util.List;

import demo.base.user.pojo.po.UserRoles;

public interface UserRolesMapper {
    int insert(UserRoles record);

    int insertSelective(UserRoles record);
    
    List<Long> findUserIdListByRoleId(Integer roleId);
    
    List<String> getRoleNameListByUserId(Long userId);
}