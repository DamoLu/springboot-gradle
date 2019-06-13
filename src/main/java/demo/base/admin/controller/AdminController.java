package demo.base.admin.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.base.admin.pojo.constant.AdminUrlConstant;
import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.base.user.pojo.po.Users;
import demo.base.user.service.UsersService;
import demo.baseCommon.controller.CommonController;
import net.sf.json.JSONObject;

/**
 * @author Acorn 2017年4月15日
 * 
 */
@Controller
@RequestMapping(value = AdminUrlConstant.root)
public class AdminController extends CommonController {

	@Autowired
	private UsersService userDetailDao;

//	@Autowired
//	private AdminService adminService;
	
	@Autowired
	private SystemConstantService systemConstantService;
	

	/**
	 * 解锁/锁定用户 请求处理
	 * 
	 * @param formData
	 * @return
	 */
	@PostMapping(value = AdminUrlConstant.userManager, produces = "text/html;charset=UTF-8")
	public void userEdit(@RequestBody MultiValueMap<String, String> formData) {
//		TODO
		Users tmpUser = new Users();
		tmpUser.setUserName(formData.get("userName").get(0));
		tmpUser.setEnable(Boolean.parseBoolean(formData.get("enable").get(0)));
		tmpUser.setAccountNonLocked(Boolean.parseBoolean(formData.get("accountNonLocked").get(0)));
		tmpUser.setAccountNonExpired(Boolean.parseBoolean(formData.get("accountNonExpired").get(0)));
		tmpUser.setCredentialsNonExpired(Boolean.parseBoolean(formData.get("credentialsNonExpired").get(0)));
		userDetailDao.setLockeds(tmpUser);
	}



	@PostMapping(value = AdminUrlConstant.refreshSystemConstant)
	public void refreshSystemConstant(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonInput = getJson(data);
		List<String> keys = Arrays.asList(jsonInput.getString("keys").split(" "));
		List<SystemConstant> result = systemConstantService.getValsByName(keys, true);
		outputJson(response, JSONObject.fromObject(result));
	}
	
	
}