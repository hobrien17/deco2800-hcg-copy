package com.deco2800.hcg.entities;


/**
 * Enum set of weapon types
 * Contains shot cooldown and number of pellets fired
 * 
 * Potential for majority of weapon info to be stored
 * either within this enum or
 * built in through factory patterns
 * 
 * @author Bodhi Howe - Sinquios
 */
public enum WeaponType {
    MACHINEGUN (5, 1),
    SHOTGUN (30, 6),
    STARFALL(50, 30);

    private final int counter;
    private final int pellets;
    
    WeaponType(int counter, int pellets) {
        this.counter = counter;
        this.pellets = pellets;
    }
    
    public int getCounter() {
        return counter;
    }
    
    public int getPellets() {
        return pellets;
    }
}
