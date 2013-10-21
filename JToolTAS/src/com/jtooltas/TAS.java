package com.jtooltas;

import java.awt.AWTException;
import java.awt.Robot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.jtooltas.recorder.TASPlayer;
import com.jtooltas.recorder.TASRecorder;
import com.jtooltas.swing.DebugFrameTAS;

public class TAS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( "plop" );
		
		try{
			
			GlobalScreen.registerNativeHook();
			
			Robot robot = new Robot();
			
			TASRecorder recorder = new TASRecorder(50);
			GlobalScreen.getInstance().addNativeMouseMotionListener(recorder);
			GlobalScreen.getInstance().addNativeMouseListener(recorder);
			
			TASPlayer player = new TASPlayer();
			
			new DebugFrameTAS(recorder, player, robot);
			
		} catch ( AWTException e ) {
			e.printStackTrace();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
	}
}