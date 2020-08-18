package game;
/**
 * 本类为游戏开始框架
 * 	主要是实现游戏开始界面，实现点击界面进入游戏
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;



public class beginFrame extends JFrame {
	//此东西无用，请忽略
	private static final long serialVersionUID = 1L;
	
	//设置长度和宽度
	public static int Frame_Width = 1050+16;
	public static int Frame_Height = 600+39;
	//封面的图片
	private Image beginFrame;
	
	
	//构造函数
	public  beginFrame() throws IOException {
		
		//封面图片的导入
		File fb0 = new File("images/界面5.jpg");		
		beginFrame   = ImageIO.read(fb0); 
		
		//封面的基本设计
		this.setTitle("Play");
		this.setVisible(true);
		this.setSize(Frame_Width, Frame_Height);
		this.setLocation(70, 50);
		Container c= getContentPane();
		
		//给封面的框架添加一个面板
		JPanel beginPanel = new JPanel();	
		beginPanel.setSize(1050, 600);
		c.add(beginPanel);
		
		//给面板添加监听者
		beginPanel.addMouseListener(new begin_Panl_Listener(this));	
		new Sound();
		Sound.playOpen();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	public void paint(Graphics g) {
		//插入图片
		g.drawImage(beginFrame, 0, 0, this);
	}
	
	
	class begin_Panl_Listener  implements MouseListener
	{
	//建立监听者对面板监听
		//将本框架导入，以便对本框架进行处理
		private JFrame beginframe;
		public begin_Panl_Listener(JFrame beginframe) 
		{
			this.beginframe=beginframe;
		}

		//点击面板触发下面事件
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			//使本框架消失
			beginframe.setVisible(false); 
			//创建游戏框架，正式启动游戏
			gameFrame gf = new gameFrame();
			//调用游戏中的初始化函数
			try {
				gf.createFrame();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

		
	}
	
	
	
}

