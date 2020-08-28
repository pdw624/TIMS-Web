package com.example.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.domain.HbtVO;
import com.example.domain.MainVO;
import com.example.mapper.HBTMapper;
import com.example.mapper.MainMapper;

@Service
public class HBTServiceImpl implements HBTService{
	@Resource
	private HBTMapper mapper;
	
//	@Override
//	public List<MainVO> getTestList(){
//		return mapper.getTestList();
//	}

	@Override
	public List<HbtVO> getHBTList() {
		// TODO Auto-generated method stub
		return mapper.getHBTList();
	}

	
	//////////////////////////////////////////////////
	@Override
	public int insertData(HashMap<String, Object> vo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertData(vo);
	}


	@Override
	public int updateData(HashMap<String, Object> vo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.updateData(vo);
	}
}
