/**
 * 
 */
package com.computation;

import java.util.Vector;
import java.util.Enumeration;
import java.util.Comparator;

import com.ornament.Pattern;
import com.primitive2d.Intersection;
import com.primitive2d.Segment;
import com.datatype.Point;

/**

 *
 */
public class FindIntersections {

	/**
	 *
	 */
	private Pattern parent;

	private PriorityQueueAdv queue;
	// private AVLNode <Segment> tree;

	private RedBlackTree sweepLineStatus;

	public FindIntersections(Pattern parent) {
		Comparator<EventPointSegment> comparator = new EventPointSegmentComparator();
		queue = new PriorityQueueAdv(10, comparator);

		sweepLineStatus = new RedBlackTree();

		this.parent = parent;

	}

	public FindIntersections(Pattern parent, Vector<Segment> segments) {

		this(parent);

		int nbSegments = segments.size();

		// long timerStart = System.currentTimeMillis();

		// add all endpoints in the event queue, with the segment if the point
		// is an upper endpoint
		for (int i = 0; i < nbSegments; i++) {
			EventPointSegment EpS1 = new EventPointSegment(segments.get(i)
					.getUpperEndpoint(), segments.get(i));
			queue.add(EpS1);
			EventPointSegment EpS2 = new EventPointSegment(segments.get(i)
					.getLowerEndpoint());
			queue.add(EpS2);

		}

		while (!queue.isEmpty()) {
			EventPointSegment newEvent = queue.getNext();
			HandleEventPoint(newEvent);
		}

		// comment the following lines out. not sure what do they do.
		// long timerEnd = System.currentTimeMillis();
		// long runningTime = Math.abs(timerEnd - timerStart);

		// System.out.println("Running time to find intersections : "+runningTime+" ms");

	}

	public void HandleEventPoint(EventPointSegment evtPointSeg) {
		// Get event point
		Point evtPoint = evtPointSeg.getPoint();
		// Get segments linked to this event point
		Vector<Segment> evtSet = evtPointSeg.getSegments();

		Vector<Segment> uSet = new Vector<Segment>();
		Vector<Segment> lcSet = new Vector<Segment>();
		Vector<Segment> lSet = new Vector<Segment>();
		Vector<Segment> cSet = new Vector<Segment>();

		// System.out.println("Handling p : {X = "+evtPoint.getX()+
		// ", Y = "+evtPoint.getY()+"}");

		// Build set U(evtPoint) : set of segments whose upper endpoint is
		// evtPoint
		if (!evtSet.isEmpty()) {
			for (int i = 0; i < evtSet.size(); i++) {
				if (evtSet.get(i).getUpperEndpoint().equals(evtPoint)) {
					uSet.add(evtSet.get(i));
				}
			}
		}

		// Find all segments stored in the tree that contain evtPoint
		lcSet = SegmentsContainingEventPoint(sweepLineStatus, evtPointSeg);
		for (int i = 0; i < lcSet.size(); i++) {
			if (lcSet.get(i).getLowerEndpoint().equals(evtPoint)) {
				// Set of segments whose lowerEndpoint is evtPoint
				lSet.add(lcSet.get(i));
			} else {
				// Set of segments who contain evtPoint in their interior
				cSet.add(lcSet.get(i));
			}
		}

		// Report intersection
		if (uSet.size() + lSet.size() + cSet.size() > 1) {
			evtPointSeg.isIntersection();
			Intersection newIntersection = parent.createIntersection(
					evtPoint.getX(), evtPoint.getY());
			Vector<Segment> segments = new Vector<Segment>();
			segments.addAll(uSet);
			segments.addAll(lSet);
			segments.addAll(cSet);
			newIntersection.setSegments(segments);
		}

		// Delete segments lSet and cSet from tree
		for (int i = 0; i < lSet.size(); i++) {
			// lSet.get(i).printSegment();
			sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(lSet.get(i)));
		}
		for (int i = 0; i < cSet.size(); i++) {
			// cSet.get(i).printSegment();
			sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(cSet.get(i)));
		}

		// Make sure the segments in the tree are in good order (order of
		// intersection with the sweep line just below the event point)
		double minVal = -1;

		try {
			for (int i = 0; i < sweepLineStatus.allNodes().size(); i++) {
				Segment segment = ((Segment) sweepLineStatus.allNodes().get(i)
						.getKey());
				if (segment.getValue() < minVal || minVal == -1) {
					minVal = segment.getValue();
				}
				sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(segment));
				if (segment.isHorizontal()) {
					segment.updateValue(minVal + 0.0002F);
				} else {
					segment.updateValue(evtPoint.getY() - 0.02F);
				}
				treeInsertBlock(segment);

			}
		} catch (NullPointerException ex) {
		}

		// Insert segments from uSet and cSet in tree
		Segment horizontalSegment = null;
		for (int i = 0; i < uSet.size(); i++) {
			Segment segment = uSet.get(i);
			segment.updateValue(evtPoint.getY() - 0.02F);
			if (segment.getValue() < minVal || minVal == -1) {
				minVal = segment.getValue();
			}
			if (segment.isHorizontal()) {
				horizontalSegment = segment;
			} else {
				treeInsertBlock(segment);
			}
		}
		for (int i = 0; i < cSet.size(); i++) {
			Segment segment = cSet.get(i);
			segment.updateValue(evtPoint.getY() - 0.02F);
			if (segment.getValue() < minVal || minVal == -1) {
				minVal = segment.getValue();
			}
			if (segment.isHorizontal()) {
				horizontalSegment = segment;
			} else {
				treeInsertBlock(segment);
			}
		}
		if (horizontalSegment != null) {
			horizontalSegment.updateValue(minVal + 0.0002F);
			treeInsertBlock(horizontalSegment);
		}

		if (uSet.size() + cSet.size() == 0) {
			// Find sl and sr, left and right segments neighbors of evtPoint in
			// tree
			// System.out.println("---CASE 9---");

			try {
				Segment sTmp = new Segment(
						new double[] { evtPoint.getX(), evtPoint.getX() }, new double[] {
								evtPoint.getY(), evtPoint.getY() + 50 }, 2);
				treeInsertBlock(sTmp);
				Segment sl = (Segment) sweepLineStatus.treeSearch(sTmp)
						.getNext().getKey();
				Segment sr = (Segment) sweepLineStatus.treeSearch(sTmp)
						.getPrev().getKey();
				sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(sTmp));
				if (sr.getId() != -1 && sl.getId() != -1) {
					FindNewEvent(sl, sr, evtPoint);
				}

			} catch (NullPointerException ex) {
			}

		} else {
			// System.out.println("---CASE 11---");
			Vector<Segment> unionSet = new Vector<Segment>();
			unionSet.addAll(uSet);
			unionSet.addAll(cSet);

			Segment sPrime = unionSet.get(0);

			// find the leftmost segment in unionSet :
			for (int i = 1; i < unionSet.size(); i++) {
				if (unionSet.get(i).getValue() < sPrime.getValue()) {
					sPrime = unionSet.get(i);
				}
			}

			// find left neighbor of sPrime in tree :
			try {
				Segment sl = (Segment) sweepLineStatus.treeSearch(sPrime)
						.getNext().getKey();
				FindNewEvent(sl, sPrime, evtPoint);

			} catch (NullPointerException ex) {
			}

			Segment sSecond = unionSet.get(0);
			// find the rightmost segment in unionSet :
			for (int j = 1; j < unionSet.size(); j++) {
				if (unionSet.get(j).getValue() > sSecond.getValue()) {
					sSecond = unionSet.get(j);
				}
			}
			// find right neighbor of sSecond in tree :
			try {
				Segment sr = (Segment) sweepLineStatus.treeSearch(sSecond)
						.getPrev().getKey();
				FindNewEvent(sSecond, sr, evtPoint);

			} catch (NullPointerException ex) {
			}
		}
	}

	public void FindNewEvent(Segment sl, Segment sr, Point p) {

		Point inter = inter2Segments(sl, sr);
		if (inter != null
				&& (inter.getY() < p.getY() || (Math.abs(inter.getY() - p.getY()) <= 1.000001E-002F && inter.getX() > p.getX()))) {
			EventPointSegment newPoint = new EventPointSegment(inter);
			if (!queue.contains(newPoint)) {
				queue.add(newPoint);
			}
		}
	}

	public Vector<Segment> SegmentsContainingEventPoint(
			RedBlackTree redblacktree, EventPointSegment evtPointSeg) {
		Vector<Segment> result = new Vector<Segment>();

		if (redblacktree == null)
			return result;
		Vector<RedBlackNode> vector = new Vector<RedBlackNode>();
		vector = redblacktree.allNodes();
		if (vector == null)
			return result;
		for (Enumeration<RedBlackNode> enumeration = vector.elements(); enumeration
				.hasMoreElements();) {
			RedBlackNode redblacknode = (RedBlackNode) enumeration
					.nextElement();
			Segment segment = (Segment) redblacknode.getKey();
			if (segment.containsPoint(evtPointSeg.getPoint())) {
				// segment.printSegment();
				// eventpoint.addIntersectionSegment(segment);

				result.add(segment);
			}
		}
		return result;
	}

	public void treeInsertBlock(Segment segment) {
		RedBlackNode redblacknode = new RedBlackNode(segment);
		if (sweepLineStatus.treeContains(redblacknode)) {
			System.out.println("Error: Segment must already be in the tree");
			return;
		}
		double d = 0.0F;
		if (segment.isVertical()) {
			sweepLineStatus.treeInsert(redblacknode);
			return;
		}
		do {
			segment.setValue(segment.getValue() - d);
			if (sweepLineStatus.treeInsert(redblacknode))
				break;
			segment.setValue(segment.getValue() + 2F * d);
			if (sweepLineStatus.treeInsert(redblacknode))
				break;
			segment.setValue(segment.getValue() - d);
			d += 0.5F;
		} while (true);
	}

	public static Point inter2Segments(Segment s1, Segment s2) {
		Point A = s1.getLowerEndpoint();
		Point B = s1.getUpperEndpoint();
		Point C = s2.getLowerEndpoint();
		Point D = s2.getUpperEndpoint();

		double Ax = A.getX();
		double Ay = A.getY();
		double Bx = B.getX();
		double By = B.getY();
		double Cx = C.getX();
		double Cy = C.getY();
		double Dx = D.getX();
		double Dy = D.getY();

		double Sx;
		double Sy;

		if (Ax == Bx) {
			if (Cx == Dx)
				return null;
			else {
				double pCD = (Cy - Dy) / (Cx - Dx);
				Sx = Ax;
				Sy = pCD * (Ax - Cx) + Cy;
			}
		} else {
			if (Cx == Dx) {
				double pAB = (Ay - By) / (Ax - Bx);
				Sx = Cx;
				Sy = pAB * (Cx - Ax) + Ay;
			} else {
				double pCD = (Cy - Dy) / (Cx - Dx);
				double pAB = (Ay - By) / (Ax - Bx);
				double oCD = Cy - pCD * Cx;
				double oAB = Ay - pAB * Ax;
				Sx = (oAB - oCD) / (pCD - pAB);
				Sy = pCD * Sx + oCD;
			}
		}
		if ((Sx < Ax && Sx < Bx) | (Sx > Ax && Sx > Bx) | (Sx < Cx && Sx < Dx)
				| (Sx > Cx && Sx > Dx) | (Sy < Ay && Sy < By)
				| (Sy > Ay && Sy > By) | (Sy < Cy && Sy < Dy)
				| (Sy > Cy && Sy > Dy)) {
			return null;
		} else {
			return new Point((double) Sx, (double) Sy);
		}
	}

}