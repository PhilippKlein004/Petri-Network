package Model;

import View.appWindow;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Philipp Klein (phi.klein17@gmail.com)
 * @version 1.0
 * @since 03.03.2025
 */

public class Transition extends PetriNetComponent {

    private final ArrayList<Edge> incomingEdges = new ArrayList<>();
    private final ArrayList<Edge> outgoingEdges = new ArrayList<>();
    private byte maxOutgoingWeight = 0;

    /**
     * Constructor of the Transition.
     *
     * @param xCoordinate on the screen.
     * @param yCoordinate on the screen.
     */

    public Transition(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, 26, 50, xCoordinate+2, yCoordinate+5);
        // Half of width/height of component
        setCenterXCoordinate(xCoordinate+15);
        setCenterYCoordinate(yCoordinate+30);
    }

    /**
     * Updated the ArrayLists for the incoming and outgoing
     * Edges of the Transition.
     */

    private void updateEdgeLists() {

        incomingEdges.clear();
        outgoingEdges.clear();

        for ( Edge edge : appWindow.getInstance().getEdges() ) {
            if (  !edge.isDirectedToNode() && edge.getTransition() == this ) incomingEdges.add( edge );
            if (   edge.isDirectedToNode() && edge.getTransition() == this ) outgoingEdges.add( edge );
        }

    }

    /**
     * Updates the Transition for its activity. If the window Mode is other than "Run", the button gets
     * active for possible interactions.
     *
     * If the window Mode is "Run" and there are no incoming OR outgoing Edges => deactivated!
     * If the weight of the incoming Nodes/Places are lower than the weights of the incoming Edges => deactivated!
     * If the weight of the incoming Nodes/Place are lower than the weights of the outgoing Edges => deactivated!
     *
     * (Vice versa)
     */

    @Override
    public void update() {

        if ( !appWindow.windowMode.equals("Run") ) {
            getButton().setEnabled(true);
            return;
        }

        updateEdgeLists();
        maxOutgoingWeight = 0;

        if ( incomingEdges.isEmpty() || outgoingEdges.isEmpty() ) {
            getButton().setEnabled(false);
            return;
        }

        for ( Edge incomingEdge : incomingEdges ) {
            if ( incomingEdge.getNode().getWeight() < incomingEdge.getWeight() ) {
                getButton().setEnabled(false);
                return;
            }
            for ( Edge outgoingEdge : outgoingEdges ) {
                if ( outgoingEdge.getWeight() > maxOutgoingWeight ) maxOutgoingWeight = outgoingEdge.getWeight();
                if ( outgoingEdge.getWeight() > incomingEdge.getNode().getWeight() ) {
                    getButton().setEnabled(false);
                    return;
                }
            }
        }

        getButton().setEnabled(true);
    }

    /**
     * Deletes the Transition from the application.
     */

    @Override
    public void delete() {
        appWindow.getInstance().deletePetriComponent(this);
    }

    /**
     * Fires the Transition. It decrements the weights of the incoming Nodes/Places by
     * the maximum weight of the outgoing edges and increments the weights of the outgoing
     * Nodes/Places by their edge weights. After it fired, it updates itself.
     */

    private void fire() {

        for ( Edge edge : incomingEdges ) {
            edge.getNode().decrementWeight(maxOutgoingWeight);
        }

        for ( Edge edge : outgoingEdges ) {
            edge.getNode().incrementWeight(edge.getWeight());
        }

        update();

    }

    /**
     * Renders the Transition on the application window.
     *
     * @param g2D Graphics2D-Instance for rendering.
     */

    @Override
    public void render(Graphics2D g2D) {
        g2D.fillRect(getXCoordinate(), getYCoordinate(), 30, 60);
        update();
    }

    /**
     * Configuration of the Button of the Transition and
     * implementation of ActionListeners.
     */

    @Override
    public void configureButton() {

        getButton().setText(">");
        getButton().addActionListener(e -> {

            if ( appWindow.windowMode.equals("Run") ) fire();
            else if ( appWindow.windowMode.equals("Delete") ) delete();
            else if ( appWindow.windowMode.equals("Connect") ) appWindow.getInstance().addTransitionOfNewEdge(this);

        });

    }

}
