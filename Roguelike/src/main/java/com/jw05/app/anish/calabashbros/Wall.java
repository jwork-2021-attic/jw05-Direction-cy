package com.jw05.app.anish.calabashbros;

import com.jw05.app.asciiPanel.AsciiPanel;

public class Wall extends Thing {

    public Wall(World world) {
        super(AsciiPanel.cyan, (char) 177, world, false, false, false, false);
    }

}
