package su.a71.tardim_ic.tardim_ic.command;

import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.common.command.tardim.ICommand;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;


/*
This command prints list of all biomes into the console to find technical names
You can also pass an argument to search for entries with this string (e.g. amethyst for terralith:amethyst_rainforest
 */
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
                                if (args.length == 0 || out.toLowerCase().contains(args[0].toLowerCase())) {
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
        return "/list-biomes <..search_query>";  // TODO: how to communicate this better
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}