package com.jw05.app.anish.calabashbros;

import com.jw05.app.asciiPanel.AsciiPanel;

public class Wall extends Thing {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Wall(World world) {
        super(AsciiPanel.cyan, (char) 7, world, false, false, false, false);
    }

}
