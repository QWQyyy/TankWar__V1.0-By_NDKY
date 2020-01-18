package cn.zimo.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
/**
 * ��Ч������
 * 
 *  @author WENXIN
 */

@SuppressWarnings("deprecation")
public class AudioUtil{
	/**
	 * ̹�˵�����Ч
	 */
	public static final String ADD="audio/add.wav";
	/**
	 * ��ը��Ч
	 */
	public static final String BLAST="audio/blast.wav";
	/**
	 * �����ӵ���Ч
	 */
	public static final String FIRE="audio/fire.wav";
	/**
	 * ��Ϸ������Ч
	 */
	public static final String GAMEOVER="audio/gameover.wav";
	/**
	 * �ӵ�ײ����Ч
	 */
	public static final String HIT="audio/hit.wav";
	/**
	 * ��Ϸ��ʼ��Ч
	 */
	public static final String START="audio/start.wav";
	/**
	 * ��ȡ�����б�����Ч�ļ��ϵķ���
	 * �������Ч�ʽϵͣ������𿨶٣�������ʹ��
	 */
	public static List<AudioClip> getAudios(){
		List<AudioClip> audios = new ArrayList<>();
		try {
			audios.add(Applet.newAudioClip(new File(AudioUtil.START).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioUtil.ADD).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioUtil.BLAST).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioUtil.FIRE).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioUtil.HIT).toURL()));
			audios.add(Applet.newAudioClip(new File(AudioUtil.GAMEOVER).toURL()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//audios.add(Applet.newAudioClip(AudioUtil.class.getResource(AudioUtil.BGM)));
		return audios;
	}
}