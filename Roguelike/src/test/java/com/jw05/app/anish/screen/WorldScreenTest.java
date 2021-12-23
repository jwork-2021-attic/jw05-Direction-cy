package com.jw05.app.anish.screen;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.asciiPanel.AsciiFont;
import com.jw05.app.asciiPanel.AsciiPanel;

import org.junit.Test;

public class WorldScreenTest {

    @Test
    public void worldScreenTest() {
        WorldScreen worldScreen = new WorldScreen(null);
        AsciiPanel terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_15_15);
        worldScreen.displayOutput(terminal);
        try {
            worldScreen.write();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}