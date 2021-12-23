package com.jw05.app.asciiPanel;

import java.awt.Color;

import com.jw05.app.anish.calabashbros.World;

import org.junit.Test;

public class AsciiPanelTest {

    @Test
    public void test()
    {
        AsciiPanel terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_15_15);
        terminal.clear();
        terminal.clear((char) 10);
        terminal.clear((char) 10, Color.white, Color.black);
    }
    
}