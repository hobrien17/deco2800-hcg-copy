package com.deco2800.hcg.contexts;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.deco2800.hcg.conversation.Conversation;
import com.deco2800.hcg.conversation.ConversationNode;
import com.deco2800.hcg.conversation.ConversationOption;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class ConversationContext extends UIContext {
    private Table table;
    private Label nodeText;
    private List<ConversationOption> nodeOptions;
    private HorizontalGroup buttons;
    private TextButtonStyle style;
	
	public ConversationContext(Conversation x){
		super();
		GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
	            gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);

		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		
		nodeOptions = new ArrayList<ConversationOption>();
		style = new TextButtonStyle();
		
		table = new Table();
		buttons = new HorizontalGroup();
		table.add(nodeText);
		table.add(buttons);
		stage.addActor(table);
		
		for(int i = 0; i< buttons.getChildren().size; i++){
			int index = i;
			buttons.getChildren().get(index).addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					nodeOptions.get(index).activate();
				}
			});
		}
	}

    public void displayNode(ConversationNode node) {
    	this.nodeText.setText(node.getNodeText());
    	nodeOptions.clear();
    	
    	for(int i = 0; i<node.getOptions().size(); i++){
    		String optionText = node.getOptions().get(i).getOptionText();
    		buttons.addActor(new TextButton(optionText, style));
    		nodeOptions.add(node.getOptions().get(i));
    	}

    } 
    	
    
}
