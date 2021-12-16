package days;

import utils.AocUtils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day15 implements DayInterface {

    int maxX = 500, maxY = 500;
    int[][] neighbourLocations = { { -1, 0}, {1, 0}, {0, 1}, {0,-1}};

    List<Point> openVisited = new ArrayList<>();
    Boolean[][] hasVisited = new Boolean[maxY][maxX];
    int[][] value = new int[maxY][maxX];
    int[][] totalValue = new int[maxY][maxX];

    AocUtils utils = new AocUtils();

    public Day15() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day15!");
        Scanner scanner = utils.getScannerFromFileName("day15.txt");
        int y = 0;

        for( int y2 = 0; y2 < maxY; y2++) {
            for( int x2 = 0; x2 < maxX; x2++) {
                value[y2][x2] = 0;
                totalValue[y2][x2] = 0;
                hasVisited[y2][x2] =  false;
            }
        }
        while(scanner.hasNextLine()) {
            String line =scanner.nextLine();

            for(int x = 0; x < line.length(); x++) {
                value[y][x] = Integer.parseInt(line.charAt(x) + "");
            }
            y++;
        }
        getAnswerB();
        getAnswerA();
    }

    @Override
    public void getAnswerA() {
        openVisited.add(new Point(0,0));
        totalValue[0][0] = 0;
        findShortestPath();
    }

    @Override
    public void getAnswerB() {
        for(int y=0; y < 100; y++) {
            for(int x = 0; x < 100; x++) {
                for(int y1 = 0; y1 < 5; y1++) {
                    for (int x1 = 0; x1 < 5; x1++) {
                        if(!(x1 == 0 && y1 == 0)) {
                            int xyValue = value[y][x];
                            xyValue += y1 + x1;
                            if (xyValue > 9) {
                                xyValue -= 9;
                            }
                            value[y + (y1*100)][(x1 * 100) + x] = xyValue;
                        }
                    }
                }
            }
        }
    }

    private void findShortestPath() {
        boolean resultFound = false;

        while (!resultFound) {
            int lowestValue = -1;
            int index = 0;
            int x = 0;
            int y = 0;

            for (int i = 0; i < openVisited.size(); i++) {
                Point point = openVisited.get(i);
                int tempX =  point.x;
                int tempY =  point.y;

                if (hasVisited[tempY][tempX] != true) {
                    int value = totalValue[tempY][tempX];

                    if (value < lowestValue || lowestValue == -1) {
                        x = tempX;
                        y = tempY;
                        lowestValue = value;
                        index = i;
                    }
                }
            }

            openVisited.remove(index);

            for (int i = 0; i < 4; i++) {
                int tempY = y + neighbourLocations[i][0];
                int tempX = x + neighbourLocations[i][1];
                if (tempY >= 0 && tempY < maxY && tempX >= 0 && tempX < maxX) {
                    int neighbourTotalValue = totalValue[tempY][tempX];
                    int neighbourValue = value[tempY][tempX];

                    if(neighbourTotalValue == -1 || neighbourValue + totalValue[y][x] < neighbourTotalValue) {
                        totalValue[tempY][tempX] = neighbourValue + totalValue[y][x];
                    }

                    if (hasVisited[tempY][tempX] == false) {
                        openVisited.add(new Point(tempX, tempY));
                    }

                    if(tempX == maxX-1 && tempY == maxY-1) {
                        resultFound = true;
                    }
                }
            }
            hasVisited[y][x] = true;
        }
        System.out.println(totalValue[maxY-1][maxX-1]);
    }
}
