package game;
/**
 * ����ֻ��Ҫ֪������Ϸ�������ʱ��
 * �����乹�췽������ʾ�Ƿ�ʤ��
 * ���췽����finalFrame XXX(boolean isVictory);
 *     ���� isVictoryΪ�Ƿ�ʤ����trueΪʤ��
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
	
	//�˶������ã������
	private static final long serialVersionUID = 1L;
	//���ó��ȺͿ��
	public static int Frame_Width = 1080+16;
	public static int Frame_Height = 600+39;
	//�����ͼƬ
	public Image victory;
	public Image defeat;	
	//�Ƿ�ʤ��
	boolean isVictory;
	
    //���캯��
	public finalFrame(boolean isVictory) throws IOException {
		//������
		this.isVictory= isVictory;
	
		//����ͼƬ�ĵ���
		File fb0 = new File("images/win.jpg");	
		victory  = ImageIO.read(fb0); 
		File fb1 = new File("images/gameover.jpg");	
		defeat   = ImageIO.read(fb1); 

		
		//����Ļ������
		setTitle("final");
		setVisible(true);
		setSize(Frame_Width, Frame_Height);
		setLocation(70, 50);
		
		//������Ŀ�����һ�����
		Container c= getContentPane();
		JPanel finalPanel = new JPanel();	
		finalPanel.setSize(1050, 600);
		finalPanel.setVisible(true);
		c.add(finalPanel);	
		
		//�������Ӽ�����
		finalPanel.addMouseListener(new begin_Panl_Listener(this));
	}
	
	
	public void paint(Graphics g) {				
		//����ͼƬ,���ʤ����ʾʤ����ͼƬ
		if(isVictory)
			g.drawImage(victory, 0, 0, 1086,639,this);
		else 
			g.drawImage(defeat, 0, 0, 1086,639,this);
	}
	
	
	class begin_Panl_Listener  implements MouseListener
	{
	//���������߶�������
		//���뱾�����Թر�
		private JFrame finalframe;
		public begin_Panl_Listener(JFrame finalframe) 
		{
			this.finalframe=finalframe;
		}

		//�����崥�������¼�
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			//�˳�����
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
