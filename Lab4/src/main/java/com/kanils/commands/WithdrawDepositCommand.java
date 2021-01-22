package com.kanils.commands;

import com.kanils.client.Terminal;

public class WithdrawDepositCommand extends Command {

    public WithdrawDepositCommand(Terminal terminal) {
        super(terminal);
    }

    @Override
    public boolean execute() {
        terminal.withdrawDeposit();
        return false;
    }
}
