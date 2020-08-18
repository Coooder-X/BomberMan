package game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.omg.CORBA.PRIVATE_MEMBER;


public class showPanel extends  JPanel {
	private static final long serialVersionUID = 1L;
	//本面板的宽度和高度
	private int panel_width;
	private int panel_height;
	//导入gameFrame
	private JFrame gameFrame;
	//导入血量的图片
	private Image panel_blood;
	//导入道具加速的图片
	private Image panel_missile;
	//导入护盾的图片
	private Image panel_buff_shield;	
	//游戏时间
	private Image panel_null;
	//导入导弹的图片
	private Image panel_range;
	private Image panel_bomb;
	private int time;
	boolean deadFlag = false;
	//导入Player类中的player对面，使得能够获取他的内部参数，来加以在此显示
	private Player player;

	
	public  showPanel(int panel_width,int panel_height,Player player, JFrame gf) {
		
		//设置面板的长度和宽度
		this.panel_width=panel_width;
		this.panel_height=panel_height;
		this.setPreferredSize(new Dimension(panel_width, panel_height));	 
		this.player = player;
		this.gameFrame = gf;
		
		//导入需要的图片
		loadImage();
		
		//游戏时间的设定
		time = 360;
		
		//更新内容，对面板进行每秒一次更新，详情在函数内
		updateTime();
	}
	
	
	public  void updateTime() 
	{	
		//	内含有循环，开辟线程
		new Thread(new Runnable() {
		
			@Override
			public void run() {		
					//当游戏进行的时候倒记时，直到时间为0，
					while(time>=0) {
						try {
							//线程停滞1秒钟
							Thread.sleep(990);
							//停滞完一秒后 秒数减1
							time--;
							//每秒一帧，每一秒更新一次图像内容
							repaint();
							if(time <= 0) {
								if(deadFlag == false) {
									deadFlag = true;
									try {
										finalFrame fF = new finalFrame ( false);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
//					游戏结束的代码
					try {
						finalFrame fF = new finalFrame(false);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}).start();
	}
	
	
	
//	此类需要的图片的导入
	public void loadImage() {
		try{
			//将图片文件一个个导入
			File fb0 = new File("images/导弹道具.png");
			File fb1 = new File("images/距离增加.png");
			File fb2 = new File("images/炸弹道具.png");
			File fb3 = new File("images/血量.png");
			
			panel_missile   = ImageIO.read(fb0);
			panel_range  = ImageIO.read(fb1);
			panel_bomb        = ImageIO.read(fb2);
			panel_blood      = ImageIO.read(fb3);
                        
        }catch(Exception e)
		{
			e.printStackTrace();
        }
		
	}
	
	
//此方法用来根据数据显示数据	
	public void paint(Graphics g) {
			//背景调色
			g.setColor(new Color(173, 173, 173));
			//创建背景
			g.fill3DRect(0, 0, 502, 800, true);			
			//字体调色
			g.setColor(new Color(100, 3, 12));
			//设置字体
			g.setFont(new java.awt.Font("黑体", 1, 25));
			
			//输出时间
			g.drawString("时间：", 30, 46);
			Integer p=new Integer(time);
			g.drawString(p.toString() + "s", 75, 100);			
//			g.drawString("s", 110, 100);
			
			//输出道具
			g.drawString("道具:" , 30, 150);
			if(player.skills.size() > 0) {
				int i = 0;
				for(Items it : player.skills) {
					if(it instanceof MissileItem) {
						g.drawImage(panel_missile, 30 + i * 70, 170, 60 , 60, this);
						i++;
					}
				}
			}
			
			g.drawString("加持:" , 30, 270);
			if(player.buff.size() > 0) {
				int i = 0;
				for(Items it : player.buff) {
					if(it instanceof bombPlus) {
						g.drawImage(panel_bomb, 30 + i * 70, 290, 60 , 60, this);
						i++;
					}
					else if(it instanceof rangePlus) {
						g.drawImage(panel_range, 30 + i * 70, 290, 60 , 60, this);
						i++;
					}
				}
			}
			
			//输出血量
			g.drawString("血量:" , 30, 390);										
			switch (player.getLive()) {		
			//如果血量有3，输出3格血量
			case 3:
				g.drawImage(panel_blood, 50, 400, 40, 40, this);
				g.drawImage(panel_blood, 100, 400, 40, 40, this);
				g.drawImage(panel_blood, 150, 400, 40, 40, this);
				break;
			//如果血量有2，输出2格血量
			case 2:
				g.drawImage(panel_blood, 50, 400, 40, 40, this);
				g.drawImage(panel_blood, 100, 400, 40, 40, this);
				break;
			//如果血量有1，输出1格血量
			case 1:
				g.drawImage(panel_blood, 50, 400, 40, 40, this);
				break;
			default:				
				
			}
				
		}
	

}
	
