package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.MessageManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.multiplayer.ChatMessage;

/**
 * UI for Multiplayer Lobby. Made by Leon Zheng from Team 9
 */
public class LobbyContext extends UIContext{

    private ImageButton start;
    private ImageButton back;
    private ImageButton send;
    private Table main;
    private Table playerTable;
    private Table labelTable;
    private Table titleTable;
    private Table chatTable;
    private Label lobbyLabel;
    private Label members;
    private Image lobbyTitle;
    private Image separator1;
    private Image separator2;
    private Image playerPortrait1;
    private Image playerPortrait2;
    private Image playerPortrait3;
    private Image playerPortrait4;
    private CheckBox readyCheckBox;
    private Stack player1;
    private Stack player2;
    private Stack player3;
    private Stack player4;
    private TextField chatTextField;
    private TextField lobbyNameTextfield;
    private TextArea chatTextArea;
    private Dialog hostName;
    private TextButton changeLobbyName;
    private TextButton hostNameAdd;
    private TextButton hostNameExit;
    private String chatString = "";

    /**
     * Lobby UI constructor, initializes the entire UI
     */
    public LobbyContext() {

        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        MessageManager messageManager = (MessageManager) gameManager.getManager(MessageManager.class);
        NetworkManager networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        main = new Table();
        playerTable = new Table();
        labelTable = new Table();
        titleTable = new Table();
        chatTable = new Table();
        main.setFillParent(true);
        main.setBackground(new Image(textureManager.getTexture("multi_menu_background")).getDrawable());
        main.setDebug(false); //display lines for debugging
        //images set up
        lobbyTitle = new Image(textureManager.getTexture("lobby_title"));
        separator1 = new Image(textureManager.getTexture("lobby_separator"));
        separator2 = new Image(textureManager.getTexture("lobby_separator"));
        playerPortrait1 = new Image(textureManager.getTexture("lobby_image_frame"));
        playerPortrait2 = new Image(textureManager.getTexture("lobby_image_frame"));
        playerPortrait3 = new Image(textureManager.getTexture("lobby_image_frame"));
        playerPortrait4 = new Image(textureManager.getTexture("lobby_image_frame"));
        //buttons set up
        back = new ImageButton(new Image(textureManager.getTexture("lobby_back_button")).getDrawable());
        start = new ImageButton(new Image(textureManager.getTexture("lobby_start_button")).getDrawable());
        send = new ImageButton(new Image(textureManager.getTexture("lobby_send_button")).getDrawable());
        readyCheckBox = new CheckBox("Ready", skin);
        //label initialize
        lobbyLabel = new Label("Lobby Name: " + networkManager.getLobbyName(), skin);
        members = new Label("Members: ", skin);
        //player portrait stack set up
        player1 = new Stack(playerPortrait1);
        player2 = new Stack(playerPortrait2);
        player3 = new Stack(playerPortrait3);
        player4 = new Stack(playerPortrait4);
        //chat UI setup
        chatTextField = new TextField("", skin);
        chatTextArea = new TextArea("", skin);
        chatTextArea.setDisabled(true);
        //LobbyName Change
        changeLobbyName = new TextButton("Change", skin);
        hostName = new Dialog("Enter new lobby name", skin);
        hostNameAdd = new TextButton("Change", skin);
        hostNameExit = new TextButton("Back", skin);
        lobbyNameTextfield = new TextField("", skin);
        hostName.add(lobbyNameTextfield).expandX();
        hostName.add(hostNameAdd);
        hostName.row();
        hostName.add(hostNameExit);

        //body of GUI
        main.row().height(90);
        titleTable.add(back).expandX().left();
        titleTable.add(lobbyTitle).expandX();

        
        // FIXME
        if (networkManager.isHost()) {
            titleTable.add(start).expandX().right();
        }
        
        main.add(titleTable).fill();
        main.row();
        main.add(separator1).colspan(4).fill();
        main.row();
        labelTable.add(lobbyLabel).left().padLeft(50);
        labelTable.add(changeLobbyName).expandX();
        labelTable.add(readyCheckBox).expandX().right().padRight(50);
        main.add(labelTable).fill();
        main.row();
        main.add(members).left();
        main.row().expandX().padBottom(20).padTop(20);
        playerTable.add(player1).expand().uniform();
        playerTable.add(player2).expand().uniform();
        playerTable.add(player3).expand().uniform();
        playerTable.add(player4).expand().uniform();
        main.add(playerTable).fill();
        main.row();
        main.add(separator2).fill();
        main.row().expand();
        main.add(chatTextArea).fill();
        main.row();
        chatTable.add(chatTextField).fill().expandX().left();
        chatTable.add(send).right();
        main.add(chatTable).fill();

        stage.addActor(main);
        
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                networkManager.startGame();
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

        changeLobbyName.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hostName.show(stage);
            }
        });

        hostNameAdd.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                networkManager.setLobbyName(lobbyNameTextfield.getText());
                lobbyNameTextfield.setText("");
                lobbyLabel.setText("Lobby Name: " + networkManager.getLobbyName());
                hostName.hide();
            }
        });

        hostNameExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                lobbyNameTextfield.setText("");
                hostName.hide();
            }
        });

		/*
		 * Setup inputs for the buttons and the game itself
		 */
        send.addListener(new ChangeListener() {
            @Override
			public void changed(ChangeEvent event, Actor actor) {
				if (!networkManager.isMultiplayerGame()) {
					return;
				}
				
				if (chatString.trim().length() > 0) {
					networkManager.queueMessage(
							new ChatMessage(chatTextField.getText()));
					chatTextArea.appendText(chatTextField.getText() + "\n");
					chatTextField.setText("");
					stage.setKeyboardFocus(null);
					chatString = "";
					return;
				}
				chatTextField.setText("");
				chatTextField.setCursorPosition(0);
				chatString = "";
			}
        });

        /*
        	Input Listener for Textfield
         */
        chatTextField.setTextFieldListener(new TextField.TextFieldListener() { //textfield Listener
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c != '\b') {
                    chatString += Character.toString(c);
				} else if (chatString.length() > 0) {
					chatString = chatString.substring(0,
							chatString.length() - 1);
				}

				if (c != '\r') {
					return;
				}

				if (chatString.trim().length() > 0) {
					networkManager.queueMessage(
							new ChatMessage(chatTextField.getText()));
					chatTextArea.appendText(chatTextField.getText() + "\n");
				} else {
					chatTextField.setCursorPosition(0);
				}
				chatTextField.setText("");
				chatString = "";
				stage.setKeyboardFocus(null);
			}
		});

        messageManager.addChatMessageListener(this::handleChatMessage);
    }
    
    private void handleChatMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }

    /**
     * adds the new player's portrait into the current player stack. The 1st player will
     * always be the host.
     * @param playerId
     * @param playerImage
     */
    public void addPlayer(int playerId, Image playerImage) {
        switch (playerId) {
            case 1 :
                player1.add(playerImage);
                break;
            case 2 :
                player2.add(playerImage);
                break;
            case 3 :
                player3.add(playerImage);
                break;
            case 4 :
                player4.add(playerImage);
                break;
            default:
                break;
        }
    }

}
