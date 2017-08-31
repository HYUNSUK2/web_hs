package com.bigdata2017.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata2017.mysite.action.board.BoardActionFactory;
import com.bigdata2017.web.Action;
import com.bigdata2017.web.ActionFactory;

/**
 * Servlet implementation class BoardServelet
 */
@WebServlet("/board")
public class BoardServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter( "a" );
		ActionFactory af = new BoardActionFactory();
		
		Action action = af.getAction( actionName );
		System.out.println("in servlet.......");
		action.execute(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
