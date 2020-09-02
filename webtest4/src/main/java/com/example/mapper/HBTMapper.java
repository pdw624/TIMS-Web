package com.example.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.HbtVO;
import com.example.domain.MainVO;

@Mapper
public interface HBTMapper {
//	public List<MainVO> getTestList();

	public List<HbtVO> getHBTList();
	public List<HbtVO> getRemovedHBTList();
	
	///////////////////////////////
	public int insertData(HashMap<String, Object> vo) throws Exception;
	public int updateData(HashMap<String, Object> vo) throws Exception;
	
	public int restoreData(HashMap<String, Object> vo) throws Exception;
}
