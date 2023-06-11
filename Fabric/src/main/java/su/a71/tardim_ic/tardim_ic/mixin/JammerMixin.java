package su.a71.tardim_ic.tardim_ic.mixin;

import com.swdteam.tardim.common.command.tardim.CommandLocate;
import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import su.a71.tardim_ic.tardim_ic.registration.CommandInit;

import static com.swdteam.tardim.common.command.tardim.CommandTardimBase.sendResponse;
import static su.a71.tardim_ic.tardim_ic.Constants.LOG;
import static su.a71.tardim_ic.tardim_ic.Registration.LOCATION_JAMMER;

@Mixin(value = CommandLocate.class, remap = false)
public class JammerMixin {
    @Inject(method="execute()V", at=@At(value = "INVOKE", target = "Lcom/swdteam/tardim/tardim/TardimData;setTravelLocation(Lcom/swdteam/tardim/tardim/TardimData$Location;)V"))
    public void execute(CallbackInfo ci) {
        LOG.info("test");
//        for (ItemStack armour : player.getArmorSlots()) {
//            if (armour.is(LOCATION_JAMMER)) {
//                sendResponse(player, "Player's location is jammed", CommandTardimBase.ResponseType.FAIL, source);
//                ci.cancel();
//            };
//        }
    }
}
