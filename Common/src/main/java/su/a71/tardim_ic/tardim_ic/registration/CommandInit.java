package su.a71.tardim_ic.tardim_ic.registration;


import su.a71.tardim_ic.tardim_ic.command.CommandModemTransmit;
import su.a71.tardim_ic.tardim_ic.command.CommandCloisterBell;
import com.swdteam.common.init.CommandManager;

public class CommandInit {
    public static void init() {
        CommandManager.register(new CommandModemTransmit());
        CommandManager.register(new CommandCloisterBell());
    }
}
