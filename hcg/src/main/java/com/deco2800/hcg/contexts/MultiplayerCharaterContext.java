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

public class MultiplayerCharaterContext extends UIContext{
    private Table main;
    private Table titleTable;
    private Table loreTable;
    private SelectBox<String> characterSelectionBox;
    private Image title;
    private Image characterImage;
    private Container<Image> characterImageContainer;
    private ImageButton back;
    private Label characterLabel;
    private String[] characterList = {"Craig", "Moe", "Carl", "Peter", "Donald"};
    private Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    private TextureManager textureManager;
    private PlayerManager playerManager;
    private NetworkManager networkManager;
    private ContextManager contextManager;
    private GameManager gameManager;





    public MultiplayerCharaterContext() {
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        setMainTable();
        addTitleTable();
        addCharacterTable();
        addLoreTable();
        stage.addActor(main);
        characterSelectionBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeCharacterLore(characterSelectionBox.getSelected());
            }
        });
    }

    /*
        set up the main table for the Character Creation screen
     */
    private void setMainTable() {
        main = new Table();
        main.setBackground(new Image(textureManager.getTexture("multi_menu_background")).getDrawable());
        main.setFillParent(true);
        main.setDebug(true);
    }

    /*
        adds the title and back button to the screen
     */
    private void addTitleTable() {
        titleTable = new Table();
        back = new ImageButton(new Image(textureManager.getTexture("lobby_back_button")).getDrawable());
        title = new Image(textureManager.getTexture("MPcharacter_title"));
        titleTable.add(back).left().padRight(50);
        titleTable.add(title).expandX().center();
        main.add(titleTable).fillX().expandX();
    }

    /*
        adds the character selection box to the screen
     */
    private void addCharacterTable() {
        characterSelectionBox = new SelectBox<String>(skin);
        characterSelectionBox.setItems(characterList);
        characterLabel = new Label("", skin);
        characterLabel.setWrap(true);
        characterImage = new Image(textureManager.getTexture("player1"));
        characterImageContainer = new Container(characterImage);
        characterImageContainer.prefSize(459, 750);
    }

    /*
        adds the lore box which includes the character lore and the character Image to the screen
     */
    private void addLoreTable() {
        loreTable = new Table();
        loreTable.add(characterSelectionBox).left().padLeft(75);
        loreTable.add(characterLabel).expand().fill().pad(100);
        loreTable.add(characterImageContainer).padRight(75);
        changeCharacterLore(characterSelectionBox.getSelected());
        loreTable.setDebug(true);
        main.row();
        main.add(loreTable).expand().fill();
    }

    /*
        updates character lore information depending on which character the player is selecting
     */
    private void changeCharacterLore(String name) {
        switch(name) {
            case "Craig":
                characterImage.setDrawable(new Image(textureManager.getTexture("player1")).getDrawable());
                characterLabel.setText("Craig was an University student before the sh*t hit the fan. He was " +
                        "passionate about gardening and was a member of the university gardening club. His parents" +
                        " wanted him to become a lawyer or engineer, but he insisted on pursuing a career in gardening" +
                        " and enrolled in Bachelor of Gardeneering.");
                break;

            case "Moe":
                characterImage.setDrawable(new Image(textureManager.getTexture("player2")).getDrawable());
                characterLabel.setText("Moe Huffman was a salesperson at Punnings Warehaus plant section. He loved " +
                        "his job and he was the go to person at his store due to his knowledge on plants. Moe" +
                        " regularly works out in the gym, and has a variety of skills up his sleeve.");
                break;

            case "Carl":
                characterImage.setDrawable(new Image(textureManager.getTexture("player3")).getDrawable());
                characterLabel.setText("Carl was a professional gardener before the war that ended it all. He was in" +
                        " his basement retrieving fertilizers when the bomb hit. Carl lost everything in the span of" +
                        " a few seconds. Started from the top now his here. Carl is eager to rebuild the devastated" +
                        " world.");
                break;

            case "Peter":
                characterImage.setDrawable(new Image(textureManager.getTexture("player4")).getDrawable());
                characterLabel.setText("Peter was an retiree who is 80 years old. He has been gardening since he" +
                        " graduated from high school. His family heirloom is an antique shovel which Peter has used" +
                        " to plant many hundred plants. After retirement he bought a large piece of land where he" +
                        " started his own potato growing business and duck farm.");
                break;

            case "Donald":
                characterImage.setDrawable(new Image(textureManager.getTexture("player5")).getDrawable());
                characterLabel.setText("Before the great war that ended it all, Donald was the president for the" +
                        " United Stores of Agriculture. A chained department store which sells whole range of " +
                        "farming plants. A capable man, he created an agricultural empire from nothing with a " +
                        "small loan of a million dollars. ");
                break;
            default:
                break;
        }
    }

}
