package aima.gui.sudoku.csp;

import aima.core.search.csp.Variable;

/*
 * @author Héctor Toral
 * 
 */
public class SudokuVariable extends Variable {
	private int value;
	private int x;
	private int y;
	
	public SudokuVariable(String _name, int _x, int _y) {
		super(_name);
		this.x = _x;
		this.y = _y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int _value) {
		this.value = _value;
	}
}