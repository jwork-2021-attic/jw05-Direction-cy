package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class HealthMedication extends Treasure{

    public HealthMedication(World world)
    {
        super(world, Color.green, (char) 3);
    }

    @Override
    public void work(Creature c)
    {
        c.increaseHealth(50);
        c.setTempThing(new Floor(world));
    }
    
}