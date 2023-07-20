import java.util.*;

public class TicTacToe {
	private static Scanner scan = new Scanner(System.in);	
	
	private static char[][] gameBoard = new char[3][3];	
	private static Set<Integer> freeSquares = new HashSet<Integer>();
	
	public static void main(String[] args) {		
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				gameBoard[i][j] = ' ';
				freeSquares.add(i * 3 + j + 1);
			}
		
		print_board();
		game();
		
		scan.close();
	}
	
	private static void game() {				
		while (!freeSquares.isEmpty()) {		
			player_move();			
			computer_move();			
			print_board();
		}
		
		System.out.println("┃          Draw!        ┃");
		System.out.print  ("┗━━━━━━━━━━━━━━━━━━━━━━━┛");
	}
	
	private static void place_character(int square, char character) {		
		int i = (square - 1) / 3;
		int j = (square - 1) % 3;

		gameBoard[i][j] = character;
		freeSquares.remove(square);
		
		search_for_winner();
	}
	
	private static void player_move() {
		System.out.print  ("┃      Your move: ");
		int square = scan.nextInt();
		System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━┛");
		
		int i = (square - 1) / 3;
		int j = (square - 1) % 3;
		
		if (square < 1 || square > 9) {
			handle_invalid_input(square, "out of bounds");
			return;
		}
		else if (gameBoard[i][j] != ' ') {
			handle_invalid_input(square, "occupied");
			return;
		}
		else place_character(square, 'X');
	}
	
	private static void computer_move() {		
		if (freeSquares.isEmpty())
			return;
		
		int randomPosition = new Random().nextInt(freeSquares.size() - 1);
		int randomFreeSquare = freeSquares.toArray(new Integer[freeSquares.size()])[randomPosition];
		
		place_character(randomFreeSquare, 'O');
	}
	
	private static void search_for_winner() {		
		char winner;
		boolean foundWinner;
		
		// Searching each line
		for (int i = 0; i < 3; ++i) {
			foundWinner = true;
			winner = gameBoard[i][0];
			
			if (winner != ' ') {
				for (int j = 1; j < 3; ++j)
					if (gameBoard[i][j] != winner) {
						foundWinner = false;
						break;
					}
				
				if (foundWinner) {
					handle_win(winner);
					return;
				}
			}
		}
		
		// Searching each column
		for (int j = 0; j < 3; ++j) {
			foundWinner = true;
			winner = gameBoard[0][j];
			
			if (winner != ' ') {
				for (int i = 1; i < 3; ++i) {
					if (gameBoard[i][j] != winner) {
						foundWinner = false;
						break;
					}
				}
				
				if (foundWinner) {
					handle_win(winner);
					return;
				}			
			}
		}
		
		// Searching the main diagonal
		foundWinner = true;
		winner = gameBoard[0][0];
		
		if (winner != ' ') {
			for (int i = 1; i < 3; ++i)
				if (gameBoard[i][i] != winner) {
					foundWinner = false;
					break;
				}
			
			if (foundWinner) {
				handle_win(winner);
				return;
			}				
		}
		
		// Searching the secondary diagonal
		foundWinner = true;
		winner = gameBoard[0][2];
		
		if (winner != ' ') {
			for (int i = 1; i < 3; ++i)
				if (gameBoard[i][2 - i] != winner) {
					foundWinner = false;
					break;
				}
			
			if (foundWinner) {
				handle_win(winner);
				return;
			}				
		}
	}
	
	private static void handle_invalid_input(int square, String invalidCase) {
		for (int k = 0; k < 5; ++k)
			System.out.println();
		
		print_board();
		System.out.println("┃ Square " + square + " is " + invalidCase + "! ┃");		
		player_move();
	}
	
	private static void handle_win(char winner) {
		switch (winner) {
			case 'X': 
				print_board();
				
				System.out.println("┃        You win!       ┃");
				System.out.print  ("┗━━━━━━━━━━━━━━━━━━━━━━━┛");
				
				System.exit(0);
			
			case 'O':
				print_board();
				
				System.out.println("┃   The computer wins!  ┃");
				System.out.print  ("┗━━━━━━━━━━━━━━━━━━━━━━━┛");
				
				System.exit(0);
		}
	}
	
	private static void print_board() {		
		for (int k = 0; k < 5; ++k)
			System.out.println();
		
		for (int i = 0; i < 5; ++i, System.out.println()) {
			if (i % 2 == 0) {
				for (int j = 0; j < 3; ++j) {
					System.out.print(" " + gameBoard[i / 2][j] + " ");
					
					if (j < 2)
						System.out.print('┃');				
				}
				
				System.out.print("   ");
				
				for (int j = 0; j < 3; ++j) {
					System.out.print(" " + (i / 2 * 3 + j + 1) + " ");
					
					if (j < 2)
						System.out.print('┃');				
				}
			}
			else 
				System.out.print("━━━╋━━━╋━━━   ━━━╋━━━╋━━━");
		}
		
		System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━┓");
	}
}
