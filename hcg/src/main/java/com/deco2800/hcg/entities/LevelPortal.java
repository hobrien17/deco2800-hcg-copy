package com.deco2800.hcg.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;

import java.util.List;

/**
 * Tower that can do things.
 * @author leggy
 *
 */
public class LevelPortal extends AbstractEntity implements Tickable {

    private SoundManager soundManager;
    /**
     * Constructor for the base
     *
     * @param posX
     *            The x-coordinate.
     * @param posY
     *            The y-coordinate.
     * @param posZ
     *            The z-coordinate.
     */
    public LevelPortal(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
        this.setTexture("levelPortal");
        this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);

    }


    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {


        List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
        boolean atTeleport = false;
        for (AbstractEntity entity : entities) {

            if(entity instanceof Player && this.getBox3D().overlaps(entity.getBox3D())) {
                atTeleport = true;
                break;
            }

        }
        if (atTeleport) {
            soundManager.playSound("teleport");

        }

    }

}
