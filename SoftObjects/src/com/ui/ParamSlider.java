package com.ui;

import com.primitive2d.LineCollection;

import processing.core.PApplet;

public class ParamSlider extends Slider{
	
	private LineCollection target;
	private String prop;
	


	public ParamSlider(LineCollection target, String prop) {
		super(ScreenManager.parent);
		double value=0;
		double min=0;
		double max=0;
		
		if(prop =="scaleX"){
			value=target.getScaleX();
			min=5;
			max=0;
		}
		else if(prop =="scaleY"){
			value=target.getScaleY();
			min=5;
			max=0;
		}
		else if(prop =="rotate"){
			value=target.getRotation();
			min=0;
			max=360;
		}
		else if(prop =="xPos"){
			value=target.getX();
			min = 0-target.getWidth();
			max = ScreenManager.parent.width+target.getWidth();
		}
		else if(prop =="yPos"){
			value=target.getY();
			min = 0-target.getHeight();
			max = ScreenManager.parent.height+target.getHeight();
		}
		
		this.init(100, 10, value, min, max,prop); 
		// TODO Auto-generated constructor stub
		this.target=target;
		this.prop = prop;
	}
	
	private void adjustProp(){
		
		/* available properties are:
		 * scale
		 * scaleX
		 * scaleY
		 * rotation
		 * xPosition
		 * yPosition
		 * 
		 */
		
		
		int propCase =0;
		
		if(prop =="scaleX"){
			propCase =2;
			this.target.scaleX(this.getSliderValue());
		}
		else if(prop =="scaleY"){
			propCase =3;
			this.target.scaleY(this.getSliderValue());
		}
		else if(prop =="rotate"){
			propCase =4;
			this.target.rotate(this.getSliderValue());
		}
		else if(prop =="xPos"){
			propCase =5;
			this.target.moveTo(this.getSliderValue(),this.target.getOrigin().getY());
		}
		else if(prop =="yPos"){
			propCase =6;
			this.target.moveTo(this.target.getOrigin().getX(),this.getSliderValue());
		}
		
		
		
	}

}
