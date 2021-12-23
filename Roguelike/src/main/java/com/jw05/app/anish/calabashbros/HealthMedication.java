package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class HealthMedication extends Treasure{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public HealthMedication(World world)
    {
        super(world, Color.green, (char) 9);
    }

    @Override
    public void work(Creature c)
    {
        c.increaseHealth(50);
        c.setTempThing(new Floor(world));
    }
    
}