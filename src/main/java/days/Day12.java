package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12 implements DayInterface {

    AocUtils utils = new AocUtils();

    List<Caveday12> caveday12List = new ArrayList<>();

    public Day12() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day12!");

        Scanner scanner = utils.getScannerFromFileName("day12.txt");

        while(scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("-");

            String caveName1 = line[0];
            String caveName2 = line[1];
            boolean cave1Found =  false;
            boolean cave2Found =  false;

            for(Caveday12 caveday12 : caveday12List) {
                if( caveday12.name.equals(caveName1)) {
                    caveday12.pathToCave.add(caveName2);
                    cave1Found = true;
                }

                if( caveday12.name.equals(caveName2)) {
                    caveday12.pathToCave.add(caveName1);
                    cave2Found = true;
                }
            }

            if(!cave2Found) {
                Caveday12 caveday12 = new Caveday12();
                caveday12.name = caveName2;
                caveday12.pathToCave.add(caveName1);
                caveday12List.add(caveday12);
            }

            if(!cave1Found) {
                Caveday12 caveday12 = new Caveday12();
                caveday12.name = caveName1;
                caveday12.pathToCave.add(caveName2);
                caveday12List.add(caveday12);
            }

        }

        getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {
        int result = 0;

        for(Caveday12 caveday12 : caveday12List) {
            if(caveday12.name.equals("start")) {
                //lets go!
                for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                    List<String> pathList = new ArrayList<>();
                    pathList.add(caveday12.name);

                    result += findPathA(caveday12.pathToCave.get(i), pathList);
                }
                break;
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

            for(Caveday12 caveday12 : caveday12List) {
                if(caveday12.name.equals(caveName)) {
                    for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                        result += findPathA(caveday12.pathToCave.get(i), new ArrayList<>(pathList));
                    }
                    break;
                }
            }
        } else {
            if(!isSmallCave(caveName)) {
                pathList.add(caveName);
                for(Caveday12 caveday12 : caveday12List) {
                    if(caveday12.name.equals(caveName)) {
                        for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                            result += findPathA(caveday12.pathToCave.get(i), new ArrayList<>(pathList));
                        }
                        break;
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

        for(Caveday12 caveday12 : caveday12List) {
            if(caveday12.name.equals("start")) {
                //lets go!
                for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                    List<String> pathList = new ArrayList<>();
                    pathList.add(caveday12.name);

                    result += findPathB(caveday12.pathToCave.get(i), pathList, false);
                }
                break;
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

            for(Caveday12 caveday12 : caveday12List) {
                if(caveday12.name.equals(caveName)) {
                    for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                        result += findPathB(caveday12.pathToCave.get(i), new ArrayList<>(pathList), hasDoubleSmall);
                    }
                    break;
                }
            }
        } else {
            if(!isSmallCave(caveName)) {
                pathList.add(caveName);
                for(Caveday12 caveday12 : caveday12List) {
                    if(caveday12.name.equals(caveName)) {
                        for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                            result += findPathB(caveday12.pathToCave.get(i), new ArrayList<>(pathList), hasDoubleSmall);
                        }
                        break;
                    }
                }
            } else {
                if(!hasDoubleSmall) {
                    pathList.add(caveName);
                    for(Caveday12 caveday12 : caveday12List) {
                        if(caveday12.name.equals(caveName)) {
                            for(int i = 0; i < caveday12.pathToCave.size(); i++) {
                                result += findPathB(caveday12.pathToCave.get(i), new ArrayList<>(pathList), true);
                            }
                            break;
                        }
                    }
                }

            }
        }
        return result;
    }
}
