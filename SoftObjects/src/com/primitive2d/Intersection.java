package com.primitive2d;

import java.util.Vector;
import com.datatype.Point;

/**
 * <p>
 * <b>Circle</b>
 * </p>
 * <p>
 * Circle est la classe implémentant le cercle
 * </p>
 * 
 * @author Depoyant Guillaume & Ludmann Michaël
 * 
 */
public class Intersection extends Ellipse {

	private double radius;
	protected static double RADIUS = 10F;// ////// change to STATIC

	private double x, y;
	private Vector<Segment> segments;

	/**
	 * <p>
	 * Constructeur de la classe
	 * </p>
	 * <p>
	 * Instancie un cercle
	 * </p>
	 */
	public Intersection(double x, double y, double radius) {
		super(x, y, radius);
		this.x = x;
		this.y = y;
		setFrame(x, y, radius);
		segments = new Vector<Segment>();
	}

	public Intersection(double x, double y) {
		super(x, y, RADIUS);
		this.x = x;
		this.y = y;
		setFrame(x, y, RADIUS);
		segments = new Vector<Segment>();
	}

	/**
	 * Getter du rayon
	 * 
	 * @return le rayon
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Mise à jour des coordonnées du cercle
	 * 
	 * @param x
	 *            nouvelle abscisse
	 * @param y
	 *            nouvelle ordonnée
	 * @param radius
	 *            nouveau rayon
	 */
	public void setFrame(double x, double y, double radius) {
		// setFrame(x - radius, y - radius, x + radius, y + radius);
		this.radius = radius;
	}

	public String toString() {
		return "Intersection : {" + this.x + ", " + this.y + "}";
	}

	public void printIntersection() {
		System.out.println("Intersection : " + this.x + " ; " + this.y);
	}

	/**
	 * Set the list of segments that are part of the intersection
	 * 
	 * @param list
	 */
	public void setSegments(Vector<Segment> list) {
		this.segments = list;
	}

	public Vector<Segment> getSegments() {
		return this.segments;
	}

	public Point getPoint() {
		return new Point(this.x, this.y);
	}
}