package com.deco2800.hcg.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Tower that can do things.
 * @author leggy
 *
 */
public class Tower extends AbstractEntity implements Clickable, Tickable, Selectable {

	boolean selected = false;

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
	public Tower(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
		this.setTexture("tower");
	}

	/**
	 * On click handler
	 */
	@Override
	public void onClick() {
		System.out.println("Base got clicked");

		if (!selected) {
			selected = true;
		}
	}

	/**
	 * On Tick handler
	 * @param i time since last tick
	 */
	@Override
	public void onTick(int i) {

		if (selected) {
			this.setTexture("tree_selected");
		} else {
			this.setTexture("tower");
		}
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void deselect() {
		selected = false;
	}

	@Override
	public Button getButton() {
		Button button = new TextButton("Make Peon", new Skin(Gdx.files.internal("resources/uiskin.json")));
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buttonWasPressed();
			}
		});
		return button;
	}

	@Override
	public void buttonWasPressed() {
		System.out.println("Button was pressed for " + this);
		/* We probably don't want these in random spots */
		//currentAction = Optional.of(new GenerateAction(new Peon(this.getParent(), rand.nextInt(24), rand.nextInt(24), 0), this.getParent()));
	}
}
