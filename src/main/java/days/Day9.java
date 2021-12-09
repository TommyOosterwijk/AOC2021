package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 implements DayInterface {

    AocUtils utils = new AocUtils();
    List<Integer> heightMap = new ArrayList<>();
    Boolean[][] map;

    int maxX = 0;
    int maxY;

    public Day9() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day9!");
        Scanner scanner = utils.getScannerFromFileName("Day9.txt");

        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("|");
            maxX = line.length;
            heightMap.addAll(Arrays.stream(line).map(Integer::valueOf).collect(Collectors.toList()));
        }
        maxY = heightMap.size() / maxX;

        getAnswerA();
        map = new Boolean[maxY][maxX];
        getAnswerB();
        getAnswerBAgain();
    }

    @Override
    public void getAnswerA() {
        int counter = 0;
        for(int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                boolean smallerThen = true;

               int targetValue = heightMap.get((y*maxX) + x);
               if(x > 0) {
                    if(heightMap.get((y*maxX) + (x -1)) <= targetValue) {
                        smallerThen = false;
                    }
               }

                if((x +1) < maxX) {
                    if(heightMap.get((y*maxX) + (x + 1)) <= targetValue) {
                        smallerThen = false;
                    }
                }

                if(y > 0 ) {
                    if(heightMap.get(((y-1)*maxX) + x) <= targetValue) {
                        smallerThen = false;
                    }
                }

                if((y+1) < maxY ) {
                    if(heightMap.get((((y+1)*maxX) + x)) <= targetValue) {
                        smallerThen = false;
                    }
                }

               if(smallerThen) {
                    counter+= (targetValue+1);
               }

            }
        }

        System.out.println("A: " + counter);
    }

    @Override
    public void getAnswerB() {
        List<Integer> basinSizeCounter = new ArrayList<>();

        for(int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if(map[y][x] == null) {
                    map[y][x] = true;
                    int value = heightMap.get((y * maxX) + x);
                    if(value != 9){
                        int basinCounter = 1;

                        basinCounter += basin(y - 1, x, value);
                        basinCounter += basin(y + 1, x, value);
                        basinCounter += basin(y, x - 1, value);
                        basinCounter += basin(y, x + 1, value);


                        if (basinCounter > 1) {
                            basinSizeCounter.add(basinCounter);
                        } else {
                            map[y][x] = null;
                        }
                    } else {
                        map[y][x] = false;
                    }
                }
            }
        }
        List<Integer> result = basinSizeCounter.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        //System.out.println(result);
        System.out.println("B: " + (result.get(0) * result.get(1) * result.get(2)));

//        for(int y = 0; y < maxY; y++) {
//            for (int x = 0; x < maxX; x++) {
//                System.out.print( map[y][x]+" ");
//            }
//            System.out.println();
//        }
    }

    private int basin(int y, int x, int neighborValue) {
        int result = 0;
        if(y >= 0 && y < maxY && x >= 0 && x < maxX) {
            if(map[y][x] == null) {
                map[y][x] = true;
                int value = heightMap.get((y * maxX) + x);

                if(value == 9) {
                    map[y][x] = false;
                } else {
                    if (Math.abs(value - neighborValue) == 1) {

                        result += basin(y - 1, x, value);
                        result += basin(y + 1, x, value);
                        result += basin(y, x - 1, value);
                        result += basin(y, x + 1, value);
                        result++;
                    } else {
                        map[y][x] = null;
                    }
                }
            }
        }
        return result;
    }

    public void getAnswerBAgain() {
        ArrayList<Integer> sizes = new ArrayList<>();
        ArrayList<String> validate = new ArrayList<>();

        for(int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                ArrayList<String> list = new ArrayList<>();
                int value = getValueFromMap(y, x);
                if(value != 9) {

                    String position = "y" + y + "x" + x;

                    if (!validate.contains(position)) {

                        list.add(position);
                        list = getBasin(y - 1, x, value, list);
                        list = getBasin(y + 1, x, value, list);
                        list = getBasin(y, x - 1, value, list);
                        list = getBasin(y, x + 1, value, list);

                        validate.addAll(list);

                        if (list.size() > 1) {
                            sizes.add(list.size());
                        }
                    }
                }
            }
        }

        sizes.sort(Comparator.reverseOrder());
        System.out.println("B) new = "+ sizes.get(0) * sizes.get(1) * sizes.get(2));
    }

    private ArrayList<String> getBasin(int y, int x, int previousValue, ArrayList<String> basinList) {

        if(y >= 0 && y < maxY && x >= 0 && x < maxX) {
            String position = "y"+y+"x"+x;
            if(!basinList.contains(position)) {
                int value = getValueFromMap(y, x);
                if(Math.abs(value - previousValue) == 1 && value != 9) {
                    basinList.add(position);

                    basinList = getBasin(y-1, x, value, basinList);
                    basinList = getBasin(y+1, x, value, basinList);
                    basinList = getBasin(y, x-1, value, basinList);
                    basinList = getBasin(y, x+1, value, basinList);
                }
            }
        }
        return basinList;
    }

    private int getValueFromMap(int y, int x) {
        return heightMap.get((y * maxX) + x);
    }
}
