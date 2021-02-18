package com.exam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.exam.vo.MemberVo;

public final class MemberDao {
	
	// �̱��� ���� ����
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance() {
		return instance;
	}
	
	///////////////////////////////////////////////////////////

	private MemberDao() {
	}

	// ȸ������ 1�� insert�ϱ�
	public void addMember(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "INSERT INTO member (id, passwd, name, age, gender, email, reg_date, address, tel) ";
			sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getId());
			pstmt.setString(2, memberVo.getPasswd());
			pstmt.setString(3, memberVo.getName());
			pstmt.setInt(4, memberVo.getAge());
			pstmt.setString(5, memberVo.getGender());
			pstmt.setString(6, memberVo.getEmail());
			pstmt.setTimestamp(7, memberVo.getRegDate());
			pstmt.setString(8, memberVo.getAddress());
			pstmt.setString(9, memberVo.getTel());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ���� �߻����ο� ������� ������ �����۾� ������.
			// try��Ͽ��� ���� ��ü�� �����ϴ� �۾��� �ַ� ��
			JdbcUtils.close(con, pstmt);
		}
	} // addMember()
	
	
	// �α��� Ȯ��.
	// check -1  ���� ���̵�
	// check  0  �н����� Ʋ��
	// check  1  ���̵�, �н����� ��� ��ġ
	public int userCheck(String id, String passwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		int check = -1; // ���� ���̵� ���°����� �ʱ�ȭ
		
		try {
			con = JdbcUtils.getConnection();
			// id�� �ش��ϴ� passwd ��������
			sql = "SELECT passwd FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// rs�� ����
			rs = pstmt.executeQuery();
			// rs�� ������(��) ������
			//             �н����� ��  ������  check = 1  Ʋ����  check = 0
			// rs�� ������(��) ������   check = -1
			if (rs.next()) {
				if (passwd.equals(rs.getString("passwd"))) {
					check = 1;
				} else {
					check = 0;
				}
			} else {
				check = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return check;
	} // userCheck()
	
	
	
	
	// ��ü ȸ����� ��������
	public List<MemberVo> getAllMembers() {
		List<MemberVo> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			sql = "SELECT * FROM member ORDER BY id";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberVo memberVo = new MemberVo();
				memberVo.setId(rs.getString("id"));
				memberVo.setPasswd(rs.getString("passwd"));
				memberVo.setName(rs.getString("name"));
				memberVo.setAge(rs.getInt("age"));
				memberVo.setGender(rs.getString("gender"));
				memberVo.setEmail(rs.getString("email"));
				memberVo.setRegDate(rs.getTimestamp("reg_date"));
				
				list.add(memberVo);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return list;
	} // getAllMembers()
	
	
	// Ư��id�� �ش��ϴ� ȸ�� 1�� ��������
	public MemberVo getMemberById(String id) {
		MemberVo memberVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			sql = "SELECT * FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				memberVo = new MemberVo();
				memberVo.setId(rs.getString("id"));
				memberVo.setPasswd(rs.getString("passwd"));
				memberVo.setName(rs.getString("name"));
				memberVo.setAge(rs.getInt("age"));
				memberVo.setGender(rs.getString("gender"));
				memberVo.setEmail(rs.getString("email"));
				memberVo.setRegDate(rs.getTimestamp("reg_date"));
			} // if
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return memberVo;
	} // getMemberById()
	
	
	public int getCountById(String id) {
		int count = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			sql = "SELECT COUNT(*) FROM member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			} // if
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		return count;
	} // getCountById()
	
	
	// Ư��id�� �ش��ϴ� ȸ�� ���� �����ϱ�
	public void update(MemberVo memberVo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "UPDATE member ";
			sql += "SET name = ?, age = ?, gender = ?, email = ?, address = ?, tel = ? ";
			sql += "WHERE id = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getName());
			pstmt.setInt(2, memberVo.getAge());
			pstmt.setString(3, memberVo.getGender());
			pstmt.setString(4, memberVo.getEmail());
			pstmt.setString(5, memberVo.getAddress());
			pstmt.setString(6, memberVo.getTel());
			pstmt.setString(7, memberVo.getId());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ���� �߻����ο� ������� ������ �����۾� ������.
			// try��Ͽ��� ���� ��ü�� �����ϴ� �۾��� �ַ� ��
			JdbcUtils.close(con, pstmt);
		}
	} // addMember()
	
	// Ư��id�� �ش��ϴ� ȸ�� 1�� �����ϱ�
	public void deleteById(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "DELETE FROM member WHERE id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt);
		}
	} // deleteById()
	
	
	// ��� ȸ�� �����ϱ�
	public void deleteAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = JdbcUtils.getConnection();
			
			String sql = "";
			sql += "DELETE FROM member ";
			pstmt = con.prepareStatement(sql);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt);
		}
	} // deleteAll()
	
	
	public List<Map<String, Object>> getGenderPerCount() {
		List<Map<String, Object>> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			// ���� ���� ȸ����
			sql  = "SELECT gender, count(*) as cnt ";
			sql += "FROM member ";
			sql += "GROUP BY gender ";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("gender", rs.getString("gender"));
				map.put("cnt", rs.getInt("cnt"));
				
				list.add(map);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		
		return list;
	} // getGenderPerCount
	
	
	public List<Map<String, Object>> getAgeRangePerCount() {
		List<Map<String, Object>> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			con = JdbcUtils.getConnection();
			
			// ���ɴ뺰 ȸ����
			sql  = "SELECT CASE  ";
			sql += "         WHEN age BETWEEN 10 AND 19 THEN '10��' ";
			sql += "         WHEN age BETWEEN 20 AND 39 THEN 'û����' ";
			sql += "         WHEN age BETWEEN 40 AND 59 THEN '�����' ";
			sql += "         WHEN age >= 60 THEN '�����' ";
			sql += "         WHEN age < 10 OR age IS NULL THEN '��Ÿ' ";
			sql += "       END as age_range ";
			sql += "	   , count(*) as cnt ";
			sql += "FROM member ";
			sql += "GROUP BY age_range ";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("ageRange", rs.getString("age_range"));
				map.put("cnt", rs.getInt("cnt"));
				
				list.add(map);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(con, pstmt, rs);
		}
		
		return list;
	} // getAgeRangePerCount
	
	
	public static void main(String[] args) {
		
		// MemberDao ��ü �غ�
		MemberDao memberDao = new MemberDao();
		
		Random random = new Random();
		
		memberDao.deleteAll(); // ��ü����
		
//		System.out.println("======== insert �׽�Ʈ =========");
		
		for (int i=0; i<1000; i++) {
			MemberVo memberVo = new MemberVo();
			memberVo.setId("user"+i);
			memberVo.setPasswd("1234");
			memberVo.setName("����"+i);
			
			// ���̰��� ����  8���̻� ~ 100������
			int age = random.nextInt(93) + 8; // (0~92)+8 -> (8~100)
			memberVo.setAge(age);
			
			boolean isMale = random.nextBoolean(); // ���� true ���� false
			if (isMale) {
				memberVo.setGender("��");
			} else {
				memberVo.setGender("��");
			}
			
			memberVo.setEmail("user" + i + "@user.com");
			memberVo.setRegDate(new Timestamp(System.currentTimeMillis()));
			memberVo.setAddress("�λ��");
			memberVo.setTel("010-1234-5678");
			
			memberDao.addMember(memberVo);
//			System.out.println("insert ����!");
		}
		
		List<MemberVo> list = memberDao.getAllMembers();
		for (MemberVo memberVo : list) {
//			System.out.println(memberVo);
		}
		
//		System.out.println("======== getMemberById �׽�Ʈ =========");
		
		String testId = "user0";
		
		MemberVo memberVo = memberDao.getMemberById(testId);
//		System.out.println(memberVo);
		
//		System.out.println("======== update �׽�Ʈ =========");
		
		memberVo.setName("�̼���"); // ������ �̸��� ����
		memberDao.update(memberVo);
		
		MemberVo getMemberVo = memberDao.getMemberById(testId);
//		System.out.println(getMemberVo);
		
//		System.out.println("======== deleteById �׽�Ʈ =========");
		
		memberDao.deleteById(testId);
		
		MemberVo getMemberVo2 = memberDao.getMemberById(testId);
//		System.out.println(getMemberVo2);
		
	} // main
	
}






