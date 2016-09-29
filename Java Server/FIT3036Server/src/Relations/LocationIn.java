package Relations;

import UCG.ICGNode;

public class LocationIn extends Relation {
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
	
	@Override
	public double evaluate(ICGNode obj, ICGNode lm) {
		objVolume = obj.getMaxX() * obj.getMaxY() * obj.getMaxZ();
		
		//checking if the object is in the lm
		//checking X plane
		// if object is touching the lm and inside the X plane of lm
		if (obj.getMinX() >= lm.getMinX() && obj.getMaxX() <= lm.getMaxX()){
			planeX = obj.getD();
			// checking Y plane
			// if object is touching the lm and inside the Y plane of lm 
			if (obj.getMinY() >= lm.getMinY() && obj.getMaxY() <= lm.getMaxY()){
				planeY = obj.getW();
				//checking Z plane
				// if object is touching the lm and inside the Z plane of lm
				if (obj.getMinZ() >= lm.getMinZ() && obj.getMaxZ() <= lm.getMaxZ()){
					planeZ = obj.getH();
				}
			}
		}
		
		// if the object is sticking out to the left side of the lm
		// but still technically inside the lm
		if (obj.getMinX() < lm.getMinX() && obj.getMaxX() < lm.getMaxX()){
			planeX = obj.getMaxX() - lm.getMinX();
		}	
		// if the object is sticking out to the front of the lm
		// but still technically inside the lm
		if (obj.getMinY() > lm.getMinY() && obj.getMaxY() > lm.getMaxY()){
			planeY = lm.getMaxY() - obj.getMinY();
		}	
		// if the object is sticking out to the top of the lm
		// but still technically inside the lm
		if (obj.getMinZ() > lm.getMinZ() && obj.getMaxZ() > lm.getMaxZ()){
			planeZ = lm.getMaxZ() - obj.getMinZ();
		}
			
		
		
		// if the object is sticking out to the right side of the lm
		if (obj.getMaxX() > lm.getMaxX() && obj.getMinX() > lm.getMinX()){
			planeX = obj.getMaxX() - lm.getMaxX();
		}
		
		if (obj.getMinY() > lm.getMinY() && obj.getMaxY() > lm.getMaxY()){
			 planeY = lm.getMaxY() - obj.getMinY();
		}
		
		if (obj.getMinZ() < lm.getMinZ() && obj.getMaxZ() > lm.getMaxZ()){
			 planeZ = lm.getMaxZ() - obj.getMinZ();
		}
			
		
		
		insideVolume = planeX * planeY * planeZ;
		
		// volume of the inside part of the object divide with its full volume
		return insideVolume / objVolume;
	}
}