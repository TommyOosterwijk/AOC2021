package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5 implements DayInterface {
    AocUtils utils = new AocUtils();
    List<Line> lines = new ArrayList<>();

    int[][] board;
    int boardX = 0, boardY = 0;


    public Day5() throws FileNotFoundException, URISyntaxException {
        System.out.println("Starting day5");
        Scanner scanner = utils.getScannerFromFileName("Day5.txt");
        while (scanner.hasNextLine()) {
            Line lineObj = new Line();
            String[] line = scanner.nextLine().split(" -> ");
            int x = Integer.parseInt(line[0].split(",")[0]);
            int y = Integer.parseInt(line[0].split(",")[1]);
            lineObj.startX = x;
            lineObj.startY = y;

            if(x > boardX) {
                boardX = x;
            }

            if(y > boardY) {
                boardY = y;
            }

            x = Integer.parseInt(line[1].split(",")[0]);
            y = Integer.parseInt(line[1].split(",")[1]);
            lineObj.endX = x;
            lineObj.endY = y;
            if(x > boardX) {
                boardX = x;
            }

            if(y > boardY) {
                boardY = y;
            }
            lines.add(lineObj);
        }

        board = new int[boardY+1][boardX+1];
        getAnswerA();
        getAnswerB();
    }


    @Override
    public void getAnswerA() {
        //printBoard();

        for(Line line : lines) {
            if(line.startY == line.endY) {

                for(int x = line.startX; x <= line.endX; x++) {
                    board[line.startY][x]++;
                }

                for(int x = line.endX; x <= line.startX; x++) {
                    board[line.startY][x]++;
                }
            } else if(line.startX == line.endX) {
                for(int y = line.startY; y <= line.endY; y++) {
                    board[y][line.startX]++;
                }

                for(int y = line.endY; y <= line.startY; y++) {
                    board[y][line.startX]++;
                }
            }
        }
        //printBoard();

        int result = 0;
        for(int y = 0; y < boardY+1; y++) {
            for(int x = 0; x < boardX+1; x++) {
                if(board[y][x] > 1) {
                    result++;
                }
            }
        }
        System.out.println("A: " + result);
    }

    @Override
    public void getAnswerB() {
        for (Line line : lines) {
            if (line.startY != line.endY && line.startX != line.endX) {
                //horizontal.
                //only 45 angle
                if(Math.abs(line.startY - line.endY) == Math.abs(line.startX - line.endX)) {
                    int stepCounter = 0;
                    for(int y1 = line.startY; y1 <= line.endY; y1++) {
                        int startX = line.startX;
                        if(startX < line.endX) {

                            board[y1][startX+ stepCounter]++;

                        } else {

                            board[y1][startX- stepCounter]++;
                        }
                        stepCounter++;
                    }
                    for(int y1 = line.endY; y1 <= line.startY; y1++) {
                        int startX = line.endX;
                        if(startX < line.startX) {
                            board[y1][startX+ stepCounter]++;
                        } else {
                            board[y1][startX- stepCounter]++;
                        }
                        stepCounter++;
                    }
                }
            }
        }


        int result = 0;
        for (int y = 0; y < boardY + 1; y++) {
            for (int x = 0; x < boardX + 1; x++) {
                if (board[y][x] > 1) {
                    result++;
                }
            }
        }

        System.out.println("B: " + result);
    }

    private void printBoard() {
        for (int y = 0; y < boardY + 1; y++) {
            for (int x = 0; x < boardX + 1; x++) {

                if (board[y][x] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(board[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
