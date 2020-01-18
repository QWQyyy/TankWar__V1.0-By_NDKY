package cn.zimo.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import cn.zimo.model.Level;
import cn.zimo.model.Map;
import cn.zimo.model.wall.BaseWall;
import cn.zimo.model.wall.Wall;

/**
 * �ؿ���ͼԤ�����
 * 
 * 
 * @author WENXIN
 *
 */
public class MapPreViewPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int level=1;
	private List<Wall> walls=Map.getWalls();//new ArrayList<>();
	private BaseWall base;
	private Graphics gra;
	private int count=Level.getCount();
	private MainFrame frame;

	
	public MapPreViewPanel(final MainFrame frame) {
		this.frame=frame;

		base=new BaseWall(360, 520);
		
		//��ʼ����ͼ
		initWalls();
				
		JButton levelReduce=new JButton("��һ��");
		levelReduce.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				level--;
				if(level==0) {
					level=count;
				}
				initWalls();
				repaint();
			}
		});
		JButton levelPlus=new JButton("��һ��");
		levelPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				level++;
				if(level>count) {
					level=1;
				}
				//System.out.println(level);
				initWalls();
				repaint();
				//System.out.println("�ؿ���"+level+"��ǽ��������"+walls.size());
			}
		});	
		JButton back=new JButton("����");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.requestFocus();
				gotoLoginPanel();
			}
		});
		this.add(back);
		this.add(levelReduce);
		this.add(levelPlus);
	}
	private void gotoLoginPanel() {
		frame.setPanel(new LoginPanel(frame));
	}
	@Override
	public void paint(Graphics g) {
		super.setBackground(Color.BLACK);
		super.paint(g);
		gra = g;
		
		g.setColor(Color.ORANGE);
		g.drawString("��ǰ�ؿ���"+level, 0, 12);
		g.drawString("�ؿ�������"+count, 0, 24);


		
		//������ͼ
		paintWalls();
	}
	/**
	 * ����ǽ��
	 */
	private void paintWalls() {
		for (int i = 0; i < walls.size(); i++) {// ѭ������ǽ�鼯��
			Wall w = walls.get(i);// ��ȡǽ�����
			if(w.x>=760) {
				w.setAlive(false);
			}
			if (w.isAlive()) {// ���ǽ����Ч
				gra.drawImage(w.getImage(), w.x, w.y, this);// ����ǽ��
			} else {// ���ǽ����Ч
				walls.remove(i);// �ڼ����Єh����ǽ��
				i--;// ѭ������-1����֤�´�ѭ��i��ֵ������i+1���Ա���Ч�������ϣ��ҷ�ֹ�±�Խ��
			}
		}
	}
	public void initWalls() {
		Map.getMap(level);// ��ȡ��ǰ�ؿ��ĵ�ͼ����
		//walls=map.getWalls();
		//walls.addAll(map.getWalls());// ǽ�鼯����ӵ�ǰ��ͼ������ǽ��
		walls.add(base);// ǽ�鼯����ӻ���
	}
	
}
