package org.dotoryWorld.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dotoryWorld.model.ListVO;
import org.dotoryWorld.model.PagingBean;
import org.dotoryWorld.model.PostDAO;
import org.dotoryWorld.model.PostVO;

public class NoticeListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String noticePostBoardNo=request.getParameter("noticePostBoardNo");
		int totalPostCount=PostDAO.getInstance().getTotalPostCount(noticePostBoardNo);
		String pageNo=request.getParameter("pageNo");
		PagingBean pagingBean=null;
		int postCountPerPage = 0;
		int pageCountPerPageGroup = 0;	
		// 일반게시판에 공지글을 노출하는 경우
		ListVO postingListPaging = (ListVO) request.getAttribute("postingListPaging");
		if(postingListPaging!=null) {
			postCountPerPage=3;
			pageCountPerPageGroup=3;
			request.setAttribute("postingListPaging", postingListPaging);
			pagingBean=new PagingBean(totalPostCount, postCountPerPage, pageCountPerPageGroup);
		} else {
		// 공지게시판에 공지글을 노출하는 경우 
			request.setAttribute("hobbyBoardNo", 17);
			postCountPerPage=15;
			pageCountPerPageGroup=9;					
			if(pageNo==null)
				pagingBean=new PagingBean(totalPostCount, postCountPerPage, pageCountPerPageGroup);
			else
				pagingBean=new PagingBean(totalPostCount,Integer.parseInt(pageNo), postCountPerPage, pageCountPerPageGroup);
		}
		ArrayList<PostVO> noticeList = PostDAO.getInstance().getPostingList(pagingBean, noticePostBoardNo);
		ListVO noticeListPaging = new ListVO(noticeList, pagingBean);
		request.setAttribute("noticeListPaging", noticeListPaging);
		request.setAttribute("url", "/views/board/post-list.jsp");
		return "/views/template/main-layout.jsp";
	}

}
