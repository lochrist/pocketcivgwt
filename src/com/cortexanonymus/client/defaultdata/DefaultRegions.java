package com.cortexanonymus.client.defaultdata;

import java.util.Vector;

import com.cortexanonymus.client.model.Region;


public class DefaultRegions {  
  static public Vector generateRegions() {
    Vector regions = new Vector();
    // Region 1
    int regionIndex = 0;
    Region reg = new Region( regionIndex++ );    
    reg.setHasForest(true);
    reg.setHasMountain(true);    
    reg.setHasCrack(true);
    regions.add(reg);
        
    // region2
    reg = new Region( regionIndex++ );
    reg.setHasForest(true);
    regions.add(reg);
    
    // region3
    reg = new Region( regionIndex++ );
    reg.setHasForest(true);
    regions.add(reg);
    
    // region4
    reg = new Region( regionIndex++ );
    reg.setHasMountain(true);    
    regions.add(reg);
    
    //  region5
    reg = new Region( regionIndex++ );
    reg.setHasMountain(true);
    reg.setHasForest(true); 
    regions.add(reg);
    
    // region6
    reg = new Region( regionIndex++ );
    reg.setHasForest(true);
    regions.add(reg);
    
    // region7
    reg = new Region( regionIndex++ );    
    reg.setHasMountain(true);
    regions.add(reg);
    
    // region8
    reg = new Region( regionIndex++ );
    reg.setHasDesert(true);
    reg.setHasMountain(true);
    regions.add(reg);
    
    return regions;
  }
}
