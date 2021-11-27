package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class Weapon extends Treasure{

    public Weapon(World world)
    {
        super(world, Color.cyan, (char) 18);
    }

    @Override
    public void work(Creature c)
    {
        c.increaseAttack(10);
        c.setTempThing(new Floor(world));
    }
    
}