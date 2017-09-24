package com.deco2800.hcg.util;

import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.World;
import com.deco2800.hcg.contexts.MainMenuContext;

import java.util.List;

/**
 * A pretty basic (and crapola) method of threading the pathfinding
 */
public class PathfindingThread implements Runnable {

    private GameManager gameManager;
    private ContextManager contextManager;

    private World world;
    private Point position;
    private Point goal;

    private List<Point> path;

    /**
     * Constructor for the pathfinding thread
     * @param world the world where to search.
     * @param position
     * @param goal
     */
    public PathfindingThread(World world, Point position, Point goal) {
        this.world = world;
        this.position = position;
        this.goal = goal;

        //Get Game Manager
        gameManager = GameManager.get();

        //Get access to game context manager
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
    }

    /**
     * Runs the thread
     */
    @Override
    public void run() {
        if (contextManager.currentContext() instanceof MainMenuContext) {
            //Wait
        } else{
            this.path = Pathfinder.aStar(position, goal, world);
        }
    }

    /**
     * Returns the path (only if the thread has completed
     * @return
     */
    public List<Point> getPath() {
        return path;
    }
}
