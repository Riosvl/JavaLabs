package com.kanils.commands;

import com.kanils.client.Terminal;

public class DepositReplenishmentCommand extends Command {

    public DepositReplenishmentCommand(Terminal terminal) {
        super(terminal);
    }

    @Override
    public boolean execute() {

        terminal.depositReplenishment();
        return false;
    }
}
