package com.ornament.lsys;

import java.util.Vector;

import processing.core.PApplet;
import com.datatype.Point;
import com.ornament.Pattern;
import com.primitive2d.Ellipse;
import com.primitive2d.Line;
import com.primitive2d.LineCollection;
import com.primitive2d.Polygon;
import com.primitive2d.Drawable;
import com.primitive2d.Turtle;
import com.primitive2d.TurtleStruct;


public class LTurtle implements Turtle{
	
	  Pattern parent;

	  double len;
	  float theta;
	  
	  //Point position;
	  //double rotation;
	  
	  Point turtlePos;
	  double turtleRot;

	  public LTurtle(float l, float t, Pattern parent) {
		this.parent = parent;
		
	    len = l; 
	    theta = t;

	    
	    turtleRot =0;
	    turtlePos = TurtleStruct.location;
	    
	  } 


	  public void render(String todo) {
		  System.out.println("render");
		    for (int i = 0; i < todo.length(); i++) {
		      char c = todo.charAt(i);
		      if (c == 'F' || c == 'G') {
		    	TurtleStruct.pen = true;
		        forward(len);
		        TurtleStruct.location = new Point(len,0);
		        TurtleStruct.pen = false;
		        forward(len);
		        
		      } 
		      else if (c == '+') {
		    	 TurtleStruct.angle += theta;
		    	 right(Math.toDegrees(theta));
		    	 
		      } 
		      else if (c == '-') {
		    	 TurtleStruct.angle += -theta;
		    	 left(Math.toDegrees(theta));
		      } 
		      
		      else if (c == '[') {
		        turtlePos = TurtleStruct.location;
		        turtleRot = TurtleStruct.angle;
		      } 
		      else if (c == ']') {
	            TurtleStruct.location = turtlePos;
	            TurtleStruct.angle = turtleRot;
		      }
		    	  
		    } 
		  }
	  
	  
	  void setLen(double l) {
	    len = l; 
	  } 

	  void changeLen(double percent) {
	    len *= percent; 
	  }

	  /*void setToDo(String s) {
	    todo = s; 
	  }*/


		@Override
		public void left(double angle) {
			TurtleStruct.angle-=angle;
			/*if(TurtleStruct.angle<0){
				TurtleStruct.angle = 360;
			}*/
			
		}

		@Override
		public void right(double angle) {
			// TODO Auto-generated method stub
			TurtleStruct.angle+=angle;
			/*if(TurtleStruct.angle>360){
				TurtleStruct.angle = 0;
			}*/
		}

		@Override
		public void forward(double dist) {
			Line newLine = new Line(TurtleStruct.location.copy(), dist, TurtleStruct.angle);
			if(TurtleStruct.pen){
				
				parent.addLine(newLine);
			}
			TurtleStruct.location = newLine.end;
			
		}
		
		

		@Override
		public void back(double dist) {
			Line newLine = new Line(TurtleStruct.location.copy(), -dist, TurtleStruct.angle);
			if(TurtleStruct.pen){
				
				parent.addLine(newLine);
			}
			TurtleStruct.location = newLine.end;
			
		}

		@Override
		public void penUp() {
			TurtleStruct.pen=false;
			// TODO Auto-generated method stub
			
		}

		@Override
		public void penDown() {
			TurtleStruct.pen=true;
			// TODO Auto-generated method stub
			
		}
		

	}



