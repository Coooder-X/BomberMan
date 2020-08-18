package game;

import java.awt.Toolkit;

public class bombPlus extends Items{//	用于增加玩家一次性可放置炸弹的个数

	public bombPlus(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		setImage(Toolkit.getDefaultToolkit().getImage("炸弹道具.png"));
	}

}
