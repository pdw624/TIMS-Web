package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.MainVO;
import com.example.service.MainService;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class MainController {

	@Autowired
	private MainService service;
	
	@RequestMapping("/main")
	public String main(Model model) {
//		log.info("Controller 실행");
		
		List<MainVO> testList = new ArrayList<>();
		testList = service.getTestList();
		
		model.addAttribute("testList", testList);
		return "test5";
	}
}
