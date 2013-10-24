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
	
	public MouseMoveAction(Point mouse_start, Point mouse_end, double delay) {
	
		this.ms = mouse_start;
		this.me = mouse_end;
		this.delay = delay;
	}
	
	@Override
	public void execute(Robot robot) {
		
		double triggered_time = (double) System.currentTimeMillis();
		double current = ((double) System.currentTimeMillis()) - triggered_time;
		double progression = 0.0;
		
		int move_x = this.me.x - this.ms.x;
		int move_y = this.me.y - this.ms.y;
		
		while (current < this.delay) {
		
			progression = current / this.delay;
			robot.mouseMove((int) (this.ms.x + move_x * progression), (int) (this.ms.y + move_y * progression));
			current = ((double) System.currentTimeMillis()) - triggered_time;
		}
		
		robot.mouseMove(this.me.x, this.me.y);
	}
}
