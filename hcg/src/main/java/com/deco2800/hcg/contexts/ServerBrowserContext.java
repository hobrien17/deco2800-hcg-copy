package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * UI for server browser, used for joining a server.
 */
public class ServerBrowserContext extends UIContext {

    private ImageButton host;
    private ImageButton refresh;
    private ImageButton join;
    private ImageButton addServer;
    private ImageButton back;
    private Table main;
    private Table buttonTable;
    private Table titleTable;
    private Image title;
    private Image separator1;
    private Image separator2;
    private ScrollPane serverListPane;
    private List<String> serverList;
    private String servers[];
    private Dialog enterServer;
    private TextButton enterServerAdd;
    private TextButton enterServerExit;
    private TextField serverIPTextfield;
    private Label serverStatus;

    public ServerBrowserContext() {

        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        NetworkManager networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        main= new Table();
        buttonTable = new Table();
        titleTable = new Table();

        main.setFillParent(true);
        main.setBackground(new Image(textureManager.getTexture("multi_menu_background")).getDrawable());
        main.setDebug(false); //display lines for debugging

        title = new Image(textureManager.getTexture("multiplayer_title"));
        separator1 = new Image(textureManager.getTexture("lobby_separator"));
        separator2 = new Image(textureManager.getTexture("lobby_separator"));
        host = new ImageButton(new Image(textureManager.getTexture("server_host_button")).getDrawable());
        join = new ImageButton(new Image(textureManager.getTexture("server_join_button")).getDrawable());
        refresh = new ImageButton(new Image(textureManager.getTexture("server_refresh_button")).getDrawable());
        addServer = new ImageButton(new Image(textureManager.getTexture("menu_add_button")).getDrawable());
        back = new ImageButton(new Image(textureManager.getTexture("lobby_back_button")).getDrawable());
        servers = new String[20];
        for (int i = 0, k = 0; i < 20; i++) {
            servers[k++] = "Server: " + i;

        }
        serverList = new List<String>(skin);
        serverList.setItems(servers);
        serverListPane = new ScrollPane(serverList);
        serverListPane.setSmoothScrolling(false);
        serverListPane.setDebug(false);

        enterServer = new Dialog("Enter Host IP", skin);
        serverIPTextfield = new TextField("", skin);
        enterServerAdd = new TextButton("Add", skin);
        enterServerExit = new TextButton("close", skin);
        serverStatus = new Label("", skin);
        enterServer.setDebug(false);  //debug for dialog box
        enterServer.add(serverIPTextfield).expandX();
        enterServer.add(enterServerAdd);
        enterServer.row();
        enterServer.add(enterServerExit).center().expand();
        enterServer.add(serverStatus);

        //GUI body
        main.row();
        titleTable.add(back).left();
        titleTable.add(title).center().expandX();
        main.add(titleTable).fill().padRight(50);
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
        
        host.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                networkManager.init(true);
                networkManager.setLobbyName("Player's Lobby");
                contextManager.pushContext(new LobbyContext());
            }
        });

        addServer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                serverStatus.setText("");
                enterServer.show(stage);
            }
        });

        enterServerExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enterServer.hide();
                serverIPTextfield.setText("");
            }
        });

        enterServerAdd.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String address;
                address = serverIPTextfield.getText(); //recommended set up public method in networkManager
                serverIPTextfield.setText("");
                if(address.trim().length() == 0 || address == "") {
                    serverStatus.setText("Invalid Server IP");
                } else {
                    enterServer.hide();
                    networkManager.init(false);
                    networkManager.join(serverIPTextfield.getText());
                }

            }
        });
    }

}
