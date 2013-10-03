package com.jtooltas.action;

import java.awt.Robot;

public abstract class ActionTAS {

	protected Robot robot;
	protected ActionTAS next = null;
	protected ActionTAS preview = null;
	
	protected ActionTAS( Robot robot ) {
		this.robot = robot;
	}
	
	public void setNext( ActionTAS next ) {
		this.next = next;
		this.next.setPreview( this );
	}
	
	private void setPreview( ActionTAS preview ) {
		this.preview = preview;
	}
	
	public abstract long execute() throws InterruptedException;
	public abstract ActionTAS next();
}
