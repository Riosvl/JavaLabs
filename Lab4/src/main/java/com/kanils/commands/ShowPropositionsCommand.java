package com.kanils.commands;

import com.kanils.client.Terminal;

public class ShowPropositionsCommand extends Command {
    public ShowPropositionsCommand(Terminal terminal) {
        super(terminal);
    }
    @Override
    public boolean execute() {
        terminal.showPropositions();
        return false;
    }
}
