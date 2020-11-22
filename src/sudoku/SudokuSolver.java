package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuSolver {
	private int sudokuTable[][];

	List<SudokuPossibleNumbers> allListsOfPossibleNumbers = new ArrayList<SudokuSolver.SudokuPossibleNumbers>();

	int countSolutions = 0;
	int[][] resultTable = new int[9][9];

	public SudokuSolver() {
		sudokuTable = new int[9][9];

	}

	public SudokuSolver(int[][] grid) {
		
		if (checkGrid(grid)) {
			sudokuTable = grid; // Your code here!
		} else {
			throw new IllegalArgumentException();
		}
	}

	private boolean sameNumbersInRow(int[][] grid) {

		for (int[] row : grid) {
			List<Integer> numInRow = new ArrayList<>();
			for (int num : row) {
				if (num != 0) {
					if (numInRow.contains(num)) {
						return false;
					} else {
						numInRow.add(num);
					}
				}
			}
		}
		return true;
	}

	private boolean checkGrid(int[][] grid) {
		if (grid.length != 9) {
			return false;
		} else {
			for (int[] is : grid) {
				if (is.length != 9) {
					return false;
				}
			}
		}
		return sameNumbersInRow(grid);
		
	}

	private int[][] copyTable(int[][] old) {
		int[][] current = new int[9][9];
		for (int i = 0; i < old.length; i++) {
			for (int j = 0; j < old[i].length; j++) {
				current[i][j] = old[i][j];
			}
		}
		return current;
	}

	public void valuesInRangeOneToNine(int[][] table) {
		int countKnown = 0;
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (sudokuTable[i][j] != 0) {
					countKnown++;
				}
				if (sudokuTable[i][j] < 0 || sudokuTable[i][j] > 9) {
					throw new IllegalArgumentException();
				}
			}
		}
		if (countKnown < 17) {
			throw new IllegalArgumentException();
		}
	}

	public int[][] solve() {

		if (sudokuTable.length != 9 || sudokuTable[0].length != 9) {
			throw new IllegalArgumentException();
		}

		valuesInRangeOneToNine(sudokuTable);
		checkAllPossibleNumbers(sudokuTable);

		if (fillRecu(sudokuTable) && countSolutions == 1) {
			return resultTable;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void updPossibleNumH(int posi, int posj, int[][] sudokuTable, List<Integer> lOpN) {

		int j = 0;
		while (j <= 8) {
			lOpN.remove(Integer.valueOf(sudokuTable[posi][j]));
			j++;
		}
	}

	public void updPossibleNumV(int posi, int posj, int[][] sudokuTable, List<Integer> lOpN) {

		int i = 0;
		while (i <= 8) {
			lOpN.remove(Integer.valueOf(sudokuTable[i][posj]));
			i++;
		}
	}

	public void updPossibleNumBySTab(int posi, int posj, int[][] sudokuTable, List<Integer> lOpN) {

		int sTab;
		sTab = whichSTab(posi, posj);
		if (sTab == 1) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 2) {
			for (int i = 0; i < 3; i++) {
				for (int j = 3; j < 6; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 3) {
			for (int i = 0; i < 3; i++) {
				for (int j = 6; j < 9; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 4) {
			for (int i = 3; i < 6; i++) {
				for (int j = 0; j < 3; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 5) {
			for (int i = 3; i < 6; i++) {
				for (int j = 3; j < 6; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 6) {
			for (int i = 3; i < 6; i++) {
				for (int j = 6; j < 9; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 7) {
			for (int i = 6; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 8) {
			for (int i = 6; i < 9; i++) {
				for (int j = 3; j < 6; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		} else if (sTab == 9) {
			for (int i = 6; i < 9; i++) {
				for (int j = 6; j < 9; j++) {
					lOpN.remove((Integer.valueOf(sudokuTable[i][j])));
				}
			}
		}
	}

	public int whichSTab(int i, int j) {
		if (i <= 2 && j <= 2) {
			return 1;
		} else if (i <= 2 && j > 2 && j <= 5) {
			return 2;
		} else if (i <= 2 && j > 5 && j <= 8) {
			return 3;
		} else if (i > 2 && i <= 5 && j <= 2) {
			return 4;
		} else if (i > 2 && i < 6 && j > 2 && j <= 5) {
			return 5;
		} else if (i > 2 && i < 6 && j > 5 && j <= 8) {
			return 6;
		} else if (i > 5 && i <= 8 && j <= 2) {
			return 7;
		} else if (i > 5 && i <= 8 && j > 2 && j <= 5) {
			return 8;
		} else if (i > 5 && i <= 8 && j > 5 && j <= 8) {
			return 9;
		} else {
			return 0;
		}
	}

	public boolean checkAllPossibleNumbers(int[][] tableToCheck) {

		allListsOfPossibleNumbers.clear();
		
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				if (tableToCheck[i][j] == 0) {
					
					SudokuPossibleNumbers spn = new SudokuPossibleNumbers(i, j);
					updPossibleNumH(i, j, tableToCheck, spn.possibleNumbers);
					updPossibleNumV(i, j, tableToCheck, spn.possibleNumbers);
					updPossibleNumBySTab(i, j, tableToCheck, spn.possibleNumbers);
					if (spn.possibleNumbers.size() > 0) {
						allListsOfPossibleNumbers.add(spn);
					} else {
						return false;
					}
				}
			}
		}
		Collections.sort(allListsOfPossibleNumbers);
		return true;
	}

	public boolean checkNextPossible(int[][] tableToCheck, int i, int j) {
		int[] emptyPos = findFirstEmpty(tableToCheck);
		i = emptyPos[0];
		j = emptyPos[1];
		for (int k = i; k < 9; k++) {
			for (int h = j; h < 9; h++) {
				if (tableToCheck[k][h] == 0) {
					allListsOfPossibleNumbers.clear();
					SudokuPossibleNumbers spn = new SudokuPossibleNumbers(k, h);
					updPossibleNumH(k, h, tableToCheck, spn.possibleNumbers);
					updPossibleNumV(k, h, tableToCheck, spn.possibleNumbers);
					updPossibleNumBySTab(k, h, tableToCheck, spn.possibleNumbers);
					if (spn.possibleNumbers.size() > 0) {
						allListsOfPossibleNumbers.add(spn);
					} else {
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	public int[] findFirstEmpty(int[][] tableToCheck) {
		int[] result = new int[2];
		for (int i = 0; i < 9; i++) {
			for (int k = 0; k < 9; k++) {
				if (tableToCheck[i][k] == 0) {
					result[0] = i;
					result[1] = k;
				}
			}
		}
		return result;
	}

	public boolean fillRecu(int[][] startTab) {
		int[][] copyTable = copyTable(startTab);

		if (allListsOfPossibleNumbers.size() > 0) {
			
			SudokuPossibleNumbers sps = allListsOfPossibleNumbers.get(0);
			ArrayList<Integer> pos = (ArrayList<Integer>) allListsOfPossibleNumbers.get(0).possibleNumbers;
			for (Integer posInt : pos) {

				copyTable[sps.i][sps.j] = posInt;
				if (isFull(copyTable)) {
					if (resultTable[0][0] == 0) {

						countSolutions++;
						resultTable = copyTable(copyTable);
					} else {
						throw new IllegalArgumentException();
					}
				}
				if (checkAllPossibleNumbers(copyTable)) {
					fillRecu(copyTable);
				}
			}
		}
		if (countSolutions == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull(int[][] tableToCheck) {
		for (int i = 0; i < 9; i++) {
			for (int k = 0; k < 9; k++) {
				if (tableToCheck[i][k] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private class SudokuPossibleNumbers implements Comparable<SudokuPossibleNumbers> {
		private int i;
		private int j;
		private List<Integer> possibleNumbers = new ArrayList<>();

		public SudokuPossibleNumbers(int i, int j) {
			this.i = i;
			this.j = j;
			for (int k = 1; k < 10; k++) {
				possibleNumbers.add(k);
			}
		}

		private int getSize() {
			return possibleNumbers.size();
		}

		@Override
		public int compareTo(SudokuPossibleNumbers o) {
			return Integer.compare(getSize(), o.getSize());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + i;
			result = prime * result + j;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SudokuPossibleNumbers other = (SudokuPossibleNumbers) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (i != other.i)
				return false;
			if (j != other.j)
				return false;
			return true;
		}

		private SudokuSolver getEnclosingInstance() {
			return SudokuSolver.this;
		}
	}
}