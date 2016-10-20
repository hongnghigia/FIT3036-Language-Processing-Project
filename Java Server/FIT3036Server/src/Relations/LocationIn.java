package Relations;

import UCG.ICGNode;

public class LocationIn extends Topological {
	// the percentage of inside an object is to the lm for all planes
	double insideX;
	double insideY;
	double insdieZ;
	
	// the value of XYZ that's inside the lm for volume calculation
	double planeX;
	double planeY;
	double planeZ;
	
	double objVolume;
	double insideVolume;
	
	double score;
	
	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		objVolume = obj.getW() * obj.getD() * obj.getH();
		
		// checking if the object is in the lm
		// checking X plane
		// if object is inside the X plane of lm
		if (obj.getMinX() >= lm.getMinX() && obj.getMaxX() <= lm.getMaxX()){
			planeX = obj.getW();
			// checking Y plane
			// if object is inside the Y plane of lmz
			if (obj.getMinY() >= lm.getMinY() && obj.getMaxY() <= lm.getMaxY()){
				planeY = obj.getD();
				// checking Z plane
				// if object is inside the Z plane of lm
				if (obj.getMinZ() >= lm.getMinZ() && obj.getMaxZ() <= lm.getMaxZ()){
					planeZ = obj.getH();
				}
			}
		} else {
			
			// if the object is sticking out to the left side of the lm
			// but still technically inside the lm
			if (obj.getMinX() < lm.getMinX() && obj.getMaxX() < lm.getMaxX()){
				planeX = obj.getMaxX() - lm.getMinX();
			} else {	
				// if the object is sticking out to the right side of the lm
				if (obj.getMaxX() > lm.getMaxX() && obj.getMinX() > lm.getMinX()){
					planeX = obj.getMaxX() - lm.getMaxX();
				}
			}
			
			// if the object is sticking out to the front of the lm
			// but still technically inside the lm
			if (obj.getMinY() > lm.getMinY() && obj.getMaxY() > lm.getMaxY()){
				planeY = lm.getMaxY() - obj.getMinY();
			} else {
				
				// sticking out the back
				if (obj.getMinY() < lm.getMinY() && obj.getMaxY() < lm.getMaxY()){
					planeY = obj.getMaxY() - lm.getMinY();
				}
			}
			
			// if the object is sticking out to the top of the lm
			// but still technically inside the lm
			if (obj.getMinZ() > lm.getMinZ() && obj.getMaxZ() > lm.getMaxZ()){
				planeZ = lm.getMaxZ() - obj.getMinZ();
			}

		}
		
		if (obj.getMinX() < lm.getMinX() || obj.getMaxX() > lm.getMaxX()){
			if (obj.getMinY() <lm.getMinY() || obj.getMaxY() > lm.getMaxY()){
				if (obj.getMinZ() < lm.getMinZ() || obj.getMaxZ() > lm.getMaxZ()) {
					planeX = 0;
					planeY = 0;
					planeZ = 0;
				}
			}
		}
		
		
		insideVolume = planeX * planeY * planeZ;
		// volume of the inside part of the object divide with its full volume
		score = insideVolume / objVolume;
		if (score <= 0.0) {
			return 0.001;
		}
		else {
			return score;
		}
	}
}
