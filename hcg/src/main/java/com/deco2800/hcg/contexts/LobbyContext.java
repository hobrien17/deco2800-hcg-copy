package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.multiplayer.NetworkState;
import com.deco2800.hcg.worlds.DemoWorld;

/**
 * UI for Multiplayer Lobby. Made by Leon Zheng from Team 9
 */
public class LobbyContext extends UIContext{

    private ImageButton start, back, send;
    private Table main;
    private Label lobbyLabel, members;
    private Image lobbyTitle, separator1, separator2, playerPortrait1, playerPortrait2, playerPortrait3, playerPortrait4;
    private CheckBox readyCheckBox;
    private Stack player1, player2, player3, player4;
    private TextField chatTextfield;
    private TextArea chatTextArea;

    /**
     * Lobby UI constructor, initializes the entire UI
     */
    public LobbyContext() {

        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        PlayerManager playerManager = (PlayerManager)
                gameManager.getManager(PlayerManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        main = new Table();
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
        lobbyLabel = new Label("Lobby Name: LOCAL", skin);
        members = new Label("Members: ", skin);
        //player portrait stack set up
        player1 = new Stack(playerPortrait1);
        player2 = new Stack(playerPortrait2);
        player3 = new Stack(playerPortrait3);
        player4 = new Stack(playerPortrait4);
        //chat UI setup
        chatTextfield = new TextField("", skin);
        chatTextArea = new TextArea("", skin);
        chatTextArea.setDisabled(true);

        //body of GUI
        main.row().height(90);
        main.add(back).expandX().left();
        main.add(lobbyTitle).expandX().colspan(2);
        main.add(start).expandX().right();
        main.row();
        main.add(separator1).colspan(4).fill();
        main.row();
        main.add(lobbyLabel).left().colspan(3);
        main.add(readyCheckBox);
        main.row();
        main.add(members).left().colspan(4);
        main.row().expandX().padBottom(20).padTop(20);
        main.add(player1).uniform();
        main.add(player2).uniform();
        main.add(player3).uniform();
        main.add(player4).uniform();
        main.row();
        main.add(separator2).colspan(4).fill();
        main.row().expand();
        main.add(chatTextArea).colspan(4).fill();
        main.row();
        main.add(chatTextfield).colspan(3).fill();
        main.add(send);

        stage.addActor(main);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });


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
