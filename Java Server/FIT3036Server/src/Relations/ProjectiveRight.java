package Relations;

import UCG.ICGNode;

public class ProjectiveRight extends Projective{
	private double centerXS;
	private double centerYS;
	private double centerXO;
	private double centerYO;
	private double centerXL;
	private double centerYL;
	private double aX;
	private double aY;
	private double bX;
	private double bY;
	private double cX;
	private double cY;
	private double dX;
	private double dY;
	private double determinant1;
	private double determinant2;
	private double determinant3;
	private double determinant4;
	private String locationSpeaker;
	private String locationObject;
	private double rightX;
	private double rightY;
	private double multiplier;
	private double distX;
	private double distY;
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
		
		// find the right edge/corner
		if (locationSpeaker.equals("top")) {
			rightX = lm.getMinX();
			rightY = centerYL;
		}
		else if (locationSpeaker.equals("bottom")) {
			rightX = lm.getMaxX();
			rightY = centerYL;
		}
		else if (locationSpeaker.equals("left")) {
			rightX = centerXL;
			rightY = lm.getMaxY();
		}
		else if (locationSpeaker.equals("right")) {
			rightX = centerXL;
			rightY = lm.getMinY();
		}
		else if (locationSpeaker.equals("topleft")) {
			rightX = lm.getMinX();
			rightY = lm.getMaxY();
		}
		else if (locationSpeaker.equals("topright")) {
			rightX = lm.getMinX();
			rightY = lm.getMinY();
		}
		else if (locationSpeaker.equals("bottomleft")) {
			rightX = lm.getMaxX();
			rightY = lm.getMaxY();
		}
		else if (locationSpeaker.equals("bottomright")) {
			rightX = lm.getMaxX();
			rightY = lm.getMinY();
		}
		
		// get score multiplier
		if (isRight(locationSpeaker, locationObject, centerXO, centerYO, centerXL, centerYL)) {
			// object is right of and off the table
			if (isOff(locationSpeaker, centerXO, centerYO, lm.getMinX(), lm.getMaxX(), lm.getMinY(), lm.getMaxY())) {
				multiplier = 1.0;
			}
			// object is right of and on the table
			else {
				multiplier = 0.50;
			}
		}
		// object is not right of
		else {
			multiplier = 0.25;
		}
		
		// get X distance from object to back point
		if (((obj.getMinX() < rightX) && (obj.getMaxX() > rightX)) || (obj.getMaxX() == rightX) || (obj.getMinX() == rightX)) {
			distX = 0;
		}
		else if (obj.getMaxX() < rightX) {
			distX = rightX - obj.getMaxX();
		}
		else {
			distX = obj.getMinX() - rightX;
		}
		
		// get Y distance from object to back point
		if (((obj.getMinY() < rightY) && (obj.getMaxY() > rightY)) || (obj.getMaxY() == rightY) || (obj.getMinY() == rightY)) {
			distY = 0;
		}
		else if (obj.getMaxY() < rightY) {
			distY = rightY - obj.getMaxY();
		}
		else {
			distY = obj.getMinY() - rightY;
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
		
		// calculate the XY distance using pythagoras
		D = Math.hypot(distX, distY);
		
		// calculate score
		score = Math.pow(Math.E, (-0.5 * (-0.5 * distZ) * D));
		score = score * multiplier;
		return score;
	}
	
	private boolean isRight(String sp, String obj, double centerXO, double centerYO, double centerXL, double centerYL) {
		if (sp.equals("bottom") && obj.equals("right")) {
			return true;
		}
		else if (sp.equals("top") && obj.equals("left")) {
			return true;
		}
		else if (sp.equals("left") && obj.equals("bottom")) {
			return true;
		}
		else if (sp.equals("right") && obj.equals("top")) {
			return true;
		}
		else if (sp.equals("bottomleft") && (centerXO > centerXL) && (centerYO > centerYL)) {
			return true;
		}
		else if (sp.equals("topright") && (centerXO < centerXL) && (centerYO < centerYL)) {
			return true;
		}
		else if (sp.equals("topleft") && (centerXO < centerXL) && (centerYO > centerYL)) {
			return true;
		}
		else if (sp.equals("bottomright") && (centerXO > centerXL) && (centerYO < centerYL)) {
			return true;
		}
		return false;
	}
	
	private boolean isOff(String sp, double centerXO, double centerYO, double minXL, double maxXL, double minYL, double maxYL) {
		if (sp.equals("bottom") && (centerXO > maxXL)) {
			return true;
		}
		else if (sp.equals("top") && (centerXO < minXL)) {
			return true;
		}
		else if (sp.equals("left") && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("right") && (centerYO < minYL)) {
			return true;
		}
		else if (sp.equals("bottomleft") && (centerXO > maxXL) && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("topright") && (centerXO < minXL) && (centerYO < minYL)) {
			return true;
		}
		else if (sp.equals("topleft") && (centerXO < minXL) && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("bottomright") && (centerXO > maxXL) && (centerYO < minYL)) {
			return true;
		}
		return false;
	}
}
