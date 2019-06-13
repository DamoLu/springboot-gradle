package demo.base.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.mapper.SystemConstantMapper;
import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.service.CommonService;

@Service
public class SystemConstantService extends CommonService {

	@Autowired
	private SystemConstantMapper systemConstantMapper;
	
	public boolean hasKey(String constantName) {
		if(StringUtils.isBlank(constantName)) {
			return false;
		}
		return redisTemplate.hasKey(constantName);
	}
	
	public String getValByName(String constantName, boolean refreshFlag) {
		if(StringUtils.isBlank(constantName)) {
			return "";
		}
		
		if(refreshFlag || !redisTemplate.hasKey(constantName)) {
			SystemConstant tmpConstant = systemConstantMapper.getValByName(constantName);
			if(tmpConstant == null || StringUtils.isBlank(tmpConstant.getConstantValue())) {
				tmpConstant = new SystemConstant();
				tmpConstant.setConstantValue("");
				return "";
			}
			redisTemplate.opsForValue().set(tmpConstant.getConstantName(), tmpConstant.getConstantValue());
			return tmpConstant.getConstantValue();
		}
		
		return redisTemplate.opsForValue().get(constantName);
	}

	public String getValByName(String constantName) {
		return getValByName(constantName, false);
	}
	
	public void setValByName(String constantName, String constantValue) {
		redisTemplate.opsForValue().set(constantName, constantValue);
	}
	
	public void setVal(SystemConstant constant) {
		setValByName(constant.getConstantName(), constant.getConstantValue());
	}
	
	public List<SystemConstant> getValsByName(List<String> constantNames, boolean refreshFlag) {
//		TODO
//		if(constantNames == null || constantNames.isEmpty()) {
//			return new ArrayList<SystemConstant>();
//		}
//		
//		List<SystemConstant> result = new ArrayList<SystemConstant>(); 
//		
//		if(refreshFlag) {
//			
//		}
//		
//		List<String> values = redisTemplate.opsForValue().multiGet(constantNames);
//		
//		List<String> queryNames = new ArrayList<String>();
//		HashMap<String, String> result = new HashMap<String, String>();
//		
//		String tmpConstantName;
//		for(int i = 0; i < constantNames.size(); i++) {
//			tmpConstantName = constantNames.get(i);
//			if(StringUtils.isNotBlank(tmpConstantName)) {
//				if(SystemConstantStore.store.containsKey(tmpConstantName)) {
//					result.put(tmpConstantName, SystemConstantStore.store.get(tmpConstantName));
//				} else {
//					queryNames.add(tmpConstantName);
//				}
//			}
//		}
//		
//		List<SystemConstant> queryResult = null;
//		if(queryNames.size() > 0) {
//			queryResult = systemConstantMapper.getValsByName(queryNames);
//		}
//		
//		if(queryResult != null && queryResult.size() > 0) {
//			queryResult.stream().forEach(tmpConstant -> result.put(tmpConstant.getConstantName(), tmpConstant.getConstantValue()));
//		}
//		
//		SystemConstantStore.store.putAll(result);
//		return result;
		return null;
	}

	public HashMap<String, String> getValsByName(List<String> constantNames) {
//		TODO
		return null;
	}
	
	public List<List<Character>> getCustomKeys() {
		List<String> constantNames = new ArrayList<String>();
		constantNames.add(SystemConstantStore.ckey0);
		constantNames.add(SystemConstantStore.ckey1);
		constantNames.add(SystemConstantStore.ckey2);
		constantNames.add(SystemConstantStore.ckey3);
		constantNames.add(SystemConstantStore.ckey4);
		constantNames.add(SystemConstantStore.ckey5);
		constantNames.add(SystemConstantStore.ckey6);
		constantNames.add(SystemConstantStore.ckey7);
		constantNames.add(SystemConstantStore.ckey8);
		constantNames.add(SystemConstantStore.ckey9);
		
		List<SystemConstant> constants = systemConstantMapper.getValsByName(constantNames);
		List<Character> tmpKey = null;
		List<List<Character>> keys = new ArrayList<List<Character>>();
		
		char[] keyCharAry = null;
		for(SystemConstant sc : constants) {
			tmpKey = new ArrayList<Character>();
			keyCharAry = sc.getConstantValue().replaceAll("[^0-9A-Za-z_]", "").toCharArray();
			for(int i = 0; i < keyCharAry.length; i++) {
				tmpKey.add(keyCharAry[i]);
			}
			keys.add(tmpKey);
		}
		
		return keys;
	}


}
