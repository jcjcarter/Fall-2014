package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.IView2ModelAdapter;

/**
 * View Class for the BallWorld GUI
 * 
 * @author Jake Kornblau, Jayson Carter
 */
public class BallView extends JFrame {
	/** Generated SerialVersion UID for the body panel */
	private static final long serialVersionUID = -7063488266654092746L;

	/**
	 * Model Adapter. Initialized to a no-op to ensure well-defined behavior by
	 * the system.
	 */
	private IView2ModelAdapter _view2ModelAdpt = IView2ModelAdapter.NULL_OBJECT;

	/** Content Panel that holds all panels. */
	private JPanel contentPane;

	/** Header panel that holds the options for the user. */
	private final JPanel hdrPanel = new JPanel();

	/** Text Field to type the name of the class into. */
	private final JTextField txtBallClassToAdd = new JTextField();

	/**
	 * Button to add a ball of the name specified by txtBallClassToAdd to
	 * bodyPanel upon click.
	 */
	private final JButton btnAddBall = new JButton("Add Ball");

	/** Button to clear all balls upon click. */
	private final JButton btnClearBalls = new JButton("Clear Balls");

	/** Panel in which the Balls will be displayed */
	private final JPanel bodyPanel = new JPanel() {
		/** Generated SerialVersion UID for the body panel */
		private static final long serialVersionUID = -6952656931251224807L;

		/**
		 * Repains the panel. Clears all items on the panel and repains as
		 * specified by the adapter.paint function.
		 * 
		 * @param g
		 *            The graphic to paint onto
		 */
		public void paintComponent(Graphics g) {
			/* Clears the panel and redoes the background */
			super.paintComponent(g);
			/* Call model to paint all required items */
			_view2ModelAdpt.paint(g);

		}
	};

	/**
	 * Constructor is supplied with an instance of the model adapter.
	 * 
	 * @param _view2ModelAdpt
	 *            The adapter between this class and the model class.
	 */
	public BallView(IView2ModelAdapter _view2ModelAdpt) {
		this._view2ModelAdpt = _view2ModelAdpt;
		initGUI();
	}

	/**
	 * Starts up the GUI
	 */
	public void start() {
		/* Makes the GUI visible */
		setVisible(true);
	}

	/**
	 * Initializes the GUI
	 */
	private void initGUI() {
		/* Creates */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		/* Sets up the header panel, including tooltips for all items */
		hdrPanel.setBackground(new Color(153, 204, 255));
		contentPane.add(hdrPanel, BorderLayout.NORTH);
		txtBallClassToAdd
				.setToolTipText("Type the name of a ball (including package) to add to the display area below");
		txtBallClassToAdd.setColumns(10);
		hdrPanel.add(txtBallClassToAdd);
		btnAddBall
				.setToolTipText("Click to add a ball of the type specified by the text box to the left to the display are below");
		hdrPanel.add(btnAddBall);
		btnClearBalls
				.setToolTipText("Click to remove all balls from the display area below");
		hdrPanel.add(btnClearBalls);

		/*
		 * Sets up addBall button to call the corresponding function in the
		 * adapter
		 */
		btnAddBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdpt.addBall(txtBallClassToAdd.getText(), bodyPanel);
			}
		});

		/*
		 * Sets clearBalls button action the corresponding function in the
		 * adapter
		 */
		btnClearBalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdpt.clearBalls();
			}
		});

		contentPane.add(bodyPanel, BorderLayout.CENTER);
	}

	/**
	 * Repaints the view.
	 */
	public void update() {
		bodyPanel.repaint();
	}
}