package su.a71.tardim_ic.command;

// This will be added whenever I manage to convince TARDIM devs to make CommandManager.register public
// 13.04.23 ITS ALIVE

import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.common.command.tardim.ICommand;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.network.Packet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/*
This command sends out a ComputerCraft modem signal
This could be useful for connecting advanced navigation systems into "vanilla" controls
You can specify both channels, message and even make the message go across dimensions
 */
public class CommandModemTransmit implements ICommand {
    @Override
    public void execute(String[] args, Player player, BlockPos pos, CommandTardimBase.CommandSource source) {
        if (args.length == 3) {  // TODO: 3 or 4???
            TardimData data = TardimManager.getFromPos(pos);
            if (data != null) {
                if (data.hasPermission(player)) {
                    try {
                        int sendChannel = Integer.parseInt(args[0]);
                        int replyChannel = Integer.parseInt(args[1]);
                        String message = args[2];
                        boolean allDimensions = Boolean.parseBoolean(args[3]) || args[3].equals("true");

                        if (data.getTravelLocation() == null) {
                            data.setTravelLocation(new TardimData.Location(data.getCurrentLocation()));
                        }

                        if (allDimensions)
                        {
                            ComputerCraftAPI.getWirelessNetwork().transmitInterdimensional(new Packet(sendChannel, replyChannel, message, new CommandSender(player, data.getTravelLocation().getPos())));
                        }
                        else {
                            ComputerCraftAPI.getWirelessNetwork().transmitSameDimension(new Packet(sendChannel, replyChannel, message,
                                    new CommandSender(player, data.getTravelLocation().getPos())), 300);
                        }
                        CommandTardimBase.sendResponse(player, "Sent modem message", CommandTardimBase.ResponseType.COMPLETE, source);
                    } catch (Exception var9) {
                        CommandTardimBase.sendResponse(player, "Invalid coordinates", CommandTardimBase.ResponseType.FAIL, source);
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
        return "cc-modem-transmit";
    }

    @Override
    public String getUsage() {
        return "/cc-modem-transmit <Chnl> <replyChnl> <msg> <ender: bool>";
    }

    @Override
    public CommandTardimBase.CommandSource allowedSource() {
        return CommandTardimBase.CommandSource.BOTH;
    }
}