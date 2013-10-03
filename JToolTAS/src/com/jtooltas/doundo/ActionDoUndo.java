package com.jtooltas.doundo;

public abstract class ActionDoUndo {

	protected ActionDoUndo preview;
	protected ActionDoUndo next;
	
	public ActionDoUndo( ActionDoUndo preview ) {
		
		this.preview = preview;
		this.preview.setNext( this );
	}
	
	public ActionDoUndo setNext( ActionDoUndo next ) {
		
		this.next = next;
		return this;
	}

	public abstract ActionDoUndo redo();
	
	public abstract ActionDoUndo undo();
	
	public abstract String getName();
	
}
