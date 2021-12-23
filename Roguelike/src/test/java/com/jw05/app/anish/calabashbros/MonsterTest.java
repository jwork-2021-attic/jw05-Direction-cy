package com.jw05.app.anish.calabashbros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;


import org.junit.Test;


public class MonsterTest {

    @Test
    public void testAttributes()
    {
        World world = new World();
        Monster monster = new Monster(world, (char) 1, Color.white, 50, 10, 20);
        world.put(monster, 0, 0);
        assertEquals(10, monster.getAttack());
        assertEquals(50, monster.getHealth());
        assertEquals(0, monster.getExp());
        assertEquals(50, monster.getHealthLimit());
        assertEquals(0, monster.getX());
        assertEquals(0, monster.getY());
        assertFalse(monster.isAvailable());
        assertFalse(monster.isStandable());
        assertTrue(monster.isCreature());
        assertTrue(monster.isEvil());
        monster.setWorld(world);
        monster.setColor(Color.black);
        assertEquals(Color.black, monster.getColor());
    }

    @Test
    public void testAction()
    {
        World world = new World();
        Monster monster = new Monster(world, (char) 1, Color.yellow, 50, 10, 20);
        Calabash calabash = new Calabash(Color.white, world);
        world.put(monster, 1, 0);
        world.put(calabash, 0, 0);
        monster.decreaseHealth(50);
        Thread thread = new Thread(monster);
        thread.start();
        monster.decreaseHealth(50);
    }

    @Test
    public void testAction2()
    {
        World world = new World();
        Monster monster = new Monster(world, (char) 1, Color.yellow, 50, 10, 20);
        world.put(monster, 1, 0);
        Thread thread = new Thread(monster);
        thread.start();
    }

    @Test
    public void testMagic()
    {
        World world = new World();
        Monster monster = new MagicMonster(world, Color.yellow);
        Calabash calabash = new Calabash(Color.white, world);
        world.put(monster, 1, 0);
        world.put(calabash, 0, 0);
        Thread thread = new Thread(monster);
        thread.start();
    }

    
}