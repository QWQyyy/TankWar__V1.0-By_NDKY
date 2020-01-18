package cn.zimo.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import cn.zimo.enumtype.Direction;
import cn.zimo.enumtype.TankType;
import cn.zimo.frame.GamePanel;
import cn.zimo.model.wall.GrassWall;
import cn.zimo.model.wall.IronWall;
import cn.zimo.model.wall.Wall;
import cn.zimo.util.AudioPlayer;
import cn.zimo.util.AudioUtil;
import cn.zimo.util.ImageUtil;

/**
 * ̹��
 * 
 * @author WENXIN
 *
 */
public class Tank extends DisplayableImage {
	GamePanel gamePanel;// ��Ϸ���
	Direction direction;// �ƶ�����
	protected boolean alive = true;// �Ƿ���
	protected int speed = 3;// �ƶ��ٶ�
	private boolean attackCoolDown = true;// ������ȴ״̬
	private int attackCoolDownTime = 500;// ������ȴʱ�䣬����
	TankType type;// ̹������
	private String upImage;// �����ƶ�ʱ��ͼƬ
	private String downImage;// �����ƶ�ʱ��ͼƬ
	private String rightImage;// �����ƶ�ʱ��ͼƬ
	private String leftImage;// �����ƶ�ʱ��ͼƬ
	
	private int life=1;//������
	private int starNum=0;//�Ե��ĵ������ǣ���һ�žͼӿ칥���ٶȣ��ۻ����žͿ��Դ����ǽ
	/**
	 * ̹�˹��췽��
	 * 
	 * @param x
	 *              ��ʼ��������
	 * @param y
	 *              ��ʼ��������
	 * @param url
	 *              ͼƬ·��
	 * @param gamePanel
	 *              ��Ϸ���
	 * @param type
	 *              ̹������
	 */
	public Tank(int x, int y, String url, GamePanel gamePanel, TankType type) {
		super(x, y, url);
		this.gamePanel = gamePanel;
		this.type = type;
		direction = Direction.UP;// ��ʼ����������
		switch (type) {// �ж�̹������
		case PLAYER1:// ��������1
			upImage = ImageUtil.PLAYER1_UP_IMAGE_URL;// ��¼���1�ĸ������ͼƬ
			downImage = ImageUtil.PLAYER1_DOWN_IMAGE_URL;
			rightImage = ImageUtil.PLAYER1_RIGHT_IMAGE_URL;
			leftImage = ImageUtil.PLAYER1_LEFT_IMAGE_URL;
			break;
		case PLAYER2:// ��������2
			upImage = ImageUtil.PLAYER2_UP_IMAGE_URL;// ��¼���2�ĸ������ͼƬ
			downImage = ImageUtil.PLAYER2_DOWN_IMAGE_URL;
			rightImage = ImageUtil.PLAYER2_RIGHT_IMAGE_URL;
			leftImage = ImageUtil.PLAYER2_LEFT_IMAGE_URL;
			break;
		case BOTTANK:// ����ǵ���
			upImage = ImageUtil.BOT_UP_IMAGE_URL;// ��¼�����ĸ������ͼƬ
			downImage = ImageUtil.BOT_DOWN_IMAGE_URL;
			rightImage = ImageUtil.BOT_RIGHT_IMAGE_URL;
			leftImage = ImageUtil.BOT_LEFT_IMAGE_URL;
			break;
		}
	}
	
	@Override
	public Rectangle getRect() {
		return new Rectangle(x, y, width-3, height-3);
	}
	/**
	 * �����ƶ�
	 */
	public void leftWard() {
		if (direction != Direction.LEFT) {// ����ƶ�֮ǰ�ķ���������
			setImage(leftImage);// ��������ͼƬ
		}
		direction = Direction.LEFT;// �ƶ�������Ϊ��
		if (!hitWall(x - speed, y) && !hitTank(x - speed, y)) {// �������֮���λ�ò���ײ��ǽ���̹��
			x -= speed;// ������ݼ�
			moveToBorder();// �ж��Ƿ��ƶ������ı߽�
		}
	}

	/**
	 * �����ƶ�
	 */
	public void rightWard() {
		if (direction != Direction.RIGHT) {// ����ƶ�֮ǰ�ķ���������
			setImage(rightImage);// ��������ͼƬ
		}
		direction = Direction.RIGHT;// �ƶ�������Ϊ��
		if (!hitWall(x + speed, y) && !hitTank(x + speed, y)) {// �������֮���λ�ò���ײ��ǽ���̹��
			x += speed;// ���������
			moveToBorder();// �ж��Ƿ��ƶ������ı߽�
		}
	}

	/**
	 * �����ƶ�
	 */
	public void upWard() {
		if (direction != Direction.UP) {// ����ƶ�֮ǰ�ķ���������
			setImage(upImage);// ��������ͼƬ
		}
		direction = Direction.UP;// �ƶ�������Ϊ��
		if (!hitWall(x, y - speed) && !hitTank(x, y - speed)) {// �������֮���λ�ò���ײ��ǽ���̹��
			y -= speed;// ������ݼ�
			moveToBorder();// �ж��Ƿ��ƶ������ı߽�
		}
	}

	/**
	 * �����ƶ�
	 */
	public void downWard() {
		if (direction != Direction.DOWN) {// ����ƶ�֮ǰ�ķ���������
			setImage(downImage);// ��������ͼƬ
		}
		direction = Direction.DOWN;// �ƶ�������Ϊ��
		if (!hitWall(x, y + speed) && !hitTank(x, y + speed)) {// �������֮���λ�ò���ײ��ǽ���̹��
			y += speed;// ���������
			moveToBorder();// �ж��Ƿ��ƶ������ı߽�
		}
	}

	/**
	 * �ж��Ƿ�ײ��ǽ��
	 * 
	 * @param x
	 *              ̹�˺�����
	 * @param y
	 *              ̹��������
	 * @return ײ������ǽ�飬�򷵻�true
	 */
	private boolean hitWall(int x, int y) {
		Rectangle next = new Rectangle(x, y, width-3, height-3);// ����̹���ƶ����Ŀ������
		List<Wall> walls = gamePanel.getWalls();// ��ȡ����ǽ��
		for (int i = 0, lengh = walls.size(); i < lengh; i++) {// ��������ǽ��
			Wall w = walls.get(i);// ��ȡǽ�����
			if (w instanceof GrassWall) {// ����ǲݵ�
				continue;// ִ����һ��ѭ��
			} else if (w.hit(next)) {// ���ײ��ǽ��
				return true;// ����ײ��ǽ��
			}
		}
		return false;
	}

	/**
	 * �ж��Ƿ�ײ������̹��
	 * 
	 * @param x
	 *              ����̹�˵ĺ�����
	 * @param y
	 *              ����̹�˵�������
	 * @return ײ������̹�ˣ��򷵻�true
	 */
	 boolean hitTank(int x, int y) {
		Rectangle next = new Rectangle(x, y, width, height);// ����̹���ƶ����Ŀ������
		List<Tank> tanks = gamePanel.getTanks();// ��ȡ����̹��
		for (int i = 0, lengh = tanks.size(); i < lengh; i++) {// ��������̹��
			Tank t = tanks.get(i);// ��ȡtank����
			if (!this.equals(t)) {// �����̹����������ͬһ������
				if (t.isAlive() && t.hit(next)) {// �����̹�˴�����������ײ
					return true;// ������ײ
				}
			}
		}
		return false;
	}
	/**
	 * �ж��Ƿ�Ե��˵���
	 * @param x ������
	 * @param y ������
	 * @return ��ײ����true������false
	 */
	public final boolean hitTool() {
		Tool tool = gamePanel.getTool();
		List<Tank> tanks = gamePanel.getPlayerTanks();// ��ȡ�������̹��
		//System.out.println("hitTool()����ִ��");
		for (int i = 0, lengh = tanks.size(); i < lengh; i++) {// �����������̹��
			Tank t = tanks.get(i);// ��ȡtank����
			//System.out.println(tool.getRect().toString());
			if (t.type==TankType.PLAYER1||t.type==TankType.PLAYER2) {// �����̹�������̹��1���������̹��2
				if (t.isAlive() && t.hit(tool)&&tool.getAlive()) {// �����̹�˴����������ײ
					//System.out.println(t.hit(tool));
					//System.out.println("��ײ������");
					switch (tool.type) {//���ݵ���ѡ���Ӧ��Ч��
					case ADD_TANK:
						t.life++;
						System.out.println("̹�����ӵ���");
						break;
					case STAR:
						System.out.println("���ǵ���");
						t.starNum++;
						if(t.starNum>3) {
							t.starNum=3;
						}
						//t.addStar();
						break;
					case SPADE:
						System.out.println("���˵���");
						addSpade();
						break;
					case TIMER:
						System.out.println("��ʱ������");
						addTimer();
						break;
					case BOMB:
						System.out.println("��ը����");
						addBomb();
						break;
					case GUN:
						t.starNum=3;
						//t.addStar();
						System.out.println("��ǹ����");
						break;
					}
					//t.addToolFunction();
					tool.setAlive(false);// �õ�����ʧ
					return true;// ����true
				}
			}
		}
		return false;
	}
	/**
	 * �Ե���ը����
	 */
	private void addBomb() {
		List<Tank> botTanks=gamePanel.getBotTanks();
		for(int i=0;i<botTanks.size();i++) {
			BotTank b=(BotTank) botTanks.get(i);
			b.setAlive(false);
		}
	}

	/**
	 * ̹�˳Ե���ʱ������
	 */
	private void addTimer() {
		List<Tank> botTanks=gamePanel.getBotTanks();
		for(int i=0;i<botTanks.size();i++) {
			BotTank b=(BotTank) botTanks.get(i);
			b.setPause(true);
		}
	}

	/**
	 * ̹�˳Ե����ǵ���
	 */
	public void addStar() {
		if(starNum==1) {
			this.setAttackCoolDownTime(400);
			//System.out.println(attackCoolDownTime+","+starNum);
		}
		if(starNum==2) {
			this.setAttackCoolDownTime(300);
			//System.out.println(attackCoolDownTime+","+starNum);
		}
		if(starNum==3) {
			this.setAttackCoolDownTime(250);
			//System.out.println(attackCoolDownTime+","+starNum);
			//���ÿ��Դ����ש
			List<Bullet> bullets=gamePanel.getBullets();
			for(int i=0;i<bullets.size();i++) {
				Bullet b=bullets.get(i);
				if(b.isAlive()&&b.owner==type) {
					b.setIsHitIronWall(true);
				}
			}
		}
	}

	/**
	 * ̹�˳Ե����˵���
	 */
	private void addSpade() {
		List<Wall> walls=gamePanel.getWalls();
		// ����שǽ
		for (int a = 340; a <= 400; a += 20) {// ѭ������שǽ�ĺ�����
			for (int b = 500; b <= 560; b += 20) {// ѭ������שǽ��������
				if (a >= 360 && a <= 380 && b >= 520) {// ���ǽ������ط����غ�
					continue;// ִ����һ��ѭ��
				} else {
					walls.add(new IronWall(a, b));// ǽ�鼯������Ӹ�ש
				}
			}
		}
		
	}

	/**
	 * �ƶ������ı߽�
	 */
	protected void moveToBorder() {
		if (x < 0) {// ���̹�˺�����С��0
			x = 0;// ��̹�˺��������0
		} else if (x > gamePanel.getWidth() - width) {// ���̹�˺����곬�������Χ
			x = gamePanel.getWidth() - width;// ��̹�˺����걣�����ֵ
		}
		if (y < 0) {// ���̹��������С��0
			y = 0;// ��̹�����������0
		} else if (y > gamePanel.getHeight() - height) {// ���̹�������곬�������Χ
			y = gamePanel.getHeight() - height;// ��̹�������걣�����ֵ
		}
	}

	/**
	 * ��ȡ̹��ͷ��
	 * 
	 * @return ͷ�����
	 */
	private Point getHeadPoint() {
		Point p = new Point();// �����������Ϊͷ��
		switch (direction) {// �ж��ƶ�����
		case UP:// �������
			p.x = x + width / 2;// ͷ�������ȡx�ӿ�ȵ�һ��
			p.y = y;// ͷ��������ȡy
			break;
		case DOWN:// �������
			p.x = x + width / 2;// ͷ�������ȡx�ӿ�ȵ�һ��
			p.y = y + height;// ͷ��������ȡy�Ӹ߶ȵ�һ��
			break;
		case RIGHT:// �������
			p.x = x + width;// ͷ�������ȡx�ӿ�ȵ�һ��
			p.y = y + height / 2;// ͷ��������ȡy�Ӹ߶ȵ�һ��
			break;
		case LEFT:// �������
			p.x = x;// ͷ�������ȡx
			p.y = y + height / 2;// ͷ��������ȡy�Ӹ߶ȵ�һ��
			break;
		default:// Ĭ��
			p = null;// ͷ��Ϊnull
		}
		return p;// ����ͷ��
	}

	/**
	 * ����
	 */
	public void attack() {
		if (attackCoolDown) {// ����������������ȴ
			Point p = getHeadPoint();// ��ȡ̹��ͷ�����
			Bullet b = new Bullet(p.x - Bullet.LENGTH / 2, p.y - Bullet.LENGTH / 2, direction, gamePanel, type);// ��̹��ͷλ�÷�����̹�˽Ƕ���ͬ���ӵ�
			gamePanel.addBullet(b);// ��Ϸ�������ӵ�
			AudioPlayer fire=new AudioPlayer(AudioUtil.FIRE);
			fire.new AudioThread().start();
			new AttackCD().start();// �������ܿ�ʼ��ȴ
		}
	}

	/**
	 * ̹���Ƿ���
	 * 
	 * @return ���״̬
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * ���ô��״̬
	 * 
	 * @param alive
	 *              ���״̬
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * �����ƶ��ٶ�
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * ������ȴʱ���߳�
	 */
	private class AttackCD extends Thread {
		public void run() {// �߳�������
			attackCoolDown = false;// ������������Ϊ��ȴ״̬
			try {
				Thread.sleep(attackCoolDownTime);// ����0.5��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			attackCoolDown = true;// ���������ܽ����ȴ״̬
		}
	}

	/**
	 * ��ȡ���������Ƿ�����ȴ
	 * 
	 * @return �����Ƿ���ȴ
	 */
	public boolean isAttackCoolDown() {
		return attackCoolDown;
	}

	/**
	 * ���ù�����ȴʱ��
	 * 
	 * @param attackCoolDownTime
	 *              ��ȴ������
	 */
	public void setAttackCoolDownTime(int attackCoolDownTime) {
		this.attackCoolDownTime = attackCoolDownTime;
	}
	
	/**
	 * ��ȡ̹����������
	 * @return
	 */
	public synchronized final int getLife() {
		return this.life;
	}
	/**
	 * ����̹����������
	 */
	public final void setLife() {
		if(life>0) {
			life--;
		} else {
			return;
		}
	}
	/**
	 * ��ȡ̹������
	 * @return
	 */
	public TankType getTankType() {
		return type;
	}
}
