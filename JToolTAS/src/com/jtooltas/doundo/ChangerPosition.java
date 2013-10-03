package com.jtooltas.doundo;

public class ChangerPosition extends ActionDoUndo {

	public ChangerPosition( ActionDoUndo preview ) {
		super( preview );
	}
	
	@Override
	public ActionDoUndo redo() {
		
		return this.next;
	}

	@Override
	public ActionDoUndo undo() {
		
		return this.preview;
	}

	@Override
	public String getName() {

		return "changer position";
	}

}
