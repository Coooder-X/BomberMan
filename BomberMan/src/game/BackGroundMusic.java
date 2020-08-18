package game;

import java.applet.Applet;
import java.net.URL;

public class BackGroundMusic {//	背景音乐独自作为一个类（bgm需要全程循环播放，若与其他音效放在一个类，则可能线程冲突，导致音乐消失）
	 static java.applet.AudioClip backMusic;
	 public BackGroundMusic () {						
			/**
			* 这里导入 背景音乐 和 爆炸 的音频文件
			*/
				//导入路径
				String fileName ="sound/bgm.wav";
				URL url=null;		
				try 
				{
					//根据路径读取内容
					url =new URL("file:"+fileName);
					backMusic = Applet.newAudioClip(url);			
				}
				catch (Exception e) 
				{
					//错误处理
					e.printStackTrace();
					System.out.println("读取文件内容出错");
				}	
			}
	 /**
		 * 调用此函数实现持续播放 背景音乐
		 *
		 */

		static public void playBackMusic() //	播放bgm
		{
			new Thread(new Runnable() {
			
				@Override
				public void run() {								
					backMusic.loop();	
				}
			}).start();
		}
}
