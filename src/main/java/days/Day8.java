package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day8 implements DayInterface{
    int answerACounter = 0;
    int answerB = 0;

    AocUtils utils = new AocUtils();
    public Day8() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day 8!");
        Scanner scanner = utils.getScannerFromFileName("Day8.txt");
        while(scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split("\\|");
            String[] defaultValues = values[0].split(" ");
            String[] inputValues = values[1].split(" ");

            for(int i = 1; i < inputValues.length; i++) {
                if(inputValues[i].length() == 2 || inputValues[i].length() == 3 || inputValues[i].length() == 4 || inputValues[i].length() == 7) {
                    answerACounter++;
                }
            }
            calculateNumbers(defaultValues, inputValues);

        }

        getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {
        System.out.println("A: " + answerACounter);
    }

    @Override
    public void getAnswerB() {
        System.out.println("B: " + answerB);
    }

    private void calculateNumbers(String[] defaultValues, String[] inputValues) {
        int[] numberValue = new int[10];
        String result = "";
        numberValue[0] = 42;
        numberValue[1] = 17;
        numberValue[2] = 34;
        numberValue[3] = 39;
        numberValue[4] = 30;
        numberValue[5] = 37;
        numberValue[6] = 41;
        numberValue[7] = 25;
        numberValue[8] = 49;
        numberValue[9] = 45;

        int[]charValue = new int[7];

        for(String str : defaultValues) {
            for(int i = 0; i < str.length(); i++) {
                if((str.charAt(i) + "").equals("a")) {
                    charValue[0]++;
                }
                if((str.charAt(i) + "").equals("b")) {
                    charValue[1]++;
                }
                if((str.charAt(i) + "").equals("c")) {
                    charValue[2]++;
                }
                if((str.charAt(i) + "").equals("d")) {
                    charValue[3]++;
                }
                if((str.charAt(i) + "").equals("e")) {
                    charValue[4]++;
                }
                if((str.charAt(i) + "").equals("f")) {
                    charValue[5]++;
                }
                if((str.charAt(i) + "").equals("g")) {
                    charValue[6]++;
                }
            }
        }


        for(String str : inputValues) {
            int stringValue = 0;
            for(int i = 0; i < str.length(); i++) {
                if((str.charAt(i) + "").equals("a")) {
                    stringValue+= charValue[0];
                }
                if((str.charAt(i) + "").equals("b")) {
                    stringValue+= charValue[1];
                }
                if((str.charAt(i) + "").equals("c")) {
                    stringValue+= charValue[2];
                }
                if((str.charAt(i) + "").equals("d")) {
                    stringValue+= charValue[3];
                }
                if((str.charAt(i) + "").equals("e")) {
                    stringValue+= charValue[4];
                }
                if((str.charAt(i) + "").equals("f")) {
                    stringValue+= charValue[5];
                }
                if((str.charAt(i) + "").equals("g")) {
                    stringValue+= charValue[6];
                }
            }


            for(int i = 0; i < numberValue.length; i++) {
                if(stringValue == numberValue[i]) {
                    result += (i + "");
                    break;
                }
            }
        }
        answerB+= Integer.parseInt(result);
    }
}
