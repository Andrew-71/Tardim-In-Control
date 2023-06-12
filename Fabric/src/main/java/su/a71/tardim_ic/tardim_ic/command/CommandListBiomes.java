package su.a71.tardim_ic.tardim_ic.command;

import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.common.command.tardim.ICommand;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;


public class CommandListBiomes implements ICommand{
    @Override
    public void execute(String[] args, Player player, BlockPos pos, CommandTardimBase.CommandSource source) {
        ;
        if (args.length == 1 || args.length == 0) {
            TardimData data = TardimManager.getFromPos(pos);
            if (data != null) {
                if (data.hasPermission(player)) {
                    Registry<Biome> biomeRegistry = player.getLevel().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
                    biomeRegistry.keySet().forEach(
                            (ResourceLocation res) -> {
                                String out = res.toString();
                                if (args.length == 0 || (args[0].equals(out.split(":")[0]))) {
                                    CommandTardimBase.sendResponse(player, out, CommandTardimBase.ResponseType.INFO, source);
                                }
                            }
                    );
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
        return "list-biomes";
    }

    @Override
    public String getUsage() {
        return "/list-biomes";
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}