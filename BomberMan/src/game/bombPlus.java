package game;

import java.awt.Toolkit;

public class bombPlus extends Items{//	�����������һ���Կɷ���ը���ĸ���

	public bombPlus(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw() {
		setImage(Toolkit.getDefaultToolkit().getImage("ը������.png"));
	}

}
