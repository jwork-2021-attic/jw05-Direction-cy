package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class Weapon extends Treasure{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Weapon(World world)
    {
        super(world, Color.cyan, (char) 12);
    }

    @Override
    public void work(Creature c)
    {
        c.increaseAttack(10);
        c.setTempThing(new Floor(world));
    }
    
}