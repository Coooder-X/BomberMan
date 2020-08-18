package game;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Missile {
	static int dx[] = {-1, 0, 1, 0};//	方向数组，便于查找四个方向
	static int dy[] = {0, 1, 0, -1};
	private int loca_x;
	private int loca_y;
	private int range;
	private int width = 34;//	图片长宽
	private int height = 34;//	图片长宽
	private Direction dir;//	存储当前导弹的方向，以便在导弹在朝不同方向飞行时，贴不同的图片
	private int map[][];//	导弹需要获得当前的地图信息，以便进行广搜（Java中二维数组传参传递的是对象管理器，相当于c++的引用，因此没有额外开销）
	private int existTime = 55;		//	爆炸后火焰特效的时长
	Image image;
	private int speed = 1;
	private boolean canExplode = false;//	判定导弹是否到达爆炸的时机
	private ArrayList<Monster> mArray = new ArrayList<Monster>();//	存储怪物位置
	 BlockingQueue<Position> MovePath = new LinkedBlockingQueue<Position>(20*15);//记录导弹路径
	
	public Missile(int x, int y, int Range, int Map[][], ArrayList<Monster>m) {//	构造函数，初始化
		loca_x = x;
		loca_y = y;
		range = Range;
		map = Map;
		dir = Direction.Stop;
		mArray = m;
	}
	
	public void bfs(int nowX, int nowY, ArrayList<Monster> M) {//参数坐标是map下标
		Position tmp = new Position(nowX, nowY, 0);//	Position类用于存放节点信息，在底部有定义
		tmp.pre = null;//	记录每个节点的父节点，以便使用栈进行回溯得到飞行路径
		boolean jud[][] = new boolean[15][20];//	判断是否重复经过某个点的判断数组
		
		BlockingQueue<Position> que = new LinkedBlockingQueue<Position>(20*15);//	广搜所需的队列
		ArrayList <Position>path = new ArrayList<Position>();//	作为栈，通过回溯获得路径
		que.offer(tmp);
		while( !que.isEmpty()) {
			tmp = que.peek();//	取队头元素
			que.remove();	//	删队头
			System.out.println("队列点： " + tmp.missileX + ", " + tmp.missileY);
			for( Monster m: M) {
				if(Math.abs(tmp.missileX*40 + 20 - m.loca_x-m.r) <= 17
						&& Math.abs(tmp.missileY*40 + 20 - m.loca_y-m.r) <= 17) {//找到击中怪物的路径
					MovePath.clear();//		将路径拷贝给导弹的飞行路线
					while( tmp != null) {// 回溯获得路径
						path.add(tmp);
						tmp = tmp.pre;
					}
					for(int i = path.size()-1; i >= 0; --i) {//输出路径，方便调试
						System.out.println("Path: " + path.get(i).missileX + "," + path.get(i).missileY);
						MovePath.offer(path.get(i));
					}
					return;
				}
			}
			for( int i = 0; i < 4; ++i) {
				int x = tmp.missileX + dx[i], y = tmp.missileY + dy[i];
				if( x >= 0 && x < 15 && y >= 0 && y < 20 && map[y][x] == 0 && jud[x][y] == false) {
					jud[x][y] = true;
					Position t = new Position(x, y, tmp.step+1);
					t.pre = tmp;
					que.offer(t);
				}
			}
		}
	}
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public int getX() {
		return this.loca_x;
	}
	public int getY() {
		return this.loca_y;
	}
	public void setX(int x) {
		this.loca_x = x;
	}
	public void setY(int y) {
		this.loca_y = y;
	}
	public boolean getCanExplode() {
		return this.canExplode;
	}

	public void setDir(Direction d) {
		this.dir = d;
	}
	public Direction getDir() {
		return dir;
	}
	public int getExistTime() {
		return existTime;
	}
	public void setExsistTime() {
		existTime -= 1;
	}
//	@Override
	public void drawM() {//	给不同方向的导弹贴图
		if(dir == Direction.D) {
			setImage(Toolkit.getDefaultToolkit().getImage("下导弹.png"));
		}
		else if( dir == Direction.L) {
			setImage(Toolkit.getDefaultToolkit().getImage("左导弹.png"));
		}
		else if( dir == Direction.R) {
			setImage(Toolkit.getDefaultToolkit().getImage("右导弹.png"));
		}
		else if( dir == Direction.U) {
			setImage(Toolkit.getDefaultToolkit().getImage("上导弹.png"));
		}
		else {
			setImage(Toolkit.getDefaultToolkit().getImage("右导弹.png"));
		}
	}
	public void MissileFly() {//	导弹的移动
		if(dir == Direction.D) {
			this.loca_y += speed;
		}
		else if( dir == Direction.L) {
			this.loca_x -= speed;
		}
		else if( dir == Direction.R) {
			this.loca_x += speed;
		}
		else if( dir == Direction.U) {
			this.loca_y -= speed;
		}
		else {
			;
		}
	}
	
	public void figureDir() {//	查看当前路径队列的队头，并控制导弹飞向该队头所指的位置，当到达目的位置后，再次广搜，更新路径队列
		if(MovePath.isEmpty()) {
			this.canExplode = true;
			return;
		}
		Position tmp = MovePath.peek();
		System.out.println("figureDir:  " + tmp.missileX + " " + tmp.missileY);
		System.out.println(loca_x + " " + loca_y);

		if( tmp.missileX*40+20 > loca_x+17) {//	向下一个目标点移动
			dir = Direction.R;
			this.loca_x += speed;
			return;
		}
		else if( tmp.missileX*40+20 < loca_x+17) {
			dir = Direction.L;
			this.loca_x -= speed;
			return;
		}
		else if( tmp.missileY*40+20 > loca_y+17) {
			dir = Direction.D;
			this.loca_y += speed;
			return;
		}
		else if( tmp.missileY*40+20 < loca_y+17) {
			dir = Direction.U;
			this.loca_y -= speed;
			return;
		}
		if( tmp.missileX*40+20 == loca_x+17 && tmp.missileY*40+20 == loca_y+17) {//导弹中心到达方格中心
			MovePath.remove();
			System.out.println("asklgbasngalkjfgnlfknagsdnsadmnvljkdsgmnv");
			bfs(tmp.missileX, tmp.missileY, mArray);
			MovePath.remove();
			figureDir();
		}
	}
	
//	@Override
	public void setImage(Image image) {
		this.image = image;
	}
	public Image getImage() {
		return this.image;
	}
	
	public boolean attack_tar(ArrayList<Monster> M) {// 判断是否击中目标
		boolean flag = false;
		for( Monster m: M) {
			int d = (int)Math.sqrt((loca_x+17-m.center_x)*(loca_x+17-m.center_x)-
					(loca_y+17-m.center_y)*(loca_y+17-m.center_y));
			if( d <= 17)
				flag = true;
		}
		return flag;
	}
	
	public void explode() {	//	破坏地图以及确定火焰的方向
		map[loca_y/40][loca_x/40] = 4;
		for( int i = 1; i <= range; ++i ) {//	up
			if(map[loca_y/40-i][loca_x/40] == 0) {//  是草地
				if( i == range) {
					map[loca_y/40-i][loca_x/40] = 6;//	上顶
				}
				else {
					map[loca_y/40-i][loca_x/40] = 5;//	上中
				}
			}
			else {
				if( map[loca_y/40-i][loca_x/40] == 2)
					map[loca_y/40-i][loca_x/40] = 0;
				break;
			}
		}
		for( int i = 1; i <= range; ++i ) {//	right
			if(map[loca_y/40][loca_x/40+i] == 0) {//  是草地
				if( i == range) {
					map[loca_y/40][loca_x/40+i] = 8;//	右顶
				}
				else {
					map[loca_y/40][loca_x/40+i] = 7;//	右中
				}
			}
			else {
				if(map[loca_y/40][loca_x/40+i] == 2)
					map[loca_y/40][loca_x/40+i] = 0;
				break;
			}
		}
		for( int i = 1; i <= range; ++i ) {//	down
			if(map[loca_y/40+i][loca_x/40] == 0) {//  是草地
				if( i == range) {
					map[loca_y/40+i][loca_x/40] = 9; //	下顶
				}
				else {
					map[loca_y/40+i][loca_x/40] = 5;//	下中
				}
			}
			else {
				if(map[loca_y/40+i][loca_x/40] == 2)
					map[loca_y/40+i][loca_x/40] = 0;
				break;
			}
		}
		for( int i = 1; i <= range; ++i ) {//	left
			if(map[loca_y/40][loca_x/40-i] == 0) {//  是草地
				if( i == range) {
					map[loca_y/40][loca_x/40-i] = 10;//	左顶
				}
				else {
					map[loca_y/40][loca_x/40-i] = 7;//	左中
				}
			}
			else {
				if(map[loca_y/40][loca_x/40-i] == 2)
					map[loca_y/40][loca_x/40-i] = 0;
				break;
			}
		}
	}
	public void overExplode() {		//	火焰特效其实是以地图的二维数组的数字映射的，这里实现火焰的消失
		map[loca_y/40][loca_x/40] = 0;
		for( int i = 1; i <= range; ++i ) {
			if( loca_y/40-i >= 0 && map[loca_y/40-i][loca_x/40] > 3) {
				map[loca_y/40-i][loca_x/40] = 0;
			}
			if( loca_y/40+i <= 14 && map[loca_y/40+i][loca_x/40] > 3) {
				map[loca_y/40+i][loca_x/40] = 0;
			}
			if( loca_x/40-i >= 0 && map[loca_y/40][loca_x/40-i] > 3) {
				map[loca_y/40][loca_x/40-i] = 0;
			}
			if( loca_x/40+i <= 20 && map[loca_y/40][loca_x/40+i] > 3) {
				map[loca_y/40][loca_x/40+i] = 0;
			}
		}
	}
}

class Position{//	该类用于存储广搜时的每一个节点信息
	int missileX;
	int missileY;
	Position pre;
	int step = 0;
	public Position(int x, int y, int step) {//map下标
		this.missileX = x;
		this.missileY = y;
		this.step = step;
	}
}
