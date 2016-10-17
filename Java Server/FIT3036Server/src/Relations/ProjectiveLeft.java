package Relations;

import UCG.ICGNode;

public class ProjectiveLeft extends Projective{

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
	private double leftX;
	private double leftY;
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
		
		// find the left edge/corner
		if (locationSpeaker.equals("top")) {
			leftX = lm.getMaxX();
			leftY = centerYL;
		}
		else if (locationSpeaker.equals("bottom")) {
			leftX = lm.getMinX();
			leftY = centerYL;
		}
		else if (locationSpeaker.equals("left")) {
			leftX = centerXL;
			leftY = lm.getMinY();
		}
		else if (locationSpeaker.equals("right")) {
			leftX = centerXL;
			leftY = lm.getMaxY();
		}
		else if (locationSpeaker.equals("topleft")) {
			leftX = lm.getMaxX();
			leftY = lm.getMinY();
		}
		else if (locationSpeaker.equals("topright")) {
			leftX = lm.getMaxX();
			leftY = lm.getMaxY();
		}
		else if (locationSpeaker.equals("bottomleft")) {
			leftX = lm.getMinX();
			leftY = lm.getMinY();
		}
		else if (locationSpeaker.equals("bottomright")) {
			leftX = lm.getMinX();
			leftY = lm.getMaxY();
		}
		
		// get score multiplier
		if (isLeft(locationSpeaker, locationObject, centerXO, centerYO, centerXL, centerYL)) {
			// object is left of and off the table
			if (isOff(locationSpeaker, centerXO, centerYO, lm.getMinX(), lm.getMaxX(), lm.getMinY(), lm.getMaxY())) {
				multiplier = 1.0;
			}
			// object is left of and on the table
			else {
				multiplier = 0.75;
			}
		}
		// object is not left of
		else {
			multiplier = 0.50;
		}
		
		// get X distance from object to back point
		if (((obj.getMinX() < leftX) && (obj.getMaxX() > leftX)) || (obj.getMaxX() == leftX) || (obj.getMinX() == leftX)) {
			distX = 0;
		}
		else if (obj.getMaxX() < leftX) {
			distX = leftX - obj.getMaxX();
		}
		else {
			distX = obj.getMinX() - leftX;
		}
		
		// get Y distance from object to back point
		if (((obj.getMinY() < leftY) && (obj.getMaxY() > leftY)) || (obj.getMaxY() == leftY) || (obj.getMinY() == leftY)) {
			distY = 0;
		}
		else if (obj.getMaxY() < leftY) {
			distY = leftY - obj.getMaxY();
		}
		else {
			distY = obj.getMinY() - leftY;
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
		score = Math.pow(Math.E, ((-0.5 + (-0.5 * distZ)) * D));
		score = score * multiplier;
		return score;
	}
	
	// returns true based on the location of the speaker and the location of the object
	private boolean isLeft(String sp, String obj, double centerXO, double centerYO, double centerXL, double centerYL) {
		if (sp.equals("bottom") && obj.equals("left")) {
			return true;
		}
		else if (sp.equals("top") && obj.equals("right")) {
			return true;
		}
		else if (sp.equals("left") && obj.equals("top")) {
			return true;
		}
		else if (sp.equals("right") && obj.equals("bottom")) {
			return true;
		}
		else if (sp.equals("topright") && (centerXO > centerXL) && (centerYO > centerYL)) {
			return true;
		}
		else if (sp.equals("bottomleft") && (centerXO < centerXL) && (centerYO < centerYL)) {
			return true;
		}
		else if (sp.equals("bottomright") && (centerXO < centerXL) && (centerYO > centerYL)) {
			return true;
		}
		else if (sp.equals("topleft") && (centerXO > centerXL) && (centerYO < centerYL)) {
			return true;
		}
		return false;
	}
	
	// returns true based on the location of the speaker and the area in which counts as "off" the landmark
	private boolean isOff(String sp, double centerXO, double centerYO, double minXL, double maxXL, double minYL, double maxYL) {
		if (sp.equals("top") && (centerXO > maxXL)) {
			return true;
		}
		else if (sp.equals("bottom") && (centerXO < minXL)) {
			return true;
		}
		else if (sp.equals("right") && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("left") && (centerYO < minYL)) {
			return true;
		}
		else if (sp.equals("topright") && (centerXO > maxXL) && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("bottomleft") && (centerXO < minXL) && (centerYO < minYL)) {
			return true;
		}
		else if (sp.equals("bottomright") && (centerXO < minXL) && (centerYO > maxYL)) {
			return true;
		}
		else if (sp.equals("topleft") && (centerXO > maxXL) && (centerYO < minYL)) {
			return true;
		}
		return false;
	}
}
