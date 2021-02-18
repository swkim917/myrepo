package com.exam.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.exam.vo.MemberVo;

public interface MemberMapper {
	//@Select("SELECT * FROM member ORDER BY id")
	List<MemberVo> getAllMembers();
	
	
	//@Select("SELECT * FROM member WHERE id = #{id}")
	MemberVo getMemberById(String id);
	
	
	//@Delete("DELETE FROM member WHERE id = #{id} ")
	void deleteById(String id);
	
	void update(MemberVo memberVo);
	
	void addMember(MemberVo memberVo);


	

	
	

	
	//@Select("SELECT passwd FROM member WHERE id = #{id}")
	String userCheck(String id);
	
	//@Select("SELECT COUNT(*) FROM member WHERE id = #{id}")
	int getCountById(String id);
	
	

	

	
	//@Delete("DELETE FROM member")
	void deleteAll();
	
	
//	@Select("SELECT gender, count(*) as cnt "
//			+ "FROM member "
//			+ "GROUP BY gender ")
	List<Map<String, Object>> getGenderPerCount();
	
	
	
	List<Map<String, Object>> getAgeRangePerCount();
	
	
}






