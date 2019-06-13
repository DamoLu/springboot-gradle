package demo.base.user.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.param.mapperParam.ResetFailAttemptParam;
import demo.base.user.pojo.param.mapperParam.UserAttemptQuerayParam;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.UserRoles;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;

public interface UsersMapper {

	int insert(Users record);

	int insertSelective(Users record);

	int insertFailAttempts(String userName);

	int resetFailAttempts(ResetFailAttemptParam param);

	ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayParam param);

	int isUserExists(String userName);
	
	int cleanAttempts(@Param("dateInput")Date dateInput);
	
	int resetPassword(@Param("pwd")String pwd, @Param("pwdd")String pwdd, @Param("userId")Long userId);

	int lockUserWithAttempts(String userName);

	Users findUserByUserName(String userName);

	int setLockeds(Users user);

	int insertNewUser(Users user);

	int insertNewUserRole(UserRoles userRoles);

	Long getUserIdByUserName(String userName);
	Long getUserIdByUserNameOrEmail(String inputUserName);
	
	UsersDetail getUserDetailByUserName(String userName);
	
	Users findUser(Long userId);
	
	int countAttempts(String userName);
	
	int matchUserPassword(@Param("userId")Long userId, @Param("pwd")String pwd);
	
}