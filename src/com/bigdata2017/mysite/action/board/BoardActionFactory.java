package com.bigdata2017.mysite.action.board;

import com.bigdata2017.web.Action;
import com.bigdata2017.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		
		Action action = null;
		
		if		 ( "add".equals( actionName ) ) {
			action = new AddAction();
		} else if( "modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if( "modify".equals(actionName)) {
			action = new ModifyAction();
		} else if( "view".equals(actionName)) {
			action = new ViewAction();
		}
		else {
			action = new ListAction();
		}
		
		return action;
	}

}
