package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.conversation.Conversation;
import com.deco2800.hcg.conversation.ConversationNode;
import com.deco2800.hcg.conversation.ConversationOption;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class ConversationContext extends UIContext {
    private Table table;
    private Label nodeText;
    private HorizontalGroup buttons;
    private Skin skin;

	public ConversationContext(Conversation conversation){
		super();

		GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
	            gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);

		skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		table = new Table();
		table.setFillParent(true);
		nodeText = new Label("", skin);
		buttons = new HorizontalGroup();
		table.add(nodeText);
		table.row();
		table.add(buttons);
		stage.addActor(table);

		table.debug(); //DEBUG

	}

    public void displayNode(ConversationNode node) {

		// Clear old buttons & replace text
		System.err.println(node.getNodeText()); //DEBUG
    	nodeText.setText(node.getNodeText());
		buttons.clearChildren();

		// Add new buttons
		for (ConversationOption option : node.getValidOptions()) {
			System.err.println(" * " + option.getOptionText()); //DEBUG
			TextButton button = new TextButton(option.getOptionText(), skin);
			button.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					option.activate();
				}
			});
			buttons.addActor(button);
		}

    } 
    	
    
}
