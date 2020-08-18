package game;

import java.awt.Image;

public abstract class Role {//	角色类，由玩家类和怪物类继承
	 int loca_x;
	 int loca_y;
	int map[][];//	角色对象需要具有地图的信息，因此传入储存地图的二维数组
	private int width;
	private int height;
	private int speed = 1;
	Direction dir = Direction.Stop;
	Image roleImage;
	boolean isEnemy;
	boolean isDead = false;
	int deadImageTime = 300;//	角色死亡到复活的间隔时间
	
	public Role(int role_x, int role_y, int width, int height, boolean isEnemy, int map[][])
	{
		loca_x = role_x;	loca_y = role_y;
		this.width = width;	this.height = height;
		this.isEnemy = isEnemy;
		this.map = map;
 	}
	public Role() {
		loca_x = 300; loca_y = 100; width = 35; height = 35; //speed = 1;
	}
	public Direction getDir()
	{
		return dir;
	}
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	public int getX()
	{
		return loca_x;
	}
	public void setX(int x)
	{
		loca_x = x;
	}
	public int getY()
	{
		return loca_y;
	}
	public void setY(int y)
	{
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
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public boolean getIsEnemy() {
		return isEnemy;
	}
	public boolean getIsDead() {
		return isDead;
	}
	public void setIsDead(boolean f)
	{
		isDead = f;
	}
	public void setImage(Image image) {
		this.roleImage = image;
	}
	public Image getImage()
	{
		return roleImage;
	}
	public void setDeadTime() {
		this.deadImageTime--;
	}
	public int getDeadTime() {
		return this.deadImageTime;
	}
	public abstract void move();//	不同角色类通过覆写抽象函数，实现不同角色的移动
	public abstract void draw();//	不同角色类通过覆写抽象函数，实现不同角色的图片粘贴
	public abstract void drawDead();
	/**
	*	判断角色是否遭到攻击
	 */
	public abstract boolean check_attack();
	public abstract void die();
}








