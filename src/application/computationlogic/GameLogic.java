package application.computationlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.constants.GameState;
import application.constants.Rows;
import application.problemdomain.SudokuGame;

public class GameLogic {
	
	public static SudokuGame getNewGame() {
		return new SudokuGame(
				GameState.NEW,
				GameGenerator.getNewGameGrid()
		);
	}

	public static GameState checkForCompletion(int[][] grid) {
		if (sudokuIsInvalid(grid)) return GameState.ACTIVE;
		if (tilesAreNotFilled(grid)) return GameState.ACTIVE;
		return GameState.COMPLETE;
	}
	
	private static boolean tilesAreNotFilled(int[][] grid) {
		for (int x = 0; x < SudokuGame.GRID_BOUNDARY; x++) {
			for (int y = 0; y < SudokuGame.GRID_BOUNDARY; y++) {
				if (grid[x][y] == 0) return true;
			}
		}
		return false;
	}

	public static boolean sudokuIsInvalid(int[][] grid) {
		if (rowsAreInvalid(grid)) return true;
		if (columnsAreInvalid(grid)) return true;
		if (squaresAreInvalid(grid)) return true;
		else return false;
	}

	private static boolean rowsAreInvalid(int[][] grid) {
		for (int y = 0; y < SudokuGame.GRID_BOUNDARY; y++) {
			List<Integer> row = new ArrayList<Integer>();
			for (int x = 0; x < SudokuGame.GRID_BOUNDARY; x++) {
				row.add(grid[x][y]);
			}
			
			if (collectionHasRepeats(row)) return true;
		}
		
		return false;
	}

	private static boolean columnsAreInvalid(int[][] grid) {
		for (int x = 0; x < SudokuGame.GRID_BOUNDARY; x++) {
			List<Integer> column = new ArrayList<Integer>();
			for (int y = 0; y < SudokuGame.GRID_BOUNDARY; y++) {
				column.add(grid[x][y]);
			}
			
			if (collectionHasRepeats(column)) return true;
		}
		
		return false;
	}

	private static boolean squaresAreInvalid(int[][] grid) {
		if (rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;
		if (rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;
		if (rowOfSquaresIsInvalid(Rows.BOTTOM, grid)) return true;
		return false;
	}

	private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
		switch(value) {
			case TOP:
				if (squareIsInvalid(0, 0, grid)) return true;
				if (squareIsInvalid(3, 0, grid)) return true;
				if (squareIsInvalid(6, 0, grid)) return true;
				return false;
			case MIDDLE:
				if (squareIsInvalid(0, 3, grid)) return true;
				if (squareIsInvalid(3, 3, grid)) return true;
				if (squareIsInvalid(6, 3, grid)) return true;
				return false;
			case BOTTOM:
				if (squareIsInvalid(0, 6, grid)) return true;
				if (squareIsInvalid(3, 6, grid)) return true;
				if (squareIsInvalid(6, 6, grid)) return true;
				return false;
			default:
				return true;
		}
	}

	private static boolean squareIsInvalid(int x, int y, int[][] grid) {
		int yEnd = y + 3;
		int xEnd = x + 3;
		
		List<Integer> square = new ArrayList<>();
		
		while (y < yEnd) {
			while (x < xEnd) {
				square.add(grid[x][y]);
				x++;
			}
			x -= 3;
			y++;
		}
		
		return collectionHasRepeats(square);
	}

	private static boolean collectionHasRepeats(List<Integer> collection) {		
		for (int value = 1; value <= SudokuGame.GRID_BOUNDARY; value++) {
			if (Collections.frequency(collection, value) > 1) return true;
		}
		
		return false;
	}

}
