package su.a71.tardim_ic.tardim_ic.registration;


import su.a71.tardim_ic.tardim_ic.command.CommandListBiomes;
import su.a71.tardim_ic.tardim_ic.command.CommandListDimensions;
import su.a71.tardim_ic.tardim_ic.command.CommandModemTransmit;
import su.a71.tardim_ic.tardim_ic.command.CommandCloisterBell;
import com.swdteam.common.init.CommandManager;

public class CommandInit {
    public static void init() {
        CommandManager.register(new CommandModemTransmit());
        CommandManager.register(new CommandCloisterBell());
        CommandManager.register(new CommandListBiomes());
        CommandManager.register(new CommandListDimensions());
    }
}
