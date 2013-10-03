package com.jtooltas.recorder;

import java.util.ArrayList;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;

import com.jtooltas.action.mouse.BasicAction;
import com.jtooltas.action.mouse.MouseClickAction;
import com.jtooltas.action.mouse.MouseMoveAction;
import com.jtooltas.commun.MousePosition;

public class TASRecorder implements Runnable, NativeMouseInputListener, NativeMouseListener {

	public ArrayList<BasicAction> actions;
	private MousePosition last_position;
	
	private boolean running = false;
	private boolean reset = true;
	private long delay;
	
	public TASRecorder( long delay_milli ) {
		this.delay = delay_milli;
		this.reset = true;
	}
	
	@Override
	public void run() {
		this.running = true;
		
		if (this.reset)
			this.actions = new ArrayList<BasicAction>();
		
		long next_tic = System.currentTimeMillis() + this.delay;
		
		MousePosition new_position = null;
		MousePosition old_position = null;
		
		while ( this.running ) {
			
			if ( System.currentTimeMillis() >= next_tic ) {
				// on récupère la position de la souris
				new_position = this.last_position;
				
				if ( old_position == null )
					old_position = new_position;
				
				MouseMoveAction position_action = new MouseMoveAction( old_position.clone(), new_position.clone(), 0 );
				
				old_position = new_position;
				this.actions.add( position_action );
				
				next_tic += this.delay;
			}
		}
	}

	public void stopRecording() {
		this.running = false;
		this.reset = true;
	}
	
	public void pauseRecording() {
		this.running = false;
		this.reset = false;
	}
	
	public void changeDelay( long new_delay_milli ) {
		this.delay = new_delay_milli;
	}

	public void setMouseClick(boolean mouse_down, int mouse_button) {
		//  une nouvelle action MouseClick est créée.
		BasicAction action = new MouseClickAction( mouse_down, mouse_button, this.delay);
		this.actions.add(action);
	}
	
	public ArrayList<BasicAction> getListActions() {
		return this.actions;
	}

	public MousePosition getLastMousePosition() {
		return this.last_position;
	}
	
	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		
		this.last_position.setMousePosition( arg0.getX(), arg0.getY() );
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
	
		this.setMouseClick( true, arg0.getButton() );
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {

		this.setMouseClick( false, arg0.getButton() );
	}
}
