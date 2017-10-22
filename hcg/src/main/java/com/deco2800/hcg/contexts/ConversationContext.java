package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.deco2800.hcg.conversation.Conversation;
import com.deco2800.hcg.conversation.ConversationNode;
import com.deco2800.hcg.conversation.ConversationOption;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used in order to display conversations
 * @author Blake Bodycote
 *
 */
public class ConversationContext extends UIContext {
    private Table table; //table that holds all the conversation items
    private Label nodeText; //The text of the conversation
    private Label npcName; //Name of the NPC character 
    private HorizontalGroup buttons; //Group of buttons
    private Skin skin; //The skin
    private Image npcImage; //image of the NPC

	private static final Logger LOGGER = LoggerFactory.getLogger(ConversationContext.class);
	
    /**
     * Intialises the conversation context with a given conversation
     * @param conversation to display
     * @param npcFace the texture that textureManager will use to get the npcFace image
     */
    public ConversationContext(Conversation conversation, String npcFName, String npcFace){
		super();

		GameManager gameManager = GameManager.get();
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);
		
		skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		
		npcImage = new Image(textureManager.getTexture(npcFace));
		npcImage.setScale(0.2f, 0.65f);
		npcImage.setPosition(980, 800);
		nodeText = new Label("", skin);
		nodeText.setFontScale(1.5f);
		npcName = new Label(npcFName, skin);
		npcName.setFontScale(1.5f);

		buttons = new HorizontalGroup();
		buttons.space(30f);
		
		table = new Table();
		table.setBackground(new Image(textureManager.getTexture("conversation_context")).getDrawable());
		table.setFillParent(true);
		
		table.row().spaceBottom(140);
		table.row().padLeft(550);
		table.add(npcImage);
		table.row().padBottom(90);
		table.add(npcName);
		table.row().spaceTop(120);
		table.add(nodeText).padBottom(130);
		table.row().spaceBottom(20);
		table.row().space(50, 0, 50, 0);
		table.add(buttons).padBottom(100);
		stage.addActor(table);
		
	}

    /**
     * Called when the conversation needs to be displayed on the screen, dynamically alters the buttons/text displayed.
     * @param node - the conversationNode to display
     */
    public void displayNode(ConversationNode node) {

		// Clear old buttons & replace text
		LOGGER.error(this + node.getNodeText()); //DEBUG
    	nodeText.setText(node.getNodeText());
		buttons.clearChildren();

		// Add new buttons
		for (ConversationOption option : node.getValidOptions()) {
			LOGGER.error(this + " * " + option.getOptionText()); //DEBUG
			TextButton button = new TextButton(option.getOptionText(), skin);
			button.pad(20);
			button.getLabel().setFontScale(1.5f);
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
