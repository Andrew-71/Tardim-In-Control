package su.a71.tardim_ic.command;

import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.common.command.tardim.ICommand;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import su.a71.tardim_ic.tardim_ic.Registration;

/*
This command transmits the cloister bell sound in a big enough radius that you could hear it in any reasonably sized interior.
 */
public class CommandCloisterBell implements ICommand {
    @Override
    public void execute(String[] args, Player player, BlockPos pos, CommandTardimBase.CommandSource source) {
        if (args.length == 0) {
            TardimData data = TardimManager.getFromPos(pos);
            if (data != null) {
                if (data.hasPermission(player)) {
                    try {
                        Level lvl = player.getLevel();
                        if (!lvl.isClientSide) {
                            lvl.playSound(
                                    null,
                                    pos,
                                    Registration.CLOISTER_SOUND_EVENT,
                                    SoundSource.BLOCKS,
                                    1.5f,
                                    1f
                            );
                        }
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
        return "/cloister-bell";
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}
