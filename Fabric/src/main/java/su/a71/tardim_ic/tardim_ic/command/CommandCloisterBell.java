package su.a71.tardim_ic.tardim_ic.command;

import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.common.command.tardim.ICommand;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

//import static com.swdteam.common.command.tardim.CommandTardimBase.sendResponse;

public class CommandCloisterBell implements ICommand {
    @Override
    public void execute(String[] args, Player player, BlockPos pos, CommandTardimBase.CommandSource source) {
        if (args.length == 0) {
            TardimData data = TardimManager.getFromPos(pos);
            if (data != null) {
                if (data.hasPermission(player)) {
                    try {
                        CommandTardimBase.sendResponse(player, "<Insert Cloister bell>", CommandTardimBase.ResponseType.COMPLETE, source);
                    } catch (Exception var9) {
                        CommandTardimBase.sendResponse(player, "There was an error", CommandTardimBase.ResponseType.FAIL, source);
                    }
                } else {
                    CommandTardimBase.sendResponse(player, "You do not have permission", CommandTardimBase.ResponseType.FAIL, source);
                }
            }
        } else {
            CommandTardimBase.sendResponse(player, this.getUsage(), CommandTardimBase.ResponseType.FAIL, source);
        }
    }

    @Override
    public String getCommandName() {
        return "cloister-bell";
    }

    @Override
    public String getUsage() {
        return "cloister-bell";
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}
