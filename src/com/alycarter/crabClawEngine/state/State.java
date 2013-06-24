package com.alycarter.crabClawEngine.state;

import java.awt.Graphics;

public interface State {
	
	public void update();
	
	public void render(Graphics g);
}
