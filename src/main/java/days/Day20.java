package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day20 implements DayInterface {

    int[][] locations = { { -1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0,0}, {0,1}, {1, -1}, {1,0}, {1,1}};

    AocUtils utils = new AocUtils();
    String[] lookupTable;
    List<String> lines = new ArrayList<>();
    int maxX = 0, maxY = 0;
    String[][] grid;

    public Day20() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day20!");
        Scanner scanner = utils.getScannerFromFileName("day20.txt");
        lookupTable = scanner.nextLine().split("|");
        scanner.nextLine();
        while(scanner.hasNextLine()) {
           lines.add(scanner.nextLine());
        }

        maxX = lines.get(0).length() + 200;
        maxY = lines.size() + 200;
        grid = new String[maxY][maxX];

        //default setup
        for(int y = 0; y < maxY; y++) {
            for(int x = 0; x < maxX; x++) {
                grid[y][x] = "0";
            }
        }

        //init setup
        for(int y = 0; y < maxY-200; y++) {
            String line = lines.get(y);

            for(int x = 0; x < maxX-200; x++) {
                if(line.charAt(x) == '#') {
                    grid[y + 100][x + 100] = "1";
                }
            }
        }
        getAnswerA();
    }

    @Override
    public void getAnswerA() {

        for(int i2 = 0; i2 < 50; i2++) {
            List<String> decimalValues = new ArrayList<>();

            for (int y = 1; y < maxY - 1; y++) {
                for (int x = 1; x < maxX - 1; x++) {
                    String decimal = "";

                    for (int i = 0; i < locations.length; i++) {
                        decimal += grid[y + locations[i][0]][x + locations[i][1]];
                    }
                    decimalValues.add(decimal);
                }
            }
            int decimalCounter = 0;
            for (int y = 1; y < maxY - 1; y++) {
                for (int x = 1; x < maxX - 1; x++) {
                    String decimal = decimalValues.get(decimalCounter);
                    int value = Integer.valueOf(decimal, 2);
                    if (lookupTable[value].equals(".")) {
                        grid[y][x] = "0";
                    } else {
                        grid[y][x] = "1";
                    }
                    decimalCounter++;
                }
            }
            expandGrid();
        }

        int result = 0;

        for(int y = 100; y < maxY-100; y++) {
            for(int x = 100; x < maxX-100; x++) {
                if(grid[y][x].equals("1")) {
                    result++;
                }
            }
        }
        System.out.println("A) " + result);
    }

    @Override
    public void getAnswerB() {

    }

    private void expandGrid() {
        int tempMaxY = maxY +2, tempMaxX = maxX + 2;

        String[][] expandedGrid = new String[tempMaxY][tempMaxX];
        for(int y = 0; y < tempMaxY; y++) {
            for(int x = 0; x < tempMaxX; x++) {
                expandedGrid[y][x] = "0";
            }
        }

        //init setup
        for(int y = 0; y < maxY; y++) {
            for(int x = 0; x < maxX; x++) {
                expandedGrid[y+1][x+1] = grid[y][x];
            }
        }

        grid = expandedGrid;
        maxX = tempMaxX;
        maxY = tempMaxY;

    }
}
