package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class CommonMonster extends Monster{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CommonMonster(World world, Color color)
    {
        super(world, (char) 1,color, 50, 10, 20);
    }
    
}