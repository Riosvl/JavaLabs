package com.kanils.commands;

import com.kanils.client.Terminal;

public class FindInfoCommand extends Command {

    public FindInfoCommand(Terminal terminal) {
        super(terminal);
    }

    @Override
    public boolean execute() {

        terminal.findInfo();
        return false;
    }
}
