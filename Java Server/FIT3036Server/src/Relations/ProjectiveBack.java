package Relations;

import java.awt.Point;

import UCG.ICGNode;

public class ProjectiveBack extends Projective {

	private double centerXS, centerYS, centerXO, centerYO, centerXL, centerYL;
	private Point point1, point2, point3, point4;
	private double aX, aY, bX, bY, cX, cY, dX, dY;
	private double determinant1, determinant2, determinant3, determinant4;
	private String locationSpeaker;
	private String locationObject;
	private double backX, backY;
	private double multiplier;
	private double distZ;
	private double D;
	private double score;
	
	@Override
	double evaluate(ICGNode obj, ICGNode lm, ICGNode speaker) {
		// find center of speaker
		centerXS = speaker.getMinX() + (speaker.getW() * 0.5);
		centerYS = speaker.getMinY() + (speaker.getD() * 0.5);
		
		// find center of object
		centerXO = obj.getMinX() + (obj.getW() * 0.5);
		centerYO = obj.getMinY() + (obj.getD() * 0.5);
		
		// find center of landmark
		centerXL = lm.getMinX() + (lm.getW() * 0.5);
		centerYL = lm.getMinY() + (lm.getD() * 0.5);
		
		// speaker coordinates
		Point speakerPoint = new Point();
		speakerPoint.setLocation(centerXS, centerYS);
		
		// object coordinates
		Point objpoint = new Point();
		objpoint.setLocation(centerXO, centerYO);
		
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
				backX = lm.getMinX() + (lm.getW()/2);
				backY = lm.getMinY();
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
				backX = lm.getMaxX();
				backY = lm.getMinY() + (lm.getW()/2);
				
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
				backX = lm.getMinX() + (lm.getW()/2);
				backY = lm.getMaxY();
				
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
				backX = lm.getMinX();
				backY = lm.getMinY() + (lm.getW()/2);
			}
			
			if (isBackFace(point1, point2, point3, point4, objpoint)){
				multiplier = 1;
			}
			else {
				multiplier = 0.5;
			}
		}
		
		else if (!lm.hasFace()) {
			// find corners of landmark
			aX = lm.getMinX();
			aY = lm.getMaxY();
			bX = lm.getMaxX();
			bY = lm.getMinY();
			cX = lm.getMinX();
			cY = lm.getMinY();
			dX = lm.getMaxX();
			dY = lm.getMaxY();
			
			// find determinants of speaker
			determinant1 = ((bX - aX) * (centerYS - aY)) - ((bY - aY) * (centerXS - aX));
			determinant2 = ((dX - cX) * (centerYS - cY)) - ((dY - cY) * (centerXS - cX));
			
			// find determinants of object
			determinant3 = ((bX - aX) * (centerYO - aY)) - ((bY - aY) * (centerXO - aX));
			determinant4 = ((dX - cX) * (centerYO - cY)) - ((dY - cY) * (centerXO - cX));
			
			// using determinants to find which quadrant the speaker and object are in
			locationSpeaker = getQuadrant(determinant1, determinant2);
			locationObject = getQuadrant(determinant3, determinant4);
			
			// find the back edge/corner
			if (locationSpeaker.equals("top")) {
				backX = centerXL;
				backY = lm.getMaxY();
			}
			else if (locationSpeaker.equals("bottom")) {
				backX = centerXL;
				backY = lm.getMinY();
			}
			else if (locationSpeaker.equals("left")) {
				backX = lm.getMaxX();
				backY = centerYL;
			}
			else if (locationSpeaker.equals("right")) {
				backX = lm.getMinX();
				backY = centerYL;
			}
			else if (locationSpeaker.equals("topleft")) {
				backX = lm.getMaxX();
				backY = lm.getMaxY();
			}
			else if (locationSpeaker.equals("topright")) {
				backX = lm.getMinX();
				backY = lm.getMaxY();
			}
			else if (locationSpeaker.equals("bottomleft")) {
				backX = lm.getMaxX();
				backY = lm.getMinY();
			}
			else if (locationSpeaker.equals("bottomright")) {
				backX = lm.getMinX();
				backY = lm.getMinY();
			}
			
			// get score multiplier
			if (isBack(locationSpeaker, locationObject, centerXO, centerYO, centerXL, centerYL)) {
				// object is back of and off the table
				if (isOff(locationSpeaker, centerXO, centerYO, lm.getMinX(), lm.getMaxX(), lm.getMinY(), lm.getMaxY())) {
					multiplier = 1.0;
				}
				// object is back of and on the table
				else {
					multiplier = 0.75;
				}
			}
			// object is not back of
			else {
				multiplier = 0.50;
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
		
		// distance from object to landmark in 3D plane
		D = Math.sqrt(Math.pow(Math.abs(centerXO - backX), 2) + Math.pow(Math.abs(centerYO - backY), 2) + Math.pow(distZ, 2));
		
		// calculate score for relation
		score = Math.pow(Math.E, -0.5 * D) * multiplier;
		return score;
	}
	
	// returns true based on the location of the speaker and the location of the object
	private boolean isBack(String sp, String obj, double centerXO, double centerYO, double centerXL, double centerYL) {
		if (sp.equals("bottom") && obj.equals("top")) {
			return true;
		}
		else if (sp.equals("top") && obj.equals("bottom")) {
			return true;
		}
		else if (sp.equals("left") && obj.equals("right")) {
			return true;
		}
		else if (sp.equals("right") && obj.equals("left")) {
			return true;
		}
		else if (sp.equals("bottomleft") && (centerXO > centerXL) && (centerYO < centerYL)) {
			return true;
		}
		else if (sp.equals("topright") && (centerXO < centerXL) && (centerYO > centerYL)) {
			return true;
		}
		else if (sp.equals("topleft") && (centerXO > centerXL) && (centerYO > centerYL)) {
			return true;
		}
		else if (sp.equals("bottomright") && (centerXO < centerXL) && (centerYO < centerYL)) {
			return true;
		}
		return false;
	}
	
	// returns true based on the location of the speaker and the area in which counts as "off" the landmark
	private boolean isOff(String sp, double centerXO, double centerYO, double minXL, double maxXL, double minYL, double maxYL) {
		if (sp.equals("bottom") && (centerYO < minYL)) {
			return true;
		}
		else if (sp.equals("top") && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("left") && (centerXO > maxXL)) {
			return true;
		}
		else if (sp.equals("right") && (centerXO < minXL)) {
			return true;
		}
		else if (sp.equals("bottomleft") && (centerYO < minYL) && (centerXO > maxXL)) {
			return true;
		}
		else if (sp.equals("topright") && (centerYO > maxYL) && (centerXO < minXL)) {
			return true;
		}
		else if (sp.equals("topleft") && (centerYO > maxYL) && (centerXO > maxXL)) {
			return true;
		}
		else if (sp.equals("bottomright") && (centerYO < minYL) && (centerXO < minXL)) {
			return true;
		}
		return false;
	}
	
	private double determinantScore(Point a, Point b, Point c){
		return ((b.getX() - a.getX()) * (c.getY() - a.getY())) - ((b.getY() - a.getY()) * (c.getX() - a.getX()));
	}
	
	private boolean isBackFace(Point p1, Point p2, Point p3, Point p4, Point ob){
		double s14 = determinantScore(p1, p4, ob);
		double s23 = determinantScore(p2, p3, ob);
		
		if (s14 < 0 && s23 < 0){
			return true;
		} else {
			return false;
		}
	}
}
