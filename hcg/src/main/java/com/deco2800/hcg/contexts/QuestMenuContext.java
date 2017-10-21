package com.deco2800.hcg.contexts;

import java.util.ArrayList;
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
		
		//Create Image Button
		questsBack = new ImageButton(new Image(textureManager.getTexture("instructions_back_button")).getDrawable());
		
		//Collect the data from the quest manager
		ArrayList<QuestArchive> completedQuests = questManager.getCompletedQuests();
		HashMap<QuestNPC,QuestArchive> completableQuests = questManager.getCompleteableQuests();
		HashMap<QuestNPC,QuestArchive> unCompleteableQuests = questManager.getUnCompleteableQuests();
		
		//Create all Lists
		readyList = new List<String>(skin);
        
        //Turn the data into readable
        int readyCounter = 0;
        String[] readyStringList = new String[completableQuests.size()];
        for (QuestArchive qa: completableQuests.values()) {
        	readyStringList[readyCounter] = qa.getQuestTitle();
        	readyCounter++;
        }
        readyList.setItems("Ready Test");
        readyQuestsPane = new ScrollPane(readyList);
        readyQuestsPane.setSmoothScrolling(false);
        readyQuestsPane.setDebug(false);
        
        activeList = new List<String>(skin);
        
        int questCounter = 0;
        String[] questStringList = new String[unCompleteableQuests.size()];
        for (QuestArchive qa: unCompleteableQuests.values()) {
        	questStringList[questCounter] = qa.getQuestTitle();
        	questCounter++;
        }
        activeList.setItems("Active Test");
        activeQuestsPane = new ScrollPane(activeList);
        activeQuestsPane.setSmoothScrolling(false);
        activeQuestsPane.setDebug(false);
        
        completedList = new List<String>(skin);
        String[] completedStringList = new String[completedQuests.size()];
        int completedCounter = 0;
        for (QuestArchive qa: completedQuests) {
        	completedStringList[completedCounter] = qa.getQuestTitle();
        	completedCounter++;
        }
        completedList.setItems("Completed Test");
        completedQuestsPane = new ScrollPane(completedList);
        completedQuestsPane.setSmoothScrolling(false);
        completedQuestsPane.setDebug(false);
        
        
        //Create all Strings		
		readyTitle = new String("Ready to Complete:");
		activeTitle = new String("Quests:");
		completedTitle = new String("Completed:");
		nameTitle = new String("Quest Name: ");
		detailsTitle = new String("Details:");
		
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
        
        main.add(logWindow).expand(1, 1).fill();
        main.add(secondaryWindow).expand(4, 1).fill();
        main.row();
        main.add(questsBack).padTop(20);
        stage.addActor(main);
        
        questsBack.center();
        
        main.debug();
		
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
				detailsText.setText("I am testing whether or not the text will run down or whether it will "
						+ "stop at a certain point. Hello my name is Dylan, I hate Java with a burning passion.");
			}
		});
		
		activeList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle.concat("Active Quest");
				detailsText.setText("Active Quest Details");
			}
		});
		
		completedList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle.concat("Completed Quest");
				detailsText.setText("Completed Quest Details");
			}
		});
			
	}
		
}
