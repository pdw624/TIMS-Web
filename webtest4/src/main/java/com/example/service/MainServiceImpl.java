package com.example.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.domain.MainVO;
import com.example.mapper.MainMapper;

@Service
public class MainServiceImpl implements MainService{
	@Resource
	private MainMapper mapper;
	
	@Override
	public List<MainVO> getTestList(){
		return mapper.getTestList();
	}
}
