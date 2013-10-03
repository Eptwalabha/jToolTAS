package com.jtooltas.action.mouse;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseClickAction implements BasicAction {

	private boolean down = true;
	private int button = InputEvent.BUTTON1_MASK;
	private long delay = 0;
	
	public MouseClickAction( boolean mouse_down, int mouse_button, long delay ) {
	
		this.down = mouse_down;
		this.button = mouse_button;
		this.delay = delay;
	}
	
	@Override
	public void execute(Robot robot) throws InterruptedException {

		long time_end_execution = System.currentTimeMillis() + delay;
		
		if ( this.down ) {
			robot.mousePress( this.button );
		} else {
			robot.mouseRelease( this.button );
		}
		
		long left = time_end_execution - System.currentTimeMillis();
		
		if ( left > 0 )
			Thread.sleep( left );
	}
}
