package demo.test.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.config.costom_component.SnowFlake;
import demo.test.pojo.constant.TestUrl;
import demo.test.service.TestService;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

//	@Autowired
//	private TestMapper testMapper;

//	@Autowired
//	private WeixinService weixinService;
	
	@Autowired
	private TestService testService;
	
	
	@Autowired
	private SnowFlake snowFlake;
	
	
	@ApiOperation(value="测试", notes="测试notes")
	@GetMapping(value = { "/test" })
	public void test(HttpServletRequest request) throws Exception {
	}

	@GetMapping(value = { "/testWebSocket" })
	public ModelAndView testWebSocket(HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		view.setViewName("testJSP/testWebSocket");
		return view;
	}
	
	@GetMapping(value = { "/testException" })
	public ModelAndView testException(HttpServletRequest request) throws Exception {
		logger.debug("dateTime: " + new Date());
		throw new Exception();
	}
	
	@GetMapping(value = "/snowFlake")
	@ResponseBody
	public String snowFlake() {
		return String.valueOf(snowFlake.getNextId());
	}
	
	@GetMapping(value = "/redis")
	@ResponseBody
	public List<String> redis() {
		return testService.redis();
	}
}
