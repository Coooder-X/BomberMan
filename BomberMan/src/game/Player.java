package game;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Player extends Role{
	keyControl k = new keyControl();
	JPanel jp;
	private int attackRange = 1;	//	玩家炸弹的攻击范围
	private int num_of_bomb;	//	玩家当前可放置的炸弹数量
	private int num_of_live = 3;//	玩家生命数
	ArrayList <Items>skills = new ArrayList<Items>();//	存放道具
	ArrayList <Items>buff = new ArrayList<Items>();//	存放属性加持的道具
	public Player(int x, int y, int width, int height, boolean isEnemy, int map[][], JPanel jp)//构造函数
	{
		super(x, y, width, height, isEnemy, map);
		this.setSpeed(1);
		this.jp = jp;
		num_of_bomb = 1;
		this.deadImageTime = 300;//		设置死亡到复活之间的间隔时间
	}
	public Player()
	{
		super();
	}
	public void setNum(int n) {
		this.num_of_bomb = n;
	}
	public int getNum() {
		return this.num_of_bomb;
	}
	public int getRange() {
		return this.attackRange;
	}
	public void setRange( int range) {
		this.attackRange = range;
	}
	public int getLive() {
		return this.num_of_live;
	}
	public void setLive(int l) {
		if(l < this.num_of_live) {
			new Sound();
			Sound.playDie();
		}
		this.num_of_live = l;
	}

	
	@Override
	public void move() {	
		// TODO Auto-generated method stub
		int r = 17;//	玩家图片的半径
		
		if( this.getDir() == Direction.U && this.getY()-this.getSpeed() > 40)//	向上移动碰到障碍物
		{
			int now_x = loca_x+r,  now_y = loca_y;
			if(map[now_y/40 -1][now_x/40] != 0) {
				if( now_y >= now_y/40 *40 && now_y <= now_y/40 *40+40) {
						if( now_y <= (now_y/40)*40) {
							return;
					}
				}
			}
			else if( map[now_y/40 -1][now_x/40 -1] != 0) {
				int d = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40, now_x, now_y+r));
				if( Math.abs(d-r) <= 0 && loca_x != now_x/40*40)
					return ;
			}
			if(map[now_y/40][now_x/40 -1] != 0) {
				int d2 = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40+40, loca_x+r, loca_y+r));
				if( Math.abs(d2-r) <= 0 && loca_x != now_x/40*40)
					return ;
			}
			if( map[now_y/40 -1][now_x/40 +1] != 0) {
				int d = (int) Math.sqrt(cal(now_x/40*40+40, now_y/40*40, now_x, now_y+r));
				if( Math.abs(d-r) <= 0 && loca_x != now_x/40*40+40)
					return ;
			}
			if(map[now_y/40][now_x/40+1] != 0) {
					int d2 = (int) Math.sqrt(cal(now_x/40*40+40, now_y/40*40+40, now_x, now_y+r));
					if( Math.abs(d2-r) <= 0 && now_x+r != now_x/40*40+40)
						return ;
			}
			this.loca_y -= this.getSpeed();
		}
		
		else if( this.getDir() == Direction.D && this.getY()+this.getSpeed() < 527) {//	向下移动碰到障碍物
			int now_x = loca_x+r,  now_y = loca_y+2*r;
			if( map[now_y/40 +1][now_x/40] != 0) {
				if( now_x >= now_x/40*40 && now_x <= now_x/40*40+40) {
					if( now_y >= (now_y/40)*40+39)
						return;
				}
			}
			else if( map[now_y/40][now_x/40 -1] != 0) {//  左下
				int d = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40, now_x, now_y-r));
				if( Math.abs(d-r) <= 0 && loca_x != now_x/40*40)
					return ;
			}
			if( map[now_y/40 ][now_x/40 +1] != 0) {//	右下
				int d = (int) Math.sqrt(cal(now_x/40*40+40, now_y/40*40, now_x, now_y-r));
				if( Math.abs(d-r) <= 0 && now_x+r != now_x/40*40+40)
					return ;
			}
			this.setY(this.getY()+this.getSpeed());
		}
			
		else if( this.getDir() == Direction.L && this.getX()-this.getSpeed() > 40) {//	向左移动碰到障碍物
			int now_x = loca_x,  now_y = loca_y+r;
			if(map[now_y/40][now_x/40 -1] != 0) {
				if( now_y >= now_y/40 *40 && now_y <= now_y/40 *40+40) {
						if( now_x <= (now_x/40)*40) {
							return;
					}
				}
			}
			else if( map[now_y/40+1][now_x/40 -1] != 0) {
				int d = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40+40, now_x+r, now_y));
				if( Math.abs(d-r) <= 0 && loca_y+2*r != now_y/40*40+40)
					return ;
			}
			if(map[now_y/40+1][now_x/40] != 0) {
				int d2 = (int) Math.sqrt(cal(now_x/40*40+40, now_y/40*40+40, now_x+r, now_y));
				
				if( Math.abs(d2-r) <= 0 && loca_y+2*r != now_y/40*40+40)
					return ;
			}
			if( map[now_y/40-1][now_x/40 -1] != 0) {
				int d = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40, now_x+r, now_y));
				if( Math.abs(d-r) <= 0 && loca_y+2*r != now_y/40*40)
					return ;
			}
			if(map[now_y/40-1][now_x/40] != 0) {
					int d2 = (int) Math.sqrt(cal(now_x/40*40+40, now_y/40*40, now_x+r, now_y));
					if( Math.abs(d2-r) <= 0 && loca_y != now_y/40*40)
						return ;
			}
			this.setX(this.getX()-this.getSpeed());
		}
			// 右
		else if( this.getDir() == Direction.R && this.getX()+this.getSpeed() < 727) {//	向右移动碰到障碍物
			int now_x = loca_x+2*r,  now_y = loca_y+r;
			if(map[now_y/40][now_x/40] != 0) {
				if( now_y >= now_y/40 *40 && now_y <= now_y/40 *40+40) {
						if( now_x <= (now_x/40)*40) {
						return;
					}
				}
			}
			else if( map[now_y/40+1][now_x/40] != 0) {
				if(map[now_y/40+1][now_x/40] != 0) {
					int d2 = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40+40, now_x-r, now_y));
					if( Math.abs(d2-r) <= 0 && loca_y+2*r != now_y/40*40+40)
						return ;
				}
			}
			if( map[now_y/40-1][now_x/40] != 0) {
				int d = (int) Math.sqrt(cal(now_x/40*40, now_y/40*40, now_x-r, now_y));
				if( Math.abs(d-r) <= 0 && loca_y != now_y/40*40)
					return ;
			}
			this.setX(this.getX()+this.getSpeed());
		}
	}
	static int cal(int x1, int y1, int x2, int y2) {//	计算距离的 函数
		int res = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
		return res;
	}

	public class keyControl extends KeyAdapter{//	加入键盘监听，根据键值更新dir缓存变量的值，达到提高帧数的效果
		@Override
		public void keyPressed(KeyEvent e) {
 
			super.keyPressed(e);
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_UP:
				dir = Direction.U;
				break;
			case KeyEvent.VK_DOWN:
				dir = Direction.D;
				break;
			case KeyEvent.VK_LEFT:
				dir = Direction.L;
				break;
			case KeyEvent.VK_RIGHT:
				dir = Direction.R;
				break;
			default:
				dir = Direction.Stop;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {//释放移动方向改变恢复，指向不改变
			
			super.keyReleased(e);
			int key = e.getKeyCode();
			if(key==KeyEvent.VK_UP||key==KeyEvent.VK_DOWN||key==KeyEvent.VK_LEFT||key==KeyEvent.VK_RIGHT){
				dir = Direction.Stop;
			}
		}
	}
	
	public Bomb putBomb(int x, int y) {//	放置炸弹的函数
		Bomb b1 = new Bomb(x, y, this.getRange(), this.map);
		return b1;
	}
	public Missile putMissile(int x, int y, ArrayList<Monster>m) {//	发射导弹的函数
		if(skills.get(0) instanceof MissileItem ){
				Missile missile = new Missile(x, y, this.getRange(), this.map, m);
				return missile;
			}
		return null;
	}
	
	@Override
	public void draw() {//	为玩家对象贴图
		setImage(Toolkit.getDefaultToolkit().getImage("滑稽2.png"));
	}
	@Override
	public void die() {//	复活函数（命名有点奇怪，来不及改）
		this.setX(43);
		this.setY(43);
		this.setSpeed(1);
	}
	@Override
	public boolean check_attack() {//	判定玩家是否遭到攻击
		int r = 17;
		int x = loca_x, y = loca_y + r;//left
		if( map[y/40][x/40] > 3) {
			if( x <= x/40*40 + 37)
				return true;
		}
		x = loca_x + r; y = loca_y;// up
		if( map[y/40][x/40] > 3) {
			if( y <= y/40*40 + 37)
				return true;
		}
		x = loca_x + 2*r; y = loca_y + r;// right
		if( map[y/40][x/40] > 3) {
			if( x >= x/40*40 + 3)
				return true;
		}
		x = loca_x + r; y = loca_y+ 2*r;// down
		if( map[y/40][x/40] > 3) {
			if( y >= y/40*40 + 3)
				return true;
		}
		return false;
	}
	@Override
	public void drawDead() {//	为死亡时的玩家贴图
		setImage(Toolkit.getDefaultToolkit().getImage("烧焦.png"));
	}
}
