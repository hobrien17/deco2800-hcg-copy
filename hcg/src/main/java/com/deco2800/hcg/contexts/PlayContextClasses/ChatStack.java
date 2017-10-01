package com.deco2800.hcg.contexts.PlayContextClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.MessageManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.multiplayer.ChatMessage;

public class ChatStack extends Stack {

    private GameManager gameManager;
    private NetworkManager networkManager;
    private TextureManager textureManager;
    private MessageManager messageManager;
    private Skin skin;

    private Table chatWindow;
    private TextField chatTextField;
    private TextArea chatTextArea;
    private Button chatButton;
    private Image chatBar;
    private String chatString = new String("");

    public ChatStack(Stage stage){
        super();

        gameManager = GameManager.get();
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        messageManager = (MessageManager) gameManager.getManager(MessageManager.class);


        /* Create window for chat and all components */
        chatBar = new Image(textureManager.getTexture("chat_background"));
        this.add(chatBar);
        chatWindow = new Table(skin);
        chatTextArea = new TextArea("", skin);
        chatTextField = new TextField("", skin);
        chatTextArea.setDisabled(true);
        chatTextArea.setText("");
        chatButton = new TextButton("Send", skin);
        chatWindow.add(chatTextArea).expand().fill().height(210).colspan(3).padBottom(20);
        chatWindow.row().height(40).padBottom(10);
        chatWindow.add(chatTextField).prefWidth(350);
        chatWindow.add(chatButton);
        chatWindow.setDebug(false);//display lines for debugging
        chatWindow.padTop(35).padLeft(15).padRight(15);

        this.setPosition(0, 0);
        this.add(chatWindow);
        this.setSize(380, 270);
        this.setScale((float) 1.2);

		/*
		 * Setup inputs for the buttons and the game itself
		 */
        chatButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (networkManager.isInitialised()) {
                    if (chatString.trim().length()>0) {
                        networkManager.queueMessage(new ChatMessage(chatTextField.getText()));
                        chatTextArea.appendText(chatTextField.getText() + "\n");
                        chatTextField.setText("");
                        stage.setKeyboardFocus(null);
                        chatString = "";
                    } else {
                        chatTextField.setText("");
                        chatTextField.setCursorPosition(0);
                        chatString = "";
                    }
                }
            }
        });

        /*
        	Input Listener for Textfield
         */
        chatTextField.setTextFieldListener(new TextField.TextFieldListener() { //textfield Listener
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c != '\b') {
                    chatString += c;
				} else if (chatString.length() > 0) {
					chatString = chatString.substring(0,
							chatString.length() - 1);
				}
				if (c == '\r' && networkManager.isInitialised()) {
					if (chatString.trim().length() > 0) {
						networkManager.queueMessage(
								new ChatMessage(chatTextField.getText()));
						chatTextArea.appendText(chatTextField.getText() + "\n");
						stage.setKeyboardFocus(null);
					} else {
						chatTextField.setCursorPosition(0);
					}

					chatTextField.setText("");
					chatString = "";
				}
			}
		});

        messageManager.addChatMessageListener(this::handleChatMessage);




    }
    private void handleChatMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }
}
