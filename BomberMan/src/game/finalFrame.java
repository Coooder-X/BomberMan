package game;
/**
 * 本类只需要知道在游戏里结束的时候
 * 调用其构造方法，显示是否胜利
 * 构造方法：finalFrame XXX(boolean isVictory);
 *     其中 isVictory为是否胜利，true为胜利
 *
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io .File;
import java.io 

.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import game.beginFrame.begin_Panl_Listener;


public class finalFrame extends JFrame {
	
	//此东西无用，请忽略
	private static final long serialVersionUID = 1L;
	//设置长度和宽度
	public static int Frame_Width = 1080+16;
	public static int Frame_Height = 600+39;
	//封面的图片
	public Image victory;
	public Image defeat;	
	//是否胜利
	boolean isVictory;
	
    //构造函数
	public finalFrame(boolean isVictory) throws IOException {
		//导入标记
		this.isVictory= isVictory;
	
		//封面图片的导入
		File fb0 = new File("images/win.jpg");	
		victory  = ImageIO.read(fb0); 
		File fb1 = new File("images/gameover.jpg");	
		defeat   = ImageIO.read(fb1); 

		
		//封面的基本设计
		setTitle("final");
		setVisible(true);
		setSize(Frame_Width, Frame_Height);
		setLocation(70, 50);
		
		//给封面的框架添加一个面板
		Container c= getContentPane();
		JPanel finalPanel = new JPanel();	
		finalPanel.setSize(1050, 600);
		finalPanel.setVisible(true);
		c.add(finalPanel);	
		
		//给面板添加监听者
		finalPanel.addMouseListener(new begin_Panl_Listener(this));
	}
	
	
	public void paint(Graphics g) {				
		//插入图片,如果胜利显示胜利的图片
		if(isVictory)
			g.drawImage(victory, 0, 0, 1086,639,this);
		else 
			g.drawImage(defeat, 0, 0, 1086,639,this);
	}
	
	
	class begin_Panl_Listener  implements MouseListener
	{
	//建立监听者对面板监听
		//导入本面板加以关闭
		private JFrame finalframe;
		public begin_Panl_Listener(JFrame finalframe) 
		{
			this.finalframe=finalframe;
		}

		//点击面板触发下面事件
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			//退出窗体
			System.exit(0);
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
