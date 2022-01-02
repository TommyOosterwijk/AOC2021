package days;

import com.google.common.base.Strings;
import utils.AocUtils;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day16 implements DayInterface {
    AocUtils utils = new AocUtils();
    String hexadecimal = "";
    int valueCounter = 0;
    int charsUsed = 0;

    public Day16() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day16!");

        Scanner scanner = utils.getScannerFromFileName("day16.txt");
        String hexa = scanner.nextLine();
        for(int i = 0; i < hexa.length(); i++) {
            hexadecimal += Strings.padStart(new BigInteger(hexa.charAt(i) + "", 16).toString(2), 4, '0');
        }
        getAnswerA();
    }

    @Override
    public void getAnswerA() {
        System.out.println(hexadecimal);
        System.out.println("A) " + parseString(hexadecimal, -1, 1, 0));
    }

    @Override
    public void getAnswerB() {
    }

    public long parseString(String hexaString, int maxChars, int maxPacket, int parentTypeId) {
        List<Long> values = new ArrayList<>();

        int localCharsUsed = 0;
        charsUsed =0;
        long value = 0;
        boolean useMaxChars = maxChars != -1;
        int typeId = 0;


        for(int i = 0; i < maxPacket; i++) {
            if(hexaString.length() == 0) {
                values.add(0l);
                break;
            }
            int t = Integer.valueOf(hexaString.substring(0, 3), 2);
            value += t;
            typeId = Integer.valueOf(hexaString.substring(3, 6), 2);
            maxChars -= 6;
            localCharsUsed+= 6;

            if (typeId == 4) {
                valueCounter=0;
                long t1 = literalValue(hexaString, 6);
                System.out.println(t1);
                values.add(t1);
                maxChars-= valueCounter;
                localCharsUsed+=valueCounter;
                hexaString = hexaString.substring(valueCounter+6);
            } else {
                //operator
                int lengthTypeId = Integer.valueOf(hexaString.substring(6, 7), 2);
                localCharsUsed++;
                maxChars--;

                if (lengthTypeId == 0) {
                    int totalLength = Integer.valueOf(hexaString.substring(7, 22), 2);
                    localCharsUsed+= 15;
                    long newValue = parseString(hexaString.substring(22), totalLength, 1, typeId);
                    values.add(newValue);
                    value += newValue;

                    hexaString = hexaString.substring(22+charsUsed);
                    localCharsUsed += charsUsed;
                    maxChars -= charsUsed;
                    if(hexaString.length() < 8) {
                        values.add(0l);
                        hexaString = "";
                        maxChars = 0;
                    }
                } else {
                    int totalSuppackets = Integer.valueOf(hexaString.substring(7, 18), 2);
                    localCharsUsed+= 11;

                    long newValue = parseString(hexaString.substring(18), -1, totalSuppackets, typeId);
                    values.add(newValue);
                    value += newValue;

                    hexaString = hexaString.substring(18+charsUsed);
                    maxChars -= charsUsed;
                    localCharsUsed += charsUsed;
                    if(hexaString.length() < 8) {
                        hexaString = "";
                        maxChars = 0;
                        values.add(0l);
                    }

                }
            }
            if(useMaxChars) {
                if(maxChars <= 0) {
                    break;
                } else {
                    maxPacket++;
                }
            }
        }

        charsUsed = localCharsUsed;

        if(parentTypeId == 0) {
            value = 0;

            for(Long i : values) {
                value += i;
            }

        } else if(parentTypeId == 1) {
            value = 0;

            if(values.size() == 1) {
                value = values.get(0);
            } else {
                value = 1;
                for(Long i : values) {
                    value *= i;
                }
            }

        } else if(parentTypeId == 2) {
            values.sort(Comparator.naturalOrder());
            value = values.get(0);

        } else if(parentTypeId == 3) {
            values.sort(Comparator.reverseOrder());
            value = values.get(0);
        } else if(parentTypeId == 5) {
            value = 1;
            if(values.get(0) <= values.get(1)) {
                value = 0;
            }

        } else if(parentTypeId == 6) {
            value = 1;
            if(values.get(0) >= values.get(1)) {
                value = 0;
            }
        } else if(parentTypeId == 7) {
            value = 1;
            if(values.get(0) != values.get(1)) {
                value = 0;
            }
        }
        return value;
    }

    public long literalValue(String string, int index) {
        String value = "";
        while(true) {
            valueCounter += 5;
            String part = string.substring(index, index+5);
            value += part.substring(1,5);
            if(part.charAt(0) == '0') {
                break;
            }
            index += 5;
        }
        System.out.println(value);
        return Long.valueOf(value, 2);
    }
}
