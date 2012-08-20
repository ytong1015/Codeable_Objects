package com.primitive2d;

import java.util.Vector;

import com.computation.Key;
import com.computation.RedBlackNode;
import com.ornament.Pattern;
import com.datatype.Point;

/**
 * <p>
 * <b>Segment</b>
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author Depoyant Guillaume & Ludmann Micha‘l
 * 
 */
public class Segment extends Pattern implements Key {

	private java.awt.Polygon segment;
	private double[] xpoints;
	private double[] ypoints;
	private Point upperEndpoint;
	private Point lowerEndpoint;
	private Point leftEndpoint;
	private Point rightEndpoint;
	private double slope;
	private double originOrdinate;
	public static final double MAX_SLOPE = 99999999999999.906F;
	private double value;
	private RedBlackNode node;
	private int id;
	private boolean isVertical = false;
	private boolean isHorizontal = false;
	protected double ERROR_ALLOWED = 1.0001E-002F;
	private Vector<Point> split;

	public Segment() {
		segment = new java.awt.Polygon();
		xpoints = null;
		ypoints = null;
		upperEndpoint = new Point(0, 0);
		lowerEndpoint = new Point(0, 0);
		rightEndpoint = new Point(0, 0);
		leftEndpoint = new Point(0, 0);
		value = -1;
		id = -1;
		setSplit(new Vector<Point>());
	}

	public Segment(double[] xpoints, double[] ypoints, int npoints) {
		this();
		setXpoints(xpoints);
		setYpoints(ypoints);

		// Here we need to register the vertices as integer for the drawing part
		int[] xpointsInt = new int[npoints], ypointsInt = new int[npoints];
		for (int i = 0; i < npoints; i++) {
			xpointsInt[i] = (int) Double.doubleToLongBits(xpoints[i]);
			ypointsInt[i] = (int) Double.doubleToLongBits(ypoints[i]);

		}
		segment = new java.awt.Polygon(xpointsInt, ypointsInt, npoints);
		computeEndpoints();
	}

	public Segment(Vector<double[]> points) {
		this();
		setSegment(points);
	}

	public Segment(Vector<double[]> points, int id) {
		this();
		this.id = id;
		setSegment(points);
	}

	public void setSegment(double[] xpoints, double[] ypoints, int npoints) {
		segment.reset();
		setXpoints(null);
		setYpoints(null);
		for (int i = 0; i < npoints; i++) {
			segment.addPoint((int) xpoints[i], (int) ypoints[i]);
		}
		setXpoints(xpoints);
		setYpoints(ypoints);
		computeEndpoints();
	}

	public void setSegment(Vector<double[]> points) {
		int npoints = points.size();
		double[] xpoints = new double[npoints];
		double[] ypoints = new double[npoints];
		for (int i = 0; i < npoints; i++) {
			double[] point = points.get(i);
			if (point.length == 2) {
				xpoints[i] = point[0];
				ypoints[i] = point[1];
			}
		}
		setSegment(xpoints, ypoints, npoints);
	}

	public double[] getXpoints() {
		return this.xpoints;
	}

	public double[] getYpoints() {
		return this.ypoints;
	}

	public java.awt.Polygon getShape() {
		return segment;
	}

	public void setXpoints(double[] xpoints) {
		this.xpoints = xpoints;
	}

	public void setYpoints(double[] ypoints) {
		this.ypoints = ypoints;
	}

	public void computeEndpoints() {
		if (xpoints.length == 2 && ypoints.length == 2) {
			if ((ypoints[0] > ypoints[1])
					|| (ypoints[0] == ypoints[1] && xpoints[0] <= xpoints[1])) {
				upperEndpoint.setLocation(xpoints[0], ypoints[0]);
				lowerEndpoint.setLocation(xpoints[1], ypoints[1]);
			} else {
				upperEndpoint.setLocation(xpoints[1], ypoints[1]);
				lowerEndpoint.setLocation(xpoints[0], ypoints[0]);
			}
			if ((xpoints[0] > xpoints[1])) {
				rightEndpoint.setLocation(xpoints[0], ypoints[0]);
				leftEndpoint.setLocation(xpoints[1], ypoints[1]);
			} else {
				rightEndpoint.setLocation(xpoints[1], ypoints[1]);
				leftEndpoint.setLocation(xpoints[0], ypoints[0]);
			}
			value = (double) leftEndpoint.getX();

			setSlope();
			setOriginOrdinate();
		}
	}

	public Point getLowerEndpoint() {
		return this.lowerEndpoint;
	}

	public Point getUpperEndpoint() {
		return this.upperEndpoint;
	}

	public Point getLeftEndpoint() {
		return this.leftEndpoint;
	}

	public Point getRightEndpoint() {
		return this.rightEndpoint;
	}

	public void printSegment() {
		System.out.println("Segment " + id + " : upperEndPoint = "
				+ upperEndpoint + " lowerEndPoint = " + lowerEndpoint);
	}

	public String toString() {
		return "Segment " + id + " : [{" + upperEndpoint.getX() + ", "
				+ upperEndpoint.getY() + "} ; {" + lowerEndpoint.getX() + ", "
				+ lowerEndpoint.getY() + "}]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setSlope() {
		if (xpoints[1] - xpoints[0] == 0) {
			slope = MAX_SLOPE;
			isVertical = true;
		} else {
			slope = (ypoints[1] - ypoints[0]) / (xpoints[1] - xpoints[0]);
			if (slope == 0) {
				isHorizontal = true;
				setValue(Double.MIN_VALUE);
			}
		}
	}

	public boolean isVertical() {
		return isVertical;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setOriginOrdinate() {
		originOrdinate = ypoints[0]
				- ((ypoints[1] - ypoints[0]) / (xpoints[1] - xpoints[0]))
				* xpoints[0];
	}

	public double getSlope() {
		return slope;
	}

	public boolean isInBoundingBox(Point p) {
		boolean c1 = ((p.getX() >= xpoints[0] || p.getX() >= xpoints[1]) && (p
				.getY() >= ypoints[0] || p.getY() >= ypoints[1]));
		boolean c2 = ((p.getX() <= xpoints[0] || p.getX() <= xpoints[1]) && (p
				.getY() <= ypoints[0] || p.getY() <= ypoints[1]));
		return c1 && c2;
	}

	public boolean containsPoint(Point p) {
		if (p.equals(lowerEndpoint)
				|| p.equals(upperEndpoint)
				|| (isVertical() && Math.abs(p.getX() - lowerEndpoint.getX()) <= ERROR_ALLOWED)) {
			return true;
		}
		return (Math
				.abs(((p.getY() - ypoints[0]) / (p.getX() - xpoints[0]) - getSlope())) <= ERROR_ALLOWED)
				&& isInBoundingBox(p);
	}

	// return 1 if p is on the left side of the segment (0 = on ; -1 = right)
	public int isLeft(Point p) {
		double res = (lowerEndpoint.getX() - p.getX()) * (upperEndpoint.getY() - p.getY())
				- (upperEndpoint.getX() - p.getX()) * (lowerEndpoint.getY() - p.getY());
		if (res > 0) {
			return 1;
		} else {
			if (res == 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public double getCurrentAbscisse(double Y) {
		return (Y - originOrdinate) / slope;
	}

	public void updateValue(double newY) {
		if (!isHorizontal()) {
			value = (newY - originOrdinate) / slope;
		} else {
			value = newY;
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof Segment) {
			return equals((Key) obj);
		} else {
			System.out.println("Bad Object equals");
			return false;
		}
	}

	public boolean equals(Key key) {
		return this == (Segment) key;
	}

	public String getLabel() {
		return "S" + value;
	}

	public RedBlackNode getNode() {
		return node;
	}

	public double getValue() {
		return value;
	}

	public boolean largerThan(Object obj) {
		if (obj instanceof Segment) {
			return largerThan((Key) obj);
		} else {
			System.out.println("Bad Object larger than");
			return false;
		}
	}

	@Override
	public boolean largerThan(Key key) {
		double d = key.getValue();
		double d1 = getValue();
		return d1 < d;
	}

	@Override
	public boolean lessThan(Object obj) {
		if (obj instanceof Segment) {
			return lessThan((Key) obj);
		} else {
			System.out.println("Bad Object less than");
			return false;
		}
	}

	@Override
	public boolean lessThan(Key key) {
		double d = key.getValue();
		double d1 = getValue();
		return d1 > d;
	}

	@Override
	public void setNode(RedBlackNode redblacknode) {
		node = redblacknode;
	}

	@Override
	public void setValue(double d) {
		value = d;
	}

	@Override
	public void swapValue(Key key) {
		double d = value;
		setValue(key.getValue());
		key.setValue(d);
	}

	public void setSplit(Vector<Point> split) {
		this.split = split;
	}

	public Vector<Point> getSplit() {
		return split;
	}

	public void initSplitSegment() {
		this.split.clear();

		if (this.isHorizontal) {
			this.split.add(this.rightEndpoint);
			this.split.add(this.leftEndpoint);
		} else {
			this.split.add(this.lowerEndpoint);
			this.split.add(this.upperEndpoint);
		}
	}

	public void addSplit(Point p) {

		int nbPoints = this.split.size();

		for (int i = 0; i < (nbPoints - 1); i++) {
			if (split.get(i).distance(p) < split.get(i).distance(
					split.get(i + 1))
					&& split.get(i + 1).distance(p) < split.get(i).distance(
							split.get(i + 1))) {
				this.split.add(i + 1, p);
			}
		}
	}

}