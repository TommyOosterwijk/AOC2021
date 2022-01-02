package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day22 implements DayInterface {

    AocUtils utils = new AocUtils();
    boolean[][][] grid = new boolean[101][101][101];
    int offset = 50;

    public Day22() throws FileNotFoundException, URISyntaxException {
//        System.out.println("Day22!");
//        initGrid();
//
//        Scanner scanner = utils.getScannerFromFileName("day22.txt");
//
//        while(scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            boolean setQubeOn = true;
//            String[] parts = line.substring(3).split(",");
//            if(line.substring(0,3).equals("off")) {
//                setQubeOn = false;
//                parts = line.substring(4).split(",");
//            }
//            int[] partX = Arrays.stream(parts[0].substring(2).split("\\..")).mapToInt(Integer::parseInt).sorted().toArray();
//            int[] partY = Arrays.stream(parts[1].substring(2).split("\\..")).mapToInt(Integer::parseInt).sorted().toArray();
//            int[] partZ = Arrays.stream(parts[2].substring(2).split("\\..")).mapToInt(Integer::parseInt).sorted().toArray();
//
//            for(int z = partZ[0]; z <= partZ[1]; z++) {
//                for(int y = partY[0]; y <= partY[1]; y++) {
//                    for(int x = partX[0]; x <= partX[1]; x++) {
//                        grid[z + offset ][y + offset][x + offset] = setQubeOn;
//                    }
//                }
//            }
//        }
//        getAnswerA();
        getAnswerB();
    }

    void initGrid() {
        for(int z = 0; z < grid.length; z++) {
            for(int y = 0; y < grid[z].length; y++) {
                for(int x = 0; x < grid[z][y].length; x++) {
                    grid[z][y][x] = false;
                }
            }
        }
    }
    @Override
    public void getAnswerA() {
        int counter = 0;
        for(int z = 0; z < grid.length; z++) {
            for(int y = 0; y < grid[z].length; y++) {
                for(int x = 0; x < grid[z][y].length; x++) {
                    if(grid[z][y][x]) {
                        counter++;
                    }
                }
            }
        }

        System.out.println("A) " + counter);
    }

    @Override
    public void getAnswerB() {
        try {
            startAgain();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    void startAgain() throws FileNotFoundException, URISyntaxException {
        Scanner scanner = utils.getScannerFromFileName("day22.txt");

        List<Cuboid> cubuidList = new ArrayList<>();

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            boolean setQubeOn = true;
            String[] parts = line.substring(3).split(",");
            if(line.substring(0,3).equals("off")) {
                setQubeOn = false;
                parts = line.substring(4).split(",");
            }
            int[] partX = Arrays.stream(parts[0].substring(2).split("\\..")).mapToInt(Integer::parseInt).sorted().toArray();
            int[] partY = Arrays.stream(parts[1].substring(2).split("\\..")).mapToInt(Integer::parseInt).sorted().toArray();
            int[] partZ = Arrays.stream(parts[2].substring(2).split("\\..")).mapToInt(Integer::parseInt).sorted().toArray();

            Cuboid cubiod = new Cuboid(partX[0],partX[1],partY[0],partY[1],partZ[0],partZ[1]);

            for(Cuboid existingCubiod : cubuidList) {
                boolean rightSide =false, leftSide = false, frontSide = false, backSide = false, top = false, below = false, full = false;


                if(existingCubiod.xMax > cubiod.xMin && existingCubiod.xMax < cubiod.xMax) {
                    rightSide = true;
                    System.out.println("rightSide " + rightSide);

                }

                if(existingCubiod.xMin < cubiod.xMax && existingCubiod.xMin > cubiod.xMin) {
                    leftSide = true;
                    System.out.println("leftSide " + leftSide);

                }

                if(existingCubiod.yMax > cubiod.yMin && existingCubiod.yMax < cubiod.yMax) {
                    top = true;
                    System.out.println("top " + top);

                }

                if(existingCubiod.yMin < cubiod.yMax && existingCubiod.yMin > cubiod.yMin) {
                    below = true;
                    System.out.println("below " + below);

                }

                if(existingCubiod.zMax > cubiod.zMin && existingCubiod.zMax < cubiod.zMax) {
                    backSide = true;
                    System.out.println("backSide " + backSide);

                }

                if(existingCubiod.zMin < cubiod.zMax && existingCubiod.zMin > cubiod.zMin) {
                    frontSide = true;
                    System.out.println("frontSide " + frontSide);

                }

                if(!(existingCubiod.zMin > cubiod.zMax) && !(existingCubiod.zMax < cubiod.zMin) && !(existingCubiod.xMin > cubiod.xMax) && !(existingCubiod.xMax < cubiod.xMin) && !(existingCubiod.yMin > cubiod.yMax) && !(existingCubiod.yMax < cubiod.yMin)) {
                    full = true;
                    System.out.println("full match" + full);
                }


            }
            cubuidList.add(cubiod);
            System.out.println();
        }

        System.out.println(cubuidList.size());
    }
}


class Cuboid
{
    public int xMin;
    public int xMax;
    public int yMin;
    public int yMax;
    public int zMin;
    public int zMax;

    public Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        super();
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    @Override
    public String toString() {
        return "Cuboid [xMin=" + xMin + ", xMax=" + xMax + ", yMin=" + yMin + ", yMax=" + yMax + ", zMin=" + zMin + ", zMax=" + zMax
                + "]";
    }
}
