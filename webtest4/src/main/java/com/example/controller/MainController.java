package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.client.NettyClient;
import com.example.domain.MainVO;
import com.example.service.MainService;
import com.example.test.TimsClientHandlerTest;
import com.example.test.TimsClientTest;
import com.example.timsclient.TimsClient;

import kr.tracom.platform.net.config.TimsConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class MainController {

	@Autowired
	private MainService service;
	

	@RequestMapping("/main")
	public String main(Model model, @RequestParam HashMap<String, Object> map, Model hbtModel) {
//		log.info("Controller 실행");

		hbtModel.addAllAttributes(map);

		System.out.println("----------------------------");
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			System.out.println(key + " = " + value);
		}
		System.out.println("----------------------------");
		
		//TimsClientTest(TimsTCP, TimsNet, TimsAttribute 참조 걸어둠)
		TimsConfig timsConfig = new TimsConfig();
		TimsClientTest tct = new TimsClientTest("192.168.34.6", 8083, timsConfig);
		tct.run();
		
		
		
		////////////////////////////////////////////
		List<MainVO> testList = new ArrayList<>();
		testList = service.getTestList();

		model.addAttribute("testList", testList);
		return "test5";
	}

//	@RequestMapping("/index")
//	public String headerSize(@RequestParam("headerSize") String headerSize, Model model) {
//		
//		System.out.println("@RequestParam : "+ headerSize);
//		model.addAttribute("headerSize", headerSize);
//		/*
//		NettyClient nc = new NettyClient();
//		try {
//            nc.connect(8085, "192.168.34.6");
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//		return "index";
//	}

//	@RequestMapping("/index")
//	public String headerSize(@RequestParam HashMap<String, Object> map, Model model) {
//
////		System.out.println("@RequestParam : "+ headerSize);
////		model.addAttribute("headerSize", headerSize);
//
//		model.addAllAttributes(map);
//
//		System.out.println("----------------------------");
//		for (String key : map.keySet()) {
//			String value = (String) map.get(key);
//			System.out.println(key + " = " + value);
//		}
//		System.out.println("----------------------------");
//		
//		//TimsClientTest(TimsTCP, TimsNet, TimsAttribute 참조 걸어둠)
//		TimsConfig timsConfig = new TimsConfig();
//		TimsClientTest tct = new TimsClientTest("192.168.34.6", 8083, timsConfig);
//		tct.run();
//		
//		
//		//TimsClient
////		TimsConfig timsConfig = new TimsConfig();
////		TimsClient tc = new TimsClient("192.168.34.6", 8083, timsConfig);
////		tc.run();
//		
//		
//		//NettyClient
////		NettyClient nc = new NettyClient();
////		try {
////			nc.connect(8083, "192.168.34.6");
////
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		
//
//		return "index";
//	}

}
