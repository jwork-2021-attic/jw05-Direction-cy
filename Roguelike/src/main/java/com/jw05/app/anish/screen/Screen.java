package com.jw05.app.anish.screen;

import java.awt.event.KeyEvent;
import java.nio.channels.SocketChannel;
import java.util.Map;

import com.jw05.app.asciiPanel.AsciiPanel;

public interface Screen {

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);

    public Screen response(int keycode, SocketChannel socketChannel);

    public void setMap(Map<String, SocketChannel> clientsMap);

    public void setChannelMessage(SocketChannel socketChannel);

}
