package com.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mindrot.jbcrypt.BCrypt;

import com.exam.mapper.MemberMapper;
import com.exam.vo.MemberVo;

public final class MemberMyBatisDao {

	private static MemberMyBatisDao instance = new MemberMyBatisDao();
	
	public static MemberMyBatisDao getInstance() {
		return instance;
	}
	///////////////////////////////////////////////////////////////
	
	private SqlSessionFactory sqlSessionFactory;

	private MemberMyBatisDao() {
		sqlSessionFactory = MyBatisUtils.getSqlSessionFactory();
	}
	
	
	public MemberVo getMemberById(String id) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			MemberVo memberVo = memberMapper.getMemberById(id);
			return memberVo;
		}
	}
	
	
	public List<MemberVo> getAllMembers() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			List<MemberVo> list = memberMapper.getAllMembers();
			return list;
		}
	}
	
	
	
	
	public void addMember(MemberVo memberVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			memberMapper.addMember(memberVo);
		}
	}
	
	
	
	// �α��� Ȯ��.
	// check -1  ���� ���̵�
	// check  0  �н����� Ʋ��
	// check  1  ���̵�, �н����� ��� ��ġ
	public int userCheck(String id, String passwd) {
		int check = -1; // ���� ���̵� ���°����� �ʱ�ȭ
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			String dbPasswd = memberMapper.userCheck(id); // dbPasswd는 암호화된 문자열임
			
			if (dbPasswd != null) {
				if (BCrypt.checkpw(passwd, dbPasswd)) { // passwd.equals(dbPasswd)
					check = 1;
				} else {
					check = 0;
				}
			} else { // dbPasswd == null
				check = -1;
			}
		}
		return check;
	}
	
	
	public int getCountById(String id) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			int count = memberMapper.getCountById(id);
			return count;
		}
	}
	
	
	public void update(MemberVo memberVo) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			memberMapper.update(memberVo);
		}
	}
	
	
	public void deleteById(String id) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			memberMapper.deleteById(id);
		}
	}
	
	
	public void deleteAll() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			memberMapper.deleteAll();
		}
	}
	
	
	public List<Map<String, Object>> getGenderPerCount() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			List<Map<String, Object>> list = memberMapper.getGenderPerCount();
			return list;
		}
	}
	
	
	
	public List<Map<String, Object>> getAgeRangePerCount() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			List<Map<String, Object>> list = memberMapper.getAgeRangePerCount();
			return list;
		}
	}
	
	
	
	public static void main(String[] args) {
		MemberMyBatisDao dao = MemberMyBatisDao.getInstance();
		
		MemberVo memberVo = dao.getMemberById("aaa");
//		System.out.println(memberVo);
		
//		System.out.println("============================");
		
		List<MemberVo> list = dao.getAllMembers();
		for (MemberVo vo : list) {
//			System.out.println(vo);
		}
	}
	
}




