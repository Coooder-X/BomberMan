package game;

import java.awt.Image;
import java.awt.Toolkit;

public class Bomb  {
	protected int loca_x;
	protected int loca_y;
	protected int range;
	private int width = 34;
	private int height = 34;
	private int explodeTime = 350;	//	爆炸的倒计时时长
	private int existTime = 55;		//	爆炸后火焰特效的时长
	protected int map[][];	//	共享地图信息，用于破坏地图时的判断
	Image bombImage;
	public Bomb( int x, int y, int range, int map[][]) {
		loca_x = x;
		loca_y = y;
		this.range = range;
		this.map = map;
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
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public void setWidth(int x) {
		this.width = x;
	}
	public void setHeight(int y) {
		this.height = y;
	}
	public int getTime() {
		return this.explodeTime;
	}
	public void setTime() {
		this.explodeTime -= 1;
	}
	public int getExistTime() {
		return this.existTime;
	}
	public void setExsistTime() {
		this.existTime -= 1;
	}
	public void setImage(Image i) {
		this.bombImage = i;
	}
	public Image getImage()
	{
		return bombImage;
	}
	
	public void draw() {
		setImage(Toolkit.getDefaultToolkit().getImage("炸弹3.png"));
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
			if( loca_x/40+i < 20 && map[loca_y/40][loca_x/40+i] > 3) {
				map[loca_y/40][loca_x/40+i] = 0;
			}
		}
	}

}
