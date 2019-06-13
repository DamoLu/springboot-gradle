package demo.base.user.mapper;

import java.util.List;

import demo.base.user.pojo.po.Roles;

public interface RolesMapper {
    int insert(Roles record);

    int insertSelective(Roles record);

	List<Roles> getRoleList();
}