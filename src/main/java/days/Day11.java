package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;

public class Day11 implements DayInterface {

    int[][] neighbourLocations = {{ -1,-1}, { -1, 0}, { -1, 1}, {0, -1}, {0, 1}, {1,-1}, {1,0},{1,1}};

    AocUtils utils = new AocUtils();
    int[][] grid = new int[10][10];
    int roundCounter;

    public Day11() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day11!");
        Scanner scanner = utils.getScannerFromFileName("day11.txt");
        int counter = 0;
        while(scanner.hasNextLine()) {
            grid[counter] = Arrays.stream(scanner.nextLine().split("|")).mapToInt(Integer::valueOf).toArray();
            counter++;
        }

        getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {
        int flashCounter = 0;

        for(int rounds = 0; rounds < 100; rounds++) {
            for(int y = 0; y < 10; y++) {
                for(int x = 0; x < 10; x++) {
                    increaseEnergy(y, x);
                }
            }

            for(int y = 0; y < 10; y++) {
                for(int x = 0; x < 10; x++) {
                    if(grid[y][x] > 9) {
                        flashCounter++;
                        grid[y][x] = 0;
                    }
                }
            }
        }

        System.out.println("A) " + flashCounter);
    }

    private void increaseEnergy(int y, int x) {
        grid[y][x]++;

        if(grid[y][x] == 10) {
            flashNeighbours(y, x);
        }
    }

    private void flashNeighbours(int y, int x) {
        for(int i = 0; i <  neighbourLocations.length; i++) {
            if(utils.isLocationInGrid(y+neighbourLocations[i][0], x+neighbourLocations[i][1], 10, 10)) {
                increaseEnergy(y+neighbourLocations[i][0], x+neighbourLocations[i][1]);
            }
        }
    }

    @Override
    public void getAnswerB() {

        while(true) {
            int flashCounter = 0;
            for(int y = 0; y < 10; y++) {
                for(int x = 0; x < 10; x++) {
                    increaseEnergy(y, x);
                }
            }

            for(int y = 0; y < 10; y++) {
                for(int x = 0; x < 10; x++) {
                    if(grid[y][x] > 9) {
                        flashCounter++;
                        grid[y][x] = 0;
                    }
                }
            }
            roundCounter++;
            if(flashCounter == 100) {
                break;
            }
        }
        System.out.println("B) = "+ (100 +roundCounter));
    }
}
