import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * ColorGame -extends JPanel
 * 
 */
public class ColorGame extends JPanel implements MouseListener, ActionListener, Runnable {
	public static final int GOOD_SHAPE_SIZE = 30;

	private int numOfGoodShapes; // Number of good shapes
	private int numOfGoodShapesToDisplay; // Number of times good shapes are presented.
	private GoodShapesPanel goodShapesPanel;
	private ShapesDisplayPanel shapesDisplayPanel;
	private JFrame frame;
	private JButton button;
	private JPanel bottomPanel;
	private JLabel scoreLabel;
	private int goodShapesShown;

	public ColorGame(int numOfGoodShapes, int numOfGoodShapesToDisplay) {
		super();
		this.numOfGoodShapes = numOfGoodShapes;
		this.numOfGoodShapesToDisplay = numOfGoodShapesToDisplay;


		frame = new JFrame("color game");
		frame.add(this);
		frame.setSize(500,400);
		setLayout(new BorderLayout());
		setGoodShapesPanel();

		shapesDisplayPanel = new ShapesDisplayPanel();

		bottomPanel = new JPanel();
		button = new JButton("start");
		bottomPanel.add(button);
		add(bottomPanel,BorderLayout.SOUTH);
		add(shapesDisplayPanel,BorderLayout.CENTER);
		button.addActionListener(this);

		frame.setVisible(true);
	}

	private void setGoodShapesPanel() {
		Set<Shape> set = new HashSet<>();

		for (int i=1; i<=numOfGoodShapes; i++){
			set.add(new Shape(new Point(10,i*(GOOD_SHAPE_SIZE+10))
					,new Dimension(GOOD_SHAPE_SIZE,GOOD_SHAPE_SIZE)));
		}

		goodShapesPanel = new GoodShapesPanel(set);

		add(goodShapesPanel, BorderLayout.WEST);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.println(me.getPoint());
		if (!shapesDisplayPanel.isDisplayShape()){
			decrementScore();
		} else {
			/*	if currently displayed shape is good	*/
			if (goodShapesPanel.hasShape(shapesDisplayPanel.getCurrentlyDisplaying())){
				/*	if clicked inside shape	*/
				if (shapesDisplayPanel.getCurrentlyDisplaying().isPointInShape(me.getPoint())){
					incrementScore();
				} else {
					decrementScore();
				}
			} else {
				decrementScore();
			}
		}
	}

	private void incrementScore() {
		scoreLabel.setText(""+(Integer.parseInt(scoreLabel.getText())+1));
	}
	private void decrementScore() {
		scoreLabel.setText(""+(Integer.parseInt(scoreLabel.getText())-1));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread(this);
		shapesDisplayPanel.addMouseListener(this);
		t.start();
	}

	public static void main(String[] args) {
		ColorGame cg = new ColorGame(2, // numOfGoodShapes
				5 // numOfGoodShapesToDisplay
		);

	}

	@Override
	public void run() {
		button.setVisible(false);
		scoreLabel = new JLabel("0");
		bottomPanel.add(scoreLabel);

		while (goodShapesShown < numOfGoodShapesToDisplay){
			shapesDisplayPanel.setDisplayShape(true);	//we want to draw shape
			shapesDisplayPanel.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (goodShapesPanel.hasShape(shapesDisplayPanel.getCurrentlyDisplaying())){
				goodShapesShown++;
			}
			shapesDisplayPanel.setDisplayShape(false);	//we want to erase shape
			shapesDisplayPanel.repaint();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(this,"Score: " + Integer.parseInt(scoreLabel.getText())
		+(Integer.parseInt(scoreLabel.getText()) > 0 ? "\nyou win!" : "\nyou lose!")
				,"game over",JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
}
