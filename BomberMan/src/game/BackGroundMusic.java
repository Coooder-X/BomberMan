package game;

import java.applet.Applet;
import java.net.URL;

public class BackGroundMusic {//	�������ֶ�����Ϊһ���ࣨbgm��Ҫȫ��ѭ�����ţ�����������Ч����һ���࣬������̳߳�ͻ������������ʧ��
	 static java.applet.AudioClip backMusic;
	 public BackGroundMusic () {						
			/**
			* ���ﵼ�� �������� �� ��ը ����Ƶ�ļ�
			*/
				//����·��
				String fileName ="sound/bgm.wav";
				URL url=null;		
				try 
				{
					//����·����ȡ����
					url =new URL("file:"+fileName);
					backMusic = Applet.newAudioClip(url);			
				}
				catch (Exception e) 
				{
					//������
					e.printStackTrace();
					System.out.println("��ȡ�ļ����ݳ���");
				}	
			}
	 /**
		 * ���ô˺���ʵ�ֳ������� ��������
		 *
		 */

		static public void playBackMusic() //	����bgm
		{
			new Thread(new Runnable() {
			
				@Override
				public void run() {								
					backMusic.loop();	
				}
			}).start();
		}
}
