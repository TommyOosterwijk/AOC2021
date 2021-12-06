package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day6 implements DayInterface{
    AocUtils utils = new AocUtils();
    List<LanternFish> fishList = new ArrayList<>();
    BigInteger[] fishListCounter = new BigInteger[9];
    int days = 256;

    public Day6() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day6!");
        Scanner scanner = utils.getScannerFromFileName("Day6.txt");
        String[] numbers = scanner.nextLine().split(",");

        for(int i = 0; i < 7; i++) {
            LanternFish fish = new LanternFish();
            fish.internalTimer = 6;
            fish.amountOfFish = 0;
            fish.daysTillSpawn = i;
            fishList.add(fish);
        }

        for(int i = 0; i < fishListCounter.length; i++) {
            fishListCounter[i] = new BigInteger("0");
        }

        for(int i = 0; i < numbers.length; i++) {
            int index = Integer.parseInt(numbers[i]);

            fishList.get(index).amountOfFish++;
            BigInteger value = new BigInteger("1");

            fishListCounter[index] = fishListCounter[index].add(value);

        }

        fishList = fishList.stream().filter(lanternFish -> lanternFish.amountOfFish != 0).collect(Collectors.toList());
        //getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {
        for(int i =1; i <= days; i++) {
            List<LanternFish> addedFish = new ArrayList<>();

            for(LanternFish fish : fishList) {
                if(fish.daysTillSpawn == 0) {
                    LanternFish childFish = new LanternFish();
                    childFish.amountOfFish = fish.amountOfFish;
                    childFish.internalTimer = 6;
                    childFish.daysTillSpawn = 8;

                    addedFish.add(childFish);
                    fish.daysTillSpawn = fish.internalTimer;
                } else {
                    fish.daysTillSpawn--;
                }
            }
            fishList.addAll(addedFish);
        }

        int result = 0;

        for(LanternFish fish : fishList) {
            result += fish.amountOfFish;
        }

        System.out.println("A: " + result);
    }

    @Override
    public void getAnswerB() {

        for(int i =1; i <= days; i++) {

            BigInteger temp = fishListCounter[0];
            for(int y = 1; y < fishListCounter.length; y++ ) {
                fishListCounter[y-1] = fishListCounter[y];
            }
            fishListCounter[8] = temp;
            fishListCounter[6] = fishListCounter[6].add(temp);
        }
        System.out.println("B: " + Arrays.stream(fishListCounter).reduce(BigInteger::add).get());
    }
}
