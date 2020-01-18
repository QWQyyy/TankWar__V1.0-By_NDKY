package cn.zimo.model;

import java.io.File;
import java.io.FileNotFoundException;

import cn.zimo.util.MapIO;

/**
 * �ؿ�
 * 
 * @author WENXIN
 *
 */
public class Level {
	private static int nextLevel = 1;// ��һ�ؼ�¼
	private static int previsousLevel = 1;// ��һ�ؼ�¼
	private static int count=0;// �ؿ�����
	static{
		readLevel();
	}
	/**
	 * ��ȡ�ؿ�
	 */
	private static void readLevel() {
		try {
			File f = new File(MapIO.DATA_PATH);// ������ͼ�ļ�Ŀ¼�ļ���
			if (!f.exists()) {// ������ļ��в�����
				throw new FileNotFoundException("��ͼ�ļ�ȱʧ��");// �׳��쳣
			}
			File fs[] = f.listFiles();// ��ȡ��ͼ�ļ�Ŀ¼�ļ����µ������ļ�����
			count = fs.length;// ���ļ�������Ϊ�ؿ�����
			if (count == 0) {// ���Ŀ¼��û���κ��ļ�
				throw new FileNotFoundException("��ͼ�ļ�ȱʧ��");// �׳��쳣
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��һ��
	 * 
	 * @return �ؿ���
	 */
	public static int nextLevel() {
		nextLevel++;// ��һ�ؼ�¼+1
		previsousLevel = nextLevel;// ��¼��һ�عؿ��ţ���Ϊ��һ�ص���һ��
		if (nextLevel > count) {// ����ؿ������ڹؿ�����
			nextLevel = 1;// �ӵ�һ�ؿ�ʼ
			previsousLevel=count;
		}
		return nextLevel;// ������һ�ص�ֵ
	}

	/**
	 * ��һ��
	 * 
	 * @return �ؿ���
	 */
	public static int previsousLevel() {
		return previsousLevel;// ������һ�ص�ֵ
	}
	/**
	 * ��ȡ�ؿ�����
	 * @return ��ǰ�ؿ�����
	 */
	public static int getCount() {
		readLevel();
		return count;
	}
}
