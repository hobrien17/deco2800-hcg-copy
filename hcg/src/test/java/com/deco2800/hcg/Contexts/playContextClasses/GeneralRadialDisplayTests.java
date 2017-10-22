/*
package com.deco2800.hcg.managers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.contexts.playContextClasses.GeneralRadialDisplay;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.bullets.BulletType;
import com.deco2800.hcg.entities.garden_entities.plants.*;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.*;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;
import org.junit.*;

import java.util.Arrays;
import java.util.List;

public class GeneralRadialDisplayTests extends BaseTest {
    private GeneralRadialDisplay generalRadialDisplay;

    private Stage stage;
    private Player player;
    private PlayerManager playerManager;

    private String[] testItems;

    //Set up
    @Before
    public void setup() {
        playerManager = ((PlayerManager)GameManager.get().getManager(PlayerManager.class))
        player = ((PlayerManager)GameManager.get().getManager(PlayerManager.class)).getPlayer();
        GameManager.get().setWorld(World.SAFEZONE);

        stage = new Stage(new ScreenViewport());
        stage.getBatch().enableBlending();

        testItems = new String[]{"sunflower", "water", "ice", "explosive","fire","grass", "sunflowerC", "waterC", "iceC",
                "explosiveC","fireC","grassC", "fertiliser", "bug_spray", "machinegun", "shotgun", "multigun", "starfall",
                "snag", "sausage", "magic_mushroom", "small_mushroom", "hoe", "trowel", "shovel"};
        List<String> testList = Arrays.asList(testItems);
        generalRadialDisplay = new GeneralRadialDisplay(stage, testList);
    }

    @Test
    public void testActive() {
        generalRadialDisplay.addRadialMenu(stage);

        Assert.assertTrue("Radial display is not active",
                generalRadialDisplay.getActive());

        generalRadialDisplay.setActive(false);

        Assert.assertFalse("Radial display is active and has not been changed",
                generalRadialDisplay.getActive());
    }

    @Test
    public void testEventListeners() {
        generalRadialDisplay.addRadialMenu(stage);

        ActionListener shovelTest = generalRadialDisplay.useItem(new Shovel());
        ActionListener trowelTest = generalRadialDisplay.useItem(new Trowel());
        ActionListener hoeTest = generalRadialDisplay.useItem(new Hoe());
        ActionListener fertiliserTest = generalRadialDisplay.useItem(new Fertiliser());
        ActionListener bugSprayTest = generalRadialDisplay.useItem(new BugSpray());

        ActionListener machineGunTest = playerManager.getPlayer().setEquipped(0);
        ActionListener shotGunTest = playerManager.getPlayer().setEquipped(1);
        ActionListener multiGunTest = playerManager.getPlayer().setEquipped(2);
        ActionListener starFallTest = playerManager.getPlayer().setEquipped(3);

        ActionListener snagTest = generalRadialDisplay.useItem(new SpeedPotion());
        ActionListener sausageTest = generalRadialDisplay.useItem(new HealthPotion(100));
        ActionListener smallMushroomTest = generalRadialDisplay.useItem(new SmallMushroom());
        ActionListener magicMushroomTest = generalRadialDisplay.useItem(new MagicMushroom());

        ActionListener basicSeedTest = generalRadialDisplay.plant(new Seed(Seed.Type.SUNFLOWER));
        ActionListener waterSeedTest = generalRadialDisplay.plant(new Seed(Seed.Type.WATER));
        ActionListener iceSeedTest = generalRadialDisplay.plant(new Seed(Seed.Type.ICE));
        ActionListener fireSeedTest = generalRadialDisplay.plant(new Seed(Seed.Type.FIRE));
        ActionListener explosiveSeedTest = generalRadialDisplay.plant(new Seed(Seed.Type.EXPLOSIVE));
        ActionListener grassSeedTest = generalRadialDisplay.plant(new Seed(Seed.Type.GRASS));

        ActionListener basicCombatTest = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.BASIC);
        ActionListener waterCombatTest = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.HOMING);
        ActionListener iceCombatTest = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.ICE);
        ActionListener fireCombatTest = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.FIRE);
        ActionListener explosiveCombatTest = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.EXPLOSION);
        ActionListener grassCombatTest = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.GRASS);

        Shovel shovelEvent = (new Shovel()).use();
        Trowel trowelEvent = (new Trowel()).use();
        Hoe hoeEvent = (new Hoe()).use();
        Fertiliser fertiliserEvent = (new Fertiliser()).use();
        BugSpray bugSprayEvent = (new BugSpray()).use();

        Weapon machineGunEvent = playerManager.getPlayer().setEquipped(0);
        Weapon shotGunEvent = playerManager.getPlayer().setEquipped(1);
        Weapon multiGunEvent = playerManager.getPlayer().setEquipped(2);
        Weapon starFallEvent = playerManager.getPlayer().setEquipped(3);

        HealthPotion sausageEvent = (new HealthPotion(100)).consume(player);
        SpeedPotion snagEvent = (new SpeedPotion()).consume(player);
        SmallMushroom smallMushroomEvent = (new SmallMushroom()).consume(player);
        MagicMushroom magicMushroomEvent = (new MagicMushroom()).consume(player);

        Seed basicSeedEvent = generalRadialDisplay.plant(new Seed(Seed.Type.SUNFLOWER));;
        Seed waterSeedEvent = generalRadialDisplay.plant(new Seed(Seed.Type.WATER));;
        Seed iceSeedEvent = generalRadialDisplay.plant(new Seed(Seed.Type.ICE));;
        Seed fireSeedEvent = generalRadialDisplay.plant(new Seed(Seed.Type.FIRE));;
        Seed explosiveSeedEvent = generalRadialDisplay.plant(new Seed(Seed.Type.EXPLOSIVE));;
        Seed grassSeedEvent = generalRadialDisplay.plant(new Seed(Seed.Type.GRASS));;

        Seed basicCombatEvent = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.BASIC);;
        Seed waterCombatEvent = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.HOMING);;
        Seed iceCombatEvent = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.ICE);;
        Seed fireCombatEvent = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.FIRE);;
        Seed explosiveCombatEvent = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.EXPLOSION);;
        Seed grassCombatEvent = playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.GRASS);;

        shovelTest.actionPerformed(shovelEvent);
        trowelTest.actionPerformed(trowelEvent);
        hoeTest.actionPerformed(hoeEvent);
        fertiliserTest.actionPerformed(fertiliserEvent);
        bugSprayTest.actionPerformed(bugSprayEvent);

        machineGunTest.actionPerformed(machineGunEvent);
        shotGunTest.actionPerformed(shotGunEvent);
        multiGunTest.actionPerformed(multiGunEvent);
        starFallTest.actionPerformed(starFallEvent);

        sausageTest.actionPerformed(sausageEvent);
        snagTest.actionPerformed(snagEvent);
        smallMushroomTest.actionPerformed(smallMushroomEvent);
        magicMushroomTest.actionPerformed(magicMushroomEvent);

        basicSeedTest.actionPerformed(basicSeedEvent);
        waterSeedTest.actionPerformed(waterSeedEvent);
        iceSeedTest.actionPerformed(iceSeedEvent);
        fireSeedTest.actionPerformed(fireSeedEvent);
        explosiveSeedTest.actionPerformed(explosiveSeedEvent);
        grassSeedTest.actionPerformed(grassSeedEvent);

        basicCombatTest.actionPerformed(basicCombatEvent);
        waterCombatTest.actionPerformed(basicCombatEvent);
        iceCombatTest.actionPerformed(basicCombatEvent);
        fireCombatTest.actionPerformed(basicCombatEvent);
        explosiveCombatTest.actionPerformed(basicCombatEvent);
        grassCombatTest.actionPerformed(basicCombatEvent);
    }
}
*/
