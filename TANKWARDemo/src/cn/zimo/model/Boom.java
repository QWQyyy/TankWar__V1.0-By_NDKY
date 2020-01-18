package cn.zimo.model;

import java.awt.Graphics;
import cn.zimo.frame.GamePanel;
import cn.zimo.util.ImageUtil;

/**
 * չʾ��ըЧ����ͼƬ)
 * 
 * @author WENXIN
 *
 */
public class Boom extends DisplayableImage {

	private int timer = 0;// ��ʱ��
	private int fresh = GamePanel.FRESHTIME;// ˢ��ʱ��
	private boolean alive = true;// �Ƿ���

	/**
	 * ��ըЧ����������
	 * 
	 * @param x
	 *            ��ըͼƬ������
	 * @param y
	 *            ��ըͼƬ������
	 */
	public Boom(int x, int y) {
		super(x, y, ImageUtil.BOOM_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ�ϱ�ըЧ��ͼƬ
	}

	/**
	 * չʾ��ըͼƬ,����Ƭֻ��ʾ0.3��
	 * 
	 * @param g
	 *            ��ͼ����
	 */
	public void show(Graphics g) {
		if (timer >= 300) {// ����ʱ����¼�ѵ�0.3��
			alive = false;// ��ըЧ��ʧЧ
		} else {
			g.drawImage(getImage(), x, y, null);// ���Ʊ�ըЧ��
			timer += fresh;// ��ʱ������ˢ��ʱ�����
		}
	}

	/**
	 * ��ըͼƬ�Ƿ���Ч
	 * 
	 * @return
	 */
	public boolean isAlive() {
		return alive;
	}
}
