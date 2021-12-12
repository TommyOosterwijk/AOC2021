package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12 implements DayInterface {

    AocUtils utils = new AocUtils();

    List<Cave> caveList = new ArrayList<>();

    public Day12() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day12!");

        Scanner scanner = utils.getScannerFromFileName("day12.txt");

        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("-");

            String caveName1 = line[0];
            String caveName2 = line[1];
            boolean cave1Found =  false;
            boolean cave2Found =  false;

            for(Cave cave : caveList) {
                if( cave.name.equals(caveName1)) {
                    cave.pathToCave.add(caveName2);
                    cave1Found = true;
                }

                if( cave.name.equals(caveName2)) {
                    cave.pathToCave.add(caveName1);
                    cave2Found = true;
                }

            }


            if(!cave2Found) {
                Cave cave = new Cave();
                cave.name = caveName2;
                cave.pathToCave.add(caveName1);
                caveList.add(cave);

            }

            if(!cave1Found) {
                Cave cave = new Cave();
                cave.name = caveName1;
                cave.pathToCave.add(caveName2);
                caveList.add(cave);

            }

        }

        getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {
        int result = 0;

        for(Cave cave : caveList) {
            if(cave.name.equals("start")) {
                //lets go!
                for(int i = 0; i < cave.pathToCave.size(); i++) {
                    List<String> pathList = new ArrayList<>();
                    pathList.add(cave.name);

                    result += findPathA(cave.pathToCave.get(i), pathList);
                }
            }
        }
        System.out.println("A) " + result);
    }

    private int findPathA(String caveName, List<String> pathList) {
        int result = 0;
        if(caveName.equals("end")) {
            return 1;
        }

        if(!pathList.contains(caveName)) {
            pathList.add(caveName);

            for(Cave cave : caveList) {
                if(cave.name.equals(caveName)) {
                    for(int i = 0; i < cave.pathToCave.size(); i++) {
                        result += findPathA(cave.pathToCave.get(i), new ArrayList<>(pathList));
                    }
                }
            }
        } else {
            if(!isSmallCave(caveName)) {
                pathList.add(caveName);
                for(Cave cave : caveList) {
                    if(cave.name.equals(caveName)) {
                        for(int i = 0; i < cave.pathToCave.size(); i++) {
                            result += findPathA(cave.pathToCave.get(i), new ArrayList<>(pathList));
                        }
                    }
                }
            }
        }
        return result;
    }

    boolean isSmallCave(String cave) {
        return cave.charAt(0) >= 'a' && cave.charAt(0) <= 'z';
    }

    @Override
    public void getAnswerB() {
        int result = 0;

        for(Cave cave : caveList) {
            if(cave.name.equals("start")) {
                //lets go!
                for(int i = 0; i < cave.pathToCave.size(); i++) {
                    List<String> pathList = new ArrayList<>();
                    pathList.add(cave.name);

                    result += findPathB(cave.pathToCave.get(i), pathList, false);
                }
            }
        }
        System.out.println("B) " + result);
    }

    private int findPathB(String caveName, List<String> pathList, boolean hasDoubleSmall) {
        int result = 0;
        if(caveName.equals("end")) {
            return 1;
        }

        if(caveName.equals("start")) {
            return 0;
        }

        if(!pathList.contains(caveName)) {
            pathList.add(caveName);

            for(Cave cave : caveList) {
                if(cave.name.equals(caveName)) {
                    for(int i = 0; i < cave.pathToCave.size(); i++) {
                        result += findPathB(cave.pathToCave.get(i), new ArrayList<>(pathList), hasDoubleSmall);
                    }
                }
            }
        } else {
            if(!isSmallCave(caveName)) {
                pathList.add(caveName);
                for(Cave cave : caveList) {
                    if(cave.name.equals(caveName)) {
                        for(int i = 0; i < cave.pathToCave.size(); i++) {
                            result += findPathB(cave.pathToCave.get(i), new ArrayList<>(pathList), hasDoubleSmall);
                        }
                    }
                }
            } else {

                if(!hasDoubleSmall) {
                    pathList.add(caveName);
                    for(Cave cave : caveList) {
                        if(cave.name.equals(caveName)) {
                            for(int i = 0; i < cave.pathToCave.size(); i++) {
                                result += findPathB(cave.pathToCave.get(i), new ArrayList<>(pathList), true);
                            }
                        }
                    }
                }

            }
        }
        return result;
    }
}
