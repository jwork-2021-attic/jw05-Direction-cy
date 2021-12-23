package com.jw05.app.anish.calabashbros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;


import org.junit.Test;


public class CalabashTest {

    @Test
    public void testAttributes()
    {
        World world = new World();
        Calabash calabash = new Calabash(Color.white, world);
        world.put(calabash, 0, 0);
        assertEquals(50, calabash.getMagic());
        assertEquals(10, calabash.getAttack());
        assertEquals(100, calabash.getHealth());
        assertEquals(50, calabash.getMagicLimit());
        assertEquals(0, calabash.getExp());
        assertEquals(100, calabash.getHealthLimit());
        assertEquals(0, calabash.getX());
        assertEquals(0, calabash.getY());
        assertFalse(calabash.isAvailable());
        assertFalse(calabash.isStandable());
        assertTrue(calabash.isCreature());
        assertFalse(calabash.isEvil());
        calabash.setWorld(world);
        calabash.setColor(Color.black);
        assertEquals(Color.black, calabash.getColor());
    }

    @Test
    public void testAction()
    {
        World world = new World();
        Calabash calabash = new Calabash(Color.white, world);
        Monster mons = new Monster(world, (char) 1, Color.yellow,100,10,30);
        world.put(calabash, 0, 0);
        calabash.tryBomb();
        world.put(mons, 1, 0);
        calabash.levelUp(100);
        assertEquals(2, calabash.getLevel());
        assertEquals(0, calabash.getX());
        assertEquals(0, calabash.getY());
        calabash.tryAttack();
        calabash.tryBomb();
    }

    @Test
    public void testAction2()
    {
        World world = new World();
        Calabash calabash = new Calabash(Color.white, world);
        world.put(calabash, 0, 0);
        world.put(new Wall(world), 1, 0);
        calabash.tryAttack();
        calabash.tryBomb();
        calabash.tryWalk(-1,0);
        calabash.tryBomb();
        calabash.tryWalk(0,-1);
        calabash.tryBomb();
        calabash.tryAttack();
    }

    @Test
    public void testGet()
    {
        World world = new World();
        Calabash calabash = new Calabash(Color.white, world);
        ExpPacket exp = new ExpPacket(world);
        HealthMedication health = new HealthMedication(world);
        MagicMedication magic = new MagicMedication(world);
        Weapon weapon = new Weapon(world);
        Key key = new Key(world);
        world.put(calabash, 0, 0);
        world.put(exp, 1, 0);
        world.put(health, 2, 0);
        world.put(magic, 3, 0);
        world.put(key, 4, 0);
        world.put(weapon, 5, 0);
        calabash.tryWalk(1,0);
        calabash.tryGet();
        calabash.tryWalk(1,0);
        calabash.tryGet();
        calabash.tryWalk(1,0);
        calabash.tryGet();
        calabash.tryWalk(1,0);
        calabash.tryGet();
        calabash.tryWalk(1, 0);
        calabash.tryGet();
        assertEquals(20, calabash.getAttack());
        assertTrue(calabash.isOpen());
    }
}