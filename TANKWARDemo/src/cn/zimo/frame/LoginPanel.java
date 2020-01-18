package cn.zimo.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cn.zimo.enumtype.GameType;
import cn.zimo.util.ImageUtil;

/**
 * ��½��壨ѡ����Ϸģʽ��
 * 
 * @author WENIXN
 *
 */
public class LoginPanel extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameType type;// ��Ϸģʽ
	private Image backgroud;// ����ͼƬ
	private Image tank;// ̹��ͼ��
	private int y1 = 270, y2 = 330, y3=390, y4=450;// ̹��ͼ���ѡ����ĸ�Y����
	private int tankY = 270;// ̹��ͼ��Y����
	private MainFrame frame;
	
	/**
	 * ��½��幹�췽��
	 * 
	 * @param frame
	 *              ������
	 */
	public LoginPanel(MainFrame frame) {
		this.frame=frame;
		addListener();// ����������
//		//this.requestFocusInWindow();
//		//Ĭ�ϻ�ȡ������
//		if(!this.isFocusable()) {
//			//System.out.println("���������ȡ������");
//			this.setFocusable(true);
//		}
//		if(!this.isFocusOwner()) {
//			//System.out.println("���û�ȡ������");
//			this.requestFocusInWindow();
//		}
		try {
			backgroud = ImageIO.read(new File(ImageUtil.LOGIN_BACKGROUD_IMAGE_URL));// ��ȡ����ͼƬ
			tank = ImageIO.read(new File(ImageUtil.PLAYER1_RIGHT_IMAGE_URL));// ��ȡ̹��ͼ��
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��д��ͼ����
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(backgroud, 0, 0, getWidth(), getHeight(), this);// ���Ʊ���ͼƬ�������������
		Font font = new Font("����", Font.BOLD, 35);// ��������
		g.setFont(font);// ʹ������
		g.setColor(Color.BLACK);// ʹ�ú�ɫ
		g.drawString("������Ϸģʽ", 300, 300);// ���Ƶ�һ������
		g.drawString("˫����Ϸģʽ", 300, 360);// ���Ƶڶ�������
		g.drawString("Ԥ���ؿ���ͼ", 300, 420);// ���Ƶ���������
		g.drawString("�Զ����ͼģʽ", 300, 480);// ���Ƶ���������
		
		g.drawImage(tank, 260, tankY, this);// ����̹��ͼ��
	}

	/**
	 * ��ת�ؿ����
	 */
	private void gotoLevelPanel() {
		frame.removeKeyListener(this);// ������ɾ�����̼���
		frame.setPanel(new LevelPanel(1, frame, type));// ��������ת���ؿ����
	}

	/**
	 * ����������
	 */
	private void addListener() {
		frame.addKeyListener(this);// ������������̼�����������ʵ��KeyListener�ӿ�
	}

	/**
	 * ����������ʱ
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();// ��ȡ���µİ���ֵ
		switch (code) {// �жϰ���ֵ
		case KeyEvent.VK_UP:// ������µ��ǡ�����
			if(tankY == y1) {
				tankY = y4;
			}else if(tankY == y4) {
				tankY = y3;
			}else if(tankY == y3){
				tankY = y2;
			}else if(tankY == y2){
				tankY=y1;
			}
			repaint();// ��������֮����Ҫ���»�ͼ
			break;
		case KeyEvent.VK_DOWN:// ������µ��ǡ�����
			if (tankY == y4) {
				tankY = y1;
			}else if(tankY ==y1){
				tankY = y2;
			}else if(tankY == y2){
				tankY = y3;
			}else if(tankY == y3){
				tankY=y4;
			}
			repaint();// ��������֮����Ҫ���»�ͼ
			break;
		case KeyEvent.VK_ENTER:// ������µ��ǡ�Enter��
			if (tankY == y1) {// ���̹��ͼ���ڵ�һ��λ��
				type=GameType.ONE_PLAYER;
				gotoLevelPanel();// ��ת�ؿ����
			}
			if(tankY == y2){
				type = GameType.TWO_PLAYER;// ��ϷģʽΪ˫��ģʽ
				gotoLevelPanel();// ��ת�ؿ����
			}
			if(tankY == y4){
				type = null;
				frame.removeKeyListener(this);//����һ��Ҫ�Ѽ��̼����Ƴ�����������Զ�����
				frame.setPanel(new MapEditorPanel(frame));
			}
			if(tankY == y3)	{
				type=null;
				frame.removeKeyListener(this);
				frame.setPanel(new MapPreViewPanel(frame));
			}
		}
		
	}

	/**
	 * ����̧��ʱ
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// ��ʵ�ִ˷�����������ɾ��
	}

	/**
	 * ����ĳ�����¼�
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// ��ʵ�ִ˷�����������ɾ��
	}
	
}
