package gui;

import game.Piece;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Tile extends JButton{

	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private boolean clicked = false;
	
	private BufferedImage bi;
//    float[] scales = { 1f, 1f, 1f, 0.5f };
//    float[] offsets = new float[4];
    RescaleOp rop;
	String imageSrc;
     
    public Tile(final FullScreen screen, Piece piece, int x, int y) {
    	    	
    	this.x = x;
    	this.y = y;
    	
    	if(piece == null)
    	{
    		 drawTile("/resources/images/blank.png");
    	}
    	else
    	{
    		switch(piece)
	    	{
	    		case En_Passant_B:
	    			drawTile("/resources/images/pawn_B.png");
	    			break;
	    			
	    		case En_Passant_W:
	    			drawTile("/resources/images/pawn_W.png");
	    			break;
	    			
	    		default:
	    			drawTile("/resources/images/" + piece.toString() + ".png");
	    			break;
	    	}
    	}
    	    
        addMouseListener(new MouseAdapter() {
			@Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                clicked = !clicked; 
//                System.out.println("clicky: comp x:"+e.getComponent().getX()+" y:"+e.getComponent().getY()+"\t e.source.hashcode: "+e.getSource().hashCode());
                
                repaint();
                Tile src = (Tile)e.getSource();
                if(screen.clickTile(src.get_X(), src.get_Y()))
                {
                	screen.repainter();
                	clicked = false;
                	repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }
		});
    }
 
    public void drawTile(String imagePath)
    {
    	try
    	{
	    	URL url = getClass().getResource(imagePath);
	    	BufferedImage img;
	    	if(url == null)
	    	{
	    		System.out.println("null url error");
	    		img = ImageIO.read(getClass().getResource("/resources/images/error.png"));
	    	}
	    	else
	    	{
	            img = ImageIO.read(url);
	    	} 
	    	
	    	int w = img.getWidth();
	        int h = img.getHeight();
	        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	        Graphics g = bi.getGraphics();
	        
	        g.drawImage(img, 0, 0, null);
    	}
    	catch(IOException e)
    	{
            System.out.println(imagePath + " Image could not be read. e:" + e.toString());
    	}
    }
    
    public Dimension getPreferredSize() {
    	if(bi == null)
    	{
    		System.out.println("bi is null");
    		return new Dimension(100, 100);
    	}
    	else
    	{
    		return new Dimension(bi.getWidth()+10, bi.getHeight()+10);
    	}
    }
 
    public void paint(Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D)g;
    	Color color;
    	if((x+y)%2 == 0)
    	{
    		color = Color.white;
    	}
    	else
    	{
    		color = new Color(100, 60, 40);
    	}

    	if(clicked)
    	{
    		g2d.setColor(new Color(140, 100, 100));
            g2d.fillRect(0,0, getWidth(), getHeight());
            g2d.setColor(color);
    		g2d.fillRect(10, 10, getWidth()-20, getHeight()-20);
    	}
    	else
    	{
    		g2d.setColor(color);
    		g2d.fillRect(0,0, getWidth(), getHeight());
    	}
    	
        g2d.drawImage(bi, rop, (getWidth() - bi.getWidth())/2, (getHeight() - bi.getHeight())/2);
        
    }
    
	public int get_X() {
		return x;
	}

	public void set_X(int x) {
		this.x = x;
	}

	public int get_Y() {
		return y;
	}

	public void set_Y(int y) {
		this.y = y;
	}
}
