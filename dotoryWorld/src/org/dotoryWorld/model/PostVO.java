package org.dotoryWorld.model;

public class PostVO {
	private String postNo;
	private String postTitle;
	private String postContent;
	private String postDate;
	private int postLike;
	private int viewCount;
	private BoardVO boardVO;
	private MemberVO memberVO;

	public PostVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PostVO(String postNo, String postTitle, String postContent, String postDate, int postLike, int viewCount,
			BoardVO boardVO, MemberVO memberVO) {
		super();
		this.postNo = postNo;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postDate = postDate;
		this.postLike = postLike;
		this.viewCount = viewCount;
		this.boardVO = boardVO;
		this.memberVO = memberVO;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public int getPostLike() {
		return postLike;
	}

	public void setPostLike(int postLike) {
		this.postLike = postLike;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public BoardVO getBoardVO() {
		return boardVO;
	}

	public void setBoardVO(BoardVO boardVO) {
		this.boardVO = boardVO;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	@Override
	public String toString() {
		return "PostVO [postNo=" + postNo + ", postTitle=" + postTitle + ", postContent=" + postContent + ", postDate="
				+ postDate + ", postLike=" + postLike + ", viewCount=" + viewCount + ", boardVO=" + boardVO
				+ ", memberVO=" + memberVO + "]";
	}

}
