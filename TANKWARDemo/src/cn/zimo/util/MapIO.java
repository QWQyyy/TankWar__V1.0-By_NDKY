package cn.zimo.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import cn.zimo.enumtype.WallType;
import cn.zimo.frame.MapEditorPanel;
import cn.zimo.model.wall.BrickWall;
import cn.zimo.model.wall.GrassWall;
import cn.zimo.model.wall.IronWall;
import cn.zimo.model.wall.RiverWall;
import cn.zimo.model.wall.Wall;

/**
 * ��ͼ���ݹ�����
 * 
 * @author WENXIN
 *
 */

public class MapIO {
	// ��ͼ�����ļ�·��
	public final static String DATA_PATH = "map/data/";
	// ��ͼԤ��ͼ·��
	public final static String IMAGE_PATH = "map/image/";
	// ��ͼ�����ļ���׺
	public final static String DATA_SUFFIX = ".map";
	// ��ͼԤ��ͼ��׺
	public final static String IMAGE_SUFFIX = ".png";
	/**
	 * ��ȡָ�����Ƶ�ͼ������ǽ�鼯��
	 *
	 * @param mapName
	 * @return
	 */
	public static List<Wall> readMap(String mapName) {
		// ������Ӧ���Ƶĵ�ͼ�ļ�
		File file = new File(DATA_PATH + mapName + DATA_SUFFIX);
		return readMap(file);// �������ط���
	}
	/**
	 * д��ǽ�鼯�ϵ�ָ���ļ���
	 * @param mapName �ļ���
	 * @return �������ط���
	 */
	public static boolean writeMap(String mapName){
		File file=new File(DATA_PATH+mapName+DATA_SUFFIX);
		return writeMap(file);
	}
	/**
	 * д��ǽ�鼯�ϵ�ָ���ļ�����
	 * @param file 
	 * 				�ļ�����
	 * @return 
	 * 				��ʵ����Ҫ����ֵ������boolean����ֻ��Ϊ�˷�����Խ���Ƿ���ȷ
	 */
	public static boolean writeMap(File file) {
		StringBuffer brickBuffer=new StringBuffer("BRICK=");//��������שǽ������
		StringBuffer grassBuffer=new StringBuffer("GRASS=");//�������вݵص�����
		StringBuffer ironBuffer=new StringBuffer("IRON=");//�������и�ש������
		StringBuffer riverBuffer=new StringBuffer("RIVER=");//�������к���������
		List<Wall> walls=MapEditorPanel.walls;//��ȡ����ͼ�༭�����������ǽ��ļ���
		//�������ϣ��ж�ÿ��ǽ������Ӧ��׷�ӵ���������ǽ�����
		for(int i=0;i<walls.size();i++) {
			Wall w=walls.get(i);//��ȡ��ÿ��ǽ�����
			if(w.isAlive()) {//���ǽ������Ǵ��״̬
				if(w instanceof BrickWall) {//�����שǽ�Ķ���
					brickBuffer.append(w.x+","+w.y+";");//׷�ӵ�שǽ��������ĺ���
				}else if(w instanceof GrassWall) {
					grassBuffer.append(w.x+","+w.y+";");
				}else if(w instanceof IronWall) {
					ironBuffer.append(w.x+","+w.y+";");
				}else if(w instanceof RiverWall) {
					riverBuffer.append(w.x+","+w.y+";");
				}
			}
		}
		BufferedWriter writer=null;//�½��ַ����������
		try {
			writer = new BufferedWriter(new FileWriter(file));//ʵ�����ַ����������
			if(!brickBuffer.toString().equals("BRICK=")) {//�ж�שǽ��������Ϊ��Ϊnull��Ҳ����ǰ����û�������StringBuffer����׷������
				writer.write(brickBuffer.toString().toCharArray());//д����Ӧ���ݵ�������
				writer.newLine();//���У�д������ǽ��
				writer.flush();//ˢ�»�������д�����ݵ��ļ�
			}
			if(!grassBuffer.toString().equals("GRASS=")) {
				//System.out.println(grassBuffer.toString());
				writer.write(grassBuffer.toString().toCharArray());
				writer.newLine();
				writer.flush();
			}
			if(!ironBuffer.toString().equals("IRON=")) {
				writer.write(ironBuffer.toString().toCharArray());
				writer.newLine();
				writer.flush();
			}
			if(!riverBuffer.toString().equals("RIVER=")) {
				writer.write(riverBuffer.toString().toCharArray());
				writer.newLine();
				writer.flush();
			}
			if(writer!=null) {//���������Ϊ�գ��ر���
				writer.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	/**
	 *
	 * ��ȡ��ͼ�ļ�������ǽ�鼯��
	 *
	 * @param file
	 *              ��ͼ�ļ�
	 * @return 
	 * 				�˵�ͼ�е�����ǽ�鼯��
	 */
	public static List<Wall> readMap(File file) {
		Properties pro = new Properties();// �������Լ�����
		List<Wall> walls = new ArrayList<>();// ������ǽ�鼯��
		try {
			pro.load(new FileInputStream(file));// ���Լ������ȡ��ͼ�ļ�
			String brickStr = (String) pro.get(WallType.BRICK.name());// ��ȡ��ͼ�ļ���שǽ�������Ե��ַ�������
			String grassStr = (String) pro.get(WallType.GRASS.name());// ��ȡ��ͼ�ļ��вݵ��������Ե��ַ�������
			String riverStr = (String) pro.get(WallType.RIVER.name());// ��ȡ��ͼ�ļ��к����������Ե��ַ�������
			String ironStr = (String) pro.get(WallType.IRON.name());// ��ȡ��ͼ�ļ�����ǽ�������Ե��ַ�������
			if (brickStr != null) {// �����ȡ��שǽ���ݲ���null
				walls.addAll(readWall(brickStr, WallType.BRICK));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
			}
			if (grassStr != null) {// �����ȡ�Ĳݵ����ݲ���null
				walls.addAll(readWall(grassStr, WallType.GRASS));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
			}
			if (riverStr != null) {// �����ȡ�ĺ������ݲ���null
				walls.addAll(readWall(riverStr, WallType.RIVER));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
			}
			if (ironStr != null) {// �����ȡ����ǽ���ݲ���null
				walls.addAll(readWall(ironStr, WallType.IRON));// �������ݣ����������н�������ǽ�鼯����ӵ���ǽ�鼯����
			}
			return walls;// ������ǽ�鼯��
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����ǽ������
	 * 
	 * @param data
	 *              ǽ�����������ַ���
	 * @param type
	 *              ǽ������
	 * @return ǽ�鼯��
	 */
	private static List<Wall> readWall(String data, WallType type) {
		String walls[] = data.split(";");// ʹ�á�;���ָ��ַ���
		Wall wall;// ����ǽ�����
		List<Wall> w = new ArrayList<>();// ����ǽ�鼯��
		switch (type) {// �ж�ǽ������
		case BRICK:// �����שǽ
			for (String wStr : walls) {// �����ָ���
				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
				wall = new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ���שǽ����
				w.add(wall);// ��������Ӵ�ǽ��
			}
			break;
		case RIVER:// ����Ǻ���
			for (String wStr : walls) {// �����ָ���
				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
				wall = new RiverWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ�����������
				w.add(wall);// ��������Ӵ�ǽ��
			}
			break;
		case GRASS:// ����ǲݵ�
			for (String wStr : walls) {// �����ָ���
				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
				wall = new GrassWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ����ݵض���
				w.add(wall);// ��������Ӵ�ǽ��
			}
			break;
		case IRON:// �������ǽ
			for (String wStr : walls) {// �����ָ���
				String axes[] = wStr.split(",");// ʹ�á�,���ָ��ַ���
				// ����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
				wall = new IronWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));// �ڴ������ϴ�����ǽ����
				w.add(wall);// ��������Ӵ�ǽ��
			}
			break;
		default:
			break;
		}
		return w;// ����ǽ�鼯��
	}
	/**
	 * ͨ��hashsetȥ��list���������ظ���Ԫ��
	 * @param list ��Ҫȥ�صļ���
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDuplicate(List list) {
		HashSet h=new HashSet(list);
		list.clear();
		list.addAll(h);
	}
}
