package game;
/**
 * 本类为游戏的音效
 * 外部只需要知道直接使用:
 * 		new Sound().playBackMusic() 静态函数来播放背景音乐
 * 		new Sound().playExplosion() 静态函数来播放爆炸音效
 */

import java.applet.Applet;
import java.net.URL;




public class Sound {
	//建立攻击的声音音效
	 static java.applet.AudioClip backMusic; 	
	 //建立爆炸的声音音效
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
	* 这里导入 背景音乐 和 爆炸 的音频文件
	*/
		//导入路径
		String fileName ="sound/bgm.wav";
		URL url=null;		

		//导入路径
		fileName ="sound/75mm_type3.wav";
		try 
		{
			//根据路径读取内容
			url =new URL("file:"+fileName);
			explosion = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			//错误处理
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/导弹.wav";
		try 
		{
			url =new URL("file:"+fileName);
			shootMissile = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/开场.wav";
		try 
		{
			url =new URL("file:"+fileName);
			open = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/奸笑.wav";
		try 
		{
			url =new URL("file:"+fileName);
			laugh = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/胜利.wav";
		try 
		{
			url =new URL("file:"+fileName);
			win = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/失败.wav";
		try 
		{
			url =new URL("file:"+fileName);
			lost = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/吃道具.wav";
		try 
		{
			url =new URL("file:"+fileName);
			eatItem = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		fileName ="sound/惨叫.wav";
		try 
		{
			url =new URL("file:"+fileName);
			die = Applet.newAudioClip(url);
		}
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

	
	/**
	 * 	调用此函数播放一次 爆炸音效
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