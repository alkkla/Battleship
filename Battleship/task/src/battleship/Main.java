package battleship;

import java.util.Scanner;

public class Main {

    static final char[][] board1 = new char[12][12];
    static final char[][] board2 = new char[12][12];
    static Scanner sc = new Scanner(System.in);
    static int[] boats = {5, 4, 3, 3, 2};
    static final String[] boatNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
    static int boatCount1 = 0;
    static int boatCount2 = 0;
    static final int[] coOrds = new int[4];
    static boolean player1;
    static boolean gameStart;

    public static void main(String[] args) {
        player1 = true;
        gameStart = false;
        startBoard(player1 ? board1 : board2);
    }
    static void startBoard(char[][] board) {
        for (int r = 1; r < 11; r++) {
            for (int c = 1; c < 11; c++) {
                board[r][c] = '~';
            }
        }
        printBoard(false, player1 ? board1 : board2);
        boats(player1 ? boatCount1 : boatCount2);
    }
    static void printBoard(boolean fog, char[][] board) {
        char firstRow = '\u0031';
        char firstCol = '\u0041';
        for (int r = 0; r < 11; r++) {
            System.out.println();
            for (int c = 0; c < 11; c++) {
                if (r == 0 && c == 0) {
                    System.out.print("  ");
                }
                if (r == 0 && c > 0) {
                    if (firstRow == ':') {
                        System.out.print("10");
                    } else {
                        System.out.print(firstRow + " ");
                        firstRow++;
                    }
                }
                if (r > 0 && c == 0) {
                    System.out.print(firstCol + " ");
                    firstCol++;
                }
                if (r > 0 && c > 0) {
                    if (!fog) {
                        System.out.print(board[r][c] + " ");
                    }
                    if (fog) {
                        if (board[r][c] == 'O') {
                            System.out.print("~ ");
                        }
                        if (board[r][c] == 'M') {
                            System.out.print("M ");
                        }
                        if (board[r][c] == '~') {
                            System.out.print("~ ");
                        }
                        if (board[r][c] == 'X') {
                            System.out.print("X ");
                        }
                    }
                }
            }
        }
    }
    static void boats(int boatCount) {
        System.out.println("\n\nEnter the coordinates of the " + boatNames[boatCount] + " (" + boats[boatCount] + " cells):");
        boatChecking();
    }
    static void boatChecking() {
        try {
            String[] input = sc.nextLine().split(" ");
            coOrds[0] = input[0].charAt(0) - 64;
            coOrds[1] = Integer.parseInt(input[0].substring(1));
            coOrds[2] = input[1].charAt(0) - 64;
            coOrds[3] = Integer.parseInt(input[1].substring(1));
            if (coOrds[0] == coOrds[2]) {
                if (coOrds[1] > coOrds[3]) {
                    int temp = coOrds[1];
                    coOrds[1] = coOrds[3];
                    coOrds[3] = temp;
                }
            }
            if (coOrds[1] == coOrds[3]) {
                if (coOrds[0] > coOrds[2]) {
                    int temp = coOrds[0];
                    coOrds[0] = coOrds[2];
                    coOrds[2] = temp;
                }
            }
            lengthCheck(player1 ? boatCount1 : boatCount2);
        } catch (Exception e) {
            System.out.println("");
        }
    }
    static void lengthCheck(int boatCount) {
        if (coOrds[0] != coOrds[2] && coOrds[1] != coOrds[3]) {
            System.out.println("\nError! Wrong ship location! Try again:");
            boatChecking();
        }
        if (coOrds[0] == coOrds[2]) {
            if (Math.abs(coOrds[1] - coOrds[3]) != boats[boatCount] - 1) {
                System.out.println("\nError! Wrong length of the " + boatNames[boatCount] + "! Try again:\n");
                boatChecking();
            } else {
                placing(player1 ? board1 : board2, player1 ? boatCount1 : boatCount2);
            }
        }
        if (coOrds[1] == coOrds[3]) {
            if (Math.abs(coOrds[0] - coOrds[2]) != boats[boatCount] - 1) {
                System.out.println("\nError! Wrong length of the " + boatNames[boatCount] + "! Try again:\n");
                boatChecking();
            } else {
                placing(player1 ? board1 : board2, player1 ? boatCount1 : boatCount2);
            }
        }
    }
    static void placing(char[][] board, int boatCount) {
        if (coOrds[0] == coOrds[2]) {
            for (int col = coOrds[1] - 1; col <= coOrds[3] + 1; col++) {
                for (int row = coOrds[0] - 1; row <= coOrds[2] + 1; row++) {
                    if (board[row][col] == 'O') {
                        tooClose();
                    }
                }
            }
        }
        if (coOrds[1] == coOrds[3]) {
            for (int col = coOrds[1] - 1; col <= coOrds[3] + 1; col++) {
                for (int row = coOrds[0] - 1; row <= coOrds[2] + 1; row++) {
                    if (board[row][col] == 'O') {
                        tooClose();
                    }
                }
            }
        }

        if (coOrds[0] == coOrds[2]) {
            for (int col = coOrds[1]; col <= coOrds[3]; col++) {
                board[coOrds[0]][col] = 'O';
            }
        }

        if (coOrds[1] == coOrds[3]) {
            for (int row = coOrds[0]; row <= coOrds[2]; row++) {
                board[row][coOrds[1]] = 'O';
            }
        }
        if (player1) {
            boatCount1++;
        } else {
            boatCount2++;
        }
        printBoard(false, player1 ? board1 : board2);
        if (boatCount1 == 5 && boatCount2 == 5) {
            player1 = true;
            gameStart();
        }
        if (boatCount < 4) {
            boats(player1 ? boatCount1 : boatCount2);
        } else {
            player1 = false;
            if (boatCount2 == 0) {
                pass();
            }
            boats(player1 ? boatCount1 : boatCount2);
        }
    }
    static void pass() {
        System.out.println("\n\nPress Enter and pass the move to another player");
        sc.nextLine();
        if(!gameStart) {
            player2();
        } else {
            player1 = !player1;
            doubleBoard();
            takeShot();
        }
    }
    static void player2() {
        System.out.println("\n\nPlayer 2, place your ships to the game field");
        startBoard(player1 ? board1 : board2);
    }
    static void tooClose() {
        System.out.println("Error! You placed it too close to another one. Try again:");
        boatChecking();
    }
    static void gameStart() {
        System.out.println("\n\nThe game starts!");
        gameStart = true;
        player1 = false;
        pass();
    }
    static void doubleBoard() {
        printBoard(true, !player1 ? board1 : board2);
        System.out.print("\n---------------------");
        printBoard(false, player1 ? board1 : board2);
    }
    static void takeShot() {
        if(player1) {
            System.out.println("\n\nPlayer 1, it's your turn:");
            shotCheck();
        } else {
            System.out.println("\n\nPlayer 2, it's your turn:");
            shotCheck();
        }
    }
    static void shotCheck() {
        try {
            String input = sc.nextLine();
            coOrds[0] = input.charAt(0) - 64;
            coOrds[1] = Integer.parseInt(input.substring(1));
            coOrdCheck();
        } catch (Exception e) {
            System.out.println("");
        }
    }
    static void coOrdCheck() {
        for (int i = 0; i < 4; i++) {
            if (coOrds[i] < 1 || coOrds[i] > 10) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                shotCheck();
            } else {
                if (!gameStart) {
                    shoot(player1 ? board1 : board2, player1 ? boatCount1 : boatCount2);
                } else {
                    shoot(player1 ? board2 : board1, player1 ? boatCount2 : boatCount1);
                }
            }
        }
    }
    static void shoot(char[][] board, int boatCount) {
        if (board[coOrds[0]][coOrds[1]] == 'X' | board[coOrds[0]][coOrds[1]] == 'M') {
            System.out.println("\nYou missed!");
            pass();
        }
        if (board[coOrds[0]][coOrds[1]] == '~') {
            board[coOrds[0]][coOrds[1]] = 'M';
            System.out.println("\nYou missed!");
            pass();
        }
        if (board[coOrds[0]][coOrds[1]] == 'O') {
            if (board[coOrds[0]][coOrds[1]] == 'O') {
                board[coOrds[0]][coOrds[1]] = 'X';
                int OCount = 0;
                for(int r = coOrds[0] - 1; r < coOrds[0] + 2; r++) {
                    for (int c = coOrds[1] - 1; c < coOrds[1] + 2; c++) {
                        if (board[r][c] == 'O') {
                            OCount++;
                        }
                    }
                }
                if (OCount == 0) {
                    System.out.println("\nYou sank a ship!");
                    if (player1) {
                        boatCount2--;
                    } else {
                        boatCount1--;
                    }
                    if (boatCount == 0) {
                        System.out.println("\nYou sank the last ship. You won. Congratulations!");
                        System.exit(0);
                    }
                } else {
                    System.out.println("\nYou hit a ship!");
                }
                pass();
            }

        }
    }
}


