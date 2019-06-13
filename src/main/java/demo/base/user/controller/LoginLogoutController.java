package demo.base.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.base.user.pojo.constant.LoginUrlConstant;
import demo.baseCommon.controller.CommonController;



/**
 * @author Acorn
 * 2017年4月15日
 * login and logout
 */
@Controller
@RequestMapping(value = LoginUrlConstant.login)
public class LoginLogoutController extends CommonController {
	
	
}
