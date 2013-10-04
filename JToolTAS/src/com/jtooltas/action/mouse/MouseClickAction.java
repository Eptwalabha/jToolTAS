package com.jtooltas.action.mouse;

import java.awt.Robot;
import java.awt.event.InputEvent;

import com.jtooltas.action.BasicAction;

/**
 * Basic Mouse action. 
 * @date 1 oct. 2013
 * @author Eptwalabha
 */
public class MouseClickAction implements BasicAction {

	private boolean mouse_button_down = true;
	private int mouse_button_key = InputEvent.BUTTON1_DOWN_MASK;
	private long delay = 0;
	
	public MouseClickAction( boolean mouse_button_down, int mouse_button_key, long delay ) {
		this.mouse_button_down = mouse_button_down;
		this.mouse_button_key = mouse_button_key;
		this.delay = delay;
	}
	
	@Override
	public void execute(Robot robot) throws InterruptedException {

		long time_end_execution = System.currentTimeMillis() + delay;
		
		if ( this.mouse_button_down ) {
			robot.mousePress( this.mouse_button_key );
		} else {
			robot.mouseRelease( this.mouse_button_key );
		}
		
		long left = time_end_execution - System.currentTimeMillis();
		
		if ( left > 0 )
			Thread.sleep( left );
	}
}
