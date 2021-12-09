package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 implements DayInterface {

    AocUtils utils = new AocUtils();
    List<Integer> heightMap = new ArrayList<>();

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
        getAnswerB();
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
        ArrayList<Integer> sizes = new ArrayList<>();
        ArrayList<String> validate = new ArrayList<>();

        for(int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                ArrayList<String> list = new ArrayList<>();
                if(getValueFromMap(y, x) != 9) {

                    String position = "y" + y + "x" + x;

                    if (!validate.contains(position)) {
                        list = getBasin(y,x, list);
                        validate.addAll(list);
                        sizes.add(list.size());
                    }
                }
            }
        }

        sizes.sort(Comparator.reverseOrder());

        System.out.println("B)  = "+ sizes.get(0) * sizes.get(1) * sizes.get(2));
    }

    private ArrayList<String> getBasin(int y, int x, ArrayList<String> basinList) {

        if(y >= 0 && y < maxY && x >= 0 && x < maxX) {
            String position = "y"+y+"x"+x;
            if(!basinList.contains(position)) {
                if(getValueFromMap(y, x) != 9) {
                    basinList.add(position);

                    basinList = getBasin(y-1, x, basinList);
                    basinList = getBasin(y+1, x, basinList);
                    basinList = getBasin(y, x-1, basinList);
                    basinList = getBasin(y, x+1, basinList);

                }
            }
        }
        return basinList;
    }

    private int getValueFromMap(int y, int x) {
        return heightMap.get((y * maxX) + x);
    }
}
