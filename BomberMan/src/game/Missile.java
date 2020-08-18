package game;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Missile {
	static int dx[] = {-1, 0, 1, 0};//	�������飬���ڲ����ĸ�����
	static int dy[] = {0, 1, 0, -1};
	private int loca_x;
	private int loca_y;
	private int range;
	private int width = 34;//	ͼƬ����
	private int height = 34;//	ͼƬ����
	private Direction dir;//	�洢��ǰ�����ķ����Ա��ڵ����ڳ���ͬ�������ʱ������ͬ��ͼƬ
	private int map[][];//	������Ҫ��õ�ǰ�ĵ�ͼ��Ϣ���Ա���й��ѣ�Java�ж�ά���鴫�δ��ݵ��Ƕ�����������൱��c++�����ã����û�ж��⿪����
	private int existTime = 55;		//	��ը�������Ч��ʱ��
	Image image;
	private int speed = 1;
	private boolean canExplode = false;//	�ж������Ƿ񵽴ﱬը��ʱ��
	private ArrayList<Monster> mArray = new ArrayList<Monster>();//	�洢����λ��
	 BlockingQueue<Position> MovePath = new LinkedBlockingQueue<Position>(20*15);//��¼����·��
	
	public Missile(int x, int y, int Range, int Map[][], ArrayList<Monster>m) {//	���캯������ʼ��
		loca_x = x;
		loca_y = y;
		range = Range;
		map = Map;
		dir = Direction.Stop;
		mArray = m;
	}
	
	public void bfs(int nowX, int nowY, ArrayList<Monster> M) {//����������map�±�
		Position tmp = new Position(nowX, nowY, 0);//	Position�����ڴ�Žڵ���Ϣ���ڵײ��ж���
		tmp.pre = null;//	��¼ÿ���ڵ�ĸ��ڵ㣬�Ա�ʹ��ջ���л��ݵõ�����·��
		boolean jud[][] = new boolean[15][20];//	�ж��Ƿ��ظ�����ĳ������ж�����
		
		BlockingQueue<Position> que = new LinkedBlockingQueue<Position>(20*15);//	��������Ķ���
		ArrayList <Position>path = new ArrayList<Position>();//	��Ϊջ��ͨ�����ݻ��·��
		que.offer(tmp);
		while( !que.isEmpty()) {
			tmp = que.peek();//	ȡ��ͷԪ��
			que.remove();	//	ɾ��ͷ
			System.out.println("���е㣺 " + tmp.missileX + ", " + tmp.missileY);
			for( Monster m: M) {
				if(Math.abs(tmp.missileX*40 + 20 - m.loca_x-m.r) <= 17
						&& Math.abs(tmp.missileY*40 + 20 - m.loca_y-m.r) <= 17) {//�ҵ����й����·��
					MovePath.clear();//		��·�������������ķ���·��
					while( tmp != null) {// ���ݻ��·��
						path.add(tmp);
						tmp = tmp.pre;
					}
					for(int i = path.size()-1; i >= 0; --i) {//���·�����������
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
	public void drawM() {//	����ͬ����ĵ�����ͼ
		if(dir == Direction.D) {
			setImage(Toolkit.getDefaultToolkit().getImage("�µ���.png"));
		}
		else if( dir == Direction.L) {
			setImage(Toolkit.getDefaultToolkit().getImage("�󵼵�.png"));
		}
		else if( dir == Direction.R) {
			setImage(Toolkit.getDefaultToolkit().getImage("�ҵ���.png"));
		}
		else if( dir == Direction.U) {
			setImage(Toolkit.getDefaultToolkit().getImage("�ϵ���.png"));
		}
		else {
			setImage(Toolkit.getDefaultToolkit().getImage("�ҵ���.png"));
		}
	}
	public void MissileFly() {//	�������ƶ�
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
	
	public void figureDir() {//	�鿴��ǰ·�����еĶ�ͷ�������Ƶ�������ö�ͷ��ָ��λ�ã�������Ŀ��λ�ú��ٴι��ѣ�����·������
		if(MovePath.isEmpty()) {
			this.canExplode = true;
			return;
		}
		Position tmp = MovePath.peek();
		System.out.println("figureDir:  " + tmp.missileX + " " + tmp.missileY);
		System.out.println(loca_x + " " + loca_y);

		if( tmp.missileX*40+20 > loca_x+17) {//	����һ��Ŀ����ƶ�
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
		if( tmp.missileX*40+20 == loca_x+17 && tmp.missileY*40+20 == loca_y+17) {//�������ĵ��﷽������
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
	
	public boolean attack_tar(ArrayList<Monster> M) {// �ж��Ƿ����Ŀ��
		boolean flag = false;
		for( Monster m: M) {
			int d = (int)Math.sqrt((loca_x+17-m.center_x)*(loca_x+17-m.center_x)-
					(loca_y+17-m.center_y)*(loca_y+17-m.center_y));
			if( d <= 17)
				flag = true;
		}
		return flag;
	}
	
	public void explode() {	//	�ƻ���ͼ�Լ�ȷ������ķ���
		map[loca_y/40][loca_x/40] = 4;
		for( int i = 1; i <= range; ++i ) {//	up
			if(map[loca_y/40-i][loca_x/40] == 0) {//  �ǲݵ�
				if( i == range) {
					map[loca_y/40-i][loca_x/40] = 6;//	�϶�
				}
				else {
					map[loca_y/40-i][loca_x/40] = 5;//	����
				}
			}
			else {
				if( map[loca_y/40-i][loca_x/40] == 2)
					map[loca_y/40-i][loca_x/40] = 0;
				break;
			}
		}
		for( int i = 1; i <= range; ++i ) {//	right
			if(map[loca_y/40][loca_x/40+i] == 0) {//  �ǲݵ�
				if( i == range) {
					map[loca_y/40][loca_x/40+i] = 8;//	�Ҷ�
				}
				else {
					map[loca_y/40][loca_x/40+i] = 7;//	����
				}
			}
			else {
				if(map[loca_y/40][loca_x/40+i] == 2)
					map[loca_y/40][loca_x/40+i] = 0;
				break;
			}
		}
		for( int i = 1; i <= range; ++i ) {//	down
			if(map[loca_y/40+i][loca_x/40] == 0) {//  �ǲݵ�
				if( i == range) {
					map[loca_y/40+i][loca_x/40] = 9; //	�¶�
				}
				else {
					map[loca_y/40+i][loca_x/40] = 5;//	����
				}
			}
			else {
				if(map[loca_y/40+i][loca_x/40] == 2)
					map[loca_y/40+i][loca_x/40] = 0;
				break;
			}
		}
		for( int i = 1; i <= range; ++i ) {//	left
			if(map[loca_y/40][loca_x/40-i] == 0) {//  �ǲݵ�
				if( i == range) {
					map[loca_y/40][loca_x/40-i] = 10;//	��
				}
				else {
					map[loca_y/40][loca_x/40-i] = 7;//	����
				}
			}
			else {
				if(map[loca_y/40][loca_x/40-i] == 2)
					map[loca_y/40][loca_x/40-i] = 0;
				break;
			}
		}
	}
	public void overExplode() {		//	������Ч��ʵ���Ե�ͼ�Ķ�ά���������ӳ��ģ�����ʵ�ֻ������ʧ
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

class Position{//	�������ڴ洢����ʱ��ÿһ���ڵ���Ϣ
	int missileX;
	int missileY;
	Position pre;
	int step = 0;
	public Position(int x, int y, int step) {//map�±�
		this.missileX = x;
		this.missileY = y;
		this.step = step;
	}
}
