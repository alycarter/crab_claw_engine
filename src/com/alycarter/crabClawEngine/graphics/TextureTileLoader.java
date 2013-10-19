package com.alycarter.crabClawEngine.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TextureTileLoader {
	private BufferedImage sheet;
	private int width;
	private int height;
	private ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	public TextureTileLoader(InputStream spriteSheetInputStream,int width, int height) {
		try {
			sheet =ImageIO.read(spriteSheetInputStream);
		} catch (Exception e) {
			System.err.println("error reading spriteSheet: "+spriteSheetInputStream.toString()+" details: "+e.getMessage());
		}
		this.height=height;
		this.width=width;
		int i=0;
		while(createTile(i)!=null){
			tiles.add(createTile(i));
			i++;
		}
	}
	
	public BufferedImage getTextureSheetImage(){
		return sheet;
	}

	private BufferedImage createTile(int tile){
		int sheetWidth = sheet.getWidth()/width;
		int yOrigin = tile / sheetWidth;
		int xOrigin = tile % sheetWidth;
		if(xOrigin<sheet.getWidth()&&yOrigin<sheet.getHeight()){
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for (int x = 0;x<width;x++){
				for (int y =0; y<height;y++){
					if((xOrigin*width)+x<sheet.getWidth()&&(yOrigin*height)+y<sheet.getHeight()){
						image.setRGB(x,y,sheet.getRGB((xOrigin*width)+x, (yOrigin*height)+y));
					}else{
						image.setRGB(x, y, new Color(0f,0f,0f,0f).getRGB());
					}
				}	
			}
			return image;
		}else{
			return null;
		}
	}
	
	public BufferedImage getTile(int tile){
		if(tile>=0 && tile<tiles.size()){
			return tiles.get(tile);
		}else{
			return null;
		}
	}
}
