package com.primitive2d;

import java.util.Vector;

import processing.core.PApplet;

import com.datatype.Point;
import com.math.Geom;

public class Curve extends LineCollection implements Drawable{ //series of symmetrical curved lines grouped together in a single line
	private Vector<HalfCurve> halfCurves = new Vector<HalfCurve>(); //stores all of the individual parabolas
	private int resolution = 100; // resolution of each curve
	
	
	public Curve(Point start, Point end, Point control,boolean addToScreen){
		super(addToScreen);
		HalfCurve hCurve = new HalfCurve(start,end,control,resolution, addToScreen);
		this.halfCurves.add(hCurve);
		//this.addAllPoints(hCurve.getAllPoints());
		this.addPoint(start);
		this.addPoint(end);
		this.addPoint(control);
		this.addAllLinesWithoutPoints(hCurve.getAllLines());
	}

	
	public void addCurve(Point end, Point control){
		Point start = halfCurves.get(halfCurves.size()-1).end.copy();
		HalfCurve hCurve = new HalfCurve(start,end,control,resolution, addToScreen);
		//this.addAllPoints(hCurve.getAllPoints());
		this.addPoint(start);
		this.addPoint(end);
		this.addPoint(control);
		this.addAllLinesWithoutPoints(hCurve.getAllLines());
		this.halfCurves.add(hCurve);
	}
	
	//=============================DRAW AND PRINT METHODS==================================//

	@Override
	 public void draw(PApplet parent, float strokeWeight){
		for(int i =0;i<halfCurves.size();i++){
			
			this.halfCurves.get(i).draw(parent, strokeWeight);
		}
		this.drawPoints(parent);
		
	 }
	
	public void drawPoints(PApplet parent){
		for(int i =0;i<halfCurves.size();i++){
		 
		 parent.strokeWeight(4);
		 HalfCurve hCurve = this.halfCurves.get(i);
		 
		 parent.stroke(152,251,152);
		 parent.point((float)hCurve.start.getX(), (float)hCurve.start.getY());
		 parent.stroke(255,105,180);
		 parent.point((float)hCurve.end.getX(), (float)hCurve.end.getY());
		 parent.stroke(171,130,255);
		 parent.point((float)hCurve.control.getX(), (float)hCurve.control.getY());
		
		} 
		 
	 }
	
	@Override
	 public void print(PApplet parent, float strokeWeight, String filename){
		for(int i =0;i<halfCurves.size();i++){
			this.halfCurves.get(i).print(parent, strokeWeight, filename);
		}
		
	 }
	
	
	//=============================MOUSE METHODS==================================//
	public void mouseDown(double x, double y){
		double range = 2;
		Point selectionPoint = new Point(x,y);
		for(int i =0;i<halfCurves.size();i++){
			HalfCurve hC = this.halfCurves.get(i);
			if(hC.start.withinRange(range,x,y)){
				hC.start.selected=true;
				System.out.println("selected");
			}
			if(hC.end.withinRange(range,x,y)){
				hC.end.selected=true;
				System.out.println("selected");
			}
			if(hC.control.withinRange(range,x,y)){
				hC.control.selected=true;
				System.out.println("selected");
			}
			
		}
	}
	
	public void mouseUp(double x, double y){
		for(int i =0;i<halfCurves.size();i++){
			HalfCurve hC = this.halfCurves.get(i);
			if(hC.start.selected){
				hC.start.selected=false;
			}
			if(hC.end.selected){
				hC.end.selected=false;
			}
			if(hC.control.selected){
				hC.control.selected=false;
			}
			
		}
	}
	
	
	public void recCalc(double x, double y){
		
		for(int i =0;i<halfCurves.size();i++){
			HalfCurve hC = this.halfCurves.get(i);
			
			if(hC.start.selected){
				hC.start.setX(x);
				hC.start.setY(y);
				//hC.calcCurve(hC.start,hC.end, hC.control);
			}
			if(hC.end.selected){
				hC.end.setX(x);
				hC.end.setY(y);
				//hC.calcCurve(hC.start,hC.end, hC.control);
			}
			if(hC.control.selected){
				hC.control.setX(x);
				hC.control.setY(y);
				//hC.calcCurve(hC.start,hC.end, hC.control);
			}
			hC.calcCurve(hC.start,hC.end, hC.control);
			
		}
	}

}


class HalfCurve extends LineCollection{
	public Point control;
	private int resolution;
	public Point start;
	public Point end;
	
	public HalfCurve(Point start, Point end, Point control, int resolution, boolean addToScreen){
		super(addToScreen);
		
		
		
		this.resolution=resolution;
		
		this.start = start;
		this.end = end;
		calcCurve(start,end,control);
	
		
	}
	
	
	public void calcCurve(Point p1, Point p2, Point control){
		this.removeAllLines();
		this.removeAllPoints();
		Point centerPoint = Geom.getMidpoint(p1, p2);
		double theta = Geom.cartToPolar(control.getX()-centerPoint.getX(), control.getY()-centerPoint.getY())[1];
		System.out.println("theta="+theta);
		if((theta>225 && theta<=315) || (theta >45 && theta<=135)){
			calculateHorzCurve(p1,p2,control);
		}
		else{
			calculateVertCurve(p1,p2,control);
		}
	}
	
	private void calculateVertCurve(Point p1, Point p2, Point control){
		System.out.println("calc vert");
		this.control= control;
		Point dStart;
		Point dEnd;
		if(p1.getY()<=p2.getY()){
			dStart=p1;
			dEnd = p2;
		}
		else{
			dStart = p2;
			dEnd=p1;
		}

		/*if(control.getY()<=start.getY()){
			control.setY(start.getY()+1);
		}
		if(control.getY()>=end.getY()){
			control.setY(end.getY()-1);
		}*/
		
		double y1 = dEnd.getY();
		double x1 = dEnd.getX();
		double y2 = control.getY();
		double x2 = control.getX();
		double y3 = dStart.getY();
		double x3 = dStart.getX();
		
		double height = dEnd.getY()-dStart.getY();
		double width = dEnd.getX()-dStart.getX();

		double denom = (y1 - y2) * (y1 - y3) * (y2 - y3);
		double a = (y3 * (x2 - x1) + y2 * (x1 - x3) + y1 * (x3 - x2)) / denom;
		double b = (Math.pow((float) y3, 2.0) * (x1 - x2) + Math.pow((float) y2, 2.0) * (x3 - x1) + Math.pow((float) y1, 2.0) * (x2 - x3)) / denom;
		double c = (y2 * y3 * (y2 - y3) * x1 + y3 * y1 * (y3 - y1) * x2 + y1 * y2 * (y1 - y2) * x3) / denom;


		for (int i = 0; i < resolution + 1; i++) {
			double startPointY = (height / resolution) * i+dStart.getY();

			double startPointX = a * startPointY * startPointY + b * startPointY + c;
			
			this.addPoint(startPointX,startPointY);	
		}
	}
	
private void calculateHorzCurve(Point p1, Point p2, Point control){
	System.out.println("calc horz");
	Point dStart;
	Point dEnd;
	this.control= control;
	if(p1.getX()<=p2.getX()){
		dStart=p1;
		dEnd = p2;
	}
	else{
		dStart = p2;
		dEnd=p1;
	}
	
	/*if(control.getX()<=start.getX()){
		control.setX(start.getX()+1);
	}
	if(control.getX()>=end.getX()){
		control.setX(end.getX()-1);
	}*/

	double y1 = dEnd.getY();
	double x1 = dEnd.getX();
	double y2 = control.getY();
	double x2 = control.getX();
	double y3 = dStart.getY();
	double x3 = dStart.getX();
	
	double height = dEnd.getY()-dStart.getY();
	double width = dEnd.getX()-dStart.getX();

		double denom = (x1 - x2) * (x1 - x3) * (x2 - x3);
		double a = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
		double b = (Math.pow((float) x3, 2.0) * (y1 - y2) + Math.pow((float) x2, 2.0) * (y3 - y1) + Math.pow((float) x1, 2.0) * (y2 - y3)) / denom;
		double c = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;


		for (int i = 0; i < resolution + 1; i++) {
			double startPointX = (width / resolution) * i+ dStart.getX();

			double startPointY = a * startPointX * startPointX+ b * startPointX + c;
			
			this.addPoint(startPointX,startPointY);	
		}
	}
	
	@Override
	public void addPoint(double x,double y){ //copied from Polygon class
		
		Point point = new Point(x,y);
		
		int numLines = this.getAllLines().size();
		int numPoints = this.getAllPoints().size();
		if(numPoints == 0){
			this.addPoint(point);
		}
		else if(numLines==0){
			Line line = new Line(this.getPointAt(0),point);
			this.addLineWithoutPoints(line);
			this.addPoint(point);
		}
		if(numLines>0){
			Point start = this.getLineAt(numLines-1).end;
			Line line = new Line(start.copy(),point.copy());
			this.addLine(line);
		}
		
		
		
	}

	
}