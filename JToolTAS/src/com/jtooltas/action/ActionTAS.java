package com.jtooltas.action;

import java.awt.Robot;

public class ActionTAS {

	private Robot robot;
	private ActionTAS next = null;
	private ActionTAS preview = null;
	private BasicAction action = null;
	
	public ActionTAS( Robot robot, BasicAction action ) {
		this.robot = robot;
		this.action = action;
	}
	
	public void setNext( ActionTAS next ) {
		this.next = next;
		this.next.setPreview( this );
	}
	
	private void setPreview( ActionTAS preview ) {
		this.preview = preview;
	}
	
	public long execute() throws InterruptedException {
		this.action.execute( this.robot );
		return 0;
	}
	
	public ActionTAS next() {
		return this.next;
	}
	
	public ActionTAS preview() {
		return this.preview;
	}

	public void setAction( BasicAction action ) {
		this.action = action;
	}
}
