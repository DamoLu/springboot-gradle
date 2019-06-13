package demo.baseCommon.pojo.result;

import demo.baseCommon.pojo.type.ResultType;

public class CommonResult {

	public String result;

	public String message;

	public String getResult() {
		return result;
	}

	public CommonResult setResult(String result) {
		this.result = result;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public CommonResult setMessage(String message) {
		this.message = message;
		return this;
	}

	public CommonResult addMessage(String message) {
		this.message += message;
		return this;
	}

	public boolean isSuccess() {
		return ResultType.success.getCode().equals(this.getResult());
	}

	public void setIsSuccess() {
		this.setResult(ResultType.success.getCode());
	}

	public void setIsFail() {
		this.setResult(ResultType.fail.getCode());
	}

	public void normalSuccess() {
		this.setResult(ResultType.success.getCode());
	}

	public void normalFail() {
		this.setResult(ResultType.fail.getCode());
	}

	public void successWithMessage(String desc) {
		this.setResult(ResultType.success.getCode());
		this.setMessage(desc);
	}

	public void failWithMessage(String desc) {
		this.setResult(ResultType.fail.getCode());
		this.setMessage(desc);
	}

	public void fillWithResult(ResultType resultType) {
		this.setResult(resultType.getCode());
		this.setMessage(resultType.getName());
	}

	@Override
	public String toString() {
		return "CommonResult [result=" + result + ", message=" + message + "]";
	}

}