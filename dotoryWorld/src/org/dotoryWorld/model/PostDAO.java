package org.dotoryWorld.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class PostDAO {
	private static PostDAO instance = new PostDAO();
	private DataSource dataSource;

	public PostDAO() {
		this.dataSource = DataSourceManager.getInstance().getDataSource();
	}

	public static PostDAO getInstance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException {
		if (pstmt != null)
			pstmt.close();
		if (con != null)
			con.close();
	}

	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(pstmt, con);
	}

	/**
	 * 페이지 번호에 해당하는 게시물 목록 리스트를 반환하는 메서드 LIST SQL -> Test 후 반영하세요 SELECT
	 * b.no,b.title,b.hits,to_char(time_posted,'YYYY.MM.DD') as
	 * time_posted,m.id,m.name FROM board b , board_member m WHERE b.id=m.id order
	 * by no desc
	 * 
	 * @param pageNo
	 * @return
	 * @throws SQLException
	 */

	public ArrayList<PostVO> getPostingList(PagingBean pagingBean, String hobbyBoardNo) throws SQLException {
		ArrayList<PostVO> list = new ArrayList<PostVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.hobbypost_no,B.hobby_title,B.hobbypost_viewcount,B.time_posted,M.id,M.name,M.nickname FROM( ");
			sql.append("SELECT row_number() over(ORDER BY hobbypost_no DESC) as rnum,hobbypost_no,hobby_title,hobbypost_viewcount, ");
			sql.append("to_char(hobbypost_date,'YYYY.MM.DD') as time_posted,id,hobbyboard_no FROM hobby_post WHERE hobbyboard_no = ? ");
			sql.append(")B, member M WHERE B.id=M.id AND rnum BETWEEN ? AND ? ");
			sql.append(" ORDER BY time_posted DESC");
			pstmt = con.prepareStatement(sql.toString());
			// start, endRowNumber를 할당한다
			pstmt.setString(1, hobbyBoardNo);
			pstmt.setInt(2, pagingBean.getStartRowNumber());
			pstmt.setInt(3, pagingBean.getEndRowNumber());
			rs = pstmt.executeQuery();
			// 목록에서 게시물 content는 필요없으므로 null로 setting
			// select no,title,time_posted,hits,id,name
			while (rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setPostTitle(rs.getString(2));
				pvo.setViewCount(rs.getInt(3));
				pvo.setPostDate(rs.getString(4));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString(5));
				mvo.setName(rs.getString(6));
				mvo.setNickname(rs.getString(7));
				pvo.setMemberVO(mvo);
				list.add(pvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	public ArrayList<PostVO> getReportPostingList(PagingBean pagingBean, String boardNo) throws SQLException {
		ArrayList<PostVO> list = new ArrayList<PostVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.reportpost_no,B.report_title,B.time_posted,M.id,M.name ");
			sql.append("FROM(SELECT row_number() over(ORDER BY reportpost_no DESC) as rnum,reportpost_no,report_title,to_char(reportpost_date,'YYYY.MM.DD') as time_posted,id,category_no FROM report_post where category_no=?");
			sql.append(")B, member M WHERE B.id=M.id AND rnum BETWEEN ? AND ?");
			pstmt = con.prepareStatement(sql.toString());
			// start, endRowNumber를 할당한다
			pstmt.setString(1, boardNo);
			pstmt.setInt(2, pagingBean.getStartRowNumber());
			pstmt.setInt(3, pagingBean.getEndRowNumber());
			rs = pstmt.executeQuery();
			// 목록에서 게시물 content는 필요없으므로 null로 setting
			// select no,title,time_posted,hits,id,name
			while (rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setPostTitle(rs.getString(2));
				pvo.setPostDate(rs.getString(3));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString(4));
				mvo.setName(rs.getString(5));
				pvo.setMemberVO(mvo);
				list.add(pvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	
	public ArrayList<PostVO> getNoticePostingList(PagingBean pagingBean, String categoryNo) throws SQLException{
		ArrayList<PostVO> list=new ArrayList<PostVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=getConnection(); 
			StringBuilder sql=new StringBuilder();		
			sql.append("SELECT B.noticepost_no,B.notice_title,B.time_posted,M.id,M.name ");
			sql.append("FROM(SELECT row_number() over(ORDER BY noticepost_no DESC) as rnum,noticepost_no,notice_title,to_char(noticepost_date,'YYYY.MM.DD') as time_posted,id,category_no FROM notice_post where category_no=?");
			sql.append(")B, member M WHERE B.id=M.id AND rnum BETWEEN ? AND ?");
			pstmt=con.prepareStatement(sql.toString());	
			//start, endRowNumber를 할당한다
			pstmt.setString(1, categoryNo);
			pstmt.setInt(2, pagingBean.getStartRowNumber());
			pstmt.setInt(3, pagingBean.getEndRowNumber());
			rs=pstmt.executeQuery();	
			//목록에서 게시물 content는 필요없으므로 null로 setting
			//select no,title,time_posted,hits,id,name
			while(rs.next()){		
				PostVO pvo=new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setPostTitle(rs.getString(2));
				pvo.setPostDate(rs.getString(3));				
				MemberVO mvo=new MemberVO();
				mvo.setId(rs.getString(4));
				mvo.setName(rs.getString(5));
				pvo.setMemberVO(mvo);
				list.add(pvo);			
			}
		}finally{
			closeAll(rs,pstmt,con);
		}
		return list;
	}
	
	public ArrayList<PostVO> getPostingListById(PagingBean pagingBean, String id) throws SQLException{
		ArrayList<PostVO> list=new ArrayList<PostVO>();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			con=getConnection(); 
			StringBuilder sql=new StringBuilder();	
			sql.append("SELECT B.hobbypost_no,B.hobby_title,B.hobbypost_viewcount,B.time_posted, H.hobbyboard_title FROM( ");
			sql.append("SELECT row_number() over(ORDER BY hobbypost_no DESC) as rnum,hobbypost_no,hobby_title,hobbypost_viewcount, ");
			sql.append("to_char(hobbypost_date,'YYYY.MM.DD') as time_posted,hobbyboard_no FROM hobby_post WHERE id = ? ");
			sql.append(")B, hobbyboard H WHERE B.hobbyboard_no=H.hobbyboard_no AND rnum BETWEEN ? AND ? ");
			pstmt = con.prepareStatement(sql.toString());
			// start, endRowNumber를 할당한다
			pstmt.setString(1, id);
			pstmt.setInt(2, pagingBean.getStartRowNumber());
			pstmt.setInt(3, pagingBean.getEndRowNumber());
			rs = pstmt.executeQuery();
			// 목록에서 게시물 content는 필요없으므로 null로 setting
			// select no,title,time_posted,hits,id,name
			while (rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setPostTitle(rs.getString(2));
				pvo.setViewCount(rs.getInt(3));
				pvo.setPostDate(rs.getString(4));
				BoardVO bvo = new BoardVO();
				bvo.setBoardTitle(rs.getString(5));
				pvo.setBoardVO(bvo);
				list.add(pvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * Sequence 글번호로 게시물을 검색하는 메서드
	 * 
	 * @param no
	 * @return
	 * @throws SQLException
	 */
	public PostVO getPostingByNo(String no) throws SQLException {
		PostVO pvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select b.hobby_title,to_char(b.hobbypost_date,'YYYY.MM.DD  HH24:MI:SS') as time_posted, c.category_name");
			sql.append(",b.hobby_content,b.hobbypost_viewcount,b.id,m.name,m.nickname,b.hobbyboard_no,h.category_no, b.hobby_like, h.hobbyboard_title");
			sql.append(" from hobby_post b,member m,hobbyboard h, category c");
			sql.append(" where b.id=m.id and b.hobbypost_no=? and b.hobbyboard_no = h.hobbyboard_no and c.category_no = h.category_no");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pvo = new PostVO();
				pvo.setPostNo(no);
				pvo.setPostTitle(rs.getString("hobby_title"));
				pvo.setPostContent(rs.getString("hobby_content"));
				pvo.setViewCount(rs.getInt("hobbypost_viewcount"));
				pvo.setPostDate(rs.getString("time_posted"));
				pvo.setPostLike(rs.getInt("hobby_like"));
				BoardVO bvo = new BoardVO();
				bvo.setBoardNo(rs.getString("hobbyboard_no"));
				bvo.setBoardTitle(rs.getString("hobbyboard_title"));
				CategoryVO cvo = new CategoryVO();
				cvo.setCategoryNo(rs.getString("category_no"));
				cvo.setCategoryName(rs.getString("category_name"));
				bvo.setCategoryVO(cvo);
				pvo.setBoardVO(bvo);
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setName(rs.getString("name"));
				mvo.setNickname(rs.getString("nickname"));
				
				pvo.setMemberVO(mvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return pvo;
	}

	public PostVO getReportPostingByNo(String no) throws SQLException {
		PostVO pvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select b.report_title,to_char(b.reportpost_date,'YYYY.MM.DD  HH24:MI:SS') as time_posted");
			sql.append(",b.report_content,b.id,m.name");
			sql.append(" from report_post b,member m");
			sql.append(" where b.id=m.id and b.reportpost_no=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pvo = new PostVO();
				pvo.setPostNo(no);
				pvo.setPostTitle(rs.getString("report_title"));
				pvo.setPostContent(rs.getString("report_content"));
				pvo.setPostDate(rs.getString("time_posted"));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setName(rs.getString("name"));
				pvo.setMemberVO(mvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return pvo;
	}
	
	//공지 신고 상세페이지
	public PostVO getNoticePostingByNo(String no) throws SQLException {
		PostVO pvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select b.notice_title,to_char(b.noticepost_date,'YYYY.MM.DD  HH24:MI:SS') as time_posted");
			sql.append(",b.notice_content,b.id,m.name");
			sql.append(" from notice_post b,member m");
			sql.append(" where b.id=m.id and b.noticepost_no=?");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pvo = new PostVO();
				pvo.setPostNo(no);
				pvo.setPostTitle(rs.getString("notice_title"));
				pvo.setPostContent(rs.getString("notice_content"));
				pvo.setPostDate(rs.getString("time_posted"));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				mvo.setName(rs.getString("name"));
				pvo.setMemberVO(mvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return pvo;
	}

	/**
	 * 조회수 증가
	 * 
	 * @param no
	 * @throws SQLException
	 */
	public void updateViewcount(String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "UPDATE hobby_post SET hobbypost_viewcount=hobbypost_viewcount+1 where hobbypost_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 게시물 등록 메서드 게시물 등록 후 생성된 시퀀스를 BoardVO에 setting 한다. insert into
	 * board(no,title,content,id,time_posted)
	 * values(board_seq.nextval,?,?,?,sysdate)
	 * 
	 * @param vo
	 * @throws SQLException
	 */
	public String postWrite(PostVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String postNo = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(
					"INSERT INTO hobby_post(hobbypost_no,hobby_title,hobby_content,hobbypost_date,hobbyboard_no,id) ");
			sql.append("VALUES(hobbypost_no_seq.NEXTVAL,?,?,sysdate,?,?)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getPostTitle());
			pstmt.setString(2, vo.getPostContent());
			pstmt.setString(3, vo.getBoardVO().getBoardNo());
			pstmt.setString(4, vo.getMemberVO().getId());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = con.prepareStatement("select hobbypost_no_seq.currval from dual");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				postNo = rs.getString(1);
			}			
		} finally {
			closeAll(rs, pstmt, con);
		}
		return postNo;
	}

	public void reportPostWrite(PostVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("Insert into hobby_post(hobbypost_no,hobby_title,hobby_content,id,hobbypost_date) ");
			sql.append("values(board_seq.nextval,?,?,?,sysdate)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getPostTitle());
			pstmt.setString(2, vo.getPostContent());
			pstmt.setString(3, vo.getMemberVO().getId());
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = con.prepareStatement("select board_seq.currval from dual");
			rs = pstmt.executeQuery();
			if (rs.next())
				vo.setPostNo(rs.getString(1));
		} finally {
			closeAll(rs, pstmt, con);
		}
	}

	/**
	 * 글번호에 해당하는 게시물을 삭제하는 메서드
	 * 
	 * @param no
	 * @throws SQLException
	 */

	public void deletePosting(int no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("delete from hobby_post where hobbypost_no=?");
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void deleteReportPosting(int no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("delete from report_post where reportpost_no=?");
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 게시물 정보 업데이트하는 메서드
	 * 
	 * @param vo
	 * @throws SQLException
	 */

	public void updatePosting(PostVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("update hobby_post set hobby_title=?,hobby_content=? where hobbypost_no=?");
			pstmt.setString(1, vo.getPostTitle());
			pstmt.setString(2, vo.getPostContent());
			pstmt.setString(3, vo.getPostNo());
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void updateReportPosting(PostVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement("update board_paging set title=?,content=? where no=?");
			pstmt.setString(1, vo.getPostTitle());
			pstmt.setString(2, vo.getPostContent());
			pstmt.setString(3, vo.getPostNo());
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public int getTotalPostCount(String hobbyBoardNo) throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from hobby_post where hobbyboard_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, hobbyBoardNo);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}

	public int getTotalReportPostCount(String categoryNo) throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from report_post where category_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1); 
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}
	public int getTotalNoticePostCount(String categoryNo) throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from notice_post where category_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}
	
	public int getTotalPostCountById(String id) throws SQLException {
		int totalCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = "select count(*) from hobby_post where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next())
				totalCount = rs.getInt(1);
		} finally {
			closeAll(rs, pstmt, con);
		}
		return totalCount;
	}

	// 게시물 검색 메서드
	// 게시물 갯수만 알고싶은거임
	public int searchPost(String postTitle) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			String sql = "select count(*) from hobby_post where hobby_title LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + postTitle + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return count;
	}
	
	public ArrayList<PostVO> searchPost(String postTitle,String hobbyboardNo ,PagingBean pgb) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PostVO> searchList = new ArrayList<PostVO>();

		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select h.hobbypost_no,h.hobby_title,h.id,h.hobbypost_date,h.hobbypost_viewcount,m.nickname ");
			sql.append("from(select row_number() over(order by hobbypost_no asc) as rnum, ");
			sql.append("hobbypost_no,hobby_title,id,hobbypost_date,hobbypost_viewcount ");
			sql.append("from hobby_post where hobby_title LIKE ? AND hobbyboard_no = ?)h, member m ");
			sql.append("where h.id=m.id AND rnum between ? and ? ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + postTitle + "%");
			pstmt.setString(2,hobbyboardNo);
			pstmt.setInt(3, pgb.getStartRowNumber());
			pstmt.setInt(4, pgb.getEndRowNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setPostTitle(rs.getString(2));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString(3));
				mvo.setNickname(rs.getString(6));
				pvo.setMemberVO(mvo);
				pvo.setPostDate(rs.getString(4));
				pvo.setViewCount(rs.getInt(5));
				searchList.add(pvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return searchList;
	}
	
	// 내 게시물 조회 페이지에서 검색하는 메서드 
	
	public int searchMyPost(String postTitle, String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			String sql = "select count(*) from hobby_post where hobby_title LIKE ? AND id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + postTitle + "%");
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return count;
	}
	
	public ArrayList<PostVO> searchMyPost(String postTitle, PagingBean pgb, String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PostVO> searchMyList = new ArrayList<PostVO>();

		try {
			con = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select h.hobbypost_no,h.hobby_title,h.id,h.hobbypost_date,h.hobbypost_viewcount,ho.hobbyboard_title ");
			sql.append("from(select row_number() over(order by hobbypost_no asc) as rnum, ");
			sql.append("hobbypost_no,hobby_title,id,TO_CHAR(hobbypost_date,'yyyy-mm-dd hh24:mi:ss') AS hobbypost_date,hobbypost_viewcount,hobbyboard_no ");
			sql.append("from hobby_post where hobby_title LIKE ? AND id = ?)h, hobbyboard ho ");
			sql.append("where rnum between ? and ? AND h.hobbyboard_no = ho.hobbyboard_no");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + postTitle + "%");
			pstmt.setString(2, id);
			pstmt.setInt(3, pgb.getStartRowNumber());
			pstmt.setInt(4, pgb.getEndRowNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString(1));
				pvo.setPostTitle(rs.getString(2));
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString(3));
				pvo.setMemberVO(mvo);
				pvo.setPostDate(rs.getString(4));
				pvo.setViewCount(rs.getInt(5));
				BoardVO bvo = new BoardVO();
				bvo.setBoardTitle(rs.getString(6));
				pvo.setBoardVO(bvo);
				
				searchMyList.add(pvo);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return searchMyList;
	}
	

	// 소카테고리 리스트(운동-축구,복싱 등) 불러오는 메서드
	public ArrayList<BoardVO> getBoardList(String categoryNo) throws SQLException {
		ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT h.hobbyboard_title,h.hobbyboard_no,h.hobbyboard_imgName,c.category_content FROM hobbyboard h, category c "
					+ "WHERE h.category_no=c.category_no AND c.category_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO boardVO = new BoardVO();
				boardVO.setBoardTitle(rs.getString(1));
				boardVO.setBoardNo(rs.getString(2));
				boardVO.setBoardImage(rs.getString(3));
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setCategoryContent(rs.getString(4));
				boardVO.setCategoryVO(categoryVO);
				boardList.add(boardVO);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return boardList;
	}

	/**
	 * 모든 게시물수[어드민 게시물 관리]
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getAllCountPost() throws SQLException {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM hobby_post";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return count;
	}

	/**
	 * 어드민 게시물 관리 페이지
	 * 
	 * @param paginBean
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PostVO> getAllPostList(PagingBean paginBean) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<PostVO> pvoList = new ArrayList<PostVO>();
		try {
			con = dataSource.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT rnum,hobbypost_no,hobby_title,id,hobbypost_date,hobbypost_viewcount FROM ");
			sb.append(
					"(SELECT hobbypost_no,ROW_NUMBER() OVER(ORDER BY hobbypost_no ASC) AS rnum,hobby_title,id,TO_CHAR(hobbypost_date,'yyyy-mm-dd') as hobbypost_date,hobbypost_viewcount");
			sb.append(" FROM hobby_post");
			sb.append(")h WHERE rnum BETWEEN ? AND ?");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, paginBean.getStartRowNumber());
			pstmt.setInt(2, paginBean.getEndRowNumber());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PostVO pvo = new PostVO();
				pvo.setPostNo(rs.getString("hobbypost_no"));
				pvo.setPostTitle(rs.getString("hobby_title"));

				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString("id"));
				pvo.setMemberVO(mvo);

				pvo.setPostDate(rs.getString("hobbypost_date"));
				pvo.setViewCount(rs.getInt("hobbypost_viewcount"));

				pvoList.add(pvo);

			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return pvoList;
	}

	/**
	 * 게시물 일괄 삭제 (관리자 페이지, 내 게시물 페이지)
	 * @param no
	 * @throws SQLException
	 */

	public void deletePostingsByNo(int no) throws SQLException {
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM hobby_post WHERE hobbypost_no =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 좋아요 중복 방지를 위한 테이블에 추가
	 * 
	 * @param id
	 * @param no
	 * @throws SQLException
	 */
	public void postLike(String id, String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO hobbypostlike VALUES(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	public void postLikeRemove(String id, String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM hobbypostlike WHERE id=? AND HOBBYPOST_NO=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, no);
			pstmt.executeQuery();
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 좋아요 중복체크
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int postLikeCheck(String id, String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = -1;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT count(*) FROM hobbypostlike WHERE id=? AND hobbypost_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}

		} finally {
			closeAll(rs, pstmt, con);
		}
		return count;
	}

	/**
	 * 좋아요 기능
	 * 
	 * @param no
	 * @throws SQLException
	 */
	public void postLikeUp(String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE hobby_post SET hobby_like = hobby_like+1 WHERE hobbypost_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}

	/**
	 * 좋아요 뺴기
	 * 
	 * @param no
	 * @throws SQLException
	 */
	public void postLikeMinus(String no) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE hobby_post SET hobby_like = hobby_like-1 WHERE hobbypost_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
	}
	
	/**
	 * 베스트 취미 가져오기
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BoardVO> bestBoardList() throws SQLException{
		ArrayList<BoardVO>  betsList = new ArrayList<BoardVO>();
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT H.HOBBYBOARD_NO,hb.hobbyboard_imgName,hb.hobbyboard_title FROM(");
			sb.append("SELECT ROW_NUMBER() OVER(ORDER BY hobbypost_viewcount DESC) AS rnum, HOBBYBOARD_NO FROM (");
			sb.append("SELECT HOBBYBOARD_NO,SUM(HOBBY_LIKE) AS HOBBY_LIKE,SUM(hobbypost_viewcount) AS hobbypost_viewcount FROM HOBBY_POST GROUP BY HOBBYBOARD_NO ");
			sb.append("HAVING HOBBYBOARD_NO NOT IN('17','18')))");
			sb.append("h,HOBBYBOARD hb WHERE h.HOBBYBOARD_NO = hb.HOBBYBOARD_NO AND RNUM<=2");
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
					BoardVO bvo = new BoardVO();
					bvo.setBoardNo(rs.getString("HOBBYBOARD_NO"));
					bvo.setBoardImage(rs.getString("hobbyboard_imgName"));
					bvo.setBoardTitle(rs.getString("hobbyboard_title"));
					betsList.add(bvo);
			}
		}finally {
			closeAll(rs, pstmt, con);
		}
		return betsList;
	}
	
	/**
	 * 핫 베스트 가져오기
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<BoardVO> hotBoardList() throws SQLException{
		ArrayList<BoardVO>  betsList = new ArrayList<BoardVO>();
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT H.HOBBYBOARD_NO,hb.hobbyboard_imgName,hb.hobbyboard_title FROM(");
			sb.append("SELECT ROW_NUMBER() OVER(ORDER BY HOBBY_LIKE DESC) AS rnum, HOBBYBOARD_NO ,HOBBY_LIKE FROM (");
			sb.append("SELECT HOBBYBOARD_NO,SUM(HOBBY_LIKE) AS HOBBY_LIKE,SUM(hobbypost_viewcount) FROM HOBBY_POST GROUP BY HOBBYBOARD_NO ");
			sb.append("HAVING HOBBYBOARD_NO NOT IN('17','18')))");
			sb.append("h,HOBBYBOARD hb WHERE h.HOBBYBOARD_NO = hb.HOBBYBOARD_NO AND RNUM<=2");
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
					BoardVO bvo = new BoardVO();
					bvo.setBoardNo(rs.getString("HOBBYBOARD_NO"));
					bvo.setBoardImage(rs.getString("hobbyboard_imgName"));
					bvo.setBoardTitle(rs.getString("hobbyboard_title"));
					betsList.add(bvo);
			}
		}finally {
			closeAll(rs, pstmt, con);
		}
		return betsList;
	}


}
