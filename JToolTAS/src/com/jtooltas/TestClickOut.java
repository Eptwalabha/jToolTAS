package com.jtooltas;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseListener;


public class TestClickOut implements NativeMouseInputListener, NativeMouseListener {


	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("drag x=" + arg0.getX() + "; y=" + arg0.getY() + ";");
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("move x=" + arg0.getX() + "; y=" + arg0.getY() + ";");
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("clicked");
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("click down");
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("click up");
	}

}
