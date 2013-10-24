package com.jtooltas.recorder;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;

import com.jtooltas.action.ActionTAS;
import com.jtooltas.action.BasicAction;
import com.jtooltas.action.mouse.MouseClickAction;
import com.jtooltas.action.mouse.MouseMoveAction;

public class TASRecorder implements Runnable, NativeMouseInputListener, NativeMouseListener {

	public ArrayList<BasicAction> actions = new ArrayList<BasicAction>();
	private Point last_position = new Point();
	
	private boolean running = false;
	private boolean reset = true;
	private long delay;
	
	public TASRecorder(long delay_milli) {
		this.delay = delay_milli;
		this.reset = true;
	}
	
	@Override
	public void run() {
		this.running = true;
		
		if (this.reset)
			this.actions = new ArrayList<BasicAction>();
		
		try { 
			System.out.print("the program will start the mouse record in 1 second\n");
			Thread.sleep(1000);
			System.out.println("recording now!");
		} catch (InterruptedException e) { e.printStackTrace();	}
		
		long next_tic = System.currentTimeMillis() + this.delay;
		
		Point new_position = null;
		Point old_position = null;
		
		while (this.running) {
			
			if (System.currentTimeMillis() >= next_tic) {
				// mouse location
				new_position = new Point(this.last_position);
				
				if (old_position == null)
					old_position = new Point(new_position);
				
				MouseMoveAction position_action = new MouseMoveAction(new Point(old_position), new Point(new_position), this.delay);
				
				old_position = new Point(new_position);
				this.actions.add(position_action);
				
				next_tic += this.delay;
			}
		}
		
		System.out.println("end of record! size=" + this.actions.size());
	}

	public void stopRecording() {
		this.running = false;
		this.reset = true;
	}
	
	public void pauseRecording() {
		this.running = false;
		this.reset = false;
	}
	
	public void changeDelay(long new_delay_milli) {
		this.delay = new_delay_milli;
	}

	public void setMouseClick(boolean mouse_down, int mouse_button) {
		
		int mouse_button_event = InputEvent.getMaskForButton(mouse_button);
		
		//  a new MouseClick action is created.
		BasicAction action = new MouseClickAction(mouse_down, mouse_button_event, this.delay);
		this.actions.add(action);
	}
	
	public ArrayList<BasicAction> getListActions() {
		return this.actions;
	}

	public Point getLastMousePosition() {
		return this.last_position;
	}

	/**
	 * Build all the ActionTAS (linked together) from the BasicAction recorded previously.
	 * @param robot
	 * @return ActionTAS (or null if there isn't any record).
	 */
	public ActionTAS getActionTASListFromRecord(Robot robot) {
		
		ActionTAS list = null;
		ActionTAS carret = null;
		
		for (BasicAction action : this.actions) {
		
			ActionTAS new_action = new ActionTAS(robot, action);
			
			if (carret == null) {
				list = new_action;
			} else {
				carret.setNext(new_action);
			}
			
			carret = new_action;
		}
		
		return list;
	}
	
	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		this.last_position.setLocation(arg0.getX(), arg0.getY());
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		this.last_position.setLocation(arg0.getX(), arg0.getY());
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
	
		if (this.running)
			this.setMouseClick(true, arg0.getButton());
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {

		if (this.running)
			this.setMouseClick(false, arg0.getButton());
	}

	/**
	 * @return
	 */
	public boolean isRecording() {
		return this.running;
	}
}
