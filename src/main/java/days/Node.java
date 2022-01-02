package days;

public class Node {

    int depth = 0;
    int value = 0;

    Node(int depth) {
        this.depth = depth;
    }

    Node parentNode;
    Node leftNode;
    Node rightNode;
}
