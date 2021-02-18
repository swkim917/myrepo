package com.exam.vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoticeVo {
	
	private int num;    // 게시판 글번호
	private String id;  // 작성자 ID
	private String subject;
	private String content;
	private int readcount;
	private Timestamp regDate;
	private String ip;
	private int reRef;  // 그룹번호. 주글번호는 그룹번호가 됨.
	private int reLev;  // 들여쓰기 레벨
	private int reSeq;  // 그룹 내에서의 순번
	
	//private AttachVo attachVo;        // JOIN에서 1:1의 관계일때
	private List<AttachVo> attachList;  // JOIN에서 1:N의 관계일때

}
