package demo.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.mapper.UserRolesMapper;
import demo.base.user.mapper.UsersDetailMapper;
import demo.base.user.mapper.UsersMapper;
import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.pojo.param.controllerParam.OtherUserInfoParam;
import demo.base.user.pojo.param.controllerParam.UserRegistParam;
import demo.base.user.pojo.param.mapperParam.ResetFailAttemptParam;
import demo.base.user.pojo.param.mapperParam.UserAttemptQuerayParam;
import demo.base.user.pojo.po.Roles;
import demo.base.user.pojo.po.UserAttempts;
import demo.base.user.pojo.po.UserConstant;
import demo.base.user.pojo.po.UserRoles;
import demo.base.user.pojo.po.Users;
import demo.base.user.pojo.po.UsersDetail;
import demo.base.user.pojo.type.RolesType;
import demo.base.user.pojo.vo.UsersDetailVO;
import demo.base.user.service.RoleService;
import demo.base.user.service.UsersService;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.config.costom_component.CustomPasswordEncoder;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

/**
 * @author Acorn 2017年4月13日
 */
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private SystemConstantService systemConstantService;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRolesMapper userRolesMapper;

	@Autowired
	private UsersDetailMapper usersDetailMapper;

	@Autowired
	private CustomPasswordEncoder passwordEncoder;

	
	@Override
	public int insertFailAttempts(String userName) {
		int insertCount = 0;
		
		if (!isUserExists(userName)) {
			return insertCount;
		}
		
		insertCount = usersMapper.insertFailAttempts(userName);
		UserAttemptQuerayParam param = new UserAttemptQuerayParam();
		param.setUserName(userName);
		int maxAttempts = Integer.parseInt(systemConstantService.getValByName(SystemConstantStore.maxAttempts));
		if (getUserAttempts(param).size() >= maxAttempts) {
			usersMapper.lockUserWithAttempts(userName);
		} 

		return insertCount;
		
	}

	@Override
	public int countAttempts(String userName) {
		if(StringUtils.isBlank(userName)) {
			return 0;
		}
		return usersMapper.countAttempts(userName);
	}
	
	@Override
	public int setLockeds(Users user) {
		return usersMapper.setLockeds(user);
	}

	@Override
	public int resetFailAttempts(String userName) {
		if(StringUtils.isBlank(userName)) {
			return 0;
		}
		ResetFailAttemptParam param = new ResetFailAttemptParam();
		param.setUserName(userName);
		return usersMapper.resetFailAttempts(param);
	}

	@Override
	public Long getUserIdByUserName(String userName) {
		if(StringUtils.isBlank(userName)) {
			return null;
		}
		return usersMapper.getUserIdByUserName(userName);
	}
	
	@Override
	public ArrayList<UserAttempts> getUserAttempts(UserAttemptQuerayParam param) {
		ArrayList<UserAttempts> userAttemptsList = usersMapper.getUserAttempts(param);
		return userAttemptsList;
	}

	@Override
	public boolean isUserExists(String userName) {

		boolean result = false;

		int count = usersMapper.isUserExists(userName);

		if (count > 0) {
			result = true;
		}

		return result;
	}

	@Override
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CommonResult newUserRegist(UserRegistParam param, String ip) {
		CommonResult result = new CommonResult();
		UsersDetail userDetail = new UsersDetail();
		JSONObject outputJson = new JSONObject();
		boolean exceptionFlag = false;

		if (!validNormalUserName(param.getUserName())) {
			outputJson.put("userName", "\"" + param.getUserName() + "\" 账户名异常, 必须以英文字母开头,长度为6~16个字符.(只可输入英文字母及数字)");
			exceptionFlag = true;
		}

		if (usersMapper.isUserExists(param.getUserName()) > 0) {
			outputJson.put("userName", "账户名已存在");
			exceptionFlag = true;
		}

		String nickNameAfterEscapeHtml = StringEscapeUtils.escapeHtml(param.getNickName());
		if(StringUtils.isBlank(param.getNickName())) {
			outputJson.put("nickName", "请您一定要起给昵称...");
			exceptionFlag = true;
		} else if (nickNameAfterEscapeHtml.length() > 32) {
			outputJson.put("nickName", "昵称太长了...");
			exceptionFlag = true;
		} else if (usersDetailMapper.isNickNameExists(nickNameAfterEscapeHtml) > 0) {
			outputJson.put("nickName", "昵称重复了...");
			exceptionFlag = true;
		} else {
			userDetail.setNickName(nickNameAfterEscapeHtml);
		}

		if (!validPassword(param.getPwd())) {
			outputJson.put("pwd", "密码长度不正确(8到16位)");
			exceptionFlag = true;
		}

		if (!param.getPwd().equals(param.getPwdRepeat())) {
			outputJson.put("pwdRepeat", "两次输入的密码不一致");
			exceptionFlag = true;
		}

		/*
		 * 2019-06-12
		 * 暂时不强制验证邮箱
		 */
//		if (!validEmail(param.getEmail())) {
//			outputJson.put("email", "请输入正确的邮箱");
//			exceptionFlag = true;
//		} else {
//			FindActiveEmailParam findActiveEmailParam = new FindActiveEmailParam();
//			findActiveEmailParam.setEmail(param.getEmail());
//			if (usersDetailMapper.isActiveEmailExists(findActiveEmailParam) > 0) {
//				outputJson.put("email", "邮箱已注册(忘记密码或用户名?可尝试找回)");
//				exceptionFlag = true;
//			} else {
//				userDetail.setEmail(param.getEmail());
//			}
//		}

		/*
		 * 2019-06-12
		 * 暂时不强制验证手机
		 */
//		if (StringUtils.isNotBlank(param.getMobile())) {
//			if (validMobile(param.getMobile())) {
//				userDetail.setMobile(Long.parseLong(param.getMobile()));
//			} else {
//				outputJson.put("mobile", "请填入正确的手机号,或留空");
//				exceptionFlag = true;
//			}
//		}

		if (StringUtils.isNotBlank(param.getQq())) {
			if (validQQ(param.getQq())) {
				userDetail.setQq(Long.parseLong(param.getQq()));
			} else {
				outputJson.put("qq", "QQ号格式异常, 可以为空");
				exceptionFlag = true;
			}
		}

		if (exceptionFlag) {
			result.normalFail();
			result.setMessage(outputJson.toString());
			return result;
		}

		if (NumericUtilCustom.matchInteger(param.getQq())) {
			userDetail.setQq(Long.parseLong(param.getQq()));
		}

		if (param.getGender() == null || param.getGender().equals(UserConstant.secret)) {
			userDetail.setGender(UserConstant.secret);
		} else if (param.getGender().equals(UserConstant.male)) {
			userDetail.setGender(UserConstant.male);
		} else {
			userDetail.setGender(UserConstant.female);
		}

		param.setPwd(passwordEncoder.encode(param.getPwd()));
		Users user = createUserFromUserRegistParam(param);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnable(true);
		usersMapper.insertNewUser(user);

		userDetail.setUserId(user.getUserId());
		userDetail.setRegistIp(NumericUtilCustom.ipToLong(ip));
		usersDetailMapper.insertSelective(userDetail);

		List<Roles> roleList = roleService.getRoleList();

		UserRoles tmpUserRole = new UserRoles();
		tmpUserRole.setUserId(user.getUserId());
		for (Roles role : roleList) {
			if (RolesType.ROLE_USER.getRoleName().equals(role.getRole())) {
				tmpUserRole.setRoleId(role.getRoleId());
			}
		}

		usersMapper.insertNewUserRole(tmpUserRole);
		
		result.normalSuccess();

		return result;
	}

	@Override
	public Users getUserbyUserName(String userName) {
		return usersMapper.findUserByUserName(userName);
	}
	
	@Override
	public MyUserPrincipal buildMyUserPrincipalByUserName(String userName) {
		MyUserPrincipal myUserDetail = new MyUserPrincipal();
		Users user = usersMapper.findUserByUserName(userName);
		if(user == null) {
			return myUserDetail;
		}
		
		myUserDetail.setUser(user);
		
		List<String> roleNameList = userRolesMapper.getRoleNameListByUserId(user.getUserId());
		
		myUserDetail.setRoles(roleNameList);
		
		return myUserDetail;
	}

	private boolean validNormalUserName(String userNameInput) {
		if (userNameInput == null) {
			return false;
		}
		return userNameInput.matches("[a-z][a-zA-Z0-9_]{5,15}");
	}

	private boolean validPassword(String passwordInput) {
		if (passwordInput == null) {
			return false;
		}
		return passwordInput.matches(".{8,16}");
	}
	
	@SuppressWarnings("unused")
	private boolean validEmail(String email) {
		if (email == null) {
			return false;
		}
		return email.matches("[a-zA-Z0-9][a-zA-Z0-9_-]*@[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]{2,4}(\\.[a-z]{2,4})?");
	}
	
	private boolean validQQ(String qq) {
		if (qq == null || !NumericUtilCustom.matchInteger(qq) || qq.length() < 5 || qq.length() > 11) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private boolean validMobile(String mobile) {
		if (mobile == null) {
			return false;
		}
		return NumericUtilCustom.matchMobile(mobile);
	}

	@Override
	public UsersDetailVO findUserDetail(Long userId) {
		if(userId == null) {
			return new UsersDetailVO();
		}
		
		return buildUserDetailVOByPo(usersDetailMapper.findUserDetail(userId));
	}
	
	@Override
	public String findHeadImageUrl(Long userId) {
		if(userId == null) {
			return null;
		}
		return usersDetailMapper.findHeadImage(userId);
	}
	
	@Override
	public UsersDetailVO findOtherUserDetail(OtherUserInfoParam param) {
		if(StringUtils.isBlank(param.getNickName()) || StringUtils.isBlank(param.getPk())) {
			return new UsersDetailVO();
		}
		String nickNameAfterEscapeHtml = StringEscapeUtils.escapeHtml(param.getNickName());
		
		return buildUserDetailVOByPo(usersDetailMapper.findUserDetailByNickName(nickNameAfterEscapeHtml));
	}
	
	private UsersDetailVO buildUserDetailVOByPo(UsersDetail ud) {
		UsersDetailVO vo = new UsersDetailVO();
		if(ud == null) {
		    return vo;
		}
		
		vo.setNickName(ud.getNickName());

		
		vo.setEmail(ud.getEmail());
		if(ud.getGender() == 1) {
			vo.setGender("男");
		} else if(ud.getGender() == 0) {
			vo.setGender("女");
		} else {
			vo.setGender("保密");
		}
		vo.setLastLoginTime(ud.getLastLoginTime());
		vo.setMobile(ud.getMobile());
		vo.setQq(ud.getQq());
		
		return vo;
	}


	@Override
	public CommonResult resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword, String newPasswordRepeat) {
		CommonResult result = new CommonResult();
		String encodePassword = passwordEncoder.encode(oldPassword);
		if(usersMapper.matchUserPassword(userId, encodePassword) < 1) {
			result.fillWithResult(ResultType.wrongOldPassword);
			return result;
		}
		
		return resetPassword(userId, newPassword, newPasswordRepeat);
	}
	
	private CommonResult resetPassword(Long userId, String newPassword, String newPasswordRepeat) {
		CommonResult result = new CommonResult();
		if (!validPassword(newPassword)) {
			result.fillWithResult(ResultType.invalidPassword);
			return result;
		}
		
		if(!newPassword.equals(newPasswordRepeat)) {
			result.fillWithResult(ResultType.differentPassword);
			return result;
		}
		
		Users user = usersMapper.findUser(userId);
		if(user == null) {
			result.fillWithResult(ResultType.linkExpired);
			return result;
		}
		
		int resetCount = usersMapper.resetPassword(passwordEncoder.encode(newPassword), newPassword, user.getUserId());
		if(resetCount > 0) {
			result.fillWithResult(ResultType.resetPassword);
			return result;
		} else {
			result.fillWithResult(ResultType.serviceError);
			return result;
		}
	}

	@Override
	public List<Long> findUserIdListByRoleId(Integer roleId) {
		if(roleId == null) {
			return new ArrayList<Long>();
		}
		
		return userRolesMapper.findUserIdListByRoleId(roleId);
	}
	
	private Users createUserFromUserRegistParam(UserRegistParam param) {
    	Users user = new Users();
    	user.setUserName(param.getUserName());
    	user.setPwd(param.getPwd());
//    	user.setAccountNonExpired(true);
//    	user.setAccountNonLocked(true);
//    	user.setCredentialsNonExpired(true);
//    	user.setEnable(true);
    	return user;
    }
}
