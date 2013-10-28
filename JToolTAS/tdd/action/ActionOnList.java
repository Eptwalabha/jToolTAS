package action;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;

import org.junit.Before;
import org.junit.Test;

import com.jtooltas.action.ActionTAS;
import com.jtooltas.action.mouse.MouseMoveAction;

public class ActionOnList {

	ActionTAS[] actions;
	ActionTAS[] actions2;
	Robot robot;
	
	/**
	 * 
	 */
	public ActionOnList() {
		
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void prepare() {
	
		actions = new ActionTAS[10];
		for(int i = 0, size = actions.length; i < size; i++) {
			actions[i] = new ActionTAS(this.robot, new MouseMoveAction(new Point(), new Point(), 200));
			if (i > 0)
				actions[i].setPrevious(actions[i - 1]);
		}

		actions2 = new ActionTAS[20];
		for(int i = 0, size = actions2.length; i < size; i++) {
			actions2[i] = new ActionTAS(this.robot, new MouseMoveAction(new Point(), new Point(), 200));
			if (i > 0)
				actions2[i].setPrevious(actions2[i - 1]);
		}
		
//		for(int i = 0, size = actions.length - 1; i < size; i++)
//		actions[i].setNext(actions[i + 1]);
		
	}
	
	@Test
	public void getSizeOfList() {

		assertEquals(10, actions[0].size());
		assertEquals(7, actions[3].size());
		assertEquals(1, actions[9].size());
		
	}
	
	@Test
	public void gettersetter() {
		
		assertEquals(actions[1], actions[0].next());
		assertEquals(actions[4], actions[3].next());
		assertEquals(null, actions[9].next());
		
		assertEquals(actions[8], actions[9].previous());
		assertEquals(actions[5], actions[6].previous());
		assertEquals(null, actions[0].previous());
		
		assertEquals(actions[9], actions[0].getLastElement());
		assertEquals(actions[9], actions[5].getLastElement());
		assertEquals(actions[9], actions[9].getLastElement());
		
		assertEquals(actions[0], actions[9].getFirstElement());
		assertEquals(actions[0], actions[5].getFirstElement());
		assertEquals(actions[0], actions[0].getFirstElement());
		
		actions[0].setNext(null);
		assertEquals(1, actions[0].size());
		
		
	}
	
	@Test
	public void removeAnElement() {
		
		// 0-1-2-3-4-5-6-7-8-9
		actions[1].removeFromList();
		// 0- -2-3-4-5-6-7-8-9
		assertEquals(9, actions[0].size());
		assertEquals(actions[2], actions[0].next());
		
		actions[4].removeFromList();
		// 0- -2-3- -5-6-7-8-9
		assertEquals(1, actions[4].size());
		
		actions[6].removeFromList();
		// 0- -2-3- -5- -7-8-9
		assertEquals(7, actions[0].size());
		assertEquals(actions[7], actions[5].next());
		assertEquals(actions[3], actions[5].previous());

		actions[4].removeFromList();
		actions[2].removeFromList();
		actions[3].removeFromList();
		actions[9].removeFromList();
		// 0- - - - -5- -7-8-
		assertEquals(4, actions[0].size());
		assertEquals(actions[5], actions[0].next());
		assertEquals(actions[0], actions[5].previous());
		assertEquals(null, actions[8].next());

	}
	
	@Test
	public void removeMultipleElements() {
		
		// 0-1-2-3-4-5-6-7-8-9
		actions[3].removeFromList(2);
		// 0-1-2-_-_-5-6-7-8-9
		assertEquals(2, actions[3].size());
		assertEquals(8, actions[0].size());
		assertEquals(actions[2], actions[5].previous());
		assertEquals(actions[5], actions[2].next());
		
		assertEquals(null, actions[9].removeFromList(-3));
		
		actions[6].removeFromList(4);
		// 0-1-2-_-_-5-_-_-_-_
		assertEquals(4, actions[6].size());
		assertEquals(4, actions[0].size());
		assertEquals(null, actions[5].next());
		
	}
	
	@Test
	public void removeMultipleElements2() {
		
		// 0-1-2-3-4-5-6-7-8-9
		actions[8].removeFromList(2);
		// 0-1-2-3-4-5-6-7-_-_
		assertEquals(2, actions[8].size());
		assertEquals(8, actions[0].size());
		assertEquals(null, actions[7].next());
		
		actions[0].removeFromList(2);
		// _-_-2-3-4-5-6-7-_-_
		assertEquals(2, actions[0].size());
		assertEquals(6, actions[2].size());
		assertEquals(null, actions[2].previous());

	}

	
	@Test
	public void insertElementAfterAnOther() {
		
		actions[5].removeFromList();
		actions[5].clearLinks();
		assertEquals(1, actions[5].size());
		assertEquals(null, actions[5].previous());
		assertEquals(null, actions[5].next());
		
		actions[0].insertAfter(actions[5]);
		assertEquals(10, actions[0].size());
		assertEquals(actions[5], actions[0].next());
		assertEquals(actions[1], actions[5].next());
		assertEquals(actions[0], actions[5].previous());
		assertEquals(actions[5], actions[1].previous());
		
		actions[5].removeFromList();
		actions[5].clearLinks();
		actions[9].insertAfter(actions[5]);
		assertEquals(10, actions[0].size());
		assertEquals(actions[5], actions[9].next());
		assertEquals(null, actions[5].next());
		assertEquals(actions[9], actions[5].previous());
		
	}

	@Test
	public void insertAChainAfter() {
		
		assertEquals(20, actions2[0].size());
		
		actions2[9].insertAfter(actions[0]);
		
		assertEquals(30, actions2[0].size());

		assertEquals(actions2[9], actions[0].previous());
		assertEquals(actions[0], actions2[9].next());
		assertEquals(actions[9], actions2[10].previous());
		assertEquals(actions2[10], actions[9].next());
		
	}
	
	@Test
	public void insertAChainBefore() {

		actions2[0].insertBefore(actions[0]);
		
		assertEquals(20, actions2[0].size());
		assertEquals(30, actions[0].size());
		
		assertEquals(actions[9], actions2[0].previous());
		assertEquals(actions2[0], actions[9].next());
		assertEquals(null, actions[0].previous());
	}
	
	@Test
	public void insertElementBeforeAnOther() {
		
		actions[5].removeFromList();
		actions[5].clearLinks();
		assertEquals(1, actions[5].size());
		
		actions[0].insertBefore(actions[5]);
		assertEquals(9, actions[0].size());
		assertEquals(10, actions[5].size());
		assertEquals(actions[1], actions[0].next());
		assertEquals(actions[0], actions[5].next());
		assertEquals(null, actions[5].previous());
		assertEquals(actions[5], actions[0].previous());
		
		actions[5].removeFromList();
		actions[5].clearLinks();
		
		actions[9].insertBefore(actions[5]);
		assertEquals(10, actions[0].size());
		assertEquals(actions[5], actions[8].next());
		assertEquals(null, actions[9].next());
		assertEquals(actions[9], actions[5].next());
		
	}
}
