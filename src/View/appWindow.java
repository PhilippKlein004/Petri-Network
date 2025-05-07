package View;

import Model.Demo.ExampleNet;
import Model.Edge;
import Model.Node;
import Model.PetriNetComponent;
import Model.Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Philipp Klein (phi.klein17@gmail.com)
 * @version 1.0
 * @since 03.03.2025
 */

public class appWindow extends JFrame {

    private static appWindow instance;
    public static String windowMode = "Run";

    private static final ArrayList<Node> nodes = ExampleNet.getNodes();
    private static final ArrayList<Transition> transitions = ExampleNet.getTransitions();
    private static final ArrayList<Edge> edges = ExampleNet.getEdges();

    private final ArrayList<JButton> windowButtons = new ArrayList<>();

    private final DrawingPanel drawingPanel = new DrawingPanel();

    private final JLabel copyright = new JLabel("Copyright (c) Philipp Klein");

    private final JButton PlayEditButton = new JButton("Edit");
    private final JButton DeleteButton = new JButton("Delete OFF");
    private final JButton AddNodeButton = new JButton("Add Node OFF");
    private final JButton AddTransitionButton = new JButton("Add Transition OFF");
    private final JButton AddEdge = new JButton("Add Edge OFF");

    private Node newEdgeNode;
    private Transition newEdgeTransition;
    private boolean directedToNode;

    /**
     * Panel for the rendering of the PetriComponents.
     */

    private class DrawingPanel extends JPanel {

        /**
         * Method that renders the Components.
         *
         * @param g the <code>Graphics</code> object to protect
         */

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setStroke(new BasicStroke(2));
            g2D.translate(0,0);
            g2D.setColor(new Color(10, 132, 255));

            for (Node node : nodes) {
                node.render(g2D);
            }

            for (Transition transition : transitions) {
                transition.render(g2D);
            }

            for (Edge edge : edges) {
                edge.render(g2D);
            }

        }

    }

    /**
     * Singleton Pattern for the appWindow.
     *
     * @return Instance of the appWindow.
     */

    public static appWindow getInstance() {
        if (instance == null) instance = new appWindow();
        return instance;
    }

    /**
     * Constructor of the app window. Here the App-Window, Buttons, Texts and ActionListeners are
     * created and customized.
     */

    private appWindow() {

        drawingPanel.setBounds(0, 0, 1200, 605);
        copyright.setBounds(515, 600, 170, 25);

        PlayEditButton.setBounds(1100, 595, 100, 25);
        PlayEditButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        PlayEditButton.addActionListener(e -> {

            if ( windowMode.equals("Run") ) {

                windowMode = "Edit";
                PlayEditButton.setText("Run");
                showAllButtons();

            } else {

                windowMode = "Run";
                PlayEditButton.setText("Edit");
                hideButtonsExcept(PlayEditButton);

            }

            for (Transition transition : transitions) transition.update();

        });

        DeleteButton.setBounds(980, 595, 110, 25);
        DeleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        DeleteButton.setVisible(false);

        DeleteButton.addActionListener(e -> {

            if ( windowMode.equals("Delete") ) {

                windowMode = "Edit";
                DeleteButton.setText("Delete OFF");
                showAllButtons();

            } else {

                DeleteButton.setText("Delete ON");
                windowMode = "Delete";
                hideButtonsExcept(DeleteButton);

            }

        });

        AddNodeButton.setBounds(850, 595, 120, 25);
        AddNodeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AddNodeButton.setVisible(false);

        AddNodeButton.addActionListener(e -> {

            if ( windowMode.equals("Add Node") ) {

                windowMode = "Edit";
                AddNodeButton.setText("Add Node OFF");
                showAllButtons();

            } else {

                windowMode = "Add Node";
                AddNodeButton.setText("Add Node ON");
                hideButtonsExcept(AddNodeButton);
            }

        });

        AddTransitionButton.setBounds(710, 595, 150, 25);
        AddTransitionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AddTransitionButton.setVisible(false);

        AddTransitionButton.addActionListener(e -> {

            if ( windowMode.equals("Add Transition") ) {

                windowMode = "Edit";
                AddTransitionButton.setText("Add Transition OFF");
                showAllButtons();

            } else {

                windowMode = "Add Transition";
                AddTransitionButton.setText("Add Transition ON");
                hideButtonsExcept(AddTransitionButton);

            }

        });

        AddEdge.setBounds(710, 570, 260, 25);
        AddEdge.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AddEdge.setVisible(false);

        AddEdge.addActionListener(e -> {

            if ( windowMode.equals("Connect") ) {

                windowMode = "Edit";
                AddEdge.setText("Add Edge OFF");
                showAllButtons();

            } else {

                windowMode = "Connect";
                AddEdge.setText("Add Edge ON");
                hideButtonsExcept(AddEdge);

            }

        });

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if ( windowMode.equals("Add Node") ) {
                    Node newNode = new Node(e.getX()-20, e.getY()-45);
                    appWindow.nodes.add(newNode);
                    addPetriComponentButton(newNode.getButton());
                } else if ( windowMode.equals("Add Transition") ) {
                    Transition newTransition = new Transition(e.getX()-20, e.getY()-45);
                    appWindow.transitions.add(newTransition);
                    addPetriComponentButton(newTransition.getButton());
                }

            }

        });

        // ------------------------ JFrame customization ------------------------

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Petri Network");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(1220, 660);
        this.setVisible(true);
        this.setLayout(null);

        for ( Node node : nodes ) this.add(node.getButton());

        for ( Transition transition : transitions ) this.add(transition.getButton());

        for ( Edge edge : edges ) this.add(edge.getButton());

        this.add(AddEdge);
        this.add(AddTransitionButton);
        this.add(AddNodeButton);
        this.add(DeleteButton);
        this.add(PlayEditButton);

        windowButtons.add(PlayEditButton);
        windowButtons.add(AddNodeButton);
        windowButtons.add(AddTransitionButton);
        windowButtons.add(AddEdge);
        windowButtons.add(DeleteButton);

        this.add(drawingPanel);
        this.add(copyright);

        this.repaint();

    }

    /**
     * Deletes the PetriComponent from the application with it's connected Components.
     *
     * @param petriNetComponent Component of the Petri-Network that's going to be deleted.
     */

    public void deletePetriComponent(PetriNetComponent petriNetComponent) {

        remove(petriNetComponent.getButton());

        if ( petriNetComponent instanceof Node ) {

            nodes.remove(petriNetComponent);
            ArrayList<Edge> edgesWithThisNode = new ArrayList<>(edges.stream().filter(edge -> edge.getNode() == petriNetComponent).collect(Collectors.toList()));

            for ( Edge edge : edgesWithThisNode ) if ( edge.getNode() == petriNetComponent ) edge.delete();

        } else if ( petriNetComponent instanceof Transition ) {

            transitions.remove(petriNetComponent);
            ArrayList<Edge> edgesWithThisTransition = new ArrayList<>(edges.stream().filter(edge -> edge.getTransition() == petriNetComponent).collect(Collectors.toList()));

            for ( Edge edge : edgesWithThisTransition ) if ( edge.getTransition() == petriNetComponent ) edge.delete();

        } else if ( petriNetComponent instanceof Edge ) {

            edges.remove(petriNetComponent);

        }

        repaint();
    }

    /**
     * Saves the Node/Place to the private attribute of the window. If this is
     * the first value to be filled, it's logical for this Edge to point to a
     * Transition, so the directedToNode is set to false.
     *
     * @param newNode new Node/Place of the Network.
     */

    public void addNodeOfNewEdge(Node newNode) {
        if ( this.newEdgeTransition == null ) directedToNode = false;
        this.newEdgeNode = newNode;
        addNewEdge();
    }

    /**
     * Saves the Transition to the private attribute of the window. If this is
     * the first value to be filled, it's logical for this Edge to point to a
     * Node/Place, so the directedToNode is set to true.
     *
     * @param newTransition new Transition of the Network.
     */

    public void addTransitionOfNewEdge(Transition newTransition) {
        if ( this.newEdgeNode == null ) directedToNode = true;
        this.newEdgeTransition = newTransition;
        addNewEdge();
    }

    /**
     * Adds the new Edge to the Network if the Transition and Node/Place are both not null and
     * if there isn't an Edge containing the same Components regardless directedToNode being true or false. If
     * there is an existing Edge, a Message dialog will show.
     *
     * This is due to visibility and usability reasons.
     */

    private void addNewEdge() {

        if ( newEdgeNode == null || newEdgeTransition == null ) return;

        Edge dummy = new Edge(newEdgeNode, newEdgeTransition, directedToNode);

        if ( doesEdgeExist(dummy) ) JOptionPane.showMessageDialog(this, "An Edge already exists between those Elements.\nDelete this one, if you want to change it.", "Existing Edge", JOptionPane.INFORMATION_MESSAGE);
        else {
            edges.add(dummy);
            addPetriComponentButton(dummy.getButton());
        }

        newEdgeNode = null;
        newEdgeTransition = null;
    }

    /**
     * Examine, the given Edge is already existing in the current network.
     *
     * @param edge Edge instance.
     * @return boolean value, if the Edge exists.
     */

    private boolean doesEdgeExist( Edge edge ) {
        for ( Edge existingEdge : edges ) if ( edge.getNode() == existingEdge.getNode() && edge.getTransition() == existingEdge.getTransition() ) return true;
        return false;
    }

    /**
     * Adds a Button from a PetriComponent to the application window. It is necessary to first
     * remove the Panel, add the button and then adding the Panel back for rendering the Edges etc.
     *
     * @param button Button from a PetriComponent.
     */

    private void addPetriComponentButton(JButton button) {
        remove(drawingPanel);
        add(button);
        add(drawingPanel);
        repaint();
    }

    /**
     * Shows all the Control-Buttons in "Edit-Mode"
     */

    private void showAllButtons() {
        for ( JButton button : windowButtons ) button.setVisible(true);
    }

    /**
     * Hides all the Buttons visible in "Edit-Mode", except the given Button.
     *
     * @param shownButton Button that is left visible.
     */

    private void hideButtonsExcept(JButton shownButton) {
        for ( JButton button : windowButtons ) if ( button != shownButton ) button.setVisible(false);
    }

    /**
     * Returns all the Edges of the Petri-Network in this application.
     *
     * @return List of Edges.
     */

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Updates the Transitions in the window.
     */

    public void updateTransitions() {
        for ( Transition transition : transitions ) transition.update();
    }

}
