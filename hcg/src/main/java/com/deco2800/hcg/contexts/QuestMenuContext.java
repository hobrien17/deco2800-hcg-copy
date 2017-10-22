package com.deco2800.hcg.contexts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.quests.QuestArchive;
import com.deco2800.hcg.quests.QuestManager;

public class QuestMenuContext extends UIContext {

	private Table main;
	private Window logWindow;
	private Table readyTable;
	private Table readyListTable;
	private Table activeTable;
	private Table activeListTable;
	private Table completedTable;
	private Table completedListTable;
	private Window secondaryWindow;
	private Table nameTable;
	private Table detailsTable;
	private Table detailsTextTable;
	private List<String> readyList;
	private ScrollPane readyQuestsPane;
	private List<String> activeList;
	private ScrollPane activeQuestsPane;
	private List<String> completedList;
	private ScrollPane completedQuestsPane;
	private String readyTitle;
	private String activeTitle;
	private String completedTitle;
	private String nameTitle;
	private String detailsTitle;
	private TextArea detailsText;
	private ImageButton questsBack;
	private Table killRequire;
	private String enemyType;
	private String enemyAmountText;
	private Integer enemyAmount;
	private Table itemRequire;
	private String itemType;
	private String itemAmountText;
	private Integer itemAmount;
	
	public QuestMenuContext() {
		
		GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);
		QuestManager questManager = (QuestManager) 
				gameManager.getManager(QuestManager.class);
		
		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		
		//Create all Tables
		main = new Table(skin);
        main.setFillParent(true);
        main.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
        
        logWindow = new Window("Quest Log", skin);
        
        readyTable = new Table(skin);
        
        readyListTable = new Table(skin);
        
        activeTable = new Table(skin);
        
        activeListTable = new Table(skin);
        
        completedTable = new Table(skin);
        
        completedListTable = new Table(skin);
        
        secondaryWindow = new Window("Quest Details", skin);
        
        nameTable = new Table(skin);
        
        detailsTable = new Table(skin);
        
        detailsTextTable = new Table(skin);    
        
        killRequire = new Table(skin);
        
        itemRequire = new Table(skin);
		
		//Create Image Button
		questsBack = new ImageButton(new Image(textureManager.getTexture("instructions_back_button")).getDrawable());
		
		//Collect the data from the quest manager
		ArrayList<QuestArchive> completedQuests = questManager.getCompletedQuests();
		HashMap<QuestNPC,QuestArchive> completableQuests = questManager.getCompleteableQuests();
		HashMap<QuestNPC,QuestArchive> unCompleteableQuests = questManager.getUnCompleteableQuests();
		
		//Create all Lists
		readyList = new List<>(skin);
        //Turn the data into readable
        int readyCounter = 0;
        String[] readyStringList = new String[completableQuests.size()];
        for (QuestArchive qa: completableQuests.values()) {
        	readyStringList[readyCounter] = qa.getQuestTitle();
        	readyCounter++;
        }
        readyList.setItems(readyStringList);
        readyQuestsPane = new ScrollPane(readyList);
        readyQuestsPane.setSmoothScrolling(false);
        readyQuestsPane.setDebug(false);
        
        activeList = new List<>(skin);
        int questCounter = 0;
        String[] questStringList = new String[unCompleteableQuests.size()];
        for (QuestArchive qa: unCompleteableQuests.values()) {
        	questStringList[questCounter] = qa.getQuestTitle();
        	questCounter++;
        }

        activeList.setItems(questStringList);

        activeQuestsPane = new ScrollPane(activeList);
        activeQuestsPane.setSmoothScrolling(false);
        activeQuestsPane.setDebug(false);
        
        completedList = new List<>(skin);
        String[] completedStringList = new String[completedQuests.size()];
        int completedCounter = 0;
        for (QuestArchive qa: completedQuests) {
        	completedStringList[completedCounter] = qa.getQuestTitle();
        	completedCounter++;
        }
        completedList.setItems(completedStringList);
        completedQuestsPane = new ScrollPane(completedList);
        completedQuestsPane.setSmoothScrolling(false);
        completedQuestsPane.setDebug(false);
        
        
        //Create all Strings		
		readyTitle = new String("Return to quest giver:");
		activeTitle = new String("Current Quests:");
		completedTitle = new String("Finished Quests:");
		nameTitle = new String("Quest Name: ");
		detailsTitle = new String("Details:");
		
		//TODO Added the new Strings here
		enemyType = new String("Enemy Type: ");
		enemyAmountText = new String("Kill Amount Required: ");
		itemType = new String("Item Type: ");
		itemAmountText = new String("Item Amount Required: ");
		
		//TODO Added the new Integers here
		enemyAmount = new Integer(0);
		itemAmount = new Integer(0);
		
		detailsText = new TextArea("Click on a Quest to Display the Details", skin);
		detailsText.setDisabled(true);
		detailsText.setFillParent(true);
		
        //questManager.getQuest(readyList.getItems().first()).getTitle()
        
        logWindow.setTransform(true);
        
        readyTable.add(readyTitle);
        logWindow.add(readyTable).expandX().fill();
        logWindow.row();
        readyListTable.add(readyQuestsPane).expand().fill();
        logWindow.add(readyListTable).expandX().fill();
        logWindow.row();
        activeTable.add(activeTitle);
        logWindow.add(activeTable).expandX().fill();
        logWindow.row();
        activeListTable.add(activeQuestsPane).expand().fill();
        logWindow.add(activeListTable).expandX().fill();
        logWindow.row();
        logWindow.row();
        completedTable.add(completedTitle);
        logWindow.add(completedTable).expandX().fill();
        logWindow.row();
        completedListTable.add(completedQuestsPane).expand().fill();
        logWindow.add(completedListTable).expandX().fill();
        
        nameTable.add(nameTitle);
        secondaryWindow.add(nameTable).expandX().fill();
        secondaryWindow.row();
        detailsTable.add(detailsTitle);
        secondaryWindow.add(detailsTable).expandX().fill();
        secondaryWindow.row();
        detailsTextTable.add(detailsText).expand().fill();
        secondaryWindow.add(detailsTextTable).expand().fill();
        secondaryWindow.row();
        killRequire.add(enemyType);
        killRequire.row();
        killRequire.add(enemyAmountText);
        killRequire.add(enemyAmount.toString());
        secondaryWindow.add(killRequire).expandX().fillX().left();
        secondaryWindow.row().padTop(10);
        itemRequire.add(itemType);
        itemRequire.row();
        itemRequire.add(itemAmountText);
        itemRequire.add(itemAmount.toString());
        secondaryWindow.add(itemRequire).expandX().fillX().left();
        main.add(logWindow).expand(1, 1).fill();
        main.add(secondaryWindow).expand(4, 1).fill();
        main.row();
        main.add(questsBack).padTop(20);
        stage.addActor(main);
        
        questsBack.center();
        
        //main.debug();
		
		questsBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});
		
		readyList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle.concat("Ready Quest");
				if(questManager.getQuest(readyList.getSelected()) != null){
					detailsText.setText(questManager.getQuest(readyList.getSelected()).getDescription());
				}
				//TODO Change the concats to whatever you want them to be
				enemyType.concat("Enemy");
				enemyAmountText.concat(enemyAmount.toString());
				itemType.concat("Item");
				itemAmountText.concat(itemAmount.toString());
			}
		});
		
		activeList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle.concat("Active Quest");
				if(questManager.getQuest(activeList.getSelected()) != null){
					detailsText.setText(questManager.getQuest(activeList.getSelected()).getDescription());
				}
				//TODO Change the concats to whatever you want them to be
				enemyType.concat("Enemy");
				enemyAmountText.concat(enemyAmount.toString());
				itemType.concat("Item");
				itemAmountText.concat(itemAmount.toString());
			}
		});
		
		completedList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle.concat("Completed Quest");
				if(questManager.getQuest(completedList.getSelected()) != null){
					detailsText.setText(questManager.getQuest(completedList.getSelected()).getDescription());
				}
				//TODO Change the concats to whatever you want them to be
				enemyType.concat("Enemy");
				enemyAmountText.concat(enemyAmount.toString());
				itemType.concat("Item");
				itemAmountText.concat(itemAmount.toString());
			}
		});
			
	}
		
}
