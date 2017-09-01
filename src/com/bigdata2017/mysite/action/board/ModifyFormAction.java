package com.bigdata2017.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata2017.mysite.dao.BoardDao;
import com.bigdata2017.mysite.vo.BoardVo;
import com.bigdata2017.web.Action;
import com.bigdata2017.web.util.WebUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no = request.getParameter("no");
		
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		
		
		vo = dao.getView(Long.parseLong(no));
		
		request.setAttribute("vo", vo);
		
		WebUtil.forward(
				"/WEB-INF/views/board/modify.jsp",
				request,
				response);

	}

}
