package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.HbtVO;
import com.example.domain.MainVO;

@Mapper
public interface MainMapper {
	public List<MainVO> getTestList();

//	public List<HbtVO> getHBTList();
}
