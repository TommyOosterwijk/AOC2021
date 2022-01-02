package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day18 implements DayInterface {
    List<String> lines = new ArrayList<>();
    AocUtils utils = new AocUtils();
    Node parent;

    public Day18() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day18");
        Scanner scanner =  utils.getScannerFromFileName("day18.txt");

        String l = scanner.nextLine();
        lines.add(l);
        processFish(null, l, -1, true);

        action();

        while(scanner.hasNextLine()) {
            increaseDepthByOne(parent);

            Node tempParent = new Node(0);
            tempParent.leftNode = parent;
            parent.parentNode = tempParent;
            parent = null;

            l = scanner.nextLine();
            lines.add(l);
            processFish(null, l, 0, true);

            tempParent.rightNode = parent;
            parent.parentNode = tempParent;

            parent = tempParent;
            action();
        }
        printNodeTree(parent);
        getAnswerA();
        getAnswerB();

    }

    public void printNodeTree(Node node) {
        if(node.leftNode == null && node.rightNode == null) {
            System.out.print(node.value + " ");
        }
        if(node.leftNode != null) {
            System.out.print("[");
            printNodeTree(node.leftNode);
        }

        if(node.rightNode != null) {
            printNodeTree(node.rightNode);
            System.out.print("]");
        }

    }

    public void increaseDepthByOne(Node node) {
        if(node != null) {
            node.depth++;

            increaseDepthByOne(node.rightNode);
            increaseDepthByOne(node.leftNode);
        }
    }

    public void findRightSide(Node start, Node previousNode, int value) {
        Node actionNode = start;
        boolean leftSide = false;

        while(true) {
            if(actionNode.value >= 0 && actionNode.leftNode == null && actionNode.rightNode == null) {
                actionNode.value += value;
                break;
            } else if( leftSide == true && actionNode.leftNode != null) {
                leftSide = true;
                previousNode = actionNode;
                actionNode = actionNode.leftNode;
            } else if (leftSide == false && actionNode.rightNode != null && actionNode.rightNode != previousNode) {
                leftSide = true;
                previousNode = actionNode;
                actionNode = actionNode.rightNode;
            } else if(actionNode.parentNode == null){
                break;
            } else {
                previousNode = actionNode;
                actionNode = actionNode.parentNode;
                leftSide = false;
            }
        }
    }

    public void action() {

        Node actionNode = parent;
        Node previousNode = new Node(0);
        Node valueNode = new Node(-3);

        boolean leftSide = true;
        boolean explode = true;
        printNodeTree(parent);
        System.out.println();

        while(true) {
            Node temp = actionNode;

            if(actionNode.depth >= 5) {

                Node nodeParent = actionNode.parentNode;

                if(valueNode.depth != -3) {
                    valueNode.value += nodeParent.leftNode.value;
                }

                findRightSide(nodeParent, nodeParent.rightNode, nodeParent.rightNode.value);

                nodeParent.rightNode = null;
                nodeParent.leftNode = null;
                nodeParent.value = 0;

                previousNode = new Node(0);
                valueNode = new Node(-3);
                leftSide = true;
                actionNode = parent;
                printNodeTree(parent);
                System.out.println();
            } else if(actionNode.value >= 10 && !explode) {
                Node leftChild = new Node(actionNode.depth + 1);
                leftChild.parentNode = actionNode;
                leftChild.value = (int) Math.floor(actionNode.value / 2.0);
                Node rightChild = new Node(actionNode.depth + 1);
                rightChild.parentNode = actionNode;
                rightChild.value = (int) Math.ceil(actionNode.value / 2.0);

                actionNode.value = 0;
                actionNode.leftNode = leftChild;
                actionNode.rightNode = rightChild;

                leftSide = true;
                previousNode = new Node(0);
                valueNode = new Node(-3);
                actionNode = parent;
                printNodeTree(parent);
                System.out.println();
                explode = true;

            } else if( leftSide == true && actionNode.leftNode != null) {
                leftSide = true;
                previousNode = actionNode;
                actionNode = actionNode.leftNode;
            } else if (leftSide == false && actionNode.rightNode != null && actionNode.rightNode != previousNode) {
                leftSide = true;
                previousNode = actionNode;
                actionNode = actionNode.rightNode;
            } else if(actionNode.parentNode == null){
                if(explode) {
                    leftSide = true;
                    previousNode = new Node(0);
                    valueNode = new Node(-3);
                    actionNode = parent;
                    explode = false;
                } else {
                    break;
                }
            } else {
                previousNode = actionNode;
                actionNode = actionNode.parentNode;
                leftSide = false;
            }

            if(temp.leftNode == null && temp.rightNode == null) {
                valueNode = temp;
            }
        }

    }

    public void processFish(Node parentNode, String s, int depth, boolean leftSide) {
        Node childNode = new Node(depth + 1);

        if(parentNode != null) {
            childNode.parentNode = parentNode;
        } else {
            if(parent == null) {
                parent = childNode;
            }
        }

        if(s.length() != 0) {
            if (s.charAt(0) == '[') {
                if (leftSide) {
                    if(parentNode != null) {
                        parentNode.leftNode = childNode;
                    }
                    processFish(childNode, s.substring(1), childNode.depth, true);
                } else {
                    if(parentNode != null) {
                        parentNode.rightNode = childNode;
                    }
                    processFish(childNode, s.substring(1), childNode.depth, true);
                }
            } else if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
                childNode.value = Integer.parseInt(s.charAt(0) + "");
                if (leftSide) {
                    parentNode.leftNode = childNode;
                } else {
                    parentNode.rightNode = childNode;
                }
                processFish(parentNode, s.substring(1), depth, false);
            } else if (s.charAt(0) == ',') {
                processFish(parentNode, s.substring(1), depth, false);
            } else if (s.charAt(0) == ']') {
                processFish(parentNode.parentNode, s.substring(1), depth-1, false);
            }
        }
    }

    @Override
    public void getAnswerA() {

        System.out.println();
        System.out.println("A) " + calculateAnswer(parent));
    }

    long calculateAnswer(Node node) {
        long result = 0;

        if(node.leftNode == null && node.rightNode == null) {
            return node.value;
        }

        if(node.leftNode != null) {
            result += (calculateAnswer(node.leftNode) * 3);
        }

        if(node.rightNode != null) {
            result += (calculateAnswer(node.rightNode) * 2);
        }

        return result;
    }

    @Override
    public void getAnswerB() {
        long highestResult = 0;
        for(int i = 0; i < lines.size(); i++) {
            for(int i2 = 0; i2 < lines.size(); i2++) {
                if(i != i2) {

                    parent = null;

                    processFish(null, lines.get(i), -1, true);
                    action();

                    increaseDepthByOne(parent);

                    Node tempParent = new Node(0);
                    tempParent.leftNode = parent;
                    parent.parentNode = tempParent;
                    parent = null;

                    processFish(null, lines.get(i2), 0, true);

                    tempParent.rightNode = parent;
                    parent.parentNode = tempParent;

                    parent = tempParent;
                    action();

                    long result = calculateAnswer(parent);

                    if(result  > highestResult) {
                        highestResult = result;
                    }
                }
            }
        }

        System.out.println("B) " + highestResult);
    }
}
