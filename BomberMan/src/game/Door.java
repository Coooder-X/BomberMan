package game;

import java.awt.Toolkit;

public class Door extends Items{//	���࣬����Ϸ����Ϊ���ڣ��ڹ���û��ȫ������ʱ���ڹر�״̬��������������
	boolean open = false;
	public Door(int x, int y) {
		super(x, y);
		this.setWidth(40);
		this.setHeight(40);
	}

	@Override
	public void draw() {//	�ֿ����͹ر�����״̬��ͼ
		if(open) {
			setImage(Toolkit.getDefaultToolkit().getImage("����.png"));
		}
		else {
			setImage(Toolkit.getDefaultToolkit().getImage("����.png"));
		}
	}
	
}
