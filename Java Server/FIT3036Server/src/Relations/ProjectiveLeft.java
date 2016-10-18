package Relations;

import java.awt.Point;

import UCG.ICGNode;

public class ProjectiveLeft extends Projective{
	Point point1, point2, point3, point4;
	Point pointA, pointB, pointC, pointD;
	double distance;
	double eval;
	double multiplier;
	double distZ;
	double midEdgeX;
	double midEdgeY;
	
	@Override
	double evaluate(ICGNode obj, ICGNode lm, ICGNode speaker) {
		double midSX = (speaker.getW()/2) + speaker.getMinX();
		double midSY = (speaker.getD()/2) + speaker.getMinY();
		
		double objmidX = (obj.getW()/2) + obj.getMinX();
		double objmidY = (obj.getD()/2) + obj.getMinY();
		double objmidZ = (obj.getH()/2) + obj.getMinZ();
		
		midEdgeX = 0;
		midEdgeY = 0;
		// speaker coordinates
		Point speakerPoint = new Point();
		speakerPoint.setLocation(midSX, midSY);
		
		Point objpoint = new Point();
		objpoint.setLocation(objmidX, objmidY);
		
		point1 = new Point();
		point2 = new Point();
		point3 = new Point();
		point4 = new Point();
		double angle = toDegree(lm.getAngle());
		
		if(lm.hasFace()){
			if (angle == 0){
				// -----------------
				// 1               3
				//
				// 2               4
				// -----------------
				point1.setLocation(lm.getMinX(), lm.getMinY());
				point2.setLocation(lm.getMinX(), lm.getMaxY());
				point3.setLocation(lm.getMaxX(), lm.getMinY());
				point4.setLocation(lm.getMaxX(), lm.getMaxY());
				midEdgeX = lm.getMinX();
				midEdgeY = lm.getD()/2 + lm.getMinY();
			}
			if (angle >= 90 && angle < 95){ // rotated 90 degrees
				// ------
				// 2    1
				//
				//
				// 4    3
				// ------
				point1.setLocation(lm.getMinX() + lm.getD(), lm.getMinY());
				point2.setLocation(lm.getMinX(), lm.getMinY());
				point3.setLocation(lm.getMinX() + lm.getD(), lm.getMinY() + lm.getW());
				point4.setLocation(lm.getMinX(), lm.getMinY() + lm.getW());
				midEdgeX = lm.getMinX() + (lm.getD()/2);
				midEdgeY = lm.getMinY();
				
			} else if (angle >= 180 && angle < 185) { // rotated 180 degrees
				// -----------------
				// 4               2
				//
				// 3               1
				// -----------------
				point1.setLocation(lm.getMinX() + lm.getW(), lm.getMinY() + lm.getD());
				point2.setLocation(lm.getMinX() + lm.getW(), lm.getMinY());
				point3.setLocation(lm.getMinX(), lm.getMinY() + lm.getD());
				point4.setLocation(lm.getMinX(), lm.getMinY());
				midEdgeX = lm.getMaxX();
				midEdgeY = lm.getMinY() + (lm.getD()/2);
				
			} else if (angle >= 270 && angle < 275) { // rotated 270 degrees
				// ------
				// 3    4
				//
				//
				// 1    2
				// ------
				point1.setLocation(lm.getMinX(), lm.getMinY() + lm.getW());
				point2.setLocation(lm.getMinX() + lm.getD(), lm.getMinY() + lm.getW());
				point3.setLocation(lm.getMinX(), lm.getMinY());
				point4.setLocation(lm.getMinX() + lm.getD(), lm.getMinY());
				midEdgeX = lm.getMinX() + lm.getW();
				midEdgeY = lm.getMinY() + (lm.getD()/2);
			}
			
			if (isLeft(point1, point2, point3, point4, objpoint)){
				multiplier = 1;
			} else {
				multiplier = 0.5;
			}
		}
		else if (!lm.hasFace()){
			point1.setLocation(lm.getMinX(), lm.getMinY());
			point2.setLocation(lm.getMinX(), lm.getMaxY());
			point3.setLocation(lm.getMaxX(), lm.getMinY());
			point4.setLocation(lm.getMaxX(), lm.getMaxY());
			
			// -------------
			// 1 		   3
			//
			// 2		   4
			// -------------
			
			// Initializing the corner points for the landmark
			
			double score14 = determinantScore(point1, point4, speakerPoint);
			double score23 = determinantScore(point2, point3, speakerPoint);
			
			double objscore14 = determinantScore(point1, point4, objpoint);
			double objscore23 = determinantScore(point2, point3, objpoint);
			// checking the determinant score
			if (score14 == 1 && score23 == 1){ // speaker at the bottom
				midEdgeX = lm.getMinX();
				midEdgeY = lm.getMinY() + (lm.getD()/2);
				if (objscore14 == 1 && objscore23 == -1){ // object to the left of landmark
					multiplier = 1;
				} else {
					multiplier = 0.5;
				}
			} else if (score14 == 1 && score23 == -1){ // speaker left quadrant
				midEdgeX = lm.getMinX() + (lm.getW()/2);
				midEdgeY = lm.getMinY();
				if (objscore14 == -1 && objscore23 == -1){
					multiplier = 1;
				} else {
					multiplier = 0.5;
				}
			} else if (score14 == -1 && score23 == -1){ // speaker top quadrant
				midEdgeX = lm.getMaxX();
				midEdgeY = lm.getMinY() + (lm.getD()/2);
				if (objscore14 == 1 && objscore23 == 1){
					multiplier = 1;
				} else {
					multiplier = 0.5;
				}
			} else if (score14 == -1 && score23 == 1){ // speaker right quadrant
				midEdgeX = lm.getMinX() + (lm.getW()/2);
				midEdgeY = lm.getMaxY();
				if (objscore14 == 1 && objscore23 == -1){
					multiplier = 1;
				} else {
					multiplier = 0.5;
				}
			}
			// -----------------
			//        C
			//        |
			// A______|_________B
			//        |
			//        |
			//        D
			// ------------------
		}
		// get Z distance from object to back point
		if (obj.getMinZ() > lm.getMaxZ()) {
			distZ = obj.getMinZ() - lm.getMaxZ();
		}
		else if (obj.getMaxZ() < lm.getMinZ()) {
			distZ = lm.getMinZ() - obj.getMaxZ();
		}
		else {
			distZ = 0;
		}
		distance = Math.sqrt(Math.pow(Math.abs(objmidX - midEdgeX), 2) + Math.pow(Math.abs(objmidY - midEdgeY), 2) + Math.pow(Math.abs(objmidZ - distZ), 2));
		eval = score(Math.abs(distance), distZ, multiplier);
		return eval;	
	}
	
	private double score(double D, double Z, double M){
		double result = Math.pow(Math.E, (-0.5 + (-0.5 * Z)) * D) * M;
		return result;
	}

	private double determinantScore(Point a, Point b, Point c){
		return Math.signum(((b.getX() - a.getX()) * (c.getY() - a.getY())) - ((b.getY() - a.getY()) * (c.getX() - a.getX())));
	}

	private boolean isLeft(Point p1, Point p2, Point p3, Point p4, Point ob){
		double s14 = determinantScore(p1, p4, ob);
		double s23 = determinantScore(p2, p3, ob);
		
		if (s14 == 1 && s23 == -1){
			return true;
		} else {
			return false;
		}
	}
}
