package game;

import java.awt.Image;
import java.awt.Toolkit;

public class MissileItem extends Items{//	��������

	public MissileItem(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw() {
		setImage(Toolkit.getDefaultToolkit().getImage("��������.png"));
	}
	
}
