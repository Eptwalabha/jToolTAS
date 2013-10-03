package com.jtooltas.action.mouse;

import java.awt.Robot;

import com.jtooltas.commun.MousePosition;

public class MouseMoveAction implements BasicAction {

	private MousePosition ms;
	private MousePosition me;

	private long delay = 0;
	
	public MouseMoveAction( MousePosition mouse_start, MousePosition mouse_end, long delay ) {
	
		this.ms = mouse_start;
		this.me = mouse_end;
		this.delay = delay;
	}
	
	@Override
	public void execute(Robot robot) {

		long triggered_time = System.currentTimeMillis();
		float current = System.currentTimeMillis() - triggered_time;
		float progression = 0f;
		
		int move_x = this.me.x - this.ms.x;
		int move_y = this.me.y - this.ms.y;
		
		while ( current < this.delay ) {
		
			progression = current / this.delay;
			
			robot.mouseMove( ( int ) ( this.ms.x + move_x * progression ), ( int ) ( this.ms.y + move_y * progression ) );
			current = System.currentTimeMillis() - triggered_time;
		}
		
		robot.mouseMove( this.me.x, this.me.y );
	}
}
