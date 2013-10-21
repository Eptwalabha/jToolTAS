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
		
		if ( this.next != null )
			this.next.previous = this ;
	}
	
	public void setPrevious( ActionTAS previous ) {
		this.previous = previous;

		if ( this.previous != null )
			this.previous.next = this ;
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
	
	public int size() {
		if ( this.next != null )
			return this.next.size() + 1;
		return 1;
	}

	public void forgetNext() {
		this.next = null;
	}

	public void removeFromList() {
	
		if ( this.previous != null ) {
			
			if ( this.next != null)
				this.previous.setNext(this.next);
			else
				this.previous.setNext(null);
		}
		
		if ( this.next != null ) {
			
			if (this.previous != null)
				this.next.setPrevious(this.previous);
			else
				this.next.setPrevious(null);
		}
	}

	public void clearLinks() {
		
		this.next = null;
		this.previous = null;
	}

	public void insertAfter(ActionTAS action) {
		
		if (this.next != null)
			action.setNext( this.next );
		
		this.setNext(action);
	}

	public void insertBefore(ActionTAS action) {
		
		if (this.previous != null)
			action.setPrevious(this.previous);
		
		this.setPrevious(action);
	}
}
