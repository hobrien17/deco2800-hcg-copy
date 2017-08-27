package com.deco2800.hcg.handlers;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.AbstractWorld;
import com.deco2800.hcg.worlds.DemoWorld;
import com.deco2800.hcg.entities.Clickable;
import com.deco2800.hcg.util.WorldUtil;

import java.util.Optional;

/**
 * Really crappy mouse handler for the game
 */
public class MouseHandler {

    private static String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * Constructor for the mouse handler
     */
    public MouseHandler() {
    }

    /**
     * Currently only handles objects on height 0
     */
    public void handleMouseClick(float x, float y) {
        System.out.printf("Clicked at %f %f%s", x, y, LINE_SEPARATOR);

        float projX = 0, projY = 0;

        projX = x / 64f;
        projY = -(y - 32f / 2f) / 32f + projX;
        projX -= projY - projX;

        Optional<AbstractEntity> closest = WorldUtil
                .closestEntityToPosition(projX, projY, 2f);
        if (closest.isPresent() && closest.get() instanceof Clickable) {
            ((Clickable) closest.get()).onClick();
        } else {
            AbstractWorld world = GameManager.get().getWorld();
            if (world instanceof DemoWorld) {
                ((DemoWorld) (world)).deSelectAll();
            }
        }
    }
}