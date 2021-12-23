package com.jw05.app.anish.calabashbros;

import com.jw05.app.asciiPanel.AsciiPanel;

public class Door extends Thing {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Door(World world) {
        super(AsciiPanel.cyan, (char) 5, world, true, false, false, false);
    }

}