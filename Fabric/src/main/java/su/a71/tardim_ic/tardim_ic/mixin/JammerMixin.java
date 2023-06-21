package su.a71.tardim_ic.tardim_ic.mixin;

import com.swdteam.tardim.common.command.tardim.CommandLocate;
import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import su.a71.tardim_ic.tardim_ic.registration.CommandInit;

import static com.swdteam.tardim.common.command.tardim.CommandTardimBase.sendResponse;
import static su.a71.tardim_ic.tardim_ic.Constants.LOG;
import static su.a71.tardim_ic.tardim_ic.Registration.PERSONAL_JAMMER;

@Mixin(value = CommandLocate.class, remap = false)
public class JammerMixin {
//    @Inject(method="execute()V", at=@At(value = "INVOKE",
//            target = "Lcom/swdteam/tardim/tardim/TardimData;setTravelLocation(Lcom/swdteam/tardim/tardim/TardimData$Location;)V"))
//    public void execute(CallbackInfo ci) {
//        LOG.info("test");
////        for (ItemStack armour : player.getArmorSlots()) {
////            if (armour.is(LOCATION_JAMMER)) {
////                sendResponse(player, "Player's location is jammed", CommandTardimBase.ResponseType.FAIL, source);
////                ci.cancel();
////            };
////        }
//    }

    @Overwrite
    public void execute(String[] args, Player player, BlockPos pos, CommandTardimBase.CommandSource source) {
        if (args.length == 1) {
            TardimData data = TardimManager.getFromPos(pos);
            if (data != null) {
                if (data.hasPermission(player)) {
                    Player otherPlayer = player.getServer().getPlayerList().getPlayerByName(args[0]);
                    if (otherPlayer != null) {
                        for (ItemStack armour : otherPlayer.getArmorSlots()) {
                            if (armour.is(PERSONAL_JAMMER)) {
                                sendResponse(player, otherPlayer.getGameProfile().getName() + "'s location is jammed", CommandTardimBase.ResponseType.FAIL, source);
                                return;
                            }
                        }
                        data.setTravelLocation(new TardimData.Location(otherPlayer.blockPosition(), otherPlayer.level.dimension()));
                        sendResponse(player, "Coords locked on to " + otherPlayer.getGameProfile().getName(), CommandTardimBase.ResponseType.COMPLETE, source);
                    } else {
                        sendResponse(player, "Player does not exist", CommandTardimBase.ResponseType.FAIL, source);
                    }
                } else {
                    sendResponse(player, "You do not have permission", CommandTardimBase.ResponseType.FAIL, source);
                }
            }
        } else {
            sendResponse(player, ((CommandLocate)(Object)this).getUsage(), CommandTardimBase.ResponseType.FAIL, source);
        }

    }
}
