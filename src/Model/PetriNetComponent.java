package Model;

import javax.swing.*;
import java.awt.*;

/**
 * @author Philipp Klein (phi.klein17@gmail.com)
 * @version 1.0
 * @since 03.03.2025
 */

public abstract class PetriNetComponent {

    private int xCoordinate, yCoordinate, centerXCoordinate, centerYCoordinate;
    private JButton button;

    public PetriNetComponent(int xCoordinate, int yCoordinate, int buttonWidth, int buttonHeight, int buttonXCoordinate, int buttonYCoordinate) {
        this(buttonWidth, buttonHeight, buttonXCoordinate, buttonYCoordinate);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public PetriNetComponent(int buttonWidth, int buttonHeight, int buttonXCoordinate, int buttonYCoordinate) {
        this.button = new JButton();
        this.button.setBounds(buttonXCoordinate, buttonYCoordinate, buttonWidth, buttonHeight);
        this.button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        configureButton();
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setCenterXCoordinate(int centerXCoordinate) {
        this.centerXCoordinate = centerXCoordinate;
    }

    public int getCenterXCoordinate() {
        return centerXCoordinate;
    }

    public void setCenterYCoordinate(int centerYCoordinate) {
        this.centerYCoordinate = centerYCoordinate;
    }

    public int getCenterYCoordinate() {
        return centerYCoordinate;
    }

    public JButton getButton() {
        return button;
    }

    public abstract void update();

    public abstract void delete();

    public abstract void render(Graphics2D g2d);

    public abstract void configureButton();

}
