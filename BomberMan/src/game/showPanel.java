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
	//�����Ŀ�Ⱥ͸߶�
	private int panel_width;
	private int panel_height;
	//����gameFrame
	private JFrame gameFrame;
	//����Ѫ����ͼƬ
	private Image panel_blood;
	//������߼��ٵ�ͼƬ
	private Image panel_missile;
	//���뻤�ܵ�ͼƬ
	private Image panel_buff_shield;	
	//��Ϸʱ��
	private Image panel_null;
	//���뵼����ͼƬ
	private Image panel_range;
	private Image panel_bomb;
	private int time;
	boolean deadFlag = false;
	//����Player���е�player���棬ʹ���ܹ���ȡ�����ڲ��������������ڴ���ʾ
	private Player player;

	
	public  showPanel(int panel_width,int panel_height,Player player, JFrame gf) {
		
		//�������ĳ��ȺͿ��
		this.panel_width=panel_width;
		this.panel_height=panel_height;
		this.setPreferredSize(new Dimension(panel_width, panel_height));	 
		this.player = player;
		this.gameFrame = gf;
		
		//������Ҫ��ͼƬ
		loadImage();
		
		//��Ϸʱ����趨
		time = 360;
		
		//�������ݣ���������ÿ��һ�θ��£������ں�����
		updateTime();
	}
	
	
	public  void updateTime() 
	{	
		//	�ں���ѭ���������߳�
		new Thread(new Runnable() {
		
			@Override
			public void run() {		
					//����Ϸ���е�ʱ�򵹼�ʱ��ֱ��ʱ��Ϊ0��
					while(time>=0) {
						try {
							//�߳�ͣ��1����
							Thread.sleep(990);
							//ͣ����һ��� ������1
							time--;
							//ÿ��һ֡��ÿһ�����һ��ͼ������
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
//					��Ϸ�����Ĵ���
					try {
						finalFrame fF = new finalFrame(false);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}).start();
	}
	
	
	
//	������Ҫ��ͼƬ�ĵ���
	public void loadImage() {
		try{
			//��ͼƬ�ļ�һ��������
			File fb0 = new File("images/��������.png");
			File fb1 = new File("images/��������.png");
			File fb2 = new File("images/ը������.png");
			File fb3 = new File("images/Ѫ��.png");
			
			panel_missile   = ImageIO.read(fb0);
			panel_range  = ImageIO.read(fb1);
			panel_bomb        = ImageIO.read(fb2);
			panel_blood      = ImageIO.read(fb3);
                        
        }catch(Exception e)
		{
			e.printStackTrace();
        }
		
	}
	
	
//�˷�����������������ʾ����	
	public void paint(Graphics g) {
			//������ɫ
			g.setColor(new Color(173, 173, 173));
			//��������
			g.fill3DRect(0, 0, 502, 800, true);			
			//�����ɫ
			g.setColor(new Color(100, 3, 12));
			//��������
			g.setFont(new java.awt.Font("����", 1, 25));
			
			//���ʱ��
			g.drawString("ʱ�䣺", 30, 46);
			Integer p=new Integer(time);
			g.drawString(p.toString() + "s", 75, 100);			
//			g.drawString("s", 110, 100);
			
			//�������
			g.drawString("����:" , 30, 150);
			if(player.skills.size() > 0) {
				int i = 0;
				for(Items it : player.skills) {
					if(it instanceof MissileItem) {
						g.drawImage(panel_missile, 30 + i * 70, 170, 60 , 60, this);
						i++;
					}
				}
			}
			
			g.drawString("�ӳ�:" , 30, 270);
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
			
			//���Ѫ��
			g.drawString("Ѫ��:" , 30, 390);										
			switch (player.getLive()) {		
			//���Ѫ����3�����3��Ѫ��
			case 3:
				g.drawImage(panel_blood, 50, 400, 40, 40, this);
				g.drawImage(panel_blood, 100, 400, 40, 40, this);
				g.drawImage(panel_blood, 150, 400, 40, 40, this);
				break;
			//���Ѫ����2�����2��Ѫ��
			case 2:
				g.drawImage(panel_blood, 50, 400, 40, 40, this);
				g.drawImage(panel_blood, 100, 400, 40, 40, this);
				break;
			//���Ѫ����1�����1��Ѫ��
			case 1:
				g.drawImage(panel_blood, 50, 400, 40, 40, this);
				break;
			default:				
				
			}
				
		}
	

}
	
