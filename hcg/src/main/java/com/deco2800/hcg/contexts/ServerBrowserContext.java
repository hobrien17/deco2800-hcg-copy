package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.badlogic.gdx.graphics.Color;

import java.lang.reflect.Array;

/**
 * UI for server browser, used for joining a server.
 */
public class ServerBrowserContext extends UIContext {

    private ImageButton host, refresh, join, addServer, back;
    private Table main, buttonTable, titleTable;
    private Image title, separator1, separator2;
    private ScrollPane serverListPane;
    private List serverList;
    private String servers[];
    private Dialog enterServer;
    private TextButton add, exit;
    private TextField serverIP;
    private Label serverStatus;

    public ServerBrowserContext() {

        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        main= new Table();
        buttonTable = new Table();
        titleTable = new Table();
        serverListPane = new ScrollPane(serverList);

        main.setFillParent(true);
        main.setBackground(new Image(textureManager.getTexture("multi_menu_background")).getDrawable());
        main.setDebug(true); //display lines for debugging

        title = new Image(textureManager.getTexture("multiplayer_title"));
        separator1 = new Image(textureManager.getTexture("lobby_separator"));
        separator2 = new Image(textureManager.getTexture("lobby_separator"));
        host = new ImageButton(new Image(textureManager.getTexture("server_host_button")).getDrawable());
        join = new ImageButton(new Image(textureManager.getTexture("server_join_button")).getDrawable());
        refresh = new ImageButton(new Image(textureManager.getTexture("server_refresh_button")).getDrawable());
        addServer = new ImageButton(new Image(textureManager.getTexture("menu_add_button")).getDrawable());
        back = new ImageButton(new Image(textureManager.getTexture("lobby_back_button")).getDrawable());
        servers = new String[] {"1.0.0.123", "2.3.1.198"};
        serverList = new List(skin);
        serverList.setItems(servers);
        enterServer = new Dialog("Enter Host IP", skin);
        serverIP = new TextField("", skin);
        add = new TextButton("add", skin);
        exit = new TextButton("close", skin);
        serverStatus = new Label("", skin);
        enterServer.setDebug(true);
        enterServer.add(serverIP).expandX();
        enterServer.add(add);
        enterServer.row();
        enterServer.add(exit).center().expand();
        enterServer.add(serverStatus);

        //GUI body
        main.row();
        titleTable.add(back).left();
        titleTable.add(title).center().expandX();
        main.add(titleTable).fill();
        main.row();
        main.add(separator1).fill();
        main.row();
        main.add(serverListPane).fill();
        main.row();
        main.add(separator2).fill();
        main.row();
        buttonTable.add(host).expand().uniform();
        buttonTable.add(refresh).expand().uniform();
        buttonTable.add(join).expand().uniform();
        buttonTable.add(addServer).expand().uniform();
        main.add(buttonTable).fill();
        stage.addActor(main);

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

        addServer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                serverStatus.setText("");
                enterServer.show(stage);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enterServer.hide();
            }
        });

        add.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String address;
                address = serverIP.getText();
                serverIP.setText("");
                System.out.printf("%s\n",address);
                serverStatus.setText("printed");
            }
        });
    }

}
