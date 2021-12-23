package com.jw05.app.anish.screen;

import java.awt.event.KeyEvent;
import java.nio.channels.SocketChannel;
import java.util.Map;

import com.jw05.app.asciiPanel.AsciiPanel;

public class VictoryScreen implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Victory!!!", 3);
        terminal.writeCenter("Press enter to back to menu", 6);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int keycode = key.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_ENTER:
                return new StartScreen();
            default:
                break;
        }
        return this;
    }

    @Override
    public Screen response(int keycode, SocketChannel socketChannel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMap(Map<String, SocketChannel> clientsMap) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setChannelMessage(SocketChannel socketChannel) {
        // TODO Auto-generated method stub

    }


}