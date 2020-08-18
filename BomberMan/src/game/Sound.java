package game;
/**
 * ����Ϊ��Ϸ����Ч
 * �ⲿֻ��Ҫ֪��ֱ��ʹ��:
 * 		new Sound().playBackMusic() ��̬���������ű�������
 * 		new Sound().playExplosion() ��̬���������ű�ը��Ч
 */

import java.applet.Applet;
import java.net.URL;




public class Sound {
	//����������������Ч
	 static java.applet.AudioClip backMusic; 	
	 //������ը��������Ч
	 static java.applet.AudioClip explosion;
	 static java.applet.AudioClip eatItem;
	 static java.applet.AudioClip shootMissile;
	 static java.applet.AudioClip laugh;
	 static java.applet.AudioClip open;
	 static java.applet.AudioClip win;
	 static java.applet.AudioClip lost;
	 static java.applet.AudioClip die;
	 	
	public Sound () {						
	/**
	* ���ﵼ�� �������� �� ��ը ����Ƶ�ļ�
	*/
		//����·��
		String fileName ="sound/bgm.wav";
		URL url=null;		

		//����·��
		fileName ="sound/75mm_type3.wav";
		try 
		{
			//����·����ȡ����
			url =new URL("file:"+fileName);
			explosion = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			//������
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/����.wav";
		try 
		{
			url =new URL("file:"+fileName);
			shootMissile = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/����.wav";
		try 
		{
			url =new URL("file:"+fileName);
			open = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/��Ц.wav";
		try 
		{
			url =new URL("file:"+fileName);
			laugh = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/ʤ��.wav";
		try 
		{
			url =new URL("file:"+fileName);
			win = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/ʧ��.wav";
		try 
		{
			url =new URL("file:"+fileName);
			lost = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/�Ե���.wav";
		try 
		{
			url =new URL("file:"+fileName);
			eatItem = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		fileName ="sound/�ҽ�.wav";
		try 
		{
			url =new URL("file:"+fileName);
			die = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
	}

	
	/**
	 * 	���ô˺�������һ�� ��ը��Ч
	 */
	public static void playExplosion() 
	{										
//		explosion.play();	
		new Thread(new Runnable() {
			
			@Override
			public void run() {								
				explosion.play();				
			}
		}).start();
	}
	
	public static void playOpen() 
	{										
		open.play();	

	}
	public static void playShoot() 
	{										
//		shootMissile.play();
		new Thread(new Runnable() {
			
			@Override
			public void run() {								
				shootMissile.play();				
			}
		}).start();
	}
	public static void playEat() 
	{										
//		eatItem.play();	
		new Thread(new Runnable() {
			
			@Override
			public void run() {								
				eatItem.play();				
			}
		}).start();
	}
	public static void playLaugh() 
	{										
		laugh.play();	
	}
	public static void playWin() 
	{										
		win.play();									
	}
	public static void playLost() 
	{										
		lost.play();									
	}
	public static void playDie() 
	{										
//		die.play();	
		new Thread(new Runnable() {
			
			@Override
			public void run() {								
				die.play();		
			}
		}).start();
	}
}