package game;

import java.awt.Toolkit;

public class rangePlus extends Items{//		增加玩家炸弹爆炸范围的加持道具类

	public rangePlus(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw() {
		setImage(Toolkit.getDefaultToolkit().getImage("距离增加.png"));
	}

}
