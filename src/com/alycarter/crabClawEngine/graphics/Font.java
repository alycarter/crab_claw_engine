package com.alycarter.crabClawEngine.graphics;

import java.awt.Graphics;


public class Font {
	private static final String letterSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890():";
	
	public static void drawString(Graphics g, String string, TextureTileLoader typeFace, int size, int overlay, int x, int y){
		int xOffset=x;
		int yOffset=y;
		string = string.toUpperCase();
		for(int i=0;i<string.length();i++){
			if(string.charAt(i)!=' '){
				g.drawImage(typeFace.getTile(getLetterTile(string.charAt(i))), xOffset, yOffset, size, size, null);
			}
			xOffset+=size-overlay;
		}
	}
	
	private static int getLetterTile(char letter){
		int i=0;
		boolean found =false;
		while(i<letterSequence.length()&&!found){
			if(letterSequence.charAt(i)==letter){
				found=true;
			}else{
				i++;
			}
		}
		return i;
	}
	
}
