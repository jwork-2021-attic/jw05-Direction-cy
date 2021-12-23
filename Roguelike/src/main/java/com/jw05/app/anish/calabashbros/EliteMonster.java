package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class EliteMonster extends Monster{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EliteMonster(World world, Color color)
    {
        super(world, (char) 4, color, 200, 20, 100);
    }
    
}