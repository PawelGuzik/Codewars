package spiral;

public class Spiral {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		spiralize(16);
	}

	public static int[][] spiralize(int size) {
		int[][] table = new int[size][size];
		short dir = 2; // 1 - right, 2 - down, 3 - left, 4 - up
		int stepsLeft = size - 1;
		int currPosX = 0;
		int currPosY = stepsLeft;
		for (int j = 1; stepsLeft > 0; j++) {

			for (int i = 0; i < stepsLeft; i++) {
				if (stepsLeft == 1) {
					stepsLeft--;
				}
				if (dir == 1) {
					currPosY++;
				} else if (dir == 2) {
					currPosX++;
				} else if (dir == 3) {
					currPosY--;
				} else if (dir == 4) {
					currPosX--;
				}
				table[currPosX][currPosY] = 1;
			}
			dir = turn(dir);
			if (j % 2 == 0) {
				stepsLeft -= 2;
			}
		}
		for (int j = 0; j < size; j++) {
			table[0][j]=1;
		}
		for (int[] is : table) {
			for (int is2 : is) {
				System.out.print(is2);
			}
			System.out.println();
		}
		
		return null;
	}

	public static void move(short dir, int currPosX, int currPosY, int[][] table) {
		if (dir == 1) {
			currPosY++;
		} else if (dir == 2) {
			currPosX--;
		} else if (dir == 3) {
			currPosY--;
		} else if (dir == 4) {
			currPosX++;
		}
		table[currPosX][currPosY] = 1;
	}

	public static short turn(short dir) {
		if (dir == 4) {
			dir = 1;
		} else {
			dir++;
		}
		return dir;
	}

}
