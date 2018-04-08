package com.bm.csxy.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;

import java.io.IOException;

/**
 * 
 * @Title:  MusicPlayer.java
 * @Copyright:  蓝色互动 Co., Ltd. Copyright 2015,  All rights reserved
 * @Description:  TODO<媒体播放音乐类>
 * @author:  Yizh
 * @data:  2015-7-7 下午5:19:47
 */
public class MusicPlayer implements OnBufferingUpdateListener, OnPreparedListener {

	/**
	 * 媒体播放器
	 */
	public MediaPlayer mediaPlayer;
	public Context context;
	
	public MusicPlayer(Context context){
		try {
			this.context=context;
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
//			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//				@Override
//				public void onCompletion(MediaPlayer mp) {
//					playData("m1.mp3");
//				}
//			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放音乐
	 */
	public  void play(){
		mediaPlayer.start();
	}
	
	/**
	 * 设置url地址
	 */
	public void playUrl(String url){
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); // 设置数据源
			mediaPlayer.prepareAsync(); // prepare自动播放
		} catch(Exception e){	
			Log.e("csxy","--"+e.getMessage());
		}
	}

	public void setMusicData(String name){
		try {
			if (mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
			}

			AssetManager am = context.getAssets();
			AssetFileDescriptor afd = am.openFd(name);
			mediaPlayer.reset();
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			mediaPlayer.prepareAsync();

		}catch (IOException e) {
//			ToastMgr.show(e.getMessage()+"");
			System.out.println(e.getMessage()+"");
		}
	}

	public void musicPlay(){
			mediaPlayer.start();

	}
	public void musicStop(){
		mediaPlayer.pause();
	}

	/**
	 * 设置播放
	 */
	public void  playData(String  name){

		 try {
			 if(mediaPlayer==null){
				 mediaPlayer=new MediaPlayer();
			 }

			AssetManager am = context.getAssets();
			AssetFileDescriptor afd = am.openFd(name);
			mediaPlayer.reset();
			mediaPlayer.setDataSource(afd.getFileDescriptor() , afd.getStartOffset(), afd.getLength());
			mediaPlayer.prepare();
			 mediaPlayer.start();
//			 mediaPlayer.setLooping(true);
		} catch (IOException e) {
//			ToastMgr.show(e.getMessage()+"");
			System.out.println(e.getMessage()+"");
		} 
	}
	
	/**
	 * 暂停
	 */
	public void pause() {
		mediaPlayer.pause();
	}
	
	
	
	/**
	 * 停止
	 */
	public void stop(){
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
//		mp.start();
		if(listener!=null){
			listener.getTime(mp.getDuration());
		}
	}

	public interface GetTimeListener{
		void getTime(int time);
	}

	private GetTimeListener listener;
	public  void setGetTimeListener(GetTimeListener listener){
		this.listener=listener;
	}



}
