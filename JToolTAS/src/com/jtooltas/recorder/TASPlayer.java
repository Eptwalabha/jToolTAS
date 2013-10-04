/**
 * 
 */
package com.jtooltas.recorder;

import com.jtooltas.action.ActionTAS;

/**
 *
 * @date 4 oct. 2013
 * @author Eptwalabha
 */
public class TASPlayer {
	
	private boolean playing = false;
	
	private ActionTAS actions = null;
	
	public TASPlayer() {
	}
	
	/**
	 * @return
	 */
	public boolean isPlaying() {
		return this.playing;
	}

	/**
	 * @param actionTASListFromRecord
	 */
	public void setActions(ActionTAS actions) {
		this.actions = actions;
	}

	/**
	 * @throws InterruptedException 
	 * 
	 */
	public void play() throws InterruptedException {
		
		System.out.println("start playing");
		this.playing = true;
		ActionTAS next = this.actions;
		long i = 0;
		while ( this.playing && next != null ) {
			
			System.out.println( "boucle n°" + i );
			i++;
			next.execute();
			next = next.next(); // ho ho ho!
		}
		System.out.println("end playing");
		this.playing = false;
	}

	/**
	 * 
	 */
	public void stopPlaying() {
		this.playing = false;
	}
	
	
}
