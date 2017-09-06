package com.deco2800.hcg.managers;

import com.deco2800.hcg.buffs.Buff;
import java.util.*;

/**
 * The BuffManager keeps track of the Buffs applied to a given entity.
 *
 * author: danie
 */
public class BuffManager {

    private ArrayList<Buff> buffs;
    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");
    /**
     * Creates a new BuffManager which contains no buffs.
     *
     */
    public BuffManager() {
        buffs = new ArrayList<>();
    }

    /**
     * Adds the given buff to the BuffManager if it does not already exist
     *
     * @param buff the buff to add
     */
    public void addBuff(Buff buff) {
        if (!buffs.contains(buff)) {
            buffs.add(buff);
        }
    }

    /**
     * Removes the buff from the BuffManager if it exists
     *
     * @param buffName the name of the buff to remove
     */
    public void removeBuff(String buffName) {
        for (Buff b : buffs) {
            if (b.getName().equals(buffName)) {
                buffs.remove(b);
            }
        }
    }

    /**
     * Returns the buffs in the BuffManager with the given name.
     *
     * @param buffName the name of the buff to get
     * @return the buff with the given name
     */
    public ArrayList<Buff> getBuff(String buffName) {

        ArrayList<Buff> returnList = new ArrayList<>();
        for (Buff b : buffs) {
            if (b.getName().equals(buffName)) {
                returnList.add(b);
            }
        }

        return returnList;
    }

    /**
     * Returns the Buffs in the BuffManager.
     *
     * @return a list of Buffs in theBuffManager
     */
    public ArrayList<Buff> getBuffs() {

        ArrayList<Buff> returnList = new ArrayList<>();
        for (Buff b : buffs) {
                returnList.add(b);
        }
        return returnList;
    }

    /**
     * Removes all buffs from the BuffManager
     */
    public void clearBuffs() {
        buffs.clear();
    }

    @Override
    public String toString() {
        String returnString = new String();
        for (Buff b : buffs) {
            returnString += b.getName() + LINE_SEPARATOR;
        }

        return returnString;
    }
}
