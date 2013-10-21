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
	
	
	@Before
	public void prepare() {
	
		Robot robot;
		try {
			robot = new Robot();
			
			actions = new ActionTAS[ 10 ];
			for( int i = 0, size = actions.length; i < size; i++ ) {
				actions[ i ] = new ActionTAS( robot, new MouseMoveAction( new Point(), new Point(), 200 ) );
				if ( i > 0 )
					actions[ i ].setPrevious( actions[ i - 1 ] );
			}
//			for( int i = 0, size = actions.length - 1; i < size; i++ )
//				actions[ i ].setNext( actions[ i + 1 ] );
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getSizeOfList() {

		assertEquals( 10, actions[ 0 ].size());
		assertEquals( 7, actions[ 3 ].size());
		assertEquals( 1, actions[ 9 ].size());
		
	}
	
	@Test
	public void gettersetter() {
		
		assertEquals( actions[ 1 ], actions[ 0 ].next() );
		assertEquals( actions[ 4 ], actions[ 3 ].next() );
		assertEquals( null, actions[ 9 ].next() );
		
		assertEquals( actions[ 8 ], actions[ 9 ].previous() );
		assertEquals( actions[ 5 ], actions[ 6 ].previous() );
		assertEquals( null, actions[ 0 ].previous() );
		
		actions[ 0 ].setNext( null );
		assertEquals( 1, actions[ 0 ].size());
		
	}
	
	@Test
	public void removeAnElement() {
		
		// 0-1-2-3-4-5-6-7-8-9
		actions[ 1 ].removeFromList();
		// 0- -2-3-4-5-6-7-8-9
		assertEquals( 9, actions[ 0 ].size());
		assertEquals( actions[ 2 ], actions[ 0 ].next() );
		
		actions[ 4 ].removeFromList();
		// 0- -2-3- -5-6-7-8-9
		assertEquals( 6, actions[ 4 ].size() );
		
		actions[ 6 ].removeFromList();
		// 0- -2-3- -5- -7-8-9
		assertEquals( 7, actions[ 0 ].size());
		assertEquals( actions[ 7 ], actions[ 5 ].next() );
		assertEquals( actions[ 3 ], actions[ 5 ].previous() );

		actions[ 4 ].removeFromList();
		actions[ 2 ].removeFromList();
		actions[ 3 ].removeFromList();
		actions[ 9 ].removeFromList();
		// 0- - - - -5- -7-8-
		assertEquals( 4, actions[ 0 ].size() );
		assertEquals( actions[ 5 ], actions[ 0 ].next() );
		assertEquals( actions[ 0 ], actions[ 5 ].previous() );
		assertEquals( null, actions[ 8 ].next() );

	}

	@Test
	public void insertElementAfterAnOther() {
		
		actions[ 5 ].removeFromList();
		actions[ 5 ].clearLinks();
		assertEquals( 1, actions[ 5 ].size() );
		assertEquals( null, actions[ 5 ].previous() );
		assertEquals( null, actions[ 5 ].next() );
		
		actions[ 0 ].insertAfter( actions[ 5 ] );
		assertEquals( 10, actions[ 0 ].size() );
		assertEquals( actions[ 5 ], actions[ 0 ].next() );
		assertEquals( actions[ 1 ], actions[ 5 ].next() );
		assertEquals( actions[ 0 ], actions[ 5 ].previous() );
		assertEquals( actions[ 5 ], actions[ 1 ].previous() );
		
		actions[ 5 ].removeFromList();
		actions[ 5 ].clearLinks();
		actions[ 9 ].insertAfter( actions[ 5 ] );
		assertEquals( 10, actions[ 0 ].size() );
		assertEquals( actions[ 5 ], actions[ 9 ].next() );
		assertEquals( null, actions[ 5 ].next() );
		assertEquals( actions[ 9 ], actions[ 5 ].previous() );
		
	}

	@Test
	public void insertElementBeforeAnOther() {
		
		actions[ 5 ].removeFromList();
		actions[ 5 ].clearLinks();
		assertEquals( 1, actions[ 5 ].size() );
		
		actions[ 0 ].insertBefore( actions[ 5 ] );
		assertEquals( 9, actions[ 0 ].size() );
		assertEquals( 10, actions[ 5 ].size() );
		assertEquals( actions[ 1 ], actions[ 0 ].next() );
		assertEquals( actions[ 0 ], actions[ 5 ].next() );
		assertEquals( null, actions[ 5 ].previous() );
		assertEquals( actions[ 5 ], actions[ 0 ].previous() );
		
		actions[ 5 ].removeFromList();
		actions[ 5 ].clearLinks();
		
		actions[ 9 ].insertBefore( actions[ 5 ] );
		assertEquals( 10, actions[ 0 ].size() );
		assertEquals( actions[ 5 ], actions[ 8 ].next() );
		assertEquals( null, actions[ 9 ].next() );
		assertEquals( actions[ 9 ], actions[ 5 ].next() );
		
	}
}
