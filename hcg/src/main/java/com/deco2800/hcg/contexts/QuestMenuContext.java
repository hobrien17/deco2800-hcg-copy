package com.deco2800.hcg.contexts;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.enemyentities.EnemyType;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
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
	private String killTitle;
	private HashMap<String, Integer> killList;
	private Table itemRequire;
	private String itemTitle;
	private HashMap<String, Integer> itemList;
	private String rewardTitle;
	private Table itemReward;
	private HashMap<String, Integer> rewardList;


	
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
        
        itemReward = new Table(skin);
		
		//Create Image Button
		questsBack = new ImageButton(new Image(textureManager.getTexture("instructions_back_button")).getDrawable());
		
		//Collect the data from the quest manager
		java.util.List<QuestArchive> completedQuests = questManager.getCompletedQuests();
		Map<QuestNPC,QuestArchive> completableQuests = questManager.getCompleteableQuests();
		Map<QuestNPC,QuestArchive> unCompleteableQuests = questManager.getUnCompleteableQuests();
		
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
		readyTitle = "Return to quest giver:";
		activeTitle = "Current Quests:";
		completedTitle = "Finished Quests:";
		nameTitle = "Quest Name: ";
		detailsTitle = "Details:";
		
		//New Strings (Titles to separate the parts)
		killTitle = "Kill Requirements";
		itemTitle = "Item Requirements";
		rewardTitle = "Item Rewards";
		
		//New HashMaps which take a String for the enemy/item and a Integer for the amount
		killList = new HashMap<>();
		itemList = new HashMap<>();
		rewardList = new HashMap<>();
		
		detailsText = new TextArea("Click on a Quest to Display the Details", skin);
		detailsText.setDisabled(true);
		detailsText.setFillParent(true);
		
        //questManager.getQuest(readyList.getItems().first()).getTitle()
        
        logWindow.setTransform(true);
        
        readyTable.add(readyTitle).align(0);
        logWindow.add(readyTable).expandX().fill();
        logWindow.row();
        readyListTable.add(readyQuestsPane).expand().fill();
        logWindow.add(readyListTable).expandX().fill();
        logWindow.row();
        activeTable.add(activeTitle).align(0);
        logWindow.add(activeTable).expandX().fill();
        logWindow.row();
        activeListTable.add(activeQuestsPane).expand().fill();
        logWindow.add(activeListTable).expandX().fill();
        logWindow.row();
        logWindow.row();
        completedTable.add(completedTitle).align(0);
        logWindow.add(completedTable).expandX().fill();
        logWindow.row();
        completedListTable.add(completedQuestsPane).expand().fill();
        logWindow.add(completedListTable).expandX().fill();
        nameTable.add(nameTitle).align(0);
        secondaryWindow.add(nameTable).expandX().fill();
        secondaryWindow.row();
        detailsTable.add(detailsTitle).align(0);
        secondaryWindow.add(detailsTable).expandX().fill();
        secondaryWindow.row();
        detailsTextTable.add(detailsText).expand().fill();
        secondaryWindow.add(detailsTextTable).expand().fill();
        secondaryWindow.row().padTop(10);
        secondaryWindow.add(killRequire).expandX().fillX().left();
        secondaryWindow.row().padTop(10);
        secondaryWindow.add(itemRequire).expandX().fillX().left();
        secondaryWindow.row().padTop(10);
        secondaryWindow.add(itemReward).expandX().fillX();
        main.add(logWindow).expand(1, 1).fill();
        main.add(secondaryWindow).expand(4, 1).fill();
        main.row();
        main.add(questsBack).padTop(20);
        stage.addActor(main);
        
        questsBack.center();
		
		questsBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});

		readyList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle = "Ready Quest";
				if(questManager.getQuest(readyList.getSelected()) != null){
					detailsText.setText(questManager.getQuest(readyList.getSelected()).getDescription());
					for (Map.Entry<QuestNPC,QuestArchive> map: questManager.getCompleteableQuests().entrySet()) {
						//Add each quest
						if (map.getValue().getQuestTitle().equals(readyList.getSelected())) {
							populateReqTable(map.getValue());
							updateTable(map.getValue());
						}
					}
				}
			}
		});
		
		activeList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle = "Active Quest";
				if(questManager.getQuest(activeList.getSelected()) != null){
					detailsText.setText(questManager.getQuest(activeList.getSelected()).getDescription());
					for (Map.Entry<QuestNPC,QuestArchive> map: questManager.getUnCompleteableQuests().entrySet()) {
						//Add each quest
						if (map.getValue().getQuestTitle().equals(activeList.getSelected())) {
							populateReqTable(map.getValue());
							updateTable(map.getValue());
						}
					}
				}
			}
		});
		
		completedList.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				nameTitle = "Completed Quest";
				if(questManager.getQuest(completedList.getSelected()) != null){
					detailsText.setText(questManager.getQuest(completedList.getSelected()).getDescription());
					for (QuestArchive qa: questManager.getCompletedQuests()) {
						//Add each quest
						if (qa.getQuestTitle().equals(completedList.getSelected())) {
							populateReqTable(qa);
							updateTable(qa);
						}
					}
				}
			}
		});
			
	}

	private void populateReqTable(QuestArchive qa) {
		//Clean the current req lists
		killList = new HashMap<>();
		itemList = new HashMap<>();
		rewardList = new HashMap<>();
		for (Map.Entry<EnemyType, Integer> map : qa.getQuest().getKillRequirement().entrySet()) {
			killList.put(map.getKey().toString(), map.getValue());
		}
		for (Map.Entry<String, Integer> map : qa.getQuest().getItemRequirement().entrySet()) {
			itemList.put(map.getKey().replace("_"," "), map.getValue());
		}
		for (Map.Entry<String, Integer> map : qa.getQuest().getRewards().entrySet()) {
			rewardList.put(map.getKey().replace("_"," "), map.getValue());
		}
	}

	private void updateTable(QuestArchive qa) {
		updateKillTable(qa);
		updateItemTable();
		updateRewardTable();
	}

	private void updateKillTable(QuestArchive qa) {
		killRequire.clear();
		killRequire.add(killTitle);
		Player player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
		for (Map.Entry<String,Integer> killMap: killList.entrySet()) {
			killRequire.row();
			EnemyType et = EnemyType.valueOf(killMap.getKey());
			if (player.killLogGet(et) >=
					qa.getInitalKillLog().get(et) + qa.getQuest().getKillRequirement().get(et)) {
				killRequire.add(killMap.getKey() + " = REQUISITE MET");
			} else {
				Integer killsGot = player.killLogGet(et) - qa.getInitalKillLog().get(et);
				killRequire.add(killMap.getKey() + " = " + killsGot.toString() + " of "  +
						qa.getQuest().getKillRequirement().get(et));
			}
		}
	}

	private void updateItemTable() {
		itemRequire.clear();
		itemRequire.add(itemTitle);
		Player player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
		for (Map.Entry<String,Integer> itemMap: itemList.entrySet()) {
			itemRequire.row();
			if (player.getInventory().numberOf(itemMap.getKey()) >= itemMap.getValue()) {
				itemRequire.add(itemMap.getKey() + " = " + player.getInventory().numberOf(itemMap.getKey()) +
						" of " + itemMap.getValue().toString() + " - REQUISITE MET");
			} else {
				itemRequire.add(itemMap.getKey() + " = " + player.getInventory().numberOf(itemMap.getKey()) +
								" of " + itemMap.getValue().toString());
			}

		}
	}

	private void updateRewardTable() {
		itemReward.clear();
		itemReward.add(rewardTitle);
		for (Map.Entry<String,Integer> itemMap: rewardList.entrySet()) {
			itemReward.row();
			itemReward.add(itemMap.getValue().toString() + " " + itemMap.getKey());
		}
	}
		
}
