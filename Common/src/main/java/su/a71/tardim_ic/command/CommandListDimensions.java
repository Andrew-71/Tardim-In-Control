package su.a71.tardim_ic.command;

import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.common.command.tardim.ICommand;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

/*
This command prints list of all dimensions into the console to find technical names
 */
public class CommandListDimensions implements ICommand{
    @Override
    public void execute(String[] args, Player player, BlockPos pos, CommandTardimBase.CommandSource source) {
        if (args.length == 0) {
            TardimData data = TardimManager.getFromPos(pos);
            if (data != null) {
                if (data.hasPermission(player)) {
                    for (ServerLevel serverLevel : player.level().getServer().getAllLevels()) {
                        CommandTardimBase.sendResponse(player, serverLevel.dimension().location().toString(), CommandTardimBase.ResponseType.INFO, source);
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
        return "list-dimensions";
    }

    @Override
    public String getUsage() {
        return "/list-dimensions";
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}