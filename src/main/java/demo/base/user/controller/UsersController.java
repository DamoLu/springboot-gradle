package demo.base.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.base.system.pojo.constant.BaseStatusCode;
import demo.base.user.pojo.constant.UsersUrlConstant;
import demo.base.user.pojo.param.controllerParam.UserRegistParam;
import demo.base.user.service.UsersService;
import demo.baseCommon.controller.CommonController;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;
import demo.util.BaseUtilCustom;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = UsersUrlConstant.root)
public class UsersController extends CommonController {
	
	@Autowired
	private UsersService usersService;
//	@Autowired
//	private MailService mailService;
//	@Autowired
//	private SystemConstantService systemConstantService;
	
	@Autowired
	private BaseUtilCustom baseUtilCustom;
	
	@PostMapping(value = {UsersUrlConstant.userNameExistCheck})
	public void userNameExistCheck(
			@RequestBody String jsonStrInput, 
			HttpServletResponse response
			) throws IOException {
		// 建输出流
		PrintWriter out = response.getWriter();
		
		JSONObject jsonInput = JSONObject.fromObject(jsonStrInput);
		String userName = jsonInput.getString("userName");
		JSONObject json = new JSONObject();
		boolean result = usersService.isUserExists(userName);
		if(result) {
			json.put("result", BaseStatusCode.fail);
		} else {
			json.put("exception", "user name not exist");
		}
		
		out.print(json);
	}
	
	
	@ApiOperation(value="用户注册", notes="用户注册请求")
	@ApiImplicitParam(name = "user", value = "用户注册UserRegistParam", required = true, dataType = "UserRegistParam")
	@PostMapping(value = UsersUrlConstant.userRegist)
	public void userRegistHandler(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
		insertVisitIp(request, "registPost");
		JSONObject json = getJson(data);
		UserRegistParam param = new UserRegistParam().fromJson(json);
		String ip = request.getHeader("X-FORWARDED-FOR");
		
		CommonResult result = usersService.newUserRegist(param, ip);
		
		JSONObject jsonOutput = JSONObject.fromObject(result);
		
		try {
			response.getWriter().println(jsonOutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
//	
//	public Long getCurrentUserId() {
//		String userName = baseUtilCustom.getCurrentUserName();
//		if(userName == null || userName.length() == 0) {
//			return null;
//		} 
//		
//		Users currentUser = usersService.getUserbyUserName(userName);
//		if(currentUser == null) {
//			return null;
//		}
//		return currentUser.getUserId();
//	}

	
	

	@GetMapping(value = UsersUrlConstant.resetPassword)
	public ModelAndView resetPassword(@RequestParam(value = "mailKey", defaultValue = "")String mailKey, RedirectAttributes redirectAttr) {
		/**
		 * 仅接受通过重置密码邮件url的访问,
		 * 用户登录后重设密码不经过此处
		 */
		ModelAndView view = new ModelAndView("userJSP/resetPassword");
		
		if(StringUtils.isBlank(mailKey)) {
			view.addObject("errorMessage", ResultType.errorParam.getName());
			return view;
		} 
		
		view.addObject("mailKey", mailKey);
		
		return view;
	}
	
	@PostMapping(value = UsersUrlConstant.resetPassword)
	public void resetPassword(@RequestBody String data, HttpServletResponse response) {
		// TODO
	}
	
	@PostMapping(value = UsersUrlConstant.isLogin)
	public void isLogin(HttpServletResponse response) {
		CommonResult result = new CommonResult();
		if(baseUtilCustom.isLoginUser()) {
			result.setIsSuccess();
		}
		outputJson(response, JSONObject.fromObject(result));
	}
	
	public String findHeadImageUrl(Long userId) {
		return usersService.findHeadImageUrl(userId);
	}

}
