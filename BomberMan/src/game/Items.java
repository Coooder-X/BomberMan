package game;

import java.awt.Image;

public abstract class Items {
	private int loca_x;
	private int loca_y;
	private int width = 35;
	private int height = 35;
	Image image;
	
	public Items(int x, int y) {//	�����๹�캯��
		loca_x = x;
		loca_y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int w)
	{
		width = w;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int h)
	{
		height = h;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public int getX() {
		return loca_x;
	}
	public int getY() {
		return loca_y;
	}
	public abstract void draw() ;
	
	/**
	 * �жϵ�������Ҽ�ľ��룬�Ƿ��ܱ�����һ��
	 */
	public boolean check(int px, int py){
		if(Math.abs(px-loca_x) <= 37 && Math.abs(py-loca_y) <= 37) {
			if(this instanceof Door == false) {
				new Sound();
				Sound.playEat();
			}
			return true;
		}
		else
			return false;
	}
}
