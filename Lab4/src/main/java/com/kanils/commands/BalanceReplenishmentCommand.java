package com.kanils.commands;

import com.kanils.client.Terminal;

public class BalanceReplenishmentCommand extends Command{

    public BalanceReplenishmentCommand(Terminal terminal) {
        super(terminal);
    }

    @Override
    public boolean execute() {
        terminal.balanceReplenishment();
        return false;
    }
}
