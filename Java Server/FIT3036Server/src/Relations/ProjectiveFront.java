package Relations;

import UCG.ICGNode;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class ProjectiveFront extends Projective{
	Point2D.Double point1, point2, point3, point4;
	Point2D.Double pointA, pointB, pointC, pointD;
	double distance;
	double eval;
	double multiplier = 1;
	double distZ;
	double midEdgeX;
	double midEdgeY;
	
	@Override
	double evaluate(ICGNode obj, ICGNode lm, ICGNode speaker) {
		// check where the speaker is
		// middle point of the speaker
		double midSX = (speaker.getW()/2) + speaker.getMinX();
		double midSY = (speaker.getD()/2) + speaker.getMinY();
		
		double objmidX = (obj.getW()/2) + obj.getMinX();
		double objmidY = (obj.getD()/2) + obj.getMinY();
		double objmidZ = (obj.getH()/2) + obj.getMinZ();
		
		midEdgeX = 0;
		midEdgeY = 0;
		
		// speaker coordinates
		Point2D.Double speakerPoint = new Point2D.Double();
		speakerPoint.setLocation(midSX, midSY);
		
		Point2D.Double objpoint = new Point2D.Double();
		objpoint.setLocation(objmidX, objmidY);
		
		point1 = new Point2D.Double();
		point2 = new Point2D.Double();
		point3 = new Point2D.Double();
		point4 = new Point2D.Double();
		
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
				midEdgeX = lm.getMinX() + (lm.getW()/2);
				midEdgeY = lm.getMaxY();
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
				midEdgeX = lm.getMinX();
				midEdgeY = lm.getMinY() + (lm.getW()/2);
				
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
				midEdgeX = lm.getMinX() + (lm.getW()/2);
				midEdgeY = lm.getMinY();
				
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
				midEdgeX = lm.getMinX() + lm.getD();
				midEdgeY = lm.getMinY() + (lm.getW()/2);
			}
			
			if (isFront(point1, point2, point3, point4, objpoint)){
				multiplier = 1;
			} else {
				multiplier = 0.5;
			}
		}
		else if (!lm.hasFace()){ // if the landmark has no face attribute
			// every thing down below
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
			if (score14 == 1 && score23 == 1){ // the speaker at the bottom quadrant
				midEdgeX = lm.getW()/2 + lm.getMinX();
				midEdgeY = lm.getMaxY();
				if(objscore14 == 1 && objscore23 == 1){ // if the object is also in the same quadrant as speaker
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
					multiplier = 1;
				} else {
					multiplier = 0.5;
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
				}
			} else if (score14 == 1 && score23 == -1) { // Left quadrant
				midEdgeX = lm.getMinX();
				midEdgeY = lm.getD()/2 + lm.getMinY();
				if(objscore14 == 1 && objscore23 == -1){ // if the object is also in the same quadrant as speaker
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
					multiplier = 1;
				} else {
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
					multiplier = 0.5;
				}
			} else if (score14 == -1 && score23 == 1) { // Right quadrant
				midEdgeX = lm.getMaxX();
				midEdgeY = lm.getD()/2 + lm.getMinY();
				if(objscore14 == -1 && objscore23 == 1){ // if the object is also in the same quadrant as speaker
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
					multiplier = 1;
				} else {
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
					multiplier = 0.5;
				}
			} else if (score14 == -1 && score23 == -1) { // top quadrant
				midEdgeX = lm.getW()/2 + lm.getMinX();
				midEdgeY = lm.getMinY();
				if(objscore14 == -1 && objscore23 == -1){ // if the object is also in the same quadrant as speaker
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
					multiplier = 1;
				} else {
//					distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
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
			pointA = new Point2D.Double();
			pointA.setLocation(lm.getMinX(), lm.getD()/2 + lm.getMinY());
			
			pointB = new Point2D.Double();
			pointB.setLocation(lm.getMaxX(), lm.getD()/2 + lm.getMinY());
			
			pointC = new Point2D.Double();
			pointC.setLocation(lm.getW()/2 + lm.getMinX(), lm.getMinY());
			
			pointD = new Point2D.Double();
			pointD.setLocation(lm.getW()/2 + lm.getMinX(), lm.getMaxY());
			
			double scoreAB = determinantScore(pointA, pointB, objpoint);
			double scoreCD = determinantScore(pointC, pointD, objpoint);
			if (score14 == 0 && score23 == 1){ // speaker at the bottom right
				if (scoreAB == 1 && scoreCD == -1){ // if object is in the same region
					distance = Math.hypot(objmidX - lm.getMaxX(), objmidY - lm.getMaxY());
				}
			} else if (score14 == 0 && score23 == -1){ // speaker at the top left
				if (scoreAB == -1 && scoreCD == 1){
					distance = Math.hypot(objmidX - lm.getMinX(), objmidY - lm.getMinY());
				}
			} else if (score14 == 1 && score23 == 0){ // speaker at the bottom left
				if (scoreAB == 1 && scoreCD == 1){
					distance = Math.hypot(objmidX - lm.getMinX(), objmidY - lm.getMaxY());
				}
			} else if (score14 == -1 && score23 == 0){ // speaker at the top right
				if (scoreAB == -1 && scoreCD == -1) {
					distance = Math.hypot(objmidX - lm.getMaxX(), objmidY - lm.getMinY());
				}
			}
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
		
		eval = score(Math.abs(distance), multiplier);
		return eval;
	}
	
	private double score(double D, double M){
		double result = Math.pow(Math.E, (-0.5 * D)) * M;
		return result;
	}
	
	private double determinantScore(Double pointA, Double pointB, Double objpoint){
		return Math.signum(((pointB.getX() - pointA.getX()) * (objpoint.getY() - pointA.getY())) - ((pointB.getY() - pointA.getY()) * (objpoint.getX() - pointA.getX())));
	}
	
	private boolean isFront(Double point12, Double point22, Double point32, Double point42, Double objpoint){
		double s14 = determinantScore(point12, point42, objpoint);
		double s23 = determinantScore(point22, point32, objpoint);
		
		if (s14 == 1 && s23 == 1){
			return true;
		} else {
			return false;
		}
	}
}
