package com.jw05.app.anish.screen;

import java.awt.event.KeyEvent;
import java.nio.channels.SocketChannel;
import java.util.Map;

import com.jw05.app.asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

    public Map<String, SocketChannel> clientsMap;

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("This is the start screen.", 3);
        terminal.writeCenter("Press number to continue...", 6);
        terminal.writeCenter("1.   New Game     ", 10);
        terminal.writeCenter("2.   Continue Game", 15);
        terminal.writeCenter("3.   Replay Game  ", 20);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int keycode = key.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
                WorldScreen worldscreen = new WorldScreen(null);
                return worldscreen;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
                return new RecordScreen();
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
                return new ReplayScreen();
            default:
                break;
        }
        return this;
    }

    @Override
    public Screen response(int keycode, SocketChannel socketChannel) {
        switch (keycode) {
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
                WorldScreen worldscreen = new WorldScreen(clientsMap);
                return worldscreen;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
                return new RecordScreen();
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
                return new ReplayScreen();
            default:
                break;
        }
        return this;
    }

    @Override
    public void setMap(Map<String, SocketChannel> clientsMap) {
        // TODO Auto-generated method stub
        this.clientsMap =clientsMap;
    }

    @Override
    public void setChannelMessage(SocketChannel socketChannel) {
        // TODO Auto-generated method stub

    }

}