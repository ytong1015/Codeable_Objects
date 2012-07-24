package com.ornament.lsys;
import com.datatype.Point;

public class TurtleStatus {
private Point posMark;
private double rotMark;

public TurtleStatus(Point pt, double angle){
setPosMark(pt);
setRotMark(angle);
}

public Point getPosMark() {
	return posMark;
}

public void setPosMark(Point posMark) {
	this.posMark = posMark;
}

public double getRotMark() {
	return rotMark;
}

public void setRotMark(double rotMark) {
	this.rotMark = rotMark;
}


}
