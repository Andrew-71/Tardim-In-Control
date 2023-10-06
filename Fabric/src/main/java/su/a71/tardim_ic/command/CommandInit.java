package su.a71.tardim_ic.command;

import com.swdteam.tardim.common.init.CommandManager;

public class CommandInit {
    public static void init() {
        CommandManager.register(new CommandCloisterBell());
        CommandManager.register(new CommandListBiomes());
        CommandManager.register(new CommandListDimensions());
    }

    public static void addCC() {
        CommandManager.register(new CommandModemTransmit());
    }
}
