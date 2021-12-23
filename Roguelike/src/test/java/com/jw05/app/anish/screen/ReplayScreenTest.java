package com.jw05.app.anish.screen;

import static org.junit.Assert.assertFalse;

import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.asciiPanel.AsciiFont;
import com.jw05.app.asciiPanel.AsciiPanel;

import org.junit.Test;

public class ReplayScreenTest {

    @Test
    public void replayScreenTest()
    {
        ReplayScreen replayScreen = new ReplayScreen();
        AsciiPanel terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_15_15);
        replayScreen.displayOutput(terminal);
    }
    
}