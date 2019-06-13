package demo.baseCommon.pojo.param;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.sf.json.JSONObject;

public interface CommonControllerParam {

	/* 
	 * return new ObjectMapper().readValue(j.toString(), CommonControllerParam.class); 
	 * */
	public CommonControllerParam fromJson(JSONObject j) throws JsonParseException, JsonMappingException, IOException;

}
