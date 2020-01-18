package cn.zimo.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ����ʾͼ�������
 * 
 * @author WENXIN
 *
 */
public abstract class DisplayableImage {
	/**
	 * ͼ�������
	 */
	public int x;
	/**
	 * ͼ��������
	 */
	public int y;
	/**
	 * ͼ��Ŀ�
	 */
	int width;
	/**
	 * ͼ��ĸ�
	 */
	int height;
	/**
	 * ͼ�����
	 */
	BufferedImage image;

	/**
	 * ���췽��
	 * @param x  ������
	 * @param y  ������
	 * @param width  ��
	 * @param height ��
	 */
	public DisplayableImage(int x, int y, int width, int height) {
		this.x = x;// ������
		this.y = y;// ������
		this.width = width;// ��
		this.height = height;// ��
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);// ʵ����ͼƬ
	}

	/**
	 * ���췽��
	 * @param x  ������
	 * @param y  ������
	 * @param url  ͼƬ·��
	 */
	public DisplayableImage(int x, int y, String url) {
		this.x = x;// ������
		this.y = y;// ������
		try {
			image = ImageIO.read(new File(url));// ��ȡ��·����ͼƬ����
			this.width = image.getWidth();// ��ΪͼƬ��
			this.height = image.getHeight();// ��ΪͼƬ��
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DisplayableImage() {
		
	}
	/**
	 * ��ȡͼƬ
	 * @return ����ʾ��ͼƬ
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * ����ͼƬ
	 * @param image   ����ʾ��ͼƬ
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * ����ͼƬ
	 * @param image   ����ʾ��ͼƬ
	 */
	public void setImage(String url) {
		try {
			this.image = ImageIO.read(new File(url));// ��ȡָ��λ�õ�ͼƬ
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ж��Ƿ�����ײ
	 * @param v   Ŀ��ͼƬ����
	 * @return ��������ཻ���򷵻�true�����򷵻�false
	 */
	public boolean hit(DisplayableImage v) {
		return hit(v.getRect());// ִ�����ط���
	}

	/**
	 * �ж��Ƿ�����ײ
	 * @param r   Ŀ��߽�
	 * @return ��������ཻ���򷵻�true�����򷵻�false
	 */
	public boolean hit(Rectangle r) {
		if (r == null) {// ���Ŀ��Ϊ��
			return false;// ���ز�������ײ
		}
		return getRect().intersects(r);// �������ߵı߽�����Ƿ��ཻ
	}

	/**
	 * ��ȡ�߽����
	 */
	public Rectangle getRect() {
		// ����һ��������(x,y)λ�ã����Ϊ(width, height)�ľ��α߽���󲢷���
		return new Rectangle(x, y, width, height);
	}

	/**
	 * ��ȡͼ��Ŀ�
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * ���ÿ�
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * ��ȡ��
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * ���ø�
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
