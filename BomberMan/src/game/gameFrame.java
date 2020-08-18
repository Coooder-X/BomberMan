package game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;

//import java.awt.Container;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;

import javax.swing.*;

public class gameFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int Frame_Width = 800+16+280;//260+6;
	public static int Frame_Height = 600+39;//260+29;
	gamePanel gp = new gamePanel(800, 600);
	showPanel sp = new showPanel(280, 600, gp.player, this);

	public void createFrame() throws IOException
	{
		Container c = getContentPane();
		setTitle("BomberMan");
		// 窗口大小及位置
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(Frame_Width, Frame_Height);
		setLocation(70, 50);

		c.add(gp,BorderLayout.CENTER);
		c.add(sp,BorderLayout.EAST);
		
		addKeyListener(gp.player.k);
		addKeyListener(gp.ks);//	加入游戏面板以及信息面板
		setVisible(true);
		System.out.println(c.getSize());//可视面积
		new Thread(gp).start();
//		if(gp.player.getLive() <= 2) {
//			finalFrame fF = new finalFrame(false);
//		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		beginFrame bf =new beginFrame();
	}
	
}
