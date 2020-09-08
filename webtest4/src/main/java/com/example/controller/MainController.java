package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	private List<MainVO> testList;
	private List<HbtVO> hbtList;
	private List<HbtVO> removedHBTList;
	public static HashMap<String,Object> usableMap;
	
	
	@RequestMapping("connect")
	public ResponseEntity<String> connect(@RequestParam HashMap<String, Object> param) throws InterruptedException {
		
		String initStrTemp = "";
		
		if(param.size() > 0) {
			for (String key : param.keySet()) {
			           String value = (String) param.get(key);
			           System.out.println(key + " = " + value);
			}
		}
		connection(param);
		
		while(TimsClientHandlerTest.initStr.contains("PlInitRequest") == false) {
			System.out.print(".");
			if(TimsClientHandlerTest.initStr.contains("PlInitRequest") == true) {
				System.out.println("도착!!");
				initStrTemp = TimsClientHandlerTest.initStr;
				System.out.println(TimsClientHandlerTest.initStr);
				break;
			}
		}
		System.out.println("----------------------------");
		TimsClientHandlerTest.initStr = "[RCVD Payload] : ";
		if(initStrTemp!=null) {
			return new ResponseEntity<>(initStrTemp, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("값없음", HttpStatus.OK);
		}
		//return new ResponseEntity<>(initStrTemp, HttpStatus.OK);
	}
	
	@RequestMapping("send")
	public ResponseEntity<String> send(@RequestParam HashMap<String, Object> param) throws InterruptedException {
		
		
		String getStrTemp = "";
		usableMap = new HashMap<String, Object>(param);
		if(param.size() > 0) {
			for (String key : param.keySet()) {
			           String value = (String) param.get(key);
			           System.out.println(key + " = " + value);
			}
		}
		
		if(param.isEmpty()==false && isConnected==true && Boolean.parseBoolean((String) param.get("isSend"))==true) {
			ClientMsgHandler.msgSelect((String) param.get("opCode"));
		}
		
		String opStr = (String)param.get("opCode");
		if(opStr.equals("OP_GET_REQ")) {
			while(TimsClientHandlerTest.getStr.contains("PlGetResponse") == false) {
				System.out.print(".");
				if(TimsClientHandlerTest.getStr.contains("PlGetResponse") == true) {
					System.out.println("도착!!");
					getStrTemp = TimsClientHandlerTest.getStr;
					System.out.println(TimsClientHandlerTest.getStr);
					break;
				}
			}
		}
		
		System.out.println("----------------------------");
		TimsClientHandlerTest.getStr = "[RCVD Payload] : ";
		if(getStrTemp!=null) {
			return new ResponseEntity<>(getStrTemp, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("값없음", HttpStatus.OK);
		}
		//return new ResponseEntity<>(getStrTemp, HttpStatus.OK);
	}
	
	@RequestMapping("/main")
	public String main(Model model, @RequestParam HashMap<String, Object> map, Model hbtModel) throws Exception {
//		log.info("Controller 실행");
		usableMap = new HashMap<String, Object>(map);
		hbtModel.addAllAttributes(map);

		//웹 클라이언트에서 넘어온 값 확인용
		System.out.println("----------------------------");
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			System.out.println(key + " = " + value);
		}
		System.out.println("----------------------------");
		
		//연결
		//connection(usableMap);
		//연결과 동시에 바로 initRequest를 뷰에 뿌려줌
		//model.addAttribute("result", TimsClientHandlerTest.initStr);
		
//		if(map.isEmpty()==false && isConnected==true &&  Boolean.parseBoolean((String) map.get("isSend"))==true) {
//			ClientMsgHandler.msgSelect((String) map.get("opCode"));
//		}
		
		//웹클라이언트 테이블 갱신
		tableUpdate(model);
		
		//data Insert (저장 버튼을 눌렀을 때 실행돼야함)
		if(Boolean.parseBoolean((String) map.get("isSaved"))==true) {
			int temp;
			temp = hbt_service.insertData(map);
			model.addAttribute("hbtList", temp);
			
			tableUpdate(model);
		}
		
		//data Remove (삭제 버튼을 눌렀을 때 실행돼야함)
		if(Boolean.parseBoolean((String) map.get("isRemoved"))==true) {
			String strTemp = (String)map.get("isRemovedThings");
			String strTempArr[] = strTemp.split(",");
			int removedThings[] = new int[strTempArr.length];
			
			for(int i=0; i<strTempArr.length; i++) {
				removedThings[i] = Integer.parseInt(strTempArr[i]);
			}
			
			map.put("isRemovedThings", removedThings);
			
			
			for(int i=0; i<removedThings.length;i++) {
				int temp;
				temp = hbt_service.updateData(map);
				model.addAttribute("hbtList", temp);
			}
			
			tableUpdate(model);
			
		}
		
		//data Restore (복원 버튼을 눌렀을 때 실행돼야함)
		if(Boolean.parseBoolean((String) map.get("isRestored"))==true) {
			String strTemp = (String)map.get("isRestoredThings");
			String strTempArr[] = strTemp.split(",");
			int restoredThings[] = new int[strTempArr.length];
			
			for(int i=0; i<strTempArr.length; i++) {
				restoredThings[i] = Integer.parseInt(strTempArr[i]);
			}
			
			map.put("isRestoredThings", restoredThings);
			
			
			for(int i=0; i<restoredThings.length;i++) {
				int temp;
				temp = hbt_service.restoreData(map);
				model.addAttribute("hbtList", temp);
			}
			
			tableUpdate(model);
			
		}
		
		return "test5";
	}
	
	
	public void tableUpdate(Model model) {
		//db조회값 화면 띄우기 테스트
		////////////////////////////////////////////
		testList = new ArrayList<>();
		testList = service.getTestList();

		model.addAttribute("testList", testList);
				
		//db조회값 화면 띄우기 테스트(실제 사용할 table)
		////////////////////////////////////////////
		hbtList = new ArrayList<>();
		hbtList = hbt_service.getHBTList();
				
		model.addAttribute("hbtList", hbtList);
		//삭제항목테이블
		////////////////////////////////////////////
		removedHBTList = new ArrayList<>();
		removedHBTList = hbt_service.getRemovedHBTList();
				
		model.addAttribute("removedHBTList", removedHBTList);
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
				boolean isSend = Boolean.parseBoolean((String) map.get("isConn"));
				String ip = (String) map.get("ip");
				int port = Integer.parseInt((String) map.get("port"));
				// 전송받으면
				if (isSend == true) {
					//tct.shutdown();
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
