package Model;

import View.appWindow;

import javax.swing.*;
import java.awt.*;

/**
 * @author Philipp Klein (phi.klein17@gmail.com)
 * @version 1.0
 * @since 03.03.2025
 */

public class Edge extends PetriNetComponent {

    private Node node;
    private Transition transition;
    private boolean directedToNode = false;
    private byte weight = 1;

    /**
     * Constructor of the Edge class.
     *
     * @param node Node/Place that the Edge is connected to,
     * @param transition Transition that the Edge is connected to.
     * @param directedToNode is the Edge directed towards a Node/Place?
     */

    public Edge(Node node, Transition transition, boolean directedToNode) {
        super(30, 30, calculateButtonXCoordinate(node, transition)-15, calculateButtonYCoordinate(node, transition)-15);
        this.node = node;
        this.transition = transition;
        this.directedToNode = directedToNode;
    }

    /**
     * Calculates the middle X-Coordinate between a given Node/Place and Transition
     * for the button placement of the edge.
     *
     * @param node Node/Place for it's center X-Coordinate.
     * @param transition Transition for it's center X-Coordinate.
     * @return X-Coordinate in the middle of the Transition and Node/Place.
     */

    private static int calculateButtonXCoordinate(Node node, Transition transition) {
        int nodeX = node.getCenterXCoordinate();
        int transitionX = transition.getCenterXCoordinate();
        if ( nodeX > transitionX ) {
            return transitionX + ((nodeX - transitionX)/2);
        } else {
            return nodeX + ((transitionX - nodeX)/2);
        }
    }

    /**
     * Calculates the middle Y-Coordinate between a given Node/Place and Transition
     * for the button placement of the edge.
     *
     * @param node Node/Place for it's center Y-Coordinate.
     * @param transition Transition for it's center Y-Coordinate.
     * @return Y-Coordinate in the middle of the Transition and Node/Place.
     */

    private static int calculateButtonYCoordinate(Node node, Transition transition) {
        int nodeY = node.getCenterYCoordinate();
        int transitionX = transition.getCenterYCoordinate();
        if ( nodeY > transitionX ) {
            return transitionX + ((nodeY - transitionX)/2);
        } else {
            return nodeY + ((transitionX - nodeY)/2);
        }
    }

    /**
     * Get-Method for the Node/Place of the Edge.
     *
     * @return Node/Place of the Edge.
     */

    public Node getNode() {
        return node;
    }

    /**
     * Get-Method for the Transition of the Edge.
     *
     * @return Transition of the Edge.
     */

    public Transition getTransition() {
        return transition;
    }

    /**
     * Get-Method for the boolean if the Edge is
     * directed to a Node/Place or a Transition.
     *
     * @return boolean
     */

    public boolean isDirectedToNode() {
        return directedToNode;
    }

    /**
     * Get-Method for the weight of the Edge.
     *
     * @return weight of the Edge.
     */

    public byte getWeight() {
        return weight;
    }

    /**
     * Updates the weight of the Edge by a certain amount. If
     * the weight is greater than 9 it's reset to 0.
     *
     * @param button JButton for which the label/text is updated.
     * @param amount by which the weight will be changed.
     */

    private void updateWeight(JButton button, byte amount) {
        this.weight = (this.weight >= 9) ? (byte) 1 : (byte) ( this.weight + amount );
        button.setText( String.valueOf( this.weight ));
        update();
    }

    /**
     * Updates the Transition of the Edge.
     */

    @Override
    public void update() {
        transition.update();
    }

    /**
     * Deletes the Edge from the application window.
     */

    @Override
    public void delete() {
        appWindow.getInstance().deletePetriComponent(this);
    }

    /**
     * Renders the Edge on the screen. If it's directed to a Node/Place it's
     * green otherwise, it's red.
     *
     * @param g2d Graphics2D-Instance from the application window for rendering.
     */

    @Override
    public void render(Graphics2D g2d) {
        if ( directedToNode ) g2d.setColor( Color.red );
        else g2d.setColor( Color.green );

        g2d.drawLine(node.getCenterXCoordinate(), node.getCenterYCoordinate(), transition.getCenterXCoordinate(), transition.getCenterYCoordinate());
    }

    /**
     * Configures the Button of the Edge and adds ActionListeners.
     */

    @Override
    public void configureButton() {

        JButton button = getButton();

        button.setText("1");

        button.addActionListener(e -> {

            if ( appWindow.windowMode.equals("Run") ) updateWeight(button, (byte) 1);
            else if ( appWindow.windowMode.equals("Delete") ) delete();

        });

    }

}
