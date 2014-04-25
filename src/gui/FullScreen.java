package gui;

import game.Board;
import game.Constants;
import game.Piece;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class FullScreen extends JFrame implements Runnable, ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	JFrame mainFrame;
	private GraphicsDevice device;
	int screenWidth;
	int screenHeight;
	private JButton newGameBtn = new JButton("New Game");
	private JButton exitBtn = new JButton("Exit");
	private JButton backBtn = new JButton("Back");
	private DisplayMode originalDM;
	private boolean isFullScreen = false;
	
	private JPanel bgPanel;
	private JPanel gamePanel;
//	private JPanel[][] tilesPanels = new JPanel[8][8];
	private Tile[][] boardTiles = new Tile[8][8];
	JLabel msg;
	JLabel turnMsg;
	
	private Board board = new Board(Constants.INIT_SET);
	private Board boardPastCopy = new Board(Constants.INIT_SET);
	
	@Override
	public void run() 
	{
		Container c = getContentPane();
		initComponents(c);
        begin();
        repainter();	//repaints the gui for resizing accordingly
        c.add(bgPanel);
        validate();
	}
	
	public FullScreen(GraphicsDevice device)
	{
		super(device.getDefaultConfiguration());
		
		this.device = device;

        screenWidth = device.getDisplayMode().getWidth();
        screenHeight = device.getDisplayMode().getHeight();
        
		setTitle("FullScreen");
		originalDM = device.getDisplayMode();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(originalDM.getWidth()/2, originalDM.getHeight()/2));
		
//		newGameBtn.setVerticalTextPosition(AbstractButton.CENTER);
//	    newGameBtn.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//	    newGameBtn.setSize(200, 200);
	    newGameBtn.setMnemonic(KeyEvent.VK_N);
	    newGameBtn.addActionListener(this);
	    
	    exitBtn.setMnemonic(KeyEvent.VK_E);
	    exitBtn.addActionListener(this);
	    
	    backBtn.setMnemonic(KeyEvent.VK_B);
	    backBtn.addActionListener(this);
	    
		mainFrame = new JFrame();
 		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 		mainFrame.setUndecorated(true);
 		mainFrame.setResizable(false);
//        mainFrame.setIgnoreRepaint(true);
 		
 		addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                System.out.println("clicky: fs comp x:"+e.getComponent());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
		});
	}
	
    public void initComponents(Container c) {
        setContentPane(c);
        
//        c.setLayout(new GridLayout(1,2));
        
        // Background Panel
        bgPanel = new JPanel();
//        bgPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        // Game Panel
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(8,8));
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.black, 5, true));
//        gamePanel.setBounds(0, 0, screenWidth, screenHeight);
        
        // Tile Panel(s)
        for(int i=0; i<8; i++)
        {
        	for(int j=0; j<8; j++)
        	{
//        		tilesPanels[i][j] = new JPanel();
//        		tilesPanels[i][j].setLayout(new BoxLayout(tilesPanels[i][j], BoxLayout.PAGE_AXIS));
        		
        		boardTiles[i][j] = new Tile(this, board.getChecker()[i][j], j, i);
        		boardTiles[i][j].setLayout(new BoxLayout(boardTiles[i][j], BoxLayout.PAGE_AXIS));
//        		boardTiles[i][j].setPreferredSize(new Dimension(90,90));
//        		boardTiles[i][j].setAlignmentX(Component.CENTER_ALIGNMENT);
        		boardTiles[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        		
//        		tilesPanels[i][j].add(Box.createVerticalGlue());
//        		tilesPanels[i][j].add(boardTiles[i][j], BorderLayout.CENTER);
//        		tilesPanels[i][j].add(Box.createVerticalGlue());
//        		tilesPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        		
        		gamePanel.add(boardTiles[i][j]);
        	}
        }
        
//        bgPanel.setPreferredSize(new Dimension(tilesPanels[0][0].getWidth()*8, tilesPanels[0][0].getHeight()*8));
//        gamePanel.setPreferredSize(new Dimension(tilesPanels[0][0].getWidth()*8, tilesPanels[0][0].getHeight()*8));
        
        // Controls panel
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        // Setting controls size
        JButton b = new JButton("null");
        Dimension buttonSize = b.getPreferredSize();
        int controlsWidth = (int)(buttonSize.getWidth() * 2.5);
        int controlsHeight = (int)(buttonSize.getHeight() * 3.5);
        controls.setPreferredSize(new Dimension(controlsWidth, controlsHeight));
        //Adding buttons
        newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        controls.add(newGameBtn, controls);
        controls.add(exitBtn, controls);
//        controls.add(backBtn, controls);
        
        //Messages panel
        JPanel messages = new JPanel();
        messages.setLayout(new BoxLayout(messages, BoxLayout.Y_AXIS));
        msg = new JLabel();
        msg.setText("messages");
        messages.setPreferredSize(new Dimension(100,200));
        turnMsg = new JLabel();
        turnMsg.setText("White turn");
        messages.add(msg);
        messages.add(turnMsg);
        
        bgPanel.add(controls, BorderLayout.WEST);
        bgPanel.add(gamePanel, BorderLayout.CENTER);
        bgPanel.add(messages, BorderLayout.EAST);
    }
	
    public void begin() {
        isFullScreen = device.isFullScreenSupported();
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        if (isFullScreen) {
            // Full-screen mode
            device.setFullScreenWindow(this);
            validate();
        } else {
            // Windowed mode
            pack();
            setVisible(true);
        }
    }

	public void actionPerformed(ActionEvent e) {
			
		Object source = e.getSource();
		if(source == newGameBtn)
		{
			System.out.println("new game");
			Piece[][] newBoard = Constants.INIT_SET;
			board = new Board(newBoard);
//			tilesPanels = new JPanel[8][8];
			boardTiles = new Tile[8][8];
			
			board.printBoard();
			repainter();
		}
		else if(source == exitBtn)
		{
			device.setDisplayMode(originalDM);
			System.exit(0);
		}
		else if(source ==  backBtn)	//unused, NEED TO ADD
		{
			System.out.println("back");
			board = boardPastCopy;
			board.printBoard();
			repainter();
		}
	}
	
	public void repainter()
	{
		gamePanel.removeAll();
        System.out.println(gamePanel.getLayout());
        for(int i=0; i<8; i++)
        {
        	for(int j=0; j<8; j++)
        	{
//        		tilesPanels[i][j] = new JPanel();
//        		tilesPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
//        		tilesPanels[i][j].setBackground(new Color(255,255,255));
        		
        		boardTiles[i][j] = new Tile(this, board.getChecker()[i][j], j, i);
        		boardTiles[i][j].setLayout(new BoxLayout(boardTiles[i][j], BoxLayout.PAGE_AXIS));
//        		tilesPanels[i][j].add(boardTiles[i][j]);
        		boardTiles[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 5));
        		gamePanel.add(boardTiles[i][j]);
        	}
        }
        System.out.println("painted");
        
        repaint();
        validate();
	}
	
	private static int startRow = -1;
	private static int startCol = -1;
	private static int endRow = -1;
	private static int endCol = -1;
	
    public boolean clickTile(int x, int y)
    {
//    	System.out.println("Board copy");
//    	boardPastCopy.printBoard();
    	
    	System.out.println("before if- start: " + startRow + "," + startCol + "\tend: " + endRow + "," + endCol);
    	if(startRow == -1 || startCol == -1)
    	{
    		startCol = x;
    		startRow = y;
    		System.out.println("before else- start: " + startRow + "," + startCol + "\tend: " + endRow + "," + endCol);
    		return false;
    	}
    	else
    	{
    		endCol = x;
    		endRow = y;
    		if(board.isMoveLegal(startRow, startCol, endRow, endCol, false))
    		{
//    			boardPastCopy = board.clone();
    			board.movePiece(startRow, startCol, endRow, endCol);
    			setMessages("", board.whosTurn()+" turn");
    		}
    		else
    		{
    			setMessages("Illegal move", board.whosTurn()+" turn");
    		}
    		System.out.println("after else- start: " + startRow + "," + startCol + "\tend: " + endRow + "," + endCol);
    		startRow = -1;
    		endCol = -1;
    		return true;
    	}
    }
    
    public void setMessages(String msg, String turnMsg)
    {
    	this.msg.setText(msg);
    	
    	this.turnMsg.setText(turnMsg);
    }
}
