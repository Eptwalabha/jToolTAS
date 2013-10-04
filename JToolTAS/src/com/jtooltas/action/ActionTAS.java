package com.jtooltas.action;

import java.awt.Robot;

public class ActionTAS {

	private Robot robot;
	private ActionTAS next = null;
	private ActionTAS previous = null;
	private BasicAction action = null;
	
	public ActionTAS( Robot robot, BasicAction action ) {
		this.robot = robot;
		this.action = action;
	}
	
	public void setNext( ActionTAS next ) {
		this.next = next;
		this.next.setPrevious( this );
	}
	
	private void setPrevious( ActionTAS preview ) {
		this.previous = preview;
	}
	
	public long execute() throws InterruptedException {
		this.action.execute( this.robot );
		return 0;
	}
	
	public ActionTAS next() {
		return this.next;
	}
	
	public ActionTAS previous() {
		return this.previous;
	}

	public void setAction( BasicAction action ) {
		this.action = action;
	}
}
