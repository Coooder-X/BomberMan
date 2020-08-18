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
	boolean Doorflag = false;//	�жϳ��ڵ����Ƿ��
	static int ratio = 1;	//	�����ƶ��Ļ���ˢ����
	static int sizeW = 34;
	static int sizeH = 34;
	private int Panel_width;
	private int Panel_height;
	boolean winFlag = false;	//	�ж���Ϸ�Ƿ����
	
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
    private String[] road ={"�ݵ�.jpg", "ש��.jpg", "��ש.jpg"};//	�ļ�·��
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
	ArrayList<Monster> monsterArray = new ArrayList<Monster>() {{// ��Ź���������˾ʹ���ɾ��
    	add(new Monster( 483, 43, sizeW, sizeH, true, backsenceXY));
    	add(new Monster( 283, 43, sizeW, sizeH, true, backsenceXY));
    	add(new Monster( 123, 403, sizeW, sizeH, true, backsenceXY));
    	add(new Monster( 14*40+3, 5*40+3, sizeW, sizeH, true, backsenceXY));
    }};
    @SuppressWarnings("serial")
	ArrayList<Items> itemsArray = new ArrayList<Items>() {//��ŵ�ͼ�еĵ��ߣ��ɸ��ĳ���ͨ���������ʵ�֣�
    	{
    		add(new MissileItem(202, 242));
    		add(new MissileItem(42, 162));
    		add(new bombPlus(322, 282));
    		add(new bombPlus(362, 11*40+2));
    		add(new rangePlus(11*40+2, 6*40+2));
    	}
    };
    ArrayList<Bomb> bombArray = new ArrayList<Bomb>();//	��ŵȴ���ը��ը��
    Missile missile;	//	��Ϸ��ͬһʱ��ֻ���ͷ�һö���������ֻ����һ���������󣬵����δ���䵼��ʱ���Ķ���δnull
    Missile explodingMissile;	//   ��������һ�������������ڴ�����Ϸ�е����� ��ըЧ��
    ArrayList<Bomb> explodedArray = new ArrayList<Bomb>();// ���ڱ�ը��ը�������ڴ���ը�ڼ���¼�
    ArrayList<Role> deadRoleArray = new ArrayList<Role>();// ����Դ������������Ľ�ɫ����
    
    public void createBackScreen(){
		try{
	        Sound.open.stop();//		beginFrame�еĿ������ֽ���
	        new BackGroundMusic();
			BackGroundMusic.playBackMusic();//	������Ϸ�еı�������
			
			File fb0 = new File(road[0]);
			backScene0 = ImageIO.read(fb0);
			File fb1 = new File(road[1]);
			backScene1 = ImageIO.read(fb1);
            File fb2 = new File(road[2]);
			backScene2 = ImageIO.read(fb2);
			File fb4 = new File("��ը����.png");
			fire4 = ImageIO.read(fb4);
			File fb5 = new File("���м��.png");
			fire5 = ImageIO.read(fb5);
            File fb6 = new File("�϶�.png");
			fire6 = ImageIO.read(fb6);  
			File fb7 = new File("�м�Ρ���ǽ��.png");
			fire7 = ImageIO.read(fb7);
			File fb8 = new File("�Ҷ�.png");
			fire8 = ImageIO.read(fb8);
            File fb9 = new File("�¶�.png");
			fire9 = ImageIO.read(fb9);
			File fb10 = new File("���.png");//		������ͼƬָ���ļ���·��
			fire10 = ImageIO.read(fb10);
        }catch(Exception e)
		{
			e.printStackTrace();
        }
	}
    public void paint(Graphics g){//	Override��paint������ʵ��ÿһ֡����ˢ��ʱ����Ļ���
        
        for(int i=0 ; i < 20 ; i++)//	��˫��ѭ�����ڻ��Ƶ�ͼ
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
        if(winFlag) {//	�����Ϸ��������ֹͣ���ű�������
        	BackGroundMusic.backMusic.stop();
        }
        if(!itemsArray.isEmpty()) {//	���Ƶ�ͼ�е����е��ߣ�ʵ����һ�õ���
        	for(int i = 0; i < itemsArray.size(); ++i) {//	ѭ�����ж����ߵ����࣬�ֱ���ͼƬ
        		Items t = itemsArray.get(i);
        		if(!t.check(player.getX(), player.getY())) {
        			t.draw();
        			g.drawImage(t.getImage(), t.getX(), t.getY(), t.getWidth(), t.getHeight(),
        					this);
        		}
        		else if(t instanceof Door && t.check(player.getX(), player.getY())
        				&& ((Door)t).open == false) {//���޴˾䣬���ű�����ʱ�����ǹ��ţ�����������ž���ʧ
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
        				if(((Door)t).open == true && winFlag == false) {//	�����ڴ򿪣���ҽ��룬����Ϸʤ��
        					winFlag = true;
        					try {
								 fF = new finalFrame (true);
								 new Sound();
								Sound.playWin();//	����ʤ����Ч 
								if(fF == null)
									System.exit(0);///////////////////////////////2019.10.12
//								fF.paint(g);
								System.out.println("newWIn");
							} catch (IOException e) {
								e.printStackTrace();
							}
        				}
        			}
        			else if(t instanceof MissileItem) {//	��һ�õ������ߣ����������߼�����ҵļ��ܶ�����
	        			player.skills.add(t);
	        			itemsArray.remove(t);
        			}
        		}
        	}
        }
        
        if(missile != null) {
        	missile.figureDir();//	figureDir�����е��ù��ѣ�ʵ�ֵ������Զ�Ѱ·
        	missile.drawM();
        	g.drawImage(missile.getImage(), missile.getX(), missile.getY(), missile.getWidth(), missile.getHeight(),
					this);//	��������Ƶ���ͼƬ
        	if(missile.getCanExplode() == true) {//		����������Ŀ�꣬����ըЧ��
        		explodingMissile = missile;
        		missile.explode();
        		Sound.playExplosion() ;
        		missile = null;
        	}
        }
        if(explodingMissile != null) {//	��������ըЧ��
        	if( explodingMissile.getExistTime() >= 0)
        		explodingMissile.setExsistTime();	//	��Ч�ĳ���ʱ��
    		else {
    			explodingMissile.overExplode();
    			explodingMissile = null;
    		}
        }
        
        if( bombArray.size() > 0) {	//	������ҷ��õ����ɸ�ը������ÿ��ը������paint�͵���ʱ
        	for( Bomb b: bombArray) {
        		if( b.getTime() >= 0) {
        			{
        				b.draw();
	        			g.drawImage(b.getImage(), b.getX(), b.getY(), b.getWidth(), b.getHeight(),
	        					this);
	        			b.setTime();	//		��ըʣ��ʱ�����
        			}
        		}
        		else if( b.getTime() < 0) {
        			player.setNum(player.getNum()+1);// ����ըһ��������� �ɷ�ը����+1
        			player.map[(b.getY()+17)/40][(b.getX()+17)/40] = 0;
        			b.explode();//	��ը
        			Sound.playExplosion() ;
        			explodedArray.add(b);	//����ը��ը��������һ�����飬�Խ��б�ը��Ч��paint�ͶԳ������ƻ�
        			bombArray.remove(b);	//��ǰը���Ѳ����ڻ�δ��ը��ը��������ɾ��
        			if( bombArray.size() == 0)
        				break;	//�����Ѿ���ը��ը��
        		}
        	}
        }
        if( explodedArray.size() > 0) {//	�������ڱ�ը��ը�����Լ���ը�������
        	for(int i = 0; i < explodedArray.size(); ++i) {
        		Bomb b = explodedArray.get(i);
        		if( b.getExistTime() >= 0)
        			b.setExsistTime();	//	��Ч�ĳ���ʱ��
        		else {
	        		b.overExplode();
	        		explodedArray.remove(b);	//	��ը��ϣ���Ч��ʧ
	        		if( explodedArray.size() == 0)
        				break;	
        		}
        	}
        }
        player.draw();
        g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(),
				this);//	�������ͼƬ
        player.move();
        if(player.check_attack() && player.getIsDead() == false) {	//	�ж�����Ƿ�ը������
        	player.deadImageTime = 300;
        	player.isDead = true;
        	player.setLive(player.getLive()-1);
        	if(player.getLive() <= 0 && winFlag == false) {// �������������0������Ϸʧ��
    			System.out.println("Lost");
    			winFlag = true;
    			try {
					fF = new finalFrame (false);//	������Ϸʧ�ܵĴ���
					if(fF == null)
						System.exit(0);///////////////////////////////2019.10.12
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		else {
    			player.setSpeed(0);//	�������δ����ʱ�޷��ƶ�����������ٶ�Ϊ0
    			new Sound();//		�������������Ч
				Sound.playDie();
        		deadRoleArray.add(player);
    		}
        }
        if( monsterArray.size() > 0) {//	�ڹ��������зֱ������й�����󣬽��м�⹥���ж��Լ�ͼƬ����
	        for( int i = 0; i < monsterArray.size(); ++i) {
	        	Monster m = monsterArray.get(i);
	        	m.draw();
	        	g.drawImage(m.getImage(), m.getX(), m.getY(), m.getWidth(), m.getHeight(),
	    				this);
	        	if(m.check_attack()) {//		�����Ƿ��⵽����
	        		deadRoleArray.add(m);
	        		monsterArray.remove(m);//  �����˾�ɾ��
	        		new Sound();
					Sound.playLaugh();//	���Ź��ﱻ���ɱ��ʱ����Ч
	        	}
	        }
        }
        else {
        	for(int i = 0; i < itemsArray.size(); ++i) {
        		Items t = itemsArray.get(i);
        		if(t instanceof Door) {
        			((Door)t).open = true;//	�����ﱻȫ��ɱ��������ڵ��ű���
        			break;
        		}
        	}
        }
        if( ratio == 1)		//	ratio����ʵ�ֵ�ռ�ձȣ����͹����ƶ�ˢ�����ʣ���ʵ�ֵֹ��ƶ��ٶ�С�����
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
        if( deadRoleArray.size() > 0) {//	��������Ľ�ɫ����ʵ�ֽ�ɫ���������Ч����ҵ��ӳٸ���
        	for( int i = 0; i < deadRoleArray.size(); ++i) {//Role r: deadRoleArray
        		Role r = deadRoleArray.get(i);
        		if( r.getDeadTime() >= 0) {
        			r.drawDead(); 
        			g.drawImage(r.getImage(), r.getX(), r.getY(), r.getWidth(), r.getHeight(),
    	    				this);
        			r.setDeadTime();
        		}
        		else {
        			if( r instanceof Player) {//	������������Ը���
        				player.setIsDead(false);
        				r.die();	//	die���������и���Ĺ��ܡ���Ϊ�˵�һָ��ԭ��֮��Ҫ�����ֿܷ���
        				deadRoleArray.remove(r);
        				continue;
        			}
        			deadRoleArray.remove(r);
        		}
        		if( deadRoleArray.size() == 0)
        			break;
        	}
        }
        for( Monster m: monsterArray) {//	�жϹ���������ɱ�����
        	int mon_centerx = m.getX()+17, mon_centery = m.getY()+17;
        	int p_centerx = player.loca_x+17, p_centery = player.loca_y+17;
        	int dis = (int) Math.sqrt((mon_centerx-p_centerx)*(mon_centerx-p_centerx)
        			+(mon_centery-p_centery)*(mon_centery-p_centery));//	������������֮��ľ���
        	if( dis == player.getWidth() && player.getIsDead() == false) {//	���������������������
        		player.isDead = true;
        		player.setSpeed(0);
        		player.setLive(player.getLive()-1);//	�����������1
        		if(player.getLive() <= 0 && winFlag == false) {//	���������ٵ�0������Ϸʧ��
        			System.out.println("Lost");
        			winFlag = true;
        			try {
						fF = new finalFrame (false);
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        		else {
	        		player.deadImageTime = 300;//	��Ҷ��������󣬽��е���ʱ������ʱ��������ܸ���
	        		deadRoleArray.add(player);
        		}
        		
        	}
        }

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public gamePanel(int w, int h)//	���캯��
	{
		this.Panel_width = w;
		this.Panel_height = h;
		createBackScreen();
	}
	
	public class keySkill extends KeyAdapter{//		�����ո����ʵ�ַ���ը��
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int key = e.getKeyCode();
			if( key == KeyEvent.VK_SPACE && player.getNum() > 0) {
				if(player.getIsDead() == true) {
					return;
				}
				for( Bomb b: bombArray) {
					if( (b.getX()+17)/40*40+3 == (player.loca_x+17)/40*40+3 && (b.getY()+17)/40*40+3 == (player.loca_y+17)/40*40+3)
						return;//	��֤һ������ֻ�ܷ�һ��ը��
				}
				bombArray.add(player.putBomb((player.loca_x+17)/40*40+3, (player.loca_y+17)/40*40+3));
				player.map[(player.loca_y+17)/40][(player.loca_x+17)/40] = 3;/////
				player.setNum(player.getNum()-1);//	����һ��ը��������ܷŵ�ը����-1
				System.out.println("put a Bomb");
			}
			else if( key == KeyEvent.VK_C) {//	�����������ҵ�ǰ���Է�ը��
				int index = -1;
				for(int i = 0; i < player.skills.size(); ++i) {
					if(player.skills.get(i) instanceof MissileItem) {
						index = i;
						break;
					}
				}
				if(missile == null && index != -1) {
					missile = player.putMissile((player.getX()+17)/40*40+3, 
							(player.getY()+17)/40*40+3, monsterArray);//	�����ĳ�ʼλ��Ϊ��ҵ�ǰ������λ��
					System.out.println("put a Messile");
					missile.bfs(missile.getX()/40, missile.getY()/40, monsterArray);//����bfs
					if(!missile.MovePath.isEmpty())// ������·�����������ͷ������Ӧ������һ�����ӣ�����ͷ�ǵ�ǰ���ӣ����Ҫ����ͷ��ɾ����	
						missile.MovePath.remove();
					else {
						missile = null;//	�����������֮�䲻����ͨ·����ʧ�ܣ������ɵ��������
						return;
					}
					new Sound();//	��������ɹ������ŷ�����Ч
					Sound.playShoot();
					player.skills.remove(0);
				}
			}
		}
	}
	
	@Override
	public void run() {//	�̵߳�ִ��
		// TODO Auto-generated method stub
		while(true){
			repaint();//	����ÿһ֡�Ļ���
			if(fF != null)
				fF.repaint();//	������ҵļ�����Ϣ�б�
			try {
				int time = 7;
				Thread.sleep(time);//	����ˢ�µ�Ƶ��
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
