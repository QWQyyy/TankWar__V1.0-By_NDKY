package cn.zimo.model;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import cn.zimo.enumtype.Direction;
import cn.zimo.enumtype.TankType;
import cn.zimo.frame.GamePanel;
import cn.zimo.util.ImageUtil;

/**
 * ����̹����
 * Ϊ�˿�����Ϸ�Ѷȣ�����̹����������ƶ��ĸ���Ӧ�ÿ���Ϊ�����������С
 * ���ҵ���̹�����ÿ���ƶ��ķ��򶼲���ͬ������ĳһ�����������ƶ���ʱ��ҲӦ�ò���ͬ
 * 
 * @author WENXIN
 *
 */
public class BotTank extends Tank {
	private Random random = new Random();// ���������
	private Direction dir;// �ƶ�����
	private int freshTime = GamePanel.FRESHTIME;// ˢ��ʱ�䣬������Ϸ����ˢ��ʱ��
	private int moveTimer = 0;// �ƶ���ʱ��

	private boolean pause=false;//����̹����ͣ״̬
	/**
	 * ��ȡ����̹����ͣ״̬
	 */
	public boolean isPause() {
		return pause;
	}
	/**
	 * ���õ���̹����ͣ״̬
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * 
	 * ����̹�˹��췽��
	 * 
	 * @param x
	 *             ������
	 * @param y
	 *             ������
	 * @param gamePanel
	 *             ��Ϸ���
	 * @param type
	 *             ̹������
	 */

	public BotTank(int x, int y, GamePanel gamePanel, TankType type) {
		super(x, y, ImageUtil.BOT_DOWN_IMAGE_URL, gamePanel, type);// ���ø��๹�췽����ʹ��Ĭ�ϻ�����̹��ͼƬ
		dir = Direction.DOWN;// �ƶ�����Ĭ������
		setAttackCoolDownTime(1000);// ���ù�����ȴʱ��
		// setSpeed(2);//���õ���̹���ƶ��ٶ�
	}

	/**
	 * ����̹��չ���ж��ķ���
	 */
	public void go(){
		if(isAttackCoolDown()){// �����ȴ�������Ϳ��Թ���
			attack();// ����
		}
		if(moveTimer>random.nextInt(1000)+500){// ����ƶ���ʱ�����������0.5~1.5�룬�����һ������
			dir=randomDirection();
			moveTimer=0;// ���ü�ʱ��
		}else{
			moveTimer+=freshTime;// ��ʱ������ˢ��ʱ�����
		}
		switch (dir) {// ���ݷ���ѡ�����ĸ������ƶ�
		case UP:
			upWard();
			break;
		case DOWN:
			downWard();
			break;
		case LEFT:
			leftWard();
			break;
		case RIGHT:
			rightWard();
			break;
		}	
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	private Direction randomDirection() {
		Direction [] dirs=Direction.values();// ��ȡ�������ö��ֵ
		Direction oldDir=dir;// ����ԭ���ķ���
		Direction newDir=dirs[random.nextInt(4)];
		if(oldDir==newDir||newDir==Direction.UP) {// �������̹��ԭ���ķ���ͱ�������ķ�����ͬ�����ߵ���̹���µķ��������ϣ���ô�������һ�η���
			return dirs[random.nextInt(4)];
		}
		return newDir;
	}

	/**
	 * ��д�ƶ������ı߽��¼�
	 */
	protected void moveToBorder() {
		if (x < 0) {// ���̹�˺�����С��0
			x = 0;// ��̹�˺��������0
			dir = randomDirection();// ��������ƶ�����
		} else if (x > gamePanel.getWidth() - width) {// ���̹�˺����곬�������Χ
			x = gamePanel.getWidth() - width;// ��̹�˺����걣�����ֵ
			dir = randomDirection();// ��������ƶ�����
		}
		if (y < 0) {// ���̹��������С��0
			y = 0;// ��̹�����������0
			dir = randomDirection();// ��������ƶ�����
		} else if (y > gamePanel.getHeight() - height) {// ���̹�������곬�������Χ
			y = gamePanel.getHeight() - height;// ��̹�������걣�����ֵ
			dir = randomDirection();// ��������ƶ�����
		}
	}

	/**
	 * ��д����̹�˷���
	 */
	@Override
	boolean hitTank(int x, int y) {
		Rectangle next = new Rectangle(x, y, width, height);// ������ײλ��
		List<Tank> tanks = gamePanel.getTanks();// ��ȡ����̹�˼���
		for (int i = 0, lengh = tanks.size(); i < lengh; i++) {// ����̹�˼���
			Tank t = tanks.get(i);// ��ȡ̹�˶���
			if (!this.equals(t)) {// �����̹�˶����뱾������ͬһ��
				if (t.isAlive() && t.hit(next)) {// ����Է�˵�Ǵ��ģ������뱾��������ײ
					if (t instanceof BotTank) {// ����Է�Ҳ�ǵ���
						dir = randomDirection();// ��������ƶ�����
					}
					return true;// ������ײ
				}
			}
		}
		return false;// δ������ײ
	}

	/**
	 * ��д����������ÿ�ι���ֻ��4%���ʻᴥ�����๥������
	 */
	@Override
	public void attack() {
		int rnum = random.nextInt(100);// �������������Χ��0-99
		if (rnum < 4) {// ��������С��4
			super.attack();// ִ�и��๥������
		}
	}
}
