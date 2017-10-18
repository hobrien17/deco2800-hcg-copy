package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class QuestMenuContext extends UIContext {

	private Color tableColor;
	private Table main;
	private Window logWindow;
	private Table readyTable;
	private Table readyListTable;
	private Table activeTable;
	private Table activeListTable;
	private Table inactiveTable;
	private Table inactiveListTable;
	private Table completedTable;
	private Table completedListTable;
	private Window secondaryWindow;
	private Table nameTable;
	private Table blurbTable;
	private Table blurbTextTable;
	private Table detailsTable;
	private Table detailsTextTable;
	private List<String> readyList;
	private ScrollPane readyQuestsPane;
	private List<String> activeList;
	private ScrollPane activeQuestsPane;
	private List<String> inactiveList;
	private ScrollPane inactiveQuestsPane;
	private List<String> completedList;
	private ScrollPane completedQuestsPane;
	private String questLogTitle;
	private String readyTitle;
	private String readyQuest;
	private String activeTitle;
	private String activeQuest;
	private String inactiveTitle;
	private String inactiveQuest;
	private String completedTitle;
	private String completedQuest;
	private String nameTitle;
	private String blurbTitle;
	private String blurbText;
	private String detailsTitle;
	private String detailsText;
	private ImageButton questsBack;
	
	public QuestMenuContext() {
		
		GameManager gameManager = GameManager.get();
		ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
		TextureManager textureManager = (TextureManager) 
				gameManager.getManager(TextureManager.class);
		
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
        
        inactiveTable = new Table(skin);
        
        inactiveListTable = new Table(skin);
        
        completedTable = new Table(skin);
        
        completedListTable = new Table(skin);
        
        secondaryWindow = new Window("Quest Details", skin);
        
        nameTable = new Table(skin);
        
        blurbTable = new Table(skin);
        
        blurbTextTable = new Table(skin);
        
        detailsTable = new Table(skin);
        
        detailsTextTable = new Table(skin);
        
        tableColor = new Color(Color.BROWN);
        
        //Create all Strings		
		questLogTitle = new String("Quest Log");
		readyTitle = new String("Ready to Complete:");
		readyQuest = new String("Quest");
		activeTitle = new String("Active:");
		activeQuest = new String("Quest");
		inactiveTitle = new String("Inactive:");
		inactiveQuest = new String("Quest");
		completedTitle = new String("Completed:");
		completedQuest = new String("Quest");
		nameTitle = new String("Quest Name");
		blurbTitle = new String("Blurb:");
		blurbText = new String("Blurb text goes in here");
		detailsTitle = new String("Details:");
		detailsText = new String("Details text goes in here");
		
		//Create Image Button
		questsBack = new ImageButton(new Image(textureManager.getTexture("instructions_back_button")).getDrawable());
		
		//Create all Lists
		readyList = new List<String>(skin);
        readyList.setItems(readyQuest);
        readyQuestsPane = new ScrollPane(readyList);
        readyQuestsPane.setSmoothScrolling(false);
        readyQuestsPane.setDebug(false);
        
        activeList = new List<String>(skin);
        activeList.setItems(activeQuest);
        activeQuestsPane = new ScrollPane(activeList);
        activeQuestsPane.setSmoothScrolling(false);
        activeQuestsPane.setDebug(false);
        
        inactiveList = new List<String>(skin);
        inactiveList.setItems(inactiveQuest);
        inactiveQuestsPane = new ScrollPane(inactiveList);
        inactiveQuestsPane.setSmoothScrolling(false);
        inactiveQuestsPane.setDebug(false);
        
        completedList = new List<String>(skin);
        completedList.setItems(completedQuest);
        completedQuestsPane = new ScrollPane(completedList);
        completedQuestsPane.setSmoothScrolling(false);
        completedQuestsPane.setDebug(false);
        
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
        inactiveTable.add(inactiveTitle);
        logWindow.add(inactiveTable).expandX().fill();
        logWindow.row();
        inactiveListTable.add(inactiveQuestsPane).expand().fill();
        logWindow.add(inactiveListTable).expandX().fill();
        logWindow.row();
        completedTable.add(completedTitle);
        logWindow.add(completedTable).expandX().fill();
        logWindow.row();
        completedListTable.add(completedQuestsPane).expand().fill();
        logWindow.add(completedListTable).expandX().fill();
        
        nameTable.add(nameTitle);
        secondaryWindow.add(nameTable).expandX().fill();
        secondaryWindow.row();
        blurbTable.add(blurbTitle);
        secondaryWindow.add(blurbTable).expandX().fill();
        secondaryWindow.row();
        blurbTextTable.add(blurbText);
        secondaryWindow.add(blurbTextTable).expandX().fill();
        secondaryWindow.row();
        detailsTable.add(detailsTitle);
        secondaryWindow.add(detailsTable).expandX().fill();
        secondaryWindow.row();
        detailsTextTable.add(detailsText);
        secondaryWindow.add(detailsTextTable).expandX().fill();
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
		
	}
	
}
