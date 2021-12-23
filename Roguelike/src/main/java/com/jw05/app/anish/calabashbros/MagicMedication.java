package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class MagicMedication extends Treasure{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MagicMedication(World world)
    {
        super(world, Color.blue, (char) 10);
    }

    @Override
    public void work(Creature c)
    {
        c.increaseMagic(20);
        c.setTempThing(new Floor(world));
    }
    
}