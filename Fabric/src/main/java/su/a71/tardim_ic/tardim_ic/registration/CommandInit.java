package su.a71.tardim_ic.tardim_ic.registration;


import com.swdteam.tardim.common.init.CommandManager;

import su.a71.tardim_ic.tardim_ic.command.CommandCloisterBell;
import su.a71.tardim_ic.tardim_ic.command.CommandListBiomes;

public class CommandInit {
    public static void init() {
        CommandManager.register(new CommandListBiomes());
        CommandManager.register(new CommandCloisterBell());
    }
}
