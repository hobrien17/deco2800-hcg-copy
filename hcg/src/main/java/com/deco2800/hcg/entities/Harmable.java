package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Effect;

import java.util.Collection;

public interface Harmable {
    //TODO Whatever harmable things need to be able to do

    // Method to cause an effect
    void giveEffect(Effect effect);

    // Method to cause multiple effects at once
    void giveEffect(Collection<Effect> effects);
}
