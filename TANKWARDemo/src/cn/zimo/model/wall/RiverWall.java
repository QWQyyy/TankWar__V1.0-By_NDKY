package cn.zimo.model.wall;

import cn.zimo.util.ImageUtil;

/**
 * ����
 * 
 * @author ��ī
 *
 */
public class RiverWall extends Wall {
	/**
	 * 
	 * �������췽��
	 * 
	 * @param x
	 *              ��ʼ��������
	 * @param y
	 *              ��ʼ��������
	 */
	public RiverWall(int x, int y) {
		super(x, y, ImageUtil.RIVERWALL_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ�Ϻ���ͼƬ
	}

}
