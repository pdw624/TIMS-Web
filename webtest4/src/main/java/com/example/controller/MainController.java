package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.client.NettyClient;
import com.example.domain.HbtVO;
import com.example.domain.MainVO;
import com.example.service.HBTService;
import com.example.service.MainService;
import com.example.test.ClientMsgHandler;
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
	
	public static boolean isConnected = false;
	public static TimsClientTest tct;
	@Autowired
	private MainService service;
	@Autowired
	private HBTService hbt_service;
	
	
	public static HashMap<String,Object> usableMap;
	
	@RequestMapping("/main")
	public String main(Model model, @RequestParam HashMap<String, Object> map, Model hbtModel) throws Exception {
//		log.info("Controller 실행");
		usableMap = new HashMap<String, Object>(map);
		
		hbtModel.addAllAttributes(map);

		System.out.println("----------------------------");
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			System.out.println(key + " = " + value);
		}
		System.out.println("----------------------------");
		
		
//		usableMap = new HashMap<String, Object>(map);
//		System.out.println((String)MainController.usableMap.get("atIdGReq"));
//		String tempStr=(String) MainController.usableMap.get("atIdGReq");
		//int tempInt = Integer.parseInt(tempStr);
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"+tempInt);
		
		//System.out.println(map.get("atIdGreq"));
		
		//처음 시작할 때 값이 있다면

		connection(usableMap);
		
		if(map.isEmpty()==false && isConnected==true &&  Boolean.parseBoolean((String) map.get("isSend"))==true) {
			ClientMsgHandler.msgSelect((String) map.get("opCode"));
		}
		
		
		//db조회값 화면 띄우기 테스트
		////////////////////////////////////////////
		List<MainVO> testList = new ArrayList<>();
		testList = service.getTestList();

		model.addAttribute("testList", testList);
		
		//db조회값 화면 띄우기 테스트(실제 사용할 table)
		////////////////////////////////////////////
		List<HbtVO> hbtList = new ArrayList<>();
		hbtList = hbt_service.getHBTList();
		
		model.addAttribute("hbtList", hbtList);
		
		//data Insert (저장 버튼을 눌렀을 때 실행돼야함)
		////////////////////////////////////////////
		if(Boolean.parseBoolean((String) map.get("isSaved"))==true) {
			int temp;
			temp = hbt_service.insertData(map);
			model.addAttribute("hbtList", temp);
			
			hbtList = new ArrayList<>();
			hbtList = hbt_service.getHBTList();
			model.addAttribute("hbtList", hbtList);
		}
		
		
		return "test5";
	}
	
	
	
	
	
	public static byte toByte(HashMap<String, Object> map, String str) {
		String strTemp = (String)map.get(str);
		byte result =(byte) strTemp.charAt(0);
		
		//String형의 숫자로 받았을땐 >> 정수로 변환해준다 ASCII
		if(result>=48 && result<=57) {
			result = (byte) (result-48);
		}
		System.out.println(str+" "+result);
		//String result=/*"0x"+*/Integer.toHexString(+intTemp);
		
		return result;
	}
	

	
	
	public void connection(HashMap<String, Object> map) {
		// 처음 시작할 때 값이 있다면
		if (map.isEmpty() == false) {

			if (isConnected == false) {
				// 연결
				boolean isSend = Boolean.parseBoolean((String) map.get("isSend"));
				String ip = (String) map.get("ip");
				int port = Integer.parseInt((String) map.get("port"));
				// 전송받으면
				if (isSend == true) {
					// TimsClientTest(TimsTCP, TimsNet, TimsAttribute 참조 걸어둠)
					TimsConfig timsConfig = new TimsConfig(toByte(map, "protocolIndicator"),
															toByte(map, "protocolVersion"), 
															toByte(map, "LF"), 
															toByte(map, "RF"), 
															toByte(map, "CE"),
															toByte(map, "TR"), 
															toByte(map, "TO_A"), 
															toByte(map, "RC"));
					tct = new TimsClientTest(ip, port, timsConfig);
					tct.run();
				}
				//isConnected = true;
			}
		}
	}

}
