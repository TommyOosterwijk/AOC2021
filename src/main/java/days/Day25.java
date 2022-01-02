package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day25 implements DayInterface {

    AocUtils utils = new AocUtils();
    String[][] grid;
    int maxX = 0, maxY = 0;

    public Day25() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day25!");
        List<String> lines = new ArrayList<>();

        Scanner scanner = utils.getScannerFromFileName("day25.txt");
        while(scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        maxY = lines.size();
        maxX = lines.get(0).length();

        grid = new String[maxY][maxX];
        for(int y = 0; y < maxY; y++) {
            String l = lines.get(y);
            for(int x = 0; x < maxX; x++) {
                grid[y][x] = l.charAt(x) + "";
            }
        }

        getAnswerA();
    }
    @Override
    public void getAnswerA() {
        int roundCounter = 0;
        boolean moved = true;

        while(moved) {
            roundCounter++;
             moved = false;
            String[][] tempGrid = new String[maxY][maxX];
            for(int y = 0; y < maxY; y++) {
                for(int x = 0; x < maxX; x++) {
                tempGrid[y][x] = grid[y][x];
                }
            }

            for(int y = 0; y < maxY; y++) {
                for(int x = 0; x < maxX; x++) {
                    if(grid[y][x].equals(">")) {
                        int newX = x +1;
                        if(newX >= maxX) {
                            newX = 0;
                        }
                        if(grid[y][newX].equals(".")) {
                            tempGrid[y][x] = ".";
                            tempGrid[y][newX] = ">";
                            moved = true;
                        }
                    }
                }
            }

            grid = tempGrid;

            tempGrid = new String[maxY][maxX];
            for(int y = 0; y < maxY; y++) {
                for(int x = 0; x < maxX; x++) {
                    tempGrid[y][x] = grid[y][x];
                }
            }

            for(int x = 0; x < maxX; x++) {
                for(int y = 0; y < maxY; y++) {
                    if(grid[y][x].equals("v")) {
                        int newY = y +1;
                        if(newY >= maxY) {
                            newY = 0;
                        }
                        if(grid[newY][x].equals(".")) {
                            tempGrid[y][x] = ".";
                            tempGrid[newY][x] = "v";
                            moved = true;
                        }
                    }
                }
            }

            grid = tempGrid;

        }

        System.out.println("A) " + roundCounter);
    }

    @Override
    public void getAnswerB() {

    }
}
