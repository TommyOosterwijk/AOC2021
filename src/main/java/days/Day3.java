package days;

import utils.AocUtils;

import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 implements DayInterface {

    AocUtils utils = new AocUtils();
    List<Integer> indexList = new ArrayList<Integer>();
    List<String> numberList = new ArrayList<String>();
    boolean firstNumber = true;
    String powerConsumption = "";

    public Day3() throws FileNotFoundException, URISyntaxException {

        System.out.println("Starting Day3!");

        Scanner scanner = utils.getScannerFromFileName("Day3.txt");
        while (scanner.hasNextLine()) {
            String number = scanner.nextLine();
            numberList.add(number);
            if(firstNumber) {
                for(int i = 0; i < number.length(); i++) {
                    indexList.add(0);
                }
                firstNumber = false;
            }
            for(int i = 0; i < number.length(); i++) {
                if((number.charAt(i) + "").equals("1")) {
                    indexList.set(i, indexList.get(i) +1);
                } else {
                    indexList.set(i, indexList.get(i) -1);
                }
            }
        }
        getAnswerA();
        getAnswerB();
    }

    public void getAnswerA() {
        String epsilonRate = "";
        String gammeRate = "";
        for(Integer intResult : indexList) {
            if(intResult < 0) {
                epsilonRate += "1";
                gammeRate += "0";
            } else {
                epsilonRate += "0";
                gammeRate += "1";
            }
        }

        powerConsumption = Integer.parseInt(epsilonRate, 2) * Integer.parseInt(gammeRate, 2) + "";
        System.out.println("A: " + powerConsumption);

    }

    public void getAnswerB() {
        System.out.println("B: " + findOxygenGeneratorRating() * findCO2ScrubberRating());
    }

    private int findOxygenGeneratorRating() {
        List<String> list = new ArrayList<>(numberList);
        List<Integer> removeList = new ArrayList<>();

        int valueSize = list.get(0).length();
        for(int i = 0; i < valueSize; i++) {
            int value = 0;

            for(int x = 0; x < list.size(); x++){
                if((list.get(x).charAt(i) + "").equals("1")) {
                    value+=1;
                } else {
                    value-=1;
                }
            }

            String target = "";

            if(value < 0) {
               target = "0";
           } else {
               target = "1";
           }

            for(int x = 0; x < list.size(); x++){
                if(!(list.get(x).charAt(i) + "").equals(target)) {
                    removeList.add(x);
                }
            }

            for(int r = removeList.size(); r > 0; r--) {
                int indexToRemove = removeList.get(r-1);
                list.remove(indexToRemove);
            }
            removeList.clear();

            if(list.size() == 1) {
                return Integer.parseInt(list.get(0), 2);
            }
        }

        return 0;
    }

    private int findCO2ScrubberRating() {
        List<String> list = new ArrayList<>(numberList);
        List<Integer> removeList = new ArrayList<>();

        int valueSize = list.get(0).length();
        for(int i = 0; i < valueSize; i++) {
            int value = 0;

            for(int x = 0; x < list.size(); x++){
                if((list.get(x).charAt(i) + "").equals("1")) {
                    value+=1;
                } else {
                    value-=1;
                }
            }

            String target = "";

            if(value >= 0) {
                target = "0";
            } else {
                target = "1";
            }

            for(int x = 0; x < list.size(); x++){
                if(!(list.get(x).charAt(i) + "").equals(target)) {
                    removeList.add(x);
                }
            }

            for(int r = removeList.size(); r > 0; r--) {
                int indexToRemove = removeList.get(r-1);
                list.remove(indexToRemove);
            }
            removeList.clear();

            if(list.size() == 1) {
                return Integer.parseInt(list.get(0), 2);
            }
        }

        return 0;
    }


}
