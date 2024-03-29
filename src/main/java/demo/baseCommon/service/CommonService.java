package demo.baseCommon.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import demo.baseCommon.pojo.param.PageParam;
import demo.baseCommon.pojo.result.CommonResult;
import demo.baseCommon.pojo.type.ResultType;

public abstract class CommonService {
	
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;

	private static final int normalPageSize = 10;
	private static final int maxPageSize = 300;
	protected static final long theStartTime = 946656000000L;

	protected PageParam setPageFromPageNo(Integer pageNo) {
		return setPageFromPageNo(pageNo, normalPageSize);
	}

	protected PageParam setPageFromPageNo(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 1;
		}
		if (pageSize > maxPageSize) {
			pageSize = maxPageSize;
		}
		PageParam pp = new PageParam();
		if (pageNo == 1) {
			pp.setPageStart(0);
			pp.setPageEnd(pageSize);
		} else if (pageNo > 1) {
			pp.setPageStart(pageSize * (pageNo - 1) + 1);
			pp.setPageEnd(pp.getPageStart() + pageSize);
		}
		pp.setPageSize(pageSize);
		return pp;
	}

	protected String createDateDescription(Date inputDate) {
		if (inputDate == null) {
			return "";
		}
		Long oneHourLong = 1000L * 60 * 60;
		Long timeDiff = System.currentTimeMillis() - inputDate.getTime();
		if (timeDiff < (oneHourLong / 2)) {
			return "a moment...";
		} else if (timeDiff <= oneHourLong) {
			return "not long ago";
		} else if (timeDiff <= (oneHourLong * 12)) {
			return String.valueOf(timeDiff / oneHourLong) + " hours ago";
		} else if (timeDiff <= (oneHourLong * 24)) {
			return "today";
		} else if (timeDiff <= (oneHourLong * 24 * 3)) {
			return String.valueOf(timeDiff / (oneHourLong * 24)) + " days ago";
		} else if (timeDiff <= (oneHourLong * 24 * 7)) {
			return "within a week";
		} else if (timeDiff <= (oneHourLong * 24 * 31)) {
			return "within a month";
		} else {
			return "long long ago...";
		}
	}

	protected CommonResult nullParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.nullParam);
		return result;
	}
	
	protected CommonResult errorParam() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.errorParam);
		return result;
	}
	
	protected CommonResult serviceError() {
		CommonResult result = new CommonResult();
		result.fillWithResult(ResultType.serviceError);
		return result;
	}
	
	protected CommonResult normalSuccess() {
		CommonResult result = new CommonResult();
		result.normalSuccess();
		return result;
	}
	
	protected CommonResult notLogin() {
		CommonResult result = new CommonResult();
		result.failWithMessage("请登录后操作");
		return result;
	}
	
	protected static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.contains("windows")) {
			return true;
		} else {
			return false;
		}
	}
}
