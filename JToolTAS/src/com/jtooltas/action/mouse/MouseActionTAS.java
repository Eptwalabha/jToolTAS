package com.jtooltas.action.mouse;

import java.awt.Robot;

import com.jtooltas.action.ActionTAS;

/**
 * 
 * @author Eptwalabha
 */
public class MouseActionTAS extends ActionTAS {

	private BasicAction action;
	
	public MouseActionTAS( Robot robot, BasicAction action ) {
		super( robot );
		this.action = action;
	}

	@Override
	public long execute() throws InterruptedException {
		// TODO Auto-generated method stub
		this.action.execute( this.robot );
		return 0;
	}

	@Override
	public ActionTAS next() {
		return this.next;
	}

}
