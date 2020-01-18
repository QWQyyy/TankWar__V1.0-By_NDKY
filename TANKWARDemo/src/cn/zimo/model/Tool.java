package cn.zimo.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;

import cn.zimo.enumtype.ToolType;
import cn.zimo.frame.GamePanel;
import cn.zimo.util.ImageUtil;
/**
 * 
 * ������
 * @author WENXIN
 *
 */
public class Tool extends DisplayableImage{
	
	private static String[] imgURL= {
			ImageUtil.ADD_TANK_URL,
			ImageUtil.BOMB_URL,
			ImageUtil.SPADE_URL,
			ImageUtil.TIMER_URL,
			ImageUtil.STAR_URL,
			ImageUtil.GUN_URL
	};
	
	private static Image [] toolImgs= {
			Toolkit.getDefaultToolkit().createImage(imgURL[0]),
			Toolkit.getDefaultToolkit().createImage(imgURL[1]),
			Toolkit.getDefaultToolkit().createImage(imgURL[2]),
			Toolkit.getDefaultToolkit().createImage(imgURL[3]),
			Toolkit.getDefaultToolkit().createImage(imgURL[4]),
			Toolkit.getDefaultToolkit().createImage(imgURL[5])
	};
	
	private int timer=0;//��ʱ��
	private int aliveTime=4500;//���ߴ���ʱ��
	private  Random r=new Random();//���������
	private static  int height=20,width=20;
	ToolType type;//��������
	private boolean alive=true;//���״̬
	
	/**
	 * ��ȡ����ʵ��
	 * @param x ���ߵ�һ�γ��ֵĺ�����
	 * @param y ���ߵ�һ�γ��ֵ�������
	 * @return һ�����߶���
	 */
	public static Tool getToolInstance(int x,int y) {
		return new Tool(x,y);
	}
	private Tool(int x,int y) {
		super(x,y,width,height);
		type=ToolType.values()[r.nextInt(6)];
	}
	
	public void changeToolType() {
		type=ToolType.values()[r.nextInt(6)];
		x=r.nextInt(550);
		y=r.nextInt(500);
		this.alive=true;
	}
	
	/**
	 * ��������
	 * @param g ����
	 */
	public void draw(Graphics g) {
		if(timer>aliveTime) {
			timer=0;
			setAlive(false);
			//changeToolType();
		}else {
			g.drawImage(toolImgs[type.ordinal()], x, y, null);
			//System.out.println("�Ƿ񻭳ɹ���"+b+",type="+type+",x="+x+",y="+y);
			timer+=GamePanel.FRESHTIME;
		}
	}
	/**
	 * ���ô��״̬
	 * @param alive ���״̬
	 */
	public void setAlive(boolean alive) {
		this.alive=alive;
		timer=0;
	}
	/**
	 * ���ߵĴ��״̬
	 * @return
	 */
	public boolean getAlive() {
		return this.alive;
	}
}
