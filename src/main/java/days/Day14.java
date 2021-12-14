package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day14 implements DayInterface {

    String polymerTemplate = "";
    AocUtils utils = new AocUtils();
    List<Rule> rulesList = new ArrayList<>();
    BigInteger[] polymer;

    public Day14() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day14!");
        Scanner scanner = utils.getScannerFromFileName("day14.txt");

        polymerTemplate = scanner.nextLine();
        scanner.nextLine();

        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" -> ");

            Rule rule = new Rule();
            rule.value = line[0];
            rule.addedChar = line[1];

            rulesList.add(rule);
        }

        polymer = new BigInteger[rulesList.size()];

        for(int i = 0; i < polymer.length; i++) {
            polymer[i] = new BigInteger("0");
        }

        for(int i = 0; i < polymerTemplate.length()-1; i++) {
            String result = polymerTemplate.charAt(i) + "" + polymerTemplate.charAt(i+1);
            for(int i2 = 0; i2 < rulesList.size(); i2++) {
                if(rulesList.get(i2).value.equals(result)) {
                    polymer[i2] = polymer[i2].add(new BigInteger("1"));
                    break;
                }
            }
        }
        getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {

        System.out.println("A) " +  executeRounds(10));

    }

    @Override
    public void getAnswerB() {
        System.out.println("B) " +  executeRounds(40));
    }

    private BigInteger executeRounds(int rounds) {
        for(int round = 0; round < rounds; round++) {

            BigInteger[] tempPolymer = new BigInteger[polymer.length];
            for(int i = 0; i < tempPolymer.length; i++) {
                tempPolymer[i] = new BigInteger("0");
            }
            for(int i = 0; i < polymer.length; i++) {
                if(polymer[i].compareTo(new BigInteger("0")) == 1) {
                    Rule rule = rulesList.get(i);

                    for(int i2 = 0; i2 < rulesList.size(); i2++) {
                        if(rulesList.get(i2).value.equals(rule.output1()) || rulesList.get(i2).value.equals(rule.output2())) {
                            tempPolymer[i2] = tempPolymer[i2].add(polymer[i]);
                        }
                    }
                }
            }
            polymer = tempPolymer;
        }

        BigInteger kCounter = new BigInteger("0"); //endpos

        for(int i = 0; i < polymer.length; i++) {
            if(polymer[i].compareTo(new BigInteger("0")) == 1) {
                Rule rule = rulesList.get(i);
                if(rule.value.contains("KK")) {
                    kCounter = kCounter.add(polymer[i].multiply(new BigInteger("2")));
                } else if(rule.value.contains("K")) {
                    kCounter = kCounter.add(polymer[i]);
                }
            }
        }

        kCounter = kCounter.divide(new BigInteger("2"));

        BigInteger vCounter = new BigInteger("1"); //endpos

        for(int i = 0; i < polymer.length; i++) {
            if(polymer[i].compareTo(new BigInteger("0")) == 1) {
                Rule rule = rulesList.get(i);
                if(rule.value.contains("VV")) {
                    vCounter = vCounter.add(polymer[i].multiply(new BigInteger("2")));
                } else if(rule.value.contains("V")) {
                    vCounter = vCounter.add(polymer[i]);
                }
            }
        }

        vCounter=vCounter.divide(new BigInteger("2"));
        return kCounter.subtract(vCounter);
    }
}
