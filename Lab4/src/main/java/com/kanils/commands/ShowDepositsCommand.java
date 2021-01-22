package com.kanils.commands;

import com.kanils.client.Terminal;

public class ShowDepositsCommand extends Command {


    public ShowDepositsCommand(Terminal terminal) {
        super(terminal);
    }

    @Override
    public boolean execute() {
        terminal.showDeposits();
        return false;
    }
}
