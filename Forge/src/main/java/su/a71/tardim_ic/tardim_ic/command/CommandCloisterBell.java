package su.a71.tardim_ic.tardim_ic.command;

import com.swdteam.common.command.tardim.CommandTardimBase;
import com.swdteam.common.command.tardim.ICommand;
import com.swdteam.tardim.TardimData;
import com.swdteam.tardim.TardimManager;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
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
        return "cloisterBell";
    }

    @Override
    public String getUsage() {
        return "cloisterBell";
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}
