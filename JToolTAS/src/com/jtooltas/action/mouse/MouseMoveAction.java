package com.jtooltas.action.mouse;

import java.awt.Point;
import java.awt.Robot;

import com.jtooltas.action.BasicAction;

/**
 * Basic Mouse action. 
 * @date 4 oct. 2013
 * @author Eptwalabha
 */
public class MouseMoveAction implements BasicAction {

	private Point ms;
	private Point me;

	private double delay = 0;
	
	public MouseMoveAction( Point mouse_start, Point mouse_end, double delay ) {
	
		this.ms = mouse_start;
		this.me = mouse_end;
		this.delay = delay;
		System.out.println( "x0=" + mouse_start.x + "; y0=" + mouse_start.y + "; x1=" + mouse_end.x + "; y1=" + mouse_end.y + ";");
	}
	
	@Override
	public void execute(Robot robot) {

		double triggered_time = System.currentTimeMillis();
		double current = System.currentTimeMillis() - triggered_time;
		double progression = 0.0;
		
		double move_x = this.me.x - this.ms.x;
		double move_y = this.me.y - this.ms.y;
		
		System.out.println( "x=" + move_x + "; y=" + move_y + ";");
		while ( current < this.delay ) {
		
			progression = current / this.delay;
			robot.mouseMove( ( int ) ( this.ms.x + move_x * progression ), ( int ) ( this.ms.y + move_y * progression ) );
			current = System.currentTimeMillis() - triggered_time;
		}
		robot.mouseMove( this.me.x, this.me.y );
	}
}
