package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day22 implements DayInterface {

    AocUtils utils = new AocUtils();

    public Day22() throws FileNotFoundException, URISyntaxException {
//        System.out.println("Day22!");
        Scanner scanner = utils.getScannerFromFileName("day22.txt");

        List<Cuboid> cubuidList = new ArrayList<>();

        while(scanner.hasNextLine()) {
            List<Cuboid> partialCubiodList = new ArrayList<>();
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

            Cuboid c = new Cuboid(partX[0],partX[1],partY[0],partY[1],partZ[0],partZ[1]);

            for(int i = cubuidList.size()-1; i >= 0; i--) {
                Cuboid e = cubuidList.get(i);

                if(c.xMin <= e.xMin && c.xMax >= e.xMax && c.yMin <= e.yMin && c.yMax >= e.yMax && c.zMin <= e.zMin && c.zMax >= e.zMax) {
                    //Old cube fully fits in new cube.
                    cubuidList.remove(i);
                } else {
                    if((c.xMin <= e.xMin && c.xMax >= e.xMin || c.xMax >= e.xMax && c.xMin <= e.xMax || c.xMin >= e.xMin && c.xMax <= e.xMax)
                            && (c.yMin <= e.yMin && c.yMax >= e.yMin || c.yMax >= e.yMax && c.yMin <= e.yMax || c.yMin >= e.yMin && c.yMax <= e.yMax)
                            && (c.zMin <= e.zMin && c.zMax >= e.zMin || c.zMax >= e.zMax && c.zMin <= e.zMax || c.zMin >= e.zMin && c.zMax <= e.zMax)) {
                        if(e.xMin < c.xMin) {
                            Cuboid t = new Cuboid(e.xMin, c.xMin-1, e.yMin, e.yMax, e.zMin, e.zMax);
                            partialCubiodList.add(t);

                            e.xMin = c.xMin;
                        }
                        if( e.xMax > c.xMax) {
                            Cuboid t = new Cuboid(c.xMax +1, e.xMax, e.yMin, e.yMax, e.zMin, e.zMax);
                            partialCubiodList.add(t);

                            e.xMax = c.xMax;
                        }

                        if(e.yMin < c.yMin) {
                            Cuboid t = new Cuboid(e.xMin, e.xMax, e.yMin, c.yMin-1, e.zMin, e.zMax);
                            partialCubiodList.add(t);

                            e.yMin = c.yMin;
                        }
                        if( e.yMax > c.yMax) {
                            Cuboid t = new Cuboid(e.xMin, e.xMax, c.yMax +1, e.yMax, e.zMin, e.zMax);
                            partialCubiodList.add(t);

                            e.yMax = c.yMax;
                        }

                        if(e.zMin < c.zMin) {
                            Cuboid t = new Cuboid(e.xMin, e.xMax, e.yMin, e.yMax, e.zMin, c.zMin-1);
                            partialCubiodList.add(t);

                            e.zMin = c.zMin;
                        }
                        if( e.zMax > c.zMax) {
                            Cuboid t = new Cuboid(e.xMin, e.xMax, e.yMin, e.yMax, c.zMax +1, e.zMax);
                            partialCubiodList.add(t);

                            e.zMax = c.zMax;
                        }
                        cubuidList.remove(i);
                    }

                }
            }
            if(setQubeOn) {
                cubuidList.add(c);
            }

            cubuidList.addAll(partialCubiodList);
        }

        BigInteger result = new BigInteger("0");

        for(Cuboid c : cubuidList) {
            result = result.add(BigInteger.valueOf(Math.abs(c.xMax - c.xMin +1)).multiply( BigInteger.valueOf(Math.abs(c.yMax - c.yMin + 1))).multiply( BigInteger.valueOf(Math.abs(c.zMax - c.zMin + 1))));
        }

        System.out.println("Day 22: " + result);
        getAnswerB();
    }
    @Override
    public void getAnswerA() {
    }

    @Override
    public void getAnswerB() {

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
