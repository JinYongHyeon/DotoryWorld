package org.dotoryWorld.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dotoryWorld.model.ListVO;
import org.dotoryWorld.model.PagingBean;
import org.dotoryWorld.model.PostDAO;
import org.dotoryWorld.model.PostVO;

public class PostListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getParameter("keyword")!=null&&request.getParameter("keyword")!="") {
			return "front?command=searchPost";
		}
		String hobbyBoardNo=request.getParameter("hobbyBoardNo");
		int totalPostCount = PostDAO.getInstance().getTotalPostCount(hobbyBoardNo);
		String pageNo = request.getParameter("pageNo");
		PagingBean  pagingBean = null;
		int postCountPerPage = 15; // 한 페이지에 보이는 게시물의 수를 설정하는 변수
		int pageCountPerPageGroup = 9; // 한 페이지 그룹에 보이는 페이지의 수를 설정하는 변수
		if(pageNo==null) {
			pagingBean = new PagingBean(totalPostCount, postCountPerPage, pageCountPerPageGroup);
		} else {
			pagingBean = new PagingBean(totalPostCount, Integer.parseInt(pageNo), postCountPerPage, pageCountPerPageGroup );
		}
		ArrayList<PostVO> postinglist = PostDAO.getInstance().getPostingList(pagingBean, hobbyBoardNo);		
		ListVO postingListPaging = new ListVO(postinglist, pagingBean);
		
		request.setAttribute("postingListPaging", postingListPaging);
		request.setAttribute("url", "/views/board/post-list.jsp");
		request.setAttribute("hobbyBoardNo", hobbyBoardNo);
		return "front?command=noticeList&noticePostBoardNo=17";
	}

}
