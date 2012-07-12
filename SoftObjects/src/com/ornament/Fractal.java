package com.ornament;

import java.util.Vector;

import processing.core.PApplet;

import com.datatype.Point;
import com.primitive2d.Ellipse;
import com.primitive2d.Line;
import com.primitive2d.Polygon;
import com.primitive2d.Drawable;


public class Fractal extends Pattern{


    public Point a = new Point(0,0);
    public Point b = new Point(800,500);
	private int levelLimit = 5;
	private double tri_Angle = 60;
	private String seedShape;
	private double width = 500;
	private double height = 500;

	public Fractal (Point start, Point end){
		super();
		a = start.copy();
		b=end.copy();
	
	}
	
	public Fractal (double startX, double startY, double endX, double endY ){
		super();
		a = new Point (startX, startY);
		b=new Point (endX, endY);
	
	}
	
	public void setAngle(double angle){
		this.tri_Angle = angle;
	}
	
	public void setLevelLimit(int setLevel){
		levelLimit = setLevel;
		}

	public void setSeedShape(String setShape){
		  seedShape = setShape;
		}


	public void generate(){
		Line line = new Line(a,b);//create the base line
		this.addLine(line);// store the line
		for(int i = 0; i < levelLimit ; i++)
		iterate(this.getAllLines());
	}

	public void iterate(Vector before){
		Vector now = new Vector();
		for(int i = 0; i < before.size(); i++){
			Line l = this.getLineAt(i);
			if(seedShape == "rectangle"){
			now.addAll(lineUp(recPoints(l)));
			}else{
			now.addAll(lineUp(triPoints(l)));
			}
		}
		this.setAllLines(now);
		//return now;
	}

	//helper method of recursion()
	//Calculate the koch points (done for us by the line object)

	// 5 koch points for triangle seed shape
	public Vector triPoints(Line l){
	      Vector now = new Vector();    //Create emtpy list
	      now.add(start(l));                 
	      now.add(kochleft(l));
	      now.add(kochmiddle(l));
	      now.add(kochright(l));
	      now.add(end(l));
	      return now;
	}

	// 9 koch points for rectangular seed shape
	public Vector recPoints(Line l){
	      Vector now = new Vector();    //Create emtpy list
	      now.add(start(l));                 
	      now.add(recLeftDown(l));
	      now.add(recLeftUp(l));
	      now.add(recMidUp(l));
	      now.add(recMidMid(l));
	      now.add(recMidDown(l));
	      now.add(recRightDown(l));
	      now.add(recRightUp(l));
	      now.add(end(l));
	      return now;
	}



	// Make line segments between all the points in the Vector
	Vector lineUp(Vector dots){
	   Vector orig = new Vector();  
	  for(int i = 0; i < dots.size()-1;i++){
	    orig.add(new Line((Point)dots.get(i),(Point)dots.get(i+1)));
	  }
	  return orig;
	}






	public Point start(Line l) {
	    return l.start.copy();
	  }

	public Point end(Line l) {
	    return l.end.copy();
	  }

	  // This is easgetY(), just 1/3 of the wagetY()
	public Point kochleft(Line l)
	  {
	    float x = (float)(l.start.getX() + (l.end.getX() - l.start.getX()) / 3f);
	    float y = (float)(l.start.getY() + (l.end.getY() - l.start.getY()) / 3f);
	    return new Point(x,y);
	  }    

	  // More complicated, have to use a little trig to figure out where this point is!
	public Point kochmiddle(Line l)
	  {
	    float x = (float)(l.start.getX() + 0.5f * (l.end.getX() -  l.start.getX()) + (Math.sin(Math.toRadians(tri_Angle))*(l.end.getY()-l.start.getY())) / 3f);
	    float y = (float)(l.start.getY() + 0.5f * (l.end.getY() - l.start.getY()) - (Math.sin(Math.toRadians(tri_Angle))*(l.end.getX()-l.start.getX())) / 3f);
	    return new Point(x,y);
	  }    
	  // EasgetY(), just 2/3 of the wagetY()
	public Point kochright(Line l)
	  {
	    float x = (float)(l.start.getX() + 2*(l.end.getX() - l.start.getX()) / 3f);
	    float y = (float)(l.start.getY() + 2*(l.end.getY() - l.start.getY()) / 3f);
	    return new Point(x,y);
	  }    

	  // Points for rectangle shape
	public Point recLeftDown(Line l){
	    float x = (float)(l.start.getX() + 0.25f * (l.end.getX() - l.start.getX()));
	    float y = (float)(l.start.getY() + 0.25f * (l.end.getY() - l.start.getY()));
	    return new Point(x,y);
	  }

	 public Point recLeftUp(Line l){
	    float x = (float)(l.start.getX() + 0.25f * (l.end.getX() - l.start.getX()) - 0.25f * (l.end.getY() - l.start.getY()));
	    float y = (float)(l.start.getY() + 0.25f * (l.end.getY() - l.start.getY()) + 0.25f * (l.end.getX() - l.start.getX()));
	    return new Point(x,y);
	  }

	  public Point recMidUp(Line l){
	    float x = (float)(l.start.getX() + 0.5f * (l.end.getX() - l.start.getX()) - 0.25f * (l.end.getY() - l.start.getY()));
	    float y = (float)(l.start.getY() + 0.5f * (l.end.getY() - l.start.getY()) + 0.25f * (l.end.getX() - l.start.getX()));
	    return new Point(x,y);
	  }



	  public Point recMidMid(Line l){
	    float x = (float)(l.start.getX() + 0.5f * (l.end.getX() - l.start.getX()));
	    float y = (float)(l.start.getY() + 0.5f * (l.end.getY() - l.start.getY()));
	    return new Point(x,y);
	  }


	  public Point recMidDown(Line l){
	    float x = (float)(l.start.getX() + 0.5f * (l.end.getX() - l.start.getX()) + 0.25f * (l.end.getY() - l.start.getY()));
	    float y = (float)(l.start.getY() + 0.5f * (l.end.getY() - l.start.getY()) - 0.25f * (l.end.getX() - l.start.getX()));
	    return new Point(x,y);
	  }

	  public Point recRightDown(Line l){
	    float x = (float)(l.start.getX() + 0.75f * (l.end.getX() - l.start.getX()) + 0.25f * (l.end.getY() - l.start.getY()));
	    float y = (float)(l.start.getY() + 0.75f * (l.end.getY() - l.start.getY()) - 0.25f * (l.end.getX() - l.start.getX()));
	    return new Point(x,y);
	  }

	   public Point recRightUp(Line l){
	    float x = (float)(l.start.getX() + 0.75f * (l.end.getX() - l.start.getX()));
	    float y = (float)(l.start.getY() + 0.75f * (l.end.getY() - l.start.getY()));
	    return new Point(x,y);
	  }






}