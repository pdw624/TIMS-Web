package com.example.service;

import java.util.HashMap;
import java.util.List;

import com.example.domain.HbtVO;
import com.example.domain.MainVO;

public interface HBTService {
//	public List<MainVO> getTestList();
	public List<HbtVO> getHBTList();
	
	
	
	
	////////////////////////////////////
	public int insertData(HashMap<String, Object> vo)throws Exception;
	public int updateData(HashMap<String, Object> vo) throws Exception;
}
