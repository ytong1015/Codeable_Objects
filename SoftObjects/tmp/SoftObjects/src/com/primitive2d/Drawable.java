package com.primitive2d;

//interface implemented by all classes that are drawable or printable by the processing applet
import processing.core.PApplet;

public interface Drawable {
	
	public void draw(PApplet parent, float strokeWeight);
	
	public void print(PApplet parent, float strokeWeight, String filename);
	

}
