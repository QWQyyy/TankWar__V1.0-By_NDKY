package cn.zimo.model.wall;


import cn.zimo.util.ImageUtil;

/**
 * ����
 * 
 * @author ��ī
 *
 */
public class BaseWall extends Wall {
	/**
	 * ���ع��췽��
	 * 
	 * @param x
	 *              ���غ�����
	 * @param y
	 *              ����������
	 */
	public BaseWall(int x, int y) {
		super(x, y, ImageUtil.BASE_IMAGE_URL);// ���ø��๹�췽����ʹ��Ĭ�ϻ���ͼƬ
	}

}
