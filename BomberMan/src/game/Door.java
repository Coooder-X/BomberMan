package game;

import java.awt.Toolkit;

public class Door extends Items{//	门类，在游戏中作为出口，在怪物没有全部消灭时处于关闭状态，怪物消灭完后打开
	boolean open = false;
	public Door(int x, int y) {
		super(x, y);
		this.setWidth(40);
		this.setHeight(40);
	}

	@Override
	public void draw() {//	分开启和关闭两种状态贴图
		if(open) {
			setImage(Toolkit.getDefaultToolkit().getImage("开门.png"));
		}
		else {
			setImage(Toolkit.getDefaultToolkit().getImage("关门.png"));
		}
	}
	
}
