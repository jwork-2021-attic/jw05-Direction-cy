package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class Key extends Treasure{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Key(World world)
    {
        super(world, Color.lightGray, (char) 8);
    }

    @Override
    public void work(Creature c)
    {
        c.getKey();
        c.setTempThing(new Floor(world));
    }
    
}