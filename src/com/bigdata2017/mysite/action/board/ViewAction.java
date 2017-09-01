package com.bigdata2017.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata2017.mysite.dao.BoardDao;
import com.bigdata2017.mysite.vo.BoardVo;
import com.bigdata2017.web.Action;
import com.bigdata2017.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String sNo = request.getParameter("no");
		
		if(sNo == null) {
			
			WebUtil.forward(
					"/WEB-INF/views/board/list.jsp",
					request,
					response);
			return;
		}
		
		Long no = Long.parseLong(sNo);
		
		BoardDao dao = new BoardDao();
		
		BoardVo vo = dao.getView(no);
		
		request.setAttribute( "vo", vo );
		
		WebUtil.forward(
				"/WEB-INF/views/board/view.jsp",
				request,
				response);
	}

}
