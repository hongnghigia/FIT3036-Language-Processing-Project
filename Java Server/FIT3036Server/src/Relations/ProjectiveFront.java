package Relations;

import UCG.ICGNode;

import java.awt.Point;

public class ProjectiveFront extends Projective{
	Point point1, point2, point3, point4;
	Point pointA, pointB, pointC, pointD;
	double distance;
	double eval;
	@Override
	double evaluate(ICGNode obj, ICGNode lm, ICGNode speaker) {
		// -------------
		// 1 		   3
		//
		// 2		   4
		// -------------
		
		// Initializing the corner points for the landmark
		point1 = new Point();
		point1.setLocation(lm.getMinX(), lm.getMinY());
		
		point3 = new Point();
		point3.setLocation(lm.getMaxX(), lm.getMinY());
		
		point2 = new Point();
		point2.setLocation(lm.getMinX(), lm.getMaxY());
		
		point4 = new Point();
		point4.setLocation(lm.getMaxX(), lm.getMaxY());
		
		// check where the speaker is
		// middle point of the speaker
		double midSX = (speaker.getW()/2) + speaker.getMinX();
		double midSY = (speaker.getD()/2) + speaker.getMinY();
		
		double objmidX = (obj.getW()/2) + obj.getMinX();
		double objmidY = (obj.getD()/2) + obj.getMinY();
		double objmidZ = (obj.getH()/2) + obj.getMinZ();
		// speaker coordinates
		Point speakerPoint = new Point();
		speakerPoint.setLocation(midSX, midSY);
		
		Point objpoint = new Point();
		objpoint.setLocation(objmidX, objmidY);
		
		double score14 = determinantScore(point1, point4, speakerPoint);
		double score23 = determinantScore(point2, point3, speakerPoint);
		
		double objscore14 = determinantScore(point1, point4, objpoint);
		double objscore23 = determinantScore(point2, point3, objpoint);
		// checking the determinant score
		if (score14 == 1 && score23 == 1){ // the speaker at the bottom quadrant
			if(objscore14 == 1 && objscore23 == 1){ // if the object is also in the same quadrant as speaker
				double midEdgeX = lm.getW()/2 + lm.getMinX();
				double midEdgeY = lm.getMaxY();
				
				distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
			}
		} else if (score14 == 1 && score23 == -1) { // Left quadrant
			if(objscore14 == 1 && objscore23 == -1){ // if the object is also in the same quadrant as speaker
				double midEdgeX = lm.getMinX();
				double midEdgeY = lm.getD()/2 + lm.getMinY();
				
				distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
			}
		} else if (score14 == -1 && score23 == 1) { // Right quadrant
			if(objscore14 == -1 && objscore23 == -1){ // if the object is also in the same quadrant as speaker
				double midEdgeX = lm.getMaxX();
				double midEdgeY = lm.getD()/2 + lm.getMinY();
				
				distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
			}
		} else if (score14 == -1 && score23 == -1) { // top quadrant
			
			if(objscore14 == -1 && objscore23 == -1){ // if the object is also in the same quadrant as speaker
				double midEdgeX = lm.getW()/2 + lm.getMinX();
				double midEdgeY = lm.getMinY();
				
				distance = Math.hypot(objmidX - midEdgeX, objmidY - midEdgeY);
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
		pointA = new Point();
		pointA.setLocation(lm.getMinX(), lm.getD()/2 + lm.getMinY());
		
		pointB = new Point();
		pointB.setLocation(lm.getMaxX(), lm.getD()/2 + lm.getMinY());
		
		pointC = new Point();
		pointC.setLocation(lm.getW()/2 + lm.getMinX(), lm.getMinY());
		
		pointD = new Point();
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

		eval = score(distance, obj.getMinZ() - lm.getMaxZ());
		return eval;
	}
	
	private double determinantScore(Point a, Point b, Point c){
		return Math.signum(((b.getX() - a.getX()) * (c.getY() - a.getY())) - ((b.getY() - a.getY()) * (c.getX() - a.getX())));
	}
	
	private double score(double D, double Z){
		double result = Math.pow(Math.E, (-0.5 + (-0.5 * Z)) * D);
		return result;
	}

}
