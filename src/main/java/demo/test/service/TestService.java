package demo.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import demo.base.user.pojo.po.Users;
import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;

@Service
public class TestService extends CommonService {

	private static String testKey = "testKey";
	private static String testValue = "testValue";

	public void redisSet() {
		Users u = new Users();
		u.setUserName(testValue);
		JSONObject j = JSONObject.fromObject(u);
		redisTemplate.opsForValue().set(testKey, j.toString());
	}
	
	public String redisGet() {
		String testV = redisTemplate.opsForValue().get(testKey);
		testV = redisTemplate.opsForValue().get(testKey);
		return testV;
	}
	
	public List<String> redis() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		map.put("key4", "value4");
		map.put("key5", "value5");
		map.put("key6", "value6");
		map.put("key7", "value7");
		map.put("key8", "value8");
		map.put("key9", "value9");
		map.put("key10", "value10");
		redisTemplate.opsForValue().multiSet(map);
		
		List<String> keys = new ArrayList<String>();
		keys.add("key10");
		keys.add("key1");
		keys.add("key5");
		keys.add("key2");
		keys.add("key8");
		keys.add("key3");
		keys.add("key9");
		keys.add("key7");
		redisTemplate.delete("key7");
		redisTemplate.delete(keys);
		List<String> values = redisTemplate.opsForValue().multiGet(keys);
		
		return values;
	}
}
