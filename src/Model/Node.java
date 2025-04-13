package Model;

import View.appWindow;

import javax.swing.*;
import java.awt.*;

/**
 * @author Philipp Klein (phi.klein17@gmail.com)
 * @version 1.0
 * @since 03.03.2025
 */

public class Node extends PetriNetComponent {

    private byte weight = 0;

    /**
     * Constructor of the Node/Place class.
     *
     * @param xCoordinate X-Coordinate on the screen.
     * @param yCoordinate Y-Coordinate on the screen.
     */

    public Node(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, 30, 30, xCoordinate+10, yCoordinate+10);
        // Half of width/height of component
        setCenterXCoordinate(xCoordinate+25);
        setCenterYCoordinate(yCoordinate+25);
    }

    /**
     * Get-Method for the weight of the Node/Place.
     *
     * @return weight of the Node/Place.
     */

    public byte getWeight() {
        return this.weight;
    }

    /**
     * Method that decrements the weight of the Node/Place
     * by a certain amount. If it's less than 0 it's set to 0.
     *
     * @param amount of which the weight is decremented.
     */

    public void decrementWeight( byte amount ) {
        weight = ( weight - amount < 0 ) ? 0 : (byte) (weight - amount);
        update();
    }

    /**
     * Method that increases the weight of the Node/Place by
     * a certain amount. If it's greater than 9 it's subtracted by 10.
     *
     * @param amount of which the weight is increased.
     */

    public void incrementWeight( byte amount ) {
        this.weight = (weight + amount > 9) ? (byte) ( ( weight + amount ) - 10 ) : (byte) (weight + amount);
        update();
    }

    /**
     * Updates the weight of the Node/Place when the Button is pressed.
     *
     * @param weight custom amount of weight increase.
     */

    private void updateWeight( byte weight ) {
        this.weight = (this.weight >= 9) ? (byte) 0 : (byte) (this.weight + weight );
        for ( Edge edge : appWindow.getInstance().getEdges() ) if ( edge.getNode() == this && !edge.isDirectedToNode() ) edge.getTransition().update();
        update();
    }

    /**
     * Updates the text of the button to display the current weight of the Node/Place.
     */

    @Override
    public void update() {
        getButton().setText(String.valueOf(weight));
    }

    /**
     * Deletes the Node/Place from the application.
     */

    @Override
    public void delete() {
        appWindow.getInstance().deletePetriComponent(this);
    }

    /**
     * Renders the Node/Place in the application window.
     *
     * @param g2D Graphics2D-Instance for rendering.
     */

    @Override
    public void render(Graphics2D g2D) {
        g2D.fillOval(getXCoordinate(), getYCoordinate(), 50, 50);
    }

    /**
     * Configures the button of the Node/Place and adds ActionListeners.
     */

    @Override
    public void configureButton() {

        JButton button = getButton();

        button.setText(String.valueOf(weight));

        button.addActionListener(e -> {

            switch ( appWindow.windowMode ) {
                case "Run" -> updateWeight((byte) 1);
                case "Delete" -> delete();
                case "Connect", "Connect N to T" -> appWindow.getInstance().addNodeOfNewEdge(this);
            }

        } );

    }

}
