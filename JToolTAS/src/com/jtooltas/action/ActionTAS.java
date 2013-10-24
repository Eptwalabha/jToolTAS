package com.jtooltas.action;

import java.awt.Robot;

public class ActionTAS {

	private Robot robot;
	private ActionTAS next = null;
	private ActionTAS previous = null;
	private BasicAction action = null;
	
	public ActionTAS(Robot robot, BasicAction action) {
		this.robot = robot;
		this.action = action;
	}
	
	public void setNext(ActionTAS next) {
		this.next = next;
		
		if (this.next != null)
			this.next.previous = this ;
	}
	
	public void setPrevious(ActionTAS previous) {
		this.previous = previous;

		if (this.previous != null)
			this.previous.next = this ;
	}
	
	public long execute() throws InterruptedException {
		this.action.execute(this.robot);
		return 0;
	}
	
	public ActionTAS next() {
		return this.next;
	}
	
	public ActionTAS previous() {
		return this.previous;
	}

	public void setAction(BasicAction action) {
		this.action = action;
	}
	
	public int sizeRecursive() {
		if (this.next != null)
			return this.next.sizeRecursive() + 1;
		return 1;
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
	
		ActionTAS cursor = this;
		int size = 1;
		while(cursor.next != null) {
			
			size++;
			cursor = cursor.next;
		}
		return size;
	}

	public void forgetNext() {
		this.next = null;
	}
	
	/**
	 * Returns the element of the chain at the given index.
	 * If there isn't any element at the given index, the method returns null.
	 * @param index
	 * @return
	 */
	public ActionTAS get(int index) {
		
		if(index == 0)
			return this;
		
		ActionTAS cursor = this;
		
		while(index != 0) {
			if(index > 0) {
				if(cursor.next == null)
					return null;
				cursor = cursor.next;
				index--;
			} else {
				if(cursor.previous == null)
					return null;
				cursor = cursor.previous;
				index++;
			}
		}
		return cursor;
	}

	/**
	 * Removes this element from its chain.
	 * The element before and the element after the element to remove (if both aren't null) will be linked together.
	 * @return The removed element.
	 */
	public ActionTAS removeFromList() {
		return this.removeFromList(0, 0);
	}
	
	/**
	 * Removes a sub-chain of elements from the current chain.
	 * The chain to remove begin at the current element and has a size of the given length.
	 * The element before and the element after the element to remove (if both aren't null) will be linked together.
	 * If the length is negative or equal to 0, the method returns null.
	 * @param length the size of the sub-chain to remove
	 * @return The first element of the removed sub-chain.
	 */
	public ActionTAS removeFromList(int length) {
		return this.removeFromList(0, length - 1);
	}
	
	/**
	 * Removes a sub-chain of elements from the current chain. The chain to remove begin at the start index and finish at the end index.
	 * The index of the first element of a list is 0
	 * Indexes start and end can be negative (-1 is the first element before the current one). 
	 * If the index start is inferior to the index end, the method returns null.
	 * If there isn't any element at the given index (start or end or both), the method returns null.
	 * @param start the index of the first element.
	 * @param end the index of the last element.
	 * @return The first element of the removed sub-chain.
	 */
	public ActionTAS removeFromList(int start, int end) {
		
		if(start > end)
			return null;
		
		ActionTAS first = this.get(start);
		ActionTAS last = this.get(end);
		
		if (first == null || last == null)
			return null;
		
		if (first.previous != null) {
			if (last.next != null)
				first.previous.setNext(last.next);
			else
				first.previous.setNext(null);
		}
		
		if (last.next != null) {
			if (first.previous != null)
				last.next.setPrevious(first.previous);
			else
				last.next.setPrevious(null);
		}
		
		first.previous = null;
		last.next = null;
		
		return first;
	}
	
	public void clearLinks() {
		this.next = null;
		this.previous = null;
	}

	public void insertAfter(ActionTAS action) {
		
		if (this.next != null)
			action.setNext(this.next);
		
		this.setNext(action);
	}

	public void insertBefore(ActionTAS action) {
		
		if (this.previous != null)
			action.setPrevious(this.previous);
		
		this.setPrevious(action);
	}
}
