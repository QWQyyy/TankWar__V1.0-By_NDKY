package cn.zimo.model.wall;

import cn.zimo.util.ImageUtil;

/**
 * �ݵ�
 * 
 * @author ��ī
 *
 */
public class GrassWall extends Wall {
	/**
	 * �ݵع��췽��
	 * 
	 * @param x
	 *              ��ʼ��������
	 * @param y
	 *              ��ʼ��������
	 */
	public GrassWall(int x, int y) {
		super(x, y, ImageUtil.GRASSWALL_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ�ϲݵ�ͼƬ
	}
}
