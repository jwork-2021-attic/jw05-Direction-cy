package com.jw05.app.anish.screen;

import java.awt.event.KeyEvent;

import com.jw05.app.asciiPanel.AsciiPanel;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);

    public boolean isVictory();
}
