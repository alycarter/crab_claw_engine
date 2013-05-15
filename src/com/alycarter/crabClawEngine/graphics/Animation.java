package com.alycarter.crabClawEngine.graphics;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

import com.alycarter.crabClawEngine.Game;

public class Animation {
	
	private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	private double currentFramePointer=0;
	private double animationLength;
	private Game game;
	
	private final String name;
	
	public Animation(Game game, String name,InputStream textureFileInputStream, int tileWidth, int tileHeight, int frames, double animationLength) {
		TextureTileLoader t = new TextureTileLoader(textureFileInputStream,tileWidth,tileHeight);
		for(int i=0;i<frames;i++){
			this.frames.add(t.getTile(i));
		}
		this.game=game;
		this.name=name;
		this.animationLength=animationLength;
	}
	
	public Animation(Game game, String name,TextureTileLoader spriteSheet, int frames,double animationLength) {
		for(int i=0;i<frames;i++){
			this.frames.add(spriteSheet.getTile(i));
		}
		this.game=game;
		this.name=name;
		this.animationLength=animationLength;
	}
	
	public Animation(Game game, String name,TextureTileLoader spriteSheet, int frames, int startFrame, double animationLength) {
		for(int i=0;i<frames;i++){
			this.frames.add(spriteSheet.getTile(i+startFrame));
		}
		this.game=game;
		this.name=name;
		this.animationLength=animationLength;
	}
	
	
	public void update(){
		currentFramePointer += (game.getDeltaTime()/animationLength)*frames.size();
		if(currentFramePointer>=frames.size()){
			reset();
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return(frames.get((int) currentFramePointer));
	}
	
	public void reset(){
		currentFramePointer = 0;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCurrentFramePointer(){
		return (int) currentFramePointer;
	}
	
	public void setAniamtionLength(double length){
		if(length>0){
			animationLength=length;
		}
	}

}
