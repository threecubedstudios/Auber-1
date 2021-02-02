package com.team3.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.team3.game.GdxTestRunner;
import com.team3.game.characters.ai.Enemy;
import com.team3.game.map.Map;
import com.team3.game.sprites.StationSystem;
import com.team3.game.tools.CharacterRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestSabotage { 
  
  @Test
  public void testSabotage() {
    CharacterRenderer.loadTextures();
    TmxMapLoader maploader = new TmxMapLoader();
    TiledMap map = maploader.load("Map/Map.tmx");
    Map.create(map);
    World world = new World(new Vector2(0, 0), true);
    Enemy enemy = new Enemy(world, 0, 0);
    StationSystem system = new StationSystem(world, map, new Rectangle(0, 0, 0, 0), "healingPod");
    enemy.sabotage(system);

    assertEquals("Unexpected value in system health", (double)system.hp, 99.5, 0);
    assertFalse("No damage was dealt", system.hp == 100);

  }
}