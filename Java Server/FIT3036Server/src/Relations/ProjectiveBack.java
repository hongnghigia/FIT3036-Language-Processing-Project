package Relations;

import UCG.ICGNode;

public class ProjectiveBack extends Projective {

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
	private double backX;
	private double backY;
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
		
		// get X distance from object to back point
		if (((obj.getMinX() < backX) && (obj.getMaxX() > backX)) || (obj.getMaxX() == backX) || (obj.getMinX() == backX)) {
			distX = 0;
		}
		else if (obj.getMaxX() < backX) {
			distX = backX - obj.getMaxX();
		}
		else {
			distX = obj.getMinX() - backX;
		}
		
		// get Y distance from object to back point
		if (((obj.getMinY() < backY) && (obj.getMaxY() > backY)) || (obj.getMaxY() == backY) || (obj.getMinY() == backY)) {
			distY = 0;
		}
		else if (obj.getMaxY() < backY) {
			distY = backY - obj.getMaxY();
		}
		else {
			distY = obj.getMinY() - backY;
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
	
}
