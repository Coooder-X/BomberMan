package game;
/**
 * ����Ϊ��Ϸ��ʼ���
 * 	��Ҫ��ʵ����Ϸ��ʼ���棬ʵ�ֵ�����������Ϸ
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
	//�˶������ã������
	private static final long serialVersionUID = 1L;
	
	//���ó��ȺͿ��
	public static int Frame_Width = 1050+16;
	public static int Frame_Height = 600+39;
	//�����ͼƬ
	private Image beginFrame;
	
	
	//���캯��
	public  beginFrame() throws IOException {
		
		//����ͼƬ�ĵ���
		File fb0 = new File("images/����5.jpg");		
		beginFrame   = ImageIO.read(fb0); 
		
		//����Ļ������
		this.setTitle("Play");
		this.setVisible(true);
		this.setSize(Frame_Width, Frame_Height);
		this.setLocation(70, 50);
		Container c= getContentPane();
		
		//������Ŀ�����һ�����
		JPanel beginPanel = new JPanel();	
		beginPanel.setSize(1050, 600);
		c.add(beginPanel);
		
		//�������Ӽ�����
		beginPanel.addMouseListener(new begin_Panl_Listener(this));	
		new Sound();
		Sound.playOpen();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	public void paint(Graphics g) {
		//����ͼƬ
		g.drawImage(beginFrame, 0, 0, this);
	}
	
	
	class begin_Panl_Listener  implements MouseListener
	{
	//���������߶�������
		//������ܵ��룬�Ա�Ա���ܽ��д���
		private JFrame beginframe;
		public begin_Panl_Listener(JFrame beginframe) 
		{
			this.beginframe=beginframe;
		}

		//�����崥�������¼�
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			//ʹ�������ʧ
			beginframe.setVisible(false); 
			//������Ϸ��ܣ���ʽ������Ϸ
			gameFrame gf = new gameFrame();
			//������Ϸ�еĳ�ʼ������
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

