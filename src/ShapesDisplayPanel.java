import javax.swing.*;
import java.awt.*;


/**
 * @author michalh
 * 
 * Panel for presenting the shapes.
 *
 */
public class ShapesDisplayPanel extends JPanel {
    private boolean displayShape;
    private Shape currentlyDisplaying;

    public static final int MAX_SHAPE_SIZE = 200;
    public ShapesDisplayPanel() {
        super();
        setBorder(BorderFactory.createEtchedBorder());
    }

    public boolean isDisplayShape() {
        return displayShape;
    }

    public void setDisplayShape(boolean displayShape) {
        this.displayShape = displayShape;
    }

    public Shape getCurrentlyDisplaying() {
        return currentlyDisplaying;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (displayShape){
            currentlyDisplaying = new Shape(new Dimension(MAX_SHAPE_SIZE,MAX_SHAPE_SIZE));
            currentlyDisplaying.draw(g);
        } else {
            g.clearRect(0,0,500,400);
        }
    }
}
