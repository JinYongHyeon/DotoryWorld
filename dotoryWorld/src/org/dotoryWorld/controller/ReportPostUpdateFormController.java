package org.dotoryWorld.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dotoryWorld.model.PostDAO;
import org.dotoryWorld.model.PostVO;

public class ReportPostUpdateFormController implements Controller {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception  {
		
		PostVO vo = PostDAO.getInstance().getReportPostingByNo(request.getParameter("no"));		
		request.setAttribute( "pvo", vo);

		return "/views/board/report-post-update-form.jsp";
	}

}
