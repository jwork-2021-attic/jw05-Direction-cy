package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class Treasure extends Thing{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Treasure(World world, Color color, char ch) {
        super(color, ch, world, true, true, false, false);
    }

}