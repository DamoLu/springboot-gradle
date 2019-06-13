package demo.base.user.service;

import java.util.ArrayList;
import java.util.List;

import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.param.controllerParam.OtherUserInfoParam;
import demo.base.user.pojo.param.controllerParam.UserRegistParam;
import demo.base.user.pojo.param.mapperParam.UserAttemptQuerayParam;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.baseCommon.pojo.result.CommonResult;


/**
 * @author Acorn
 * 2017年4月13日
 */
public interface UsersService {

	int insertFailAttempts(String userName);

	int setLockeds(Users user);
	
	int resetFailAttempts(String userName);

	Long getUserIdByUserName(String userName);
	
	ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayParam param);
	
	CommonResult newUserRegist(UserRegistParam param, String ip);

	boolean isUserExists(String userName);
	
	Users getUserbyUserName(String userName);

	UsersDetailVO findUserDetail(Long userId);
	String findHeadImageUrl(Long userId);

	int countAttempts(String userName);

	CommonResult resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword,
			String newPasswordRepeat);

	UsersDetailVO findOtherUserDetail(OtherUserInfoParam param);

	/** 查找持有某种权限的用户ID */
	List<Long> findUserIdListByRoleId(Integer roleId);

	MyUserPrincipal buildMyUserPrincipalByUserName(String userName);

}
