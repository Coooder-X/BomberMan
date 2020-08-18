package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
//import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class gamePanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean Doorflag = false;//	判断出口的门是否打开
	static int ratio = 1;	//	怪物移动的画面刷新率
	static int sizeW = 34;
	static int sizeH = 34;
	private int Panel_width;
	private int Panel_height;
	boolean winFlag = false;	//	判断游戏是否结束
	
	private Image backScene0;
    private Image backScene1;
    private Image backScene2;
    private Image fire4;
    private Image fire5;
    private Image fire6;
    private Image fire7;
    private Image fire8;
    private Image fire9;
    private Image fire10;
    finalFrame fF = null;
    private String[] road ={"草地.jpg", "砖块.jpg", "红砖.jpg"};//	文件路径
    keySkill ks = new keySkill();
         
    private int[][] backsenceXY=
    {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
     {1, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1},
     {1, 0, 0, 0, 2, 0, 1, 2, 1, 2, 1, 0, 1, 2, 1, 2, 1, 1, 0, 1},
     {1, 1, 2, 1, 2, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1},
     {1, 0, 0, 2, 0, 0, 0, 0, 2, 1, 0, 0, 0, 1, 2, 2, 2, 1, 2, 1},
     {1, 1, 1, 0, 2, 2, 1, 0, 2, 2, 2, 1, 2, 1, 0, 0, 0, 0, 0, 1},
     {1, 0, 2, 0, 2, 0, 2, 2, 1, 0, 0, 0, 2, 0, 0, 1, 2, 1, 0, 1},
     {1, 0, 1, 0, 0, 0, 1, 0, 0, 2, 2, 0, 2, 0, 2, 0, 2, 0, 2, 1},
     {1, 0, 2, 0, 1, 2, 0, 0, 0, 0, 1, 1, 2, 2, 2, 0, 1, 0, 0, 1},
     {1, 2, 1, 2, 1, 0, 1, 0, 1, 0, 2, 0, 0, 0, 1, 1, 0, 2, 0, 1},
     {1, 0, 0, 0, 0, 2, 2, 0, 1, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 1},
     {1, 2, 1, 0, 0, 0, 1, 0, 2, 0, 2, 1, 2, 1, 2, 0, 0, 0, 1, 1},
     {1, 0, 2, 2, 0, 0, 0, 2, 2, 1, 0, 0, 2, 0, 0, 2, 1, 2, 0, 1},
     {1, 0, 0, 1, 0, 2, 2, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 1},
     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    Player player = new Player(43, 43, sizeW, sizeH, false, this.backsenceXY, this);

    @SuppressWarnings("serial")
	ArrayList<Monster> monsterArray = new ArrayList<Monster>() {{// 存放怪物，怪物死了就从中删除
    	add(new Monster( 483, 43, sizeW, sizeH, true, backsenceXY));
    	add(new Monster( 283, 43, sizeW, sizeH, true, backsenceXY));
    	add(new Monster( 123, 403, sizeW, sizeH, true, backsenceXY));
    	add(new Monster( 14*40+3, 5*40+3, sizeW, sizeH, true, backsenceXY));
    }};
    @SuppressWarnings("serial")
	ArrayList<Items> itemsArray = new ArrayList<Items>() {//存放地图中的道具（可更改程序，通过随机生成实现）
    	{
    		add(new MissileItem(202, 242));
    		add(new MissileItem(42, 162));
    		add(new bombPlus(322, 282));
    		add(new bombPlus(362, 11*40+2));
    		add(new rangePlus(11*40+2, 6*40+2));
    	}
    };
    ArrayList<Bomb> bombArray = new ArrayList<Bomb>();//	存放等待爆炸的炸弹
    Missile missile;	//	游戏中同一时间只能释放一枚导弹，因此只创建一个导弹对象，当玩家未发射导弹时，改对象未null
    Missile explodingMissile;	//   单独创建一个导弹对象，用于处理游戏中导弹的 爆炸效果
    ArrayList<Bomb> explodedArray = new ArrayList<Bomb>();// 正在爆炸的炸弹，用于处理爆炸期间的事件
    ArrayList<Role> deadRoleArray = new ArrayList<Role>();// 存放以处理正在死亡的角色对象
    
    public void createBackScreen(){
		try{
	        Sound.open.stop();//		beginFrame中的开场音乐结束
	        new BackGroundMusic();
			BackGroundMusic.playBackMusic();//	开启游戏中的背景音乐
			
			File fb0 = new File(road[0]);
			backScene0 = ImageIO.read(fb0);
			File fb1 = new File(road[1]);
			backScene1 = ImageIO.read(fb1);
            File fb2 = new File(road[2]);
			backScene2 = ImageIO.read(fb2);
			File fb4 = new File("爆炸中心.png");
			fire4 = ImageIO.read(fb4);
			File fb5 = new File("竖中间段.png");
			fire5 = ImageIO.read(fb5);
            File fb6 = new File("上端.png");
			fire6 = ImageIO.read(fb6);  
			File fb7 = new File("中间段、触墙段.png");
			fire7 = ImageIO.read(fb7);
			File fb8 = new File("右端.png");
			fire8 = ImageIO.read(fb8);
            File fb9 = new File("下端.png");
			fire9 = ImageIO.read(fb9);
			File fb10 = new File("左端.png");//		给所有图片指定文件的路径
			fire10 = ImageIO.read(fb10);
        }catch(Exception e)
		{
			e.printStackTrace();
        }
	}
    public void paint(Graphics g){//	Override的paint函数，实现每一帧画面刷新时画面的绘制
        
        for(int i=0 ; i < 20 ; i++)//	该双重循环用于绘制地图
        {  
            for( int j = 0 ; j < 15 ; j++)
            {
                if(backsenceXY[j][i]==0)
                {		
                	g.drawImage(backScene0, i*40, j*40, this);
                }
                if(backsenceXY[j][i]==1)
                {		
                    g.drawImage(backScene1, i*40, j*40, this);
                }
                if(backsenceXY[j][i]==2)
                {		
                    g.drawImage(backScene2, i*40, j*40, this);
                }
                if(backsenceXY[j][i]==3)
                {		
                    g.drawImage(backScene0, i*40, j*40, this);
                }
                if(backsenceXY[j][i] == 4)
                	g.drawImage(fire4, i*40, j*40, this);
                if(backsenceXY[j][i] == 5)
                    g.drawImage(fire5, i*40, j*40, this);
                if(backsenceXY[j][i] == 6)
                    g.drawImage(fire6, i*40, j*40, this);
                if(backsenceXY[j][i] == 7)
                    g.drawImage(fire7, i*40, j*40, this);
                if(backsenceXY[j][i] == 8)
                	g.drawImage(fire8, i*40, j*40, this);
                if(backsenceXY[j][i] == 9)
                    g.drawImage(fire9, i*40, j*40, this);
                if(backsenceXY[j][i] == 10)
                    g.drawImage(fire10, i*40, j*40, this);
                if(backsenceXY[10][13] == 0 && !Doorflag) {
                	itemsArray.add(new Door(13*40+2, 402));
                	System.out.println("PUT A DOOR");
                	Doorflag = true;
                }
            }
         }
        if(winFlag) {//	如果游戏结束，则停止播放背景音乐
        	BackGroundMusic.backMusic.stop();
        }
        if(!itemsArray.isEmpty()) {//	绘制地图中的所有道具，实现玩家获得道具
        	for(int i = 0; i < itemsArray.size(); ++i) {//	循环中判定道具的种类，分别处理图片
        		Items t = itemsArray.get(i);
        		if(!t.check(player.getX(), player.getY())) {
        			t.draw();
        			g.drawImage(t.getImage(), t.getX(), t.getY(), t.getWidth(), t.getHeight(),
        					this);
        		}
        		else if(t instanceof Door && t.check(player.getX(), player.getY())
        				&& ((Door)t).open == false) {//若无此句，当门被发现时若还是关着，则玩家碰门门就消失
        			t.draw();
        			g.drawImage(t.getImage(), t.getX(), t.getY(), t.getWidth(), t.getHeight(),
        					this);
        		}
        		else {
        			if(t instanceof bombPlus) {
        				player.setNum(player.getNum()+1);
        				itemsArray.remove(t);
        				player.buff.add(t);
        			}
        			else if(t instanceof rangePlus) {
        				player.setRange(player.getRange()+1);
        				itemsArray.remove(t);
        				player.buff.add(t);
        			}
        			else if(t instanceof Door) {
        				if(((Door)t).open == true && winFlag == false) {//	若出口打开，玩家进入，则游戏胜利
        					winFlag = true;
        					try {
								 fF = new finalFrame (true);
								 new Sound();
								Sound.playWin();//	播放胜利音效 
								if(fF == null)
									System.exit(0);///////////////////////////////2019.10.12
//								fF.paint(g);
								System.out.println("newWIn");
							} catch (IOException e) {
								e.printStackTrace();
							}
        				}
        			}
        			else if(t instanceof MissileItem) {//	玩家获得导弹道具，将导弹道具加入玩家的技能队列中
	        			player.skills.add(t);
	        			itemsArray.remove(t);
        			}
        		}
        	}
        }
        
        if(missile != null) {
        	missile.figureDir();//	figureDir函数中调用广搜，实现导弹道自动寻路
        	missile.drawM();
        	g.drawImage(missile.getImage(), missile.getX(), missile.getY(), missile.getWidth(), missile.getHeight(),
					this);//	函数与绘制导弹图片
        	if(missile.getCanExplode() == true) {//		若导弹击中目标，则处理爆炸效果
        		explodingMissile = missile;
        		missile.explode();
        		Sound.playExplosion() ;
        		missile = null;
        	}
        }
        if(explodingMissile != null) {//	处理导弹爆炸效果
        	if( explodingMissile.getExistTime() >= 0)
        		explodingMissile.setExsistTime();	//	特效的持续时间
    		else {
    			explodingMissile.overExplode();
    			explodingMissile = null;
    		}
        }
        
        if( bombArray.size() > 0) {	//	处理被玩家放置的若干个炸弹，对每个炸弹进行paint和倒计时
        	for( Bomb b: bombArray) {
        		if( b.getTime() >= 0) {
        			{
        				b.draw();
	        			g.drawImage(b.getImage(), b.getX(), b.getY(), b.getWidth(), b.getHeight(),
	        					this);
	        			b.setTime();	//		爆炸剩余时间减少
        			}
        		}
        		else if( b.getTime() < 0) {
        			player.setNum(player.getNum()+1);// 若爆炸一个，则玩家 可放炸弹数+1
        			player.map[(b.getY()+17)/40][(b.getX()+17)/40] = 0;
        			b.explode();//	爆炸
        			Sound.playExplosion() ;
        			explodedArray.add(b);	//将爆炸的炸弹放入另一个数组，以进行爆炸特效的paint和对场景的破坏
        			bombArray.remove(b);	//当前炸弹已不属于还未爆炸的炸弹，于是删除
        			if( bombArray.size() == 0)
        				break;	//销毁已经爆炸的炸弹
        		}
        	}
        }
        if( explodedArray.size() > 0) {//	处理正在爆炸的炸弹，以及爆炸后的销毁
        	for(int i = 0; i < explodedArray.size(); ++i) {
        		Bomb b = explodedArray.get(i);
        		if( b.getExistTime() >= 0)
        			b.setExsistTime();	//	特效的持续时间
        		else {
	        		b.overExplode();
	        		explodedArray.remove(b);	//	爆炸完毕，特效消失
	        		if( explodedArray.size() == 0)
        				break;	
        		}
        	}
        }
        player.draw();
        g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),
				this);//	绘制玩家图片
        player.move();
        if(player.check_attack() && player.getIsDead() == false) {	//	判断玩家是否被炸弹波及
        	player.deadImageTime = 300;
        	player.isDead = true;
        	player.setLive(player.getLive()-1);
        	if(player.getLive() <= 0 && winFlag == false) {// 玩家生命数减到0，则游戏失败
    			System.out.println("Lost");
    			winFlag = true;
    			try {
					fF = new finalFrame (false);//	调用游戏失败的窗体
					if(fF == null)
						System.exit(0);///////////////////////////////2019.10.12
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		else {
    			player.setSpeed(0);//	玩家死亡未复活时无法移动，因此设置速度为0
    			new Sound();//		播放玩家死亡音效
				Sound.playDie();
        		deadRoleArray.add(player);
    		}
        }
        if( monsterArray.size() > 0) {//	在怪物数组中分别处理所有怪物对象，进行检测攻击判定以及图片绘制
	        for( int i = 0; i < monsterArray.size(); ++i) {
	        	Monster m = monsterArray.get(i);
	        	m.draw();
	        	g.drawImage(m.getImage(), m.getX(), m.getY(), m.getWidth(), m.getHeight(),
	    				this);
	        	if(m.check_attack()) {//		检测怪是否遭到攻击
	        		deadRoleArray.add(m);
	        		monsterArray.remove(m);//  怪死了就删除
	        		new Sound();
					Sound.playLaugh();//	播放怪物被玩家杀死时的音效
	        	}
	        }
        }
        else {
        	for(int i = 0; i < itemsArray.size(); ++i) {
        		Items t = itemsArray.get(i);
        		if(t instanceof Door) {
        			((Door)t).open = true;//	若怪物被全部杀死，则出口的门被打开
        			break;
        		}
        	}
        }
        if( ratio == 1)		//	ratio类似实现的占空比，降低怪物移动刷新速率，以实现怪的移动速度小于玩家
        {
        	for( Monster m: monsterArray)
        		m.move();
			ratio = 5;
        }
        if( ratio > 1) {
        	ratio++;
        	if( ratio == 8)	
        		ratio = 1;
        }
        if( deadRoleArray.size() > 0) {//	存放死亡的角色对象，实现角色死亡后的特效和玩家的延迟复活
        	for( int i = 0; i < deadRoleArray.size(); ++i) {//Role r: deadRoleArray
        		Role r = deadRoleArray.get(i);
        		if( r.getDeadTime() >= 0) {
        			r.drawDead(); 
        			g.drawImage(r.getImage(), r.getX(), r.getY(), r.getWidth(), r.getHeight(),
    	    				this);
        			r.setDeadTime();
        		}
        		else {
        			if( r instanceof Player) {//	如果是玩家则可以复活
        				player.setIsDead(false);
        				r.die();	//	die方法还含有复活的功能。。为了单一指责原则，之后要将功能分开来
        				deadRoleArray.remove(r);
        				continue;
        			}
        			deadRoleArray.remove(r);
        		}
        		if( deadRoleArray.size() == 0)
        			break;
        	}
        }
        for( Monster m: monsterArray) {//	判断怪物碰到并杀死玩家
        	int mon_centerx = m.getX()+17, mon_centery = m.getY()+17;
        	int p_centerx = player.loca_x+17, p_centery = player.loca_y+17;
        	int dis = (int) Math.sqrt((mon_centerx-p_centerx)*(mon_centerx-p_centerx)
        			+(mon_centery-p_centery)*(mon_centery-p_centery));//	计算怪物与玩家之间的距离
        	if( dis == player.getWidth() && player.getIsDead() == false) {//	若玩家碰到怪物，则玩家死亡
        		player.isDead = true;
        		player.setSpeed(0);
        		player.setLive(player.getLive()-1);//	玩家生命数减1
        		if(player.getLive() <= 0 && winFlag == false) {//	生命数减少到0，则游戏失败
        			System.out.println("Lost");
        			winFlag = true;
        			try {
						fF = new finalFrame (false);
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        		else {
	        		player.deadImageTime = 300;//	玩家对象死亡后，进行倒计时，倒计时结束后才能复活
	        		deadRoleArray.add(player);
        		}
        		
        	}
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public gamePanel(int w, int h)//	构造函数
	{
		this.Panel_width = w;
		this.Panel_height = h;
		createBackScreen();
	}
	
	public class keySkill extends KeyAdapter{//		监听空格键，实现放置炸弹
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int key = e.getKeyCode();
			if( key == KeyEvent.VK_SPACE && player.getNum() > 0) {
				if(player.getIsDead() == true) {
					return;
				}
				for( Bomb b: bombArray) {
					if( (b.getX()+17)/40*40+3 == (player.loca_x+17)/40*40+3 && (b.getY()+17)/40*40+3 == (player.loca_y+17)/40*40+3)
						return;//	保证一个坐标只能放一个炸弹
				}
				bombArray.add(player.putBomb((player.loca_x+17)/40*40+3, (player.loca_y+17)/40*40+3));
				player.map[(player.loca_y+17)/40][(player.loca_x+17)/40] = 3;/////
				player.setNum(player.getNum()-1);//	放完一个炸弹，玩家能放的炸弹数-1
				System.out.println("put a Bomb");
			}
			else if( key == KeyEvent.VK_C) {//	加条件：并且当前可以放炸弹
				int index = -1;
				for(int i = 0; i < player.skills.size(); ++i) {
					if(player.skills.get(i) instanceof MissileItem) {
						index = i;
						break;
					}
				}
				if(missile == null && index != -1) {
					missile = player.putMissile((player.getX()+17)/40*40+3, 
							(player.getY()+17)/40*40+3, monsterArray);//	导弹的初始位置为玩家当前所处的位置
					System.out.println("put a Messile");
					missile.bfs(missile.getX()/40, missile.getY()/40, monsterArray);//导弹bfs
					if(!missile.MovePath.isEmpty())// 若存在路径，则出掉队头（导弹应飞向下一个格子，而队头是当前格子，因此要将队头先删除）	
						missile.MovePath.remove();
					else {
						missile = null;//	若导弹与怪物之间不存在通路则发射失败，不生成到带你对象
						return;
					}
					new Sound();//	导弹发射成功，播放发射音效
					Sound.playShoot();
					player.skills.remove(0);
				}
			}
		}
	}
	
	@Override
	public void run() {//	线程的执行
		// TODO Auto-generated method stub
		while(true){
			repaint();//	绘制每一帧的画面
			if(fF != null)
				fF.repaint();//	绘制玩家的技能信息列表
			try {
				int time = 7;
				Thread.sleep(time);//	控制刷新的频率
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
