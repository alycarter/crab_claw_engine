package com.alycarter.crabClawEngine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public abstract class Game {
	
	private JFrame frame;
	private Canvas canvas;
	
	private final String title;
	
	private int windowedFrameWidth=0;
	private int windowedFrameHeight=0;
	
	private BufferedImage image;
	private int renderResolutionWidth;
	private int renderResolutionHeight;
	
	private GraphicsDevice gd;
	
	private Controls controls;
	
	private boolean running=false;
	
	private double deltaTime=0;
	private int ups=0;
	private int fps=0;
	private int frameLimit = 120;
	
	public Game(String title, int windowedFrameWidth, int windowedFrameHeight) {
		this.title=title;
		this.windowedFrameWidth=windowedFrameWidth;
		this.windowedFrameHeight=windowedFrameHeight;
		renderResolutionWidth=windowedFrameWidth;
		renderResolutionHeight=windowedFrameHeight;
		initialize();
	}
	
	public Game(String title, int windowedFrameWidth, int windowedFrameHeight, int renderResolutionWidth, int renderResolutionHeight) {
		this.title=title;
		this.windowedFrameWidth=windowedFrameWidth;
		this.windowedFrameHeight=windowedFrameHeight;
		this.renderResolutionWidth=renderResolutionWidth;
		this.renderResolutionHeight=renderResolutionHeight;
		initialize();
	}
	
	public void play(){
		new Thread(){
			public void run() {
				frame.setVisible(true);
				canvas.requestFocusInWindow();
				running = true;
				long start;
				long end;
				long timeTaken;
				long timeSinceRender=0;
				long ns =  1000000000;
				int framesThisSecond=0;
				int updatesThisSecond = 0;
				long second=0;
				while(running){
					start = System.nanoTime();
					if(frame.isFocused()){
						canvas.requestFocusInWindow();
						update();
						updatesThisSecond++;
						if((double)timeSinceRender/(double)ns>1.0/(double)frameLimit){
							render();
							timeSinceRender=0;
							framesThisSecond++;
						}
						end = System.nanoTime();
						timeTaken = end - start;
						timeSinceRender+=timeTaken;
						deltaTime = (double)timeTaken/(double)ns;
						second+=timeTaken;
						if(second>ns){
							fps=framesThisSecond;
							ups=updatesThisSecond;
							framesThisSecond=0;
							updatesThisSecond=0;
							second =0;
						}
					}else{
						controls.clearControls();
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {e.printStackTrace();}
						
					}
				}
				gd.setFullScreenWindow(null);
				Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
			}
		}.start();
	}
	
	private void render() {
		if(canvas.getBufferStrategy()==null){
			canvas.createBufferStrategy(3);
		}
		onRender(image.getGraphics());
		canvas.getBufferStrategy().getDrawGraphics().drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(),null);
		canvas.getBufferStrategy().show();
	}

	private void update() {
		controls.update();
		onUpdate();
	}

	public abstract void onUpdate();
	
	public abstract void onRender(Graphics g);

	private void initialize(){
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame= new JFrame(title); 
		canvas= new Canvas();
		frame.getContentPane().add(canvas);
		setWindowedFrameSize(windowedFrameWidth, windowedFrameHeight);
		frame.setResizable(false);
		image= new BufferedImage(renderResolutionWidth,renderResolutionHeight,BufferedImage.TYPE_INT_ARGB);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controls = new Controls(canvas);
		canvas.addKeyListener(controls);
		canvas.addMouseListener(controls);
		onInitialize();
	}
	
	public void setWindowedFrameSize(int width, int height){
		this.windowedFrameWidth=width;
		this.windowedFrameHeight=height;
		frame.setSize(width, height);
		canvas.setSize(width, height);
	}
	
	public void setRenderResolutionSize(int width, int height){
		this.renderResolutionWidth=width;
		this.renderResolutionHeight=height;
		image= new BufferedImage(renderResolutionWidth,renderResolutionHeight,BufferedImage.TYPE_INT_ARGB);
	}
	
	public int getResolutionWidth(){
		return renderResolutionWidth;
	}
	
	public int getResolutionHeight(){
		return renderResolutionHeight;
	}
	
	public int getWindowedFrameWidth(){
		return windowedFrameWidth;
	}
	
	public int getWindowedFrameHeight(){
		return windowedFrameHeight;
	}
	
	public void endGame(){
		running=false;
	}
	
	public void setFullScreen(){
		frame.setUndecorated(true);
		gd.setFullScreenWindow(frame);
		canvas.setSize(frame.getContentPane().getSize());
	}
	
	public void setWindowedMode(){
		frame.dispose();
		frame = new JFrame(title);
		frame.getContentPane().add(canvas);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		setWindowedFrameSize(windowedFrameWidth, windowedFrameHeight);
		frame.setResizable(false);
		gd.setFullScreenWindow(null);
	}
	
	public Controls getControls(){
		return controls;
	}
	
	public double getDeltaTime(){
		return deltaTime;
	}
	
	public int getFramesLastSecond(){
		return fps;
	}
	
	public int getUpdatesLastSecond(){
		return ups;
	}

	public int getFrameLimit(){
		return frameLimit;
	}
	
	public void setFrameLimit(int limit){
		if(limit<1){
			limit=1;
		}
		frameLimit=limit;
	}
	
	public abstract void onInitialize();
	
	
}
