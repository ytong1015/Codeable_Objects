package com.ornament.lsys;

import java.util.*;

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
	  
	  String todo;
	  double len;
	  float theta;
	  
      Stack<TurtleStatus> marks;


	  
	  //Point turtlePos;
	  //double turtleRot;

	  public LTurtle(String s,float l, float t, Pattern parent) {
	  //public LTurtle(float l, float t, Pattern parent) {
		this.parent = parent;
		
		todo = s;
	    len = l; 
	    theta = t;

	    //turtleRot =0;
	    //turtlePos = TurtleStruct.location;
	    
	    marks = new Stack<TurtleStatus>();
	    
	  } 


	  public void render() {
		  System.out.println("render");
		    for (int i = 0; i < todo.length(); i++) {
		      char c = todo.charAt(i);
		      if (c == 'F' || c == 'G') {
		    	penDown();
		        forward(len);
		        System.out.println(TurtleStruct.location.toString());/////////////////////////////////
		      }
		      else if (c == '+') {
		    	 right(Math.toDegrees(theta));
		    	 
		    	 System.out.println("turn right");
		    	 
		      } 
		      else if (c =='-') {
		    	 left(Math.toDegrees(theta));
		    	 
		    	 System.out.println("turn left");
		    	 
		      } 
		      
		      else if (c == '[') {
		    	  
		        //turtlePos = TurtleStruct.location;
		        //turtleRot = TurtleStruct.angle;
		        
		        marks.push(new TurtleStatus(TurtleStruct.location,TurtleStruct.angle));
		        
		        //System.out.println("push");
		        
		      } 
		      else if (c == ']') {
	            //TurtleStruct.location = turtlePos;
	            //TurtleStruct.angle = turtleRot;
	            
	            TurtleStruct.location = marks.peek().getPosMark();
	            TurtleStruct.angle = marks.pop().getRotMark();

	            
		        //System.out.println("pop");
	            
		      }
		    	  
		    } 
		  }
	  
	  
	  public void setLen(double l) {
	    len = l; 
	  } 

	  public void changeLen(double percent) {
	    len *= percent; 
	  }


	  public void setToDo(String s) {
	    todo = s; 
	  }


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



