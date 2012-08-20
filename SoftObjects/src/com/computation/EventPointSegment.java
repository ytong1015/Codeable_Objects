/**
 * 
 */
package com.computation;

import java.util.Vector;

import com.primitive2d.Segment;
import com.datatype.Point;

/**
 *
 */
public class EventPointSegment {

	/**
         * 
         */
	private Vector<Segment> segments;
	private Point point;
	private boolean isIntersection;

	public EventPointSegment() {
		segments = new Vector<Segment>();
		point = new Point();
		isIntersection = false;
	}

	public EventPointSegment(Point pt) {
		this();
		this.point = pt;
		segments.clear();
	}

	public EventPointSegment(Point pt, Segment seg) {
		this();
		this.point = pt;
		segments.add(seg);
	}

	public Point getPoint() {
		return point;
	}

	public Double getX() {
		return point.getX();
	}

	public Double getY() {
		return point.getY();
	}

	public void addSegment(Segment seg) {
		segments.add(seg);
	}

	public Vector<Segment> getSegments() {
		return segments;
	}

	public void setIsIntersection() {
		isIntersection = true;
	}

	// set boolean to True if this point is reported as an intersection
	public boolean isIntersection() {
		return isIntersection;
	}

	public void printEventPoint() {
		System.out.println("EventPoint : " + point);
		for (int i = 0; i < segments.size(); i++) {
			segments.get(i).printSegment();
		}
	}

	public void addIntersectionSegment(Segment segment) {
		// TODO Auto-generated method stub

	}
}
