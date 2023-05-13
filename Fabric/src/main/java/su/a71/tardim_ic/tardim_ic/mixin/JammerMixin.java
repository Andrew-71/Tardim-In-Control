package su.a71.tardim_ic.tardim_ic.mixin;

import com.swdteam.tardim.common.command.tardim.CommandLocate;
import com.swdteam.tardim.common.command.tardim.CommandTardimBase;
import dan200.computercraft.api.lua.LuaException;
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
import static su.a71.tardim_ic.tardim_ic.Registration.LOCATION_JAMMER;

@Mixin(value = CommandLocate.class, remap = false)
public class JammerMixin {
    @Inject(method="execute([Ljava/lang/String;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lcom/swdteam/tardim/common/command/tardim/CommandTardimBase/CommandSource;)V", at=@At(value = "INVOKE", target = "Lcom/swdteam/tardim/tardim/TardimData;setTravelLocation(Lcom/swdteam/tardim/tardim/TardimData/Location)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void init(CallbackInfo ci, Player player, Player otherPlayer, CommandTardimBase.CommandSource source) {
        for (ItemStack armour : player.getArmorSlots()) {
            if (armour.is(LOCATION_JAMMER)) {
                sendResponse(player, "Player's location is jammed", CommandTardimBase.ResponseType.FAIL, source);
            };
        }
    }
}
