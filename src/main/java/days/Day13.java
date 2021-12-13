package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Day13 implements DayInterface {

    AocUtils utils = new AocUtils();
    String[][] grid = new String[895][1311];
    int maxX = 1311;
    int maxY = 895;


    public Day13() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day13!");
        Scanner scanner = utils.getScannerFromFileName("day13.txt");
        initGrid();

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(!line.isEmpty()) {
                if (line.charAt(0) == 'f') {
                    int x = 0,y = 0;

                    String[] foldLine = line.split("=");
                    System.out.println(foldLine[0].charAt(foldLine[0].length() -1));
                    if(foldLine[0].charAt(foldLine[0].length() -1) == 'y') {
                        y = maxY -1;
                        maxY  = maxY / 2;

                    } else {
                        x = maxX -1;
                        maxX  = maxX / 2;

                    }

                    foldGrid(y, x);
                } else {
                    String[] xy = line.split(",");
                    grid[Integer.parseInt(xy[1])][Integer.parseInt(xy[0])] = "#";
                }
            }
        }

        getAnswerA();
    }

    private void initGrid() {
        for(int y = 0; y < grid.length; y++) {
            for( int x = 0; x < grid[y].length; x++) {
                grid[y][x] = ".";
            }
        }
    }

    private void foldGrid(int offsetY, int offsetX) {
        for(int y = 0; y < maxY; y++) {
            int tempOffsetX = offsetX;
            for(int x = 0; x < maxX; x++) {
                if(grid[y + offsetY][x + tempOffsetX].equals("#")) {
                    grid[y][x] = "#";
                }

                if(tempOffsetX > 0) {
                    tempOffsetX = tempOffsetX -2;
                }


            }
            if(offsetY > 0) {
                offsetY = offsetY -2;
            }
        }
    }

    @Override
    public void getAnswerA() {
        int result = 0;
        utils.printBoard(grid, maxY, maxX);

        for(int y = 0; y < maxY; y++) {
            for(int x = 0; x < maxX; x++) {
                if(grid[y][x].equals("#")) {
                    result++;
                }
            }
        }

        System.out.println("A) " + result);
    }

    @Override
    public void getAnswerB() {

    }
}
