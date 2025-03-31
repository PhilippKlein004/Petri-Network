package Model.Demo;

import Model.Edge;
import Model.Node;
import Model.Transition;

import java.util.ArrayList;

/**
 * @author Philipp Klein (phi.klein17@gmail.com)
 * @version 1.0
 * @since 03.03.2025
 */

public abstract class ExampleNet {

    private static final ArrayList<Node> nodes = new ArrayList<>();
    private static final ArrayList<Transition> transitions = new ArrayList<>();
    private static final ArrayList<Edge> edges = new ArrayList<>();

    private static void setUp() {

        Node test = new Node(50, 50);
        Node test2 = new Node(100, 300);
        Node test3 = new Node(500, 200);
        Node test4 = new Node(300, 300);

        Transition t1 = new Transition(200, 200);

        Edge e1 = new Edge(test, t1, false);
        Edge e2 = new Edge(test2, t1, false);
        Edge e3 = new Edge(test3, t1, true);
        Edge e4 = new Edge(test4, t1, true);

        nodes.add(test);
        nodes.add(test2);
        nodes.add(test3);
        nodes.add(test4);

        transitions.add(t1);

        edges.add(e1);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);

    }

    public static ArrayList<Node> getNodes() {
        if ( nodes.isEmpty() ) setUp();
        return nodes;
    }

    public static ArrayList<Transition> getTransitions() {
        if ( transitions.isEmpty() ) setUp();
        return transitions;
    }

    public static ArrayList<Edge> getEdges() {
        if ( edges.isEmpty() ) setUp();
        return edges;
    }

}
