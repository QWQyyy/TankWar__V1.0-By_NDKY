package cn.zimo.model;

import java.util.ArrayList;
import java.util.List;

import cn.zimo.model.wall.BrickWall;
import cn.zimo.model.wall.Wall;
import cn.zimo.util.MapIO;

/**
 * ��ͼ�࣬����ģʽ
 * 
 * @author WENXIN
 *
 */
public class Map {
	public static List<Wall> walls = new ArrayList<>();// ��ͼ������ǽ��ļ���

	/**
	 * ˽�й��췽��
	 */
	private Map() {

	}

	/**
	 * ��ȡ��ͼ����
	 * 
	 * @param level
	 *              �ؿ���
	 * @return ָ���ؿ��ĵ�ͼ����
	 */
	public static Map getMap(String level) {
		walls.clear();// ǽ�鼯�����
		walls.addAll(MapIO.readMap(level));// ��ȡָ���ؿ���ǽ�鼯��
		// ����שǽ
		for (int a = 340; a <= 400; a += 20) {// ѭ������שǽ�ĺ�����
			for (int b = 500; b <= 560; b += 20) {// ѭ������שǽ��������
				if (a >= 360 && a <= 380 && b >= 520) {// ���ǽ������ط����غ�
					continue;// ִ����һ��ѭ��
				} else {
					walls.add(new BrickWall(a, b));// ǽ�鼯�������ǽ��
				}
			}
		}
		return new Map();// �����µĵ�ͼ����
	}

	/**
	 * ��ȡ��ͼ����
	 * 
	 * @param level
	 *              �ؿ���
	 * @return ָ���ؿ��ĵ�ͼ����
	 */
	public static Map getMap(int level) {
		return getMap(String.valueOf(level));// �������ط���
	}

	/**
	 * ��ȡ��ͼ�����е�����ǽ��
	 * 
	 * @return ǽ�鼯��
	 */
	public static List<Wall> getWalls() {
		return walls;
	}

}
