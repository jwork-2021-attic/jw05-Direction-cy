package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class Floor extends Thing {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Floor(World world) {
        super(Color.gray, (char) 6, world, true, false, false, false);
    }

}
