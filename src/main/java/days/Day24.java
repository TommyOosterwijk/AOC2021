package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day24 implements DayInterface {

    boolean aFound = false;
    boolean bFound = false;
    List<List<String>> instructions = new ArrayList<>();

    long[] xyzw = new long[4];

    AocUtils utils = new AocUtils();

    public Day24() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day24!");
        Scanner scanner = utils.getScannerFromFileName("day24.txt");
        for(int  i = 0; i < xyzw.length; i++) {
            xyzw[i] =0;
        }
        List<String> l = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if(s.startsWith("inp")) {
                if(l.size() > 0) {
                    instructions.add(l);
                }
                l = new ArrayList<>();
            }
            l.add(s);
        }
        instructions.add(l);
        getAnswerA();
        getAnswerB();
    }

    void doInstructions(int input, int instruction) {
        for(String s : instructions.get(instruction)) {
            String[] split = s.split(" ");
            int index = indexReverter(split[1]);
            if(split[0].equals("inp")) {
                xyzw[index] = input;
            } else {

                int index2 = indexReverter(split[2] );
                long valueRightSide = 0;

                if(index2 == -1) {
                    valueRightSide = Integer.parseInt(split[2]);
                } else {
                    valueRightSide = xyzw[index2];
                }

                if(split[0].equals("add")) {
                    xyzw[index]+= valueRightSide;
                } else if(split[0].equals("mod")) {
                    xyzw[index] = xyzw[index] % valueRightSide;
                } else if(split[0].equals("div")) {
                    xyzw[index] = xyzw[index] / valueRightSide;
                } else if(split[0].equals("mul")) {
                    xyzw[index]*= valueRightSide;
                } else if(split[0].equals("eql")) {
                    if(xyzw[index] == valueRightSide) {
                        xyzw[index] = 1;
                    } else {
                        xyzw[index] = 0;
                    }
                }
            }
        }
    }

    @Override
    public void getAnswerA() {
        findNextDigit("", 0);
    }

    void findNextDigitReverted(String digits, long zValue) {
        if(!bFound) {
            if(digits.length() == 14) {
                if(zValue == 0) {
                    bFound = true;
                    System.out.println(digits);
                }
            } else {
                for(int i = 1; i <= 9; i++) {
                    //5,7,8,10,11,12,13

                    if(digits.length() == 5 && zValue % 26 - 1 != i) {

                    } else if(digits.length() == 7 && zValue % 26 - 16 != i) {

                    } else if(digits.length() == 8 && zValue % 26 - 8 != i) {

                    } else if(digits.length() == 10 && zValue % 26 - 16 != i) {

                    } else if(digits.length() == 11 && zValue % 26 - 13 != i) {

                    } else if(digits.length() == 12 && zValue % 26 - 6 != i) {

                    } else if(digits.length() == 13 && zValue % 26 - 6 != i) {

                    } else {
                        xyzw[2] = zValue;
                        doInstructions(i, digits.length());
                        findNextDigitReverted(digits + "" + i, xyzw[2]);
                    }
                }
            }
        }
    }

    void findNextDigit(String digits, long zValue) {
        if(!aFound) {
            if(digits.length() == 14) {
                if(zValue == 0) {
                    aFound = true;
                    System.out.println(digits);
                }
            } else {
                for(int i = 9; i > 0; i--) {
                    //5,7,8,10,11,12,13

                    if(digits.length() == 5 && zValue % 26 - 1 != i) {

                    } else if(digits.length() == 7 && zValue % 26 - 16 != i) {

                    } else if(digits.length() == 8 && zValue % 26 - 8 != i) {

                    } else if(digits.length() == 10 && zValue % 26 - 16 != i) {

                    } else if(digits.length() == 11 && zValue % 26 - 13 != i) {

                    } else if(digits.length() == 12 && zValue % 26 - 6 != i) {

                    } else if(digits.length() == 13 && zValue % 26 - 6 != i) {

                    } else {
                        xyzw[2] = zValue;
                        doInstructions(i, digits.length());
                        findNextDigit(digits + "" + i, xyzw[2]);
                    }
                }
            }
        }
    }

    @Override
    public void getAnswerB() {
        findNextDigitReverted("", 0);
    }

    int indexReverter(String s) {
        int index = -1;

        if(s.equals("x")) {
            index = 0;
        } else if(s.equals("y")) {
            index = 1;
        } else if(s.equals("z")) {
            index = 2;
        } else if(s.equals("w")) {
            index = 3;
        }
        return index;
    }
}
