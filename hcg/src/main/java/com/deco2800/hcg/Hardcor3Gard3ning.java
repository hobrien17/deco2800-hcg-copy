package com.deco2800.hcg;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.deco2800.hcg.contexts.MainMenuContext;
import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.CommandManager;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.ParticleEffectManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.ShaderManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.managers.WeatherManager;
import com.deco2800.hcg.managers.WorldManager;
import com.deco2800.hcg.quests.QuestManager;
import com.deco2800.hcg.renderers.Renderable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the creation of the world and rendering.
 */
public class Hardcor3Gard3ning extends Game {

    private GameManager gameManager;
    private ContextManager contextManager;
    private SoundManager soundManager;
    private WeatherManager weatherManager;
	private StopwatchManager stopwatchManager;
	private NetworkManager networkManager;
	private CommandManager commandManager;
	private ShaderManager shaderManager;
	private WorldManager worldManager;
    private long gameTickCount = 0;
    private long gameTickPeriod = 20;  // Tickrate = 50Hz
    private long nextGameTick = TimeUtils.millis() + gameTickPeriod;

    private static final Logger LOGGER = LoggerFactory.getLogger(Hardcor3Gard3ning.class);
    /**
     * Creates the required objects for the game to start. Called when the game first starts
     */
    @Override
    public void create() {

        // Create game manager
        gameManager = GameManager.get();

		/* Create a context manager, and set is as the screen target */
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        this.setScreen(contextManager);

		/* Create a sound manager for the whole game */
        soundManager = (SoundManager) gameManager.getManager(SoundManager.class);

        /* Create a weather manager. */
        weatherManager = (WeatherManager) gameManager.getManager(WeatherManager.class);
        
        /* Setup stopwatch manager */
        stopwatchManager = (StopwatchManager) gameManager.getManager(StopwatchManager.class);
        stopwatchManager.startTimer(-1);
        
        /* Setup network manager */
        networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        networkManager.init(false);
        
        /* Create a command manager */
        commandManager = (CommandManager) gameManager.getManager(CommandManager.class);


        /* Create a command manager */
        shaderManager = (ShaderManager) gameManager.getManager(ShaderManager.class);

        /* Create a world manager */
        worldManager = (WorldManager) gameManager.getManager(WorldManager.class);

        // add echo command
        // note args[0] is the command name, not the first argument
        commandManager.registerCommand("echo", new CommandManager.Command() {
            @Override
            public String run(String... args) {
                return args.length > 1 ? args[1] : "";
            }
            
        });
        
        //stopped weather
		commandManager.registerCommand("stopWweather", new CommandManager.Command() {
			@Override
			public String run(String... args) {
				weatherManager.stopAllEffect();
				return "weather stoped";
			}
		});

        commandManager.registerCommand("shader", new CommandManager.Command() {
            @Override
            public String run(String... args) {
                if ("set".equals(args[1])) {
                    if ("contrast".equals(args[2])) {
                        shaderManager.setOvercast(Float.parseFloat(args[3]));
                        return "Success!";
                    }
                } else if ("get".equals(args[1])) {

                } else {
                    return "Invalid option";
                }
                return args[1];
            }
        });
        
        commandManager.registerCommand("toggleShaders", new CommandManager.Command() {
            @Override
            public String run(String... args) {
                ShaderManager manager = (ShaderManager)GameManager.get().getManager(ShaderManager.class);
                manager.toggleShaders();
                return String.format("Shaders %s", manager.shadersEnabled() ? "enabled" : "disabled");
            }
        });
        
        commandManager.registerCommand("give", new CommandManager.Command() {
            @Override
            public String run(String... args) {
                if(!(args.length ==  2 || args.length == 3)) {
                    return "Invalid number of arguments. \nCorrect Usage: /give item <amount>";
                }
                
                Item item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(args[1]);
                
                if(item != null) {
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        return String
                                .format("%s is not a valid number", args[2]);
                    } catch (Exception e) {
                        LOGGER.error("error occurred", e);
                        amount = 1;
                    }

                    item.setStackSize(amount);
                    
                    Player player = ((PlayerManager)GameManager.get().getManager(PlayerManager.class)).getPlayer();
                    
                    if(player != null) {
                        player.addItemToInventory(item);
                    } else {
                        return "Something went wrong!";
                    }
                } else {
                    return "No such item";
                }
                
                return "Success!";
            }
        });

        commandManager.registerCommand("spawnItem", new CommandManager.Command() {
            @Override
            public String run(String... args) {
                if(!(args.length ==  2 || args.length == 3)) {
                    return "Invalid number of arguments. \nCorrect Usage: /spawnItem item <amount>";
                }
                
                Item item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(args[1]);
                
                if(item != null) {
                    int amount = 0;
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        return String.format("%s is not a valid number", args[2]);
                    } catch (Exception e) {
                        LOGGER.error("error occurred", e);
                        amount = 1;
                    }
                    item.setStackSize(amount);
                    
                    Player player = ((PlayerManager)GameManager.get().getManager(PlayerManager.class)).getPlayer();
                    
                    Vector2 position = new Vector2(0, 0);
                    
                    if(player != null) {
                        position = new Vector2(player.getPosX(), player.getPosY());
                        position.sub(1, 1);
                    }
                    
                    GameManager.get().getWorld().addEntity(new ItemEntity(position.x, position.y, 0, item));
                } else {
                    return "No such item";
                }
                
                return "Success!";
            }
        });
        
        commandManager.registerCommand("setTime", new CommandManager.Command() {
            @Override
            public String run(String... args) {
                if(args.length != 2) {
                    return "Invalid number of arguments. \nCorrect Usage: /setTime time";
                }
                
                TimeManager manager = (TimeManager)GameManager.get().getManager(TimeManager.class);
                LocalTime time;
                if(manager.timeNames.containsKey(args[1])) {
                    time = manager.timeNames.get(args[1]);
                } else {
                    try {
                        time = LocalTime.parse(args[1], DateTimeFormatter.ISO_LOCAL_TIME);
                    } catch(DateTimeParseException e) {
                        time = null;
                        LOGGER.error("datetime error occurred", e);
                    }
                }
                
                if(time != null) {
                    manager.setDateTime(time.getSecond(), time.getMinute(), time.getHour(), 
                            manager.getDay(), manager.getMonth(), manager.getYear());
                } else {
                    return "Invalid time.";
                }
                
                return String.format("Time set to %s.", time);
            }
        });
        
        // Procedurally generate the world map and store it.
        worldManager.generateAndSetWorldStack();

        contextManager.pushContext(new MainMenuContext());
    }

    /**
     * Renderer thread Must update all displayed elements using a Renderer
     */
    @Override
    public void render() {
        networkManager.tick();
        fireTicks();
        clearScreen();
        super.render(); // Will render current context
        updateTitle();
    }

    /**
     * Disposes of assets etc when the rendering system is stopped.
     */
    @Override
    public void dispose() {
        // Don't need this at the moment
        contextManager.dispose();
    }

    /**
     * Clears the entire display as we are using lazy rendering
     */
    public void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Fires waiting game ticks
     */
    private void fireTicks() {
        while (TimeUtils.millis() >= nextGameTick) {
        	if (networkManager.shouldBlock()) {
        		return;
        	} else if (!contextManager.ticksRunning()) {
        		// Schedule next tick
        		nextGameTick += gameTickPeriod;
        		return;
        	}

        	// Tick managers
        	GameManager.get().onTick(gameTickCount);

        	// Tick entities
        	for (Renderable e : GameManager.get().getWorld().getEntities()) {
        		if (e instanceof Tickable) {
        			((Tickable) e).onTick(gameTickCount);
        		}
        	}

        	// Increment tick count
        	gameTickCount ++;
        	nextGameTick += gameTickPeriod;
        }
    }

    /**
     * Display FPS in window title
     */
    private void updateTitle() {
        Gdx.graphics.setTitle(
                "DECO2800 " + this.getClass().getCanonicalName() + " - FPS: " + Gdx.graphics
                        .getFramesPerSecond());
    }

}
