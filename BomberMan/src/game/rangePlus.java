package game;

import java.awt.Toolkit;

public class rangePlus extends Items{//		�������ը����ը��Χ�ļӳֵ�����

	public rangePlus(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw() {
		setImage(Toolkit.getDefaultToolkit().getImage("��������.png"));
	}

}
