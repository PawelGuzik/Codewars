package battleFieldValidator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class BattleField {
	static List<Ship> ships = new ArrayList<>(10);

	static class Ship {
		int size;
		List<Point> shipSegs;
		List<Point> neighbours;

		public Ship(int size, List<Point> shipSegs) {
			this.size = size;
			this.shipSegs = shipSegs;
		}

		public Ship(int size, Point shipSeg) {
			this.size = size;
			this.shipSegs = new ArrayList<Point>();
			shipSegs.add(shipSeg);
			this.neighbours = new ArrayList<>();
		}

		// True if the ship is smaller than 4 points
		public boolean addSegToShip(Point shipSeg) {
			size++;
			if (size <= 4) {
				this.shipSegs.add(shipSeg);
				return true;
			} else {
				return false;
			}
		}

		public boolean findShipNeighbours(boolean[][] checked, int[][] field) {

			for (Point point : shipSegs) {
				for (int i = point.x - 1; i <= point.x + 1; i++) {
					for (int k = point.y - 1; k <= point.y + 1; k++) {
						if (!AddNeighbour(new Point(i, k), checked, field)) {
							return false;
						}

					}
				}

			}
			return true;
		}

		private boolean AddNeighbour(Point p, boolean[][] checked, int[][] field) {
			if (p.x >= 0 && p.y >= 0 && p.x <= 9 && p.y <= 9 && !shipSegs.contains(p)) {
				if (field[p.x][p.y] != 1) {
					neighbours.add(p);
					checked[p.x][p.y] = true;
				} else {
					return false;
				}
			}
			return true;
		}
	}

	public static boolean fieldValidator(int[][] field) {
		boolean[][] checked = new boolean[10][10];
		ships.clear();
		for (int i = 0; i < 10; i++) {
			System.out.println();
			for (int k = 0; k < 10; k++) {
				System.out.print(field[i][k]);
				if (field[i][k] == 1 && !checked[i][k]) {
					Point p = new Point(i, k);
					Ship s1 = new Ship(1, p);
					checked[i][k] = true;
					if (!checkHorizontal(p.x, p.y, s1.size, field, s1, checked)) {
						return false;
					}
				} else {
					checked[i][k] = true;
				}
			}
		}
		return checkShipsNumber();

	}

	public static boolean checkHorizontal(int x, int y, int size, int field[][], Ship ship, boolean[][] checked) {

		while (x<9&&field[x + 1][y] == 1) {
			size++;
			x++;
			ship.addSegToShip(new Point(x, y));
			checked[x][y] = true;

		}
		/*
		 * while (start>0 &&field[start - 1][y] == 1) { size++; x--; }
		 */
		if (size == 1) {
			size = checkVertical(x, y, size, field, ship, checked);
		}
		ships.add(ship);
		if (ship.findShipNeighbours(checked, field)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkShipsNumber() {
		int size4Count = 0;
		int size3Count = 0;
		int size2Count = 0;
		int size1Count = 0;
		for (Ship ship : ships) {
			if (ship.size == 4) {
				size4Count++;
			}
			if (ship.size == 3) {
				size3Count++;
			}
			if (ship.size == 2) {
				size2Count++;
			}
			if (ship.size == 1) {
				size1Count++;
			}
		}
		if (size1Count != 4 || size2Count != 3 || size3Count != 2 || size4Count != 1) {
			return false;
		} else {
			return true;
		}
	}

	public static int checkVertical(int x, int y, int size, int field[][], Ship ship, boolean[][] checked) {

		while (y < 9 && field[x][y + 1] == 1) {
			size++;
			y++;
			ship.addSegToShip(new Point(x, y));
			checked[x][y] = true;
		}
		/*
		 * while (field[x][y - 1] == 1) { size++; y--; }
		 */
		return size;

	}
}