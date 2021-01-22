package com.kanils.commands;

import com.kanils.client.Terminal;

abstract public class Command {
    Terminal terminal;

    public Command(Terminal terminal) {
        this.terminal = terminal;
    }
    public abstract boolean execute();

}
