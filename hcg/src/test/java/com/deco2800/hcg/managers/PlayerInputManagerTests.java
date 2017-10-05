package com.deco2800.hcg.managers;

import static org.junit.Assert.*;


import com.badlogic.gdx.Input;
import com.deco2800.hcg.BaseTest;
import org.junit.*;

import com.deco2800.hcg.entities.Player;

public class PlayerInputManagerTests extends BaseTest {
  
  @Test
  public void testPlayerInput() {
    
    Player player = new Player(0, 0, 0);
    
      PlayerInputManager input = (PlayerInputManager) GameManager.get()
          .getManager(PlayerInputManager.class);

    input.keyDown(0, Input.Keys.S);
    
    assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(0, Input.Keys.S);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);

      input.keyDown(0, Input.Keys.A);
      
      assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(0, Input.Keys.A);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);
      
      input.keyDown(0, Input.Keys.D);
      
      assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(0, Input.Keys.D);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);
      
      input.keyDown(0, Input.Keys.W);
      
      assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(0, Input.Keys.W);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);
      
      // TODO This must be performed by a PlayerInputManager test as inputs are now queued
  }
  
  @Test
  public void testMultiPlayerInput() {
        
    Player player = new Player(0, 0, 0);
        
    PlayerInputManager input = (PlayerInputManager) GameManager.get()
        .getManager(PlayerInputManager.class);
    
      // test multiple keys at same time

      input.keyDown(0, Input.Keys.W);
      input.keyDown(0, Input.Keys.S);

      assertTrue("Player X was moving.", player.getSpeedX() == 0);
      assertTrue("Player Y was moving.", player.getSpeedY() == 0);

      input.keyUp(0, Input.Keys.W);
      input.keyUp(0, Input.Keys.S);

      input.keyDown(0, Input.Keys.A);
      input.keyDown(0, Input.Keys.D);

      assertTrue("Player X was moving.", player.getSpeedX() == 0);
      assertTrue("Player Y was moving.", player.getSpeedY() == 0);

      input.keyUp(0, Input.Keys.A);
      input.keyUp(0, Input.Keys.D);

      input.keyDown(0, Input.Keys.A);
      input.keyDown(0, Input.Keys.S);

      assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn change.", player.getSpeedY() == 0);

      input.keyUp(0, Input.Keys.A);
      input.keyUp(0, Input.Keys.S);
      
      input.keyDown(0, Input.Keys.A);
      input.keyDown(0, Input.Keys.W);

      assertTrue("Player X speed didn change.", player.getSpeedX() == 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(0, Input.Keys.A);
      input.keyUp(0, Input.Keys.W);

      input.keyDown(0, Input.Keys.D);
      input.keyDown(0, Input.Keys.S);

      assertTrue("Player X speed didn change.", player.getSpeedX() == 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(0, Input.Keys.D);
      input.keyUp(0, Input.Keys.S);

      input.keyDown(0, Input.Keys.D);
      input.keyDown(0, Input.Keys.W);

      assertTrue("Player X wasn't moving.", player.getSpeedX() != 0);
      assertTrue("Player Y was moving.", player.getSpeedY() == 0);

      input.keyUp(0, Input.Keys.D);
      input.keyUp(0, Input.Keys.W);

  }

 
}