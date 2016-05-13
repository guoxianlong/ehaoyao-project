package com.haoyao.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.haoyao.goods.model.SyncParam;
import com.haoyao.goods.util.JsonUtil;

@Service
public class DataSyncServiceImpl {

	public void sendDataToTargetSys(String url,String dataType,String actionType,Object o){
		RestTemplate restTemplate = new RestTemplate();
        String restUri = url+"?syncParam={syncParam}";
        SyncParam syncParam = new SyncParam();
        syncParam.setDataType(dataType);
        syncParam.setActionType(actionType);
        syncParam.setObject(o);
        String data = JsonUtil.getJSONString(syncParam);
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("syncParam", data);//分配给接入应用的唯一ID
		String response = restTemplate.getForObject(restUri,String.class,variables);
	}
	
	public void sendDataToTargetSys(List<String> urls,String dataType,String actionType,Object o){
		for(String url:urls){
			this.sendDataToTargetSys(url, dataType, actionType, o);
		}
	}

}
