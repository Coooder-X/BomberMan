package game;

import java.awt.Toolkit;
import java.util.ArrayList;

public class Monster extends Role {//implements Runnable
	static int dx[] = new int[] { 0, 1, 0, -1};
	static int dy[] = new int[] { -1, 0, 1, 0};
	static Direction Dir[] = new Direction[] { Direction.U, Direction.R,Direction.D,Direction.L};
	int center_x;
	int center_y;
	int r = 17;//	����뾶
	ArrayList<Direction> surround = new ArrayList<Direction>();//�洢���ﵽ���·��ʱ���ߵķ���
	public Monster(int x, int y, int width, int height, boolean isEnemy, int map[][])
	{
		super(x, y, width, height, isEnemy, map);
		center_x = x + r;
		center_y = y + r;
	}
	
	public void watch_and_turn()//	����ʹ���������ܵĵ�ͼ��Ϣ����¼���ߵķ���ע�͵Ĵ������ÿ�ɾ
	{
		surround.clear();
		center_x = loca_x + r;
		center_y = loca_y + r;
		
		for( int i = 0; i < 4; ++i)
		{
			int y = center_y/40 + dy[i], x = center_x/40 + dx[i];
			if( map[y][x] == 0 ) {
				surround.add(Dir[i]);//		�ҳ��������ߵķ���
			}
		}
		
		//���²���Ϊ�������������ʣ��ù�����ݸ���ѡ�����ƶ�
		if( surround.size() > 2 && center_x %20 == 0 && center_x % 40 != 0 && center_y %20 == 0 && center_y % 40 != 0) {
			for( int i = 0; ; ++i) {
				if( i >= surround.size()) {
					i %= surround.size();
				}
				if( Math.random() < 1.0/(surround.size()-1)) {//	���ݸ��ʣ�����ó�����ߵķ���
					Direction d = surround.get(i);
					if( dir == Direction.D && d == Direction.U) continue;
					if( dir == Direction.U && d == Direction.D) continue;
					if( dir == Direction.L && d == Direction.R) continue;
					if( dir == Direction.R && d == Direction.L) continue;
					dir = d;
					return;
				}
			}
		}
		else if( surround.size() > 2) {
			for( int i = 0; i < 4; ++i)
			{
				int y = center_y/40 + dy[i], x = center_x/40 + dx[i];
				if( map[y][x] != 0){
					if( dir == Direction.D && Math.abs(y*40-center_y)==r+3) {
						dir = Direction.U;return;
					}
					else if( dir == Direction.U && Math.abs(y*40+40-center_y)==r+3) {//flag = true;
						dir = Direction.D;return;
					}
					else if( dir == Direction.L && Math.abs(x*40+40-center_x)==r+3) {//flag = true;
						dir = Direction.R;return;
					}
					else if( dir == Direction.R && Math.abs(x*40-center_x)==r+3) {//flag = true;
						dir = Direction.L;return;
					}
				}
				else if( map[y][x] == 0 && dir == Direction.Stop){
					dir = Dir[i];return;
				}
			}
		}// ֻ��һ������
		else if( surround.size() == 2&& center_x %20 == 0 && center_x % 40 != 0 && center_y %20 == 0 && center_y % 40 != 0) {
			if( surround.get(0)==Direction.U && surround.get(1)==Direction.D)
				return ;
			if( surround.get(0)==Direction.R && surround.get(1)==Direction.L)
				return ;
			if( surround.get(0)==Direction.L && surround.get(1)==Direction.R)
				return ;
			if( surround.get(0)==Direction.D && surround.get(1)==Direction.U)
				return ;
			for( int i = 0; ; ++i) {
				if( i >= surround.size()) {
					i %= surround.size();
				}
				if( Math.random() < 1.0/(surround.size())) {
					Direction d = surround.get(i);
					dir = d;
					return;
				}
			}
		}
		else if( surround.size() == 1){//	���ݸ��ʣ�����ó�����ߵķ���
			for( int i = 0; i < 4; ++i)
			{
				int y = center_y/40 + dy[i], x = center_x/40 + dx[i];
				if( map[y][x] != 0){
					if( dir == Direction.D && Math.abs(y*40-center_y)==r+3) {
						dir = Direction.U;return;
					}
					else if( dir == Direction.U && Math.abs(y*40+40-center_y)==r+3) {//flag = true;
						dir = Direction.D;return;
					}
					else if( dir == Direction.L && Math.abs(x*40+40-center_x)==r+3) {//flag = true;
						dir = Direction.R;return;
					}
					else if( dir == Direction.R && Math.abs(x*40-center_x)==r+3) {//flag = true;
						dir = Direction.L;return;
					}
				}
				else if( map[y][x] == 0 && dir == Direction.Stop){
					dir = Dir[i];return;
				}
			}
		}
		
	}
	@Override
	public void move() {//		����watch_and_turn()������¼�ķ�����Ϣʵ�ֹ��������ƶ�
		// TODO Auto-generated method stub
		watch_and_turn();
		if( dir == Direction.U) {
			loca_y -= this.getSpeed();
		}
		else if( dir == Direction.R) {
			loca_x += this.getSpeed();
		}
		else if( dir == Direction.D) {
			loca_y += this.getSpeed();
		}
		else if( dir == Direction.L) {
			loca_x -= this.getSpeed();
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		setImage(Toolkit.getDefaultToolkit().getImage("����2.png"));
	}
	public void drawDead() {
		setImage(Toolkit.getDefaultToolkit().getImage("�ս�.png"));
	}
	@Override
	public boolean check_attack() {
		int x = loca_x, y = loca_y + r;//left
		if( map[y/40][x/40] > 3) {
			if( x <= x/40*40 + 37) {
				this.die();
				return true;
			}
		}
		x = loca_x + r; y = loca_y;// up
		if( map[y/40][x/40] > 3) {
			if( y <= y/40*40 + 37) {
				this.die();
				return true;
			}
		}
		x = loca_x + 2*r; y = loca_y + r;// right
		if( map[y/40][x/40] > 3) {
			if( x >= x/40*40 + 3) {
				this.die();
				return true;
			}
		}
		x = loca_x + r; y = loca_y+ 2*r;// down
		if( map[y/40][x/40] > 3) {
			if( y >= y/40*40 + 3) {
				this.die();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void die() {//	������������
		// TODO Auto-generated method stub
		this.setSpeed(0);
		new Sound();
		Sound.playLaugh();// 	������Ч
		this.isDead = true;
	}

}
