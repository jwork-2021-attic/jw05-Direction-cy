package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class ExpPacket extends Treasure{

    public ExpPacket(World world)
    {
        super(world, Color.yellow, (char) 4);
    }
    
    @Override
    public void work(Creature c)
    {
        c.levelUp(50);
        c.setTempThing(new Floor(world));
    }
}