package com.alycarter.crabClawEngine.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class TextureTileLoader {
	private BufferedImage sheet;
	private int width;
	private int height;
	
	public TextureTileLoader(InputStream spriteSheetInputStream,int width, int height) {
		try {
			sheet =ImageIO.read(spriteSheetInputStream);
		} catch (Exception e) {
			System.err.println("error reading spriteSheet: "+spriteSheetInputStream.toString()+" details: "+e.getMessage());
		}
		this.height=height;
		this.width=width;
	}
	
	public BufferedImage getTextureSheetImage(){
		return sheet;
	}

	public BufferedImage getTile(int tile){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int sheetWidth = sheet.getWidth()/this.width;
		int yOrigin = tile / sheetWidth;
		int xOrigin = tile % sheetWidth;
		if(xOrigin<sheet.getWidth()&&yOrigin<sheet.getHeight()){
			for (int x = 0;x<width;x++){
				for (int y =0; y<height;y++){
					if((xOrigin*width)+x<sheet.getWidth()&&(yOrigin*height)+y<sheet.getHeight()){
						image.setRGB(x,y,sheet.getRGB((xOrigin*width)+x, (yOrigin*height)+y));
					}else{
						image.setRGB(x, y, new Color(0f,0f,0f,0f).getRGB());
					}
				}	
			}
		}
		return image;
	}
}
