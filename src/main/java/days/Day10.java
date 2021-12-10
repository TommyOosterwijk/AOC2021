package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day10 implements DayInterface {
    int errorCounter = 0;
    List<BigInteger> values =  new ArrayList<>();

    AocUtils utils = new AocUtils();
    public Day10() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day 10!");
        Scanner scanner = utils.getScannerFromFileName("day10.txt");
        while(scanner.hasNextLine()) {
            processLine(scanner.nextLine());
        }

        getAnswerA();
        getAnswerB();
    }

    private void processLine(String line) {
        boolean error = false;
        BigInteger sum  = new BigInteger("0");

        List<String> chars = new ArrayList<>();
        for(int i = 0; i < line.length(); i++) {
            String chr = line.charAt(i) + "";
            if(chr.equals("{") || chr.equals("(") || chr.equals("[") || chr.equals("<")) {
                chars.add(chr);
            } else {
                if(chr.equals("}")) {
                    if(chars.get(chars.size()-1).equals("{")) {
                        chars.remove(chars.size()-1);
                    } else {
                        errorCounter+=1197;
                        error = true;
                        //Found the error.
                        break;
                    }
                }

                else if(chr.equals("]")) {
                    if(chars.get(chars.size()-1).equals("[")) {
                        chars.remove(chars.size()-1);
                    } else {
                        errorCounter+=57;
                        error = true;
                        //Found the error.
                        break;
                    }
                }

               else if(chr.equals(")")) {
                    if(chars.get(chars.size()-1).equals("(")) {
                        chars.remove(chars.size()-1);
                    } else {
                        errorCounter+=3;
                        error = true;
                        //Found the error.
                        break;
                    }
                }

               else if(chr.equals(">")) {
                    if(chars.get(chars.size()-1).equals("<")) {
                        chars.remove(chars.size()-1);
                    } else {
                        errorCounter+=25137;
                        error = true;
                        //Found the error.
                        break;
                    }
                }
            }
        }

        if(!error) {
            for( int  i = chars.size(); i > 0; i--) {
                String value = chars.get(i-1);
                sum = sum.multiply(new BigInteger("5"));
                if(value.equals("{")) {
                    sum = sum.add(new BigInteger("3"));
                }
                else if(value.equals("[")) {
                    sum = sum.add(new BigInteger("2"));
                }
                else if(value.equals("(")) {
                    sum = sum.add(new BigInteger("1"));
                }
                else if(value.equals("<")) {
                    sum = sum.add(new BigInteger("4"));
                }
            }
            values.add(sum);
        }

    }

    @Override
    public void getAnswerA() {
        System.out.println("A) " + errorCounter);
    }

    @Override
    public void getAnswerB() {
        values.sort(Comparator.reverseOrder());
        System.out.println("B) " + values.get(values.size()/2));
    }
}
