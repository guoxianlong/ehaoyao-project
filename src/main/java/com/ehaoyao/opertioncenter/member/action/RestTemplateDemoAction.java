package com.ehaoyao.opertioncenter.member.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ehaoyao.opertioncenter.member.model.RestObject;
import com.ehaoyao.opertioncenter.member.model.RestParam;
import com.haoyao.goods.util.JaxbBinder;
import com.haoyao.goods.util.JaxbBinder.CollectionWrapper;

@Controller
@RequestMapping("/rest")
public class RestTemplateDemoAction {
	private RestTemplate restTemplate = new RestTemplate();
	@RequestMapping(value = "/test。do", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String test(HttpServletRequest request){
		RestParam param = new RestParam();
		RestObject object = new RestObject();
		object.setOrderid("12345");
		List<RestObject> list = new ArrayList<RestObject>();
		list.add(object);
		param.setOrderList(list);
		param.setSize("101");
		JaxbBinder requestBinder = new JaxbBinder(RestParam.class,CollectionWrapper.class);
		String test = requestBinder.toXml(param, "utf-8");
		System.out.println(test);
		return test;
	}
	
	@RequestMapping(value = "/test1。do", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public void test1(HttpServletRequest request){
		String restUri ="http://localhost:8080/operation_center/rest/test.do?orderid={orderid}&size={size}";
		
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("orderid", "12345");
		variables.put("size", "101");
		RestParam param = null;
		param = restTemplate.getForObject(restUri,
				RestParam.class, variables);
		System.out.println("adfasfdasdf");
		
	}

}
