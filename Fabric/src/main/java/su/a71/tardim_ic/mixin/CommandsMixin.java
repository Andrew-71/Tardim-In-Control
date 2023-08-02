package su.a71.tardim_ic.mixin;

import com.swdteam.tardim.common.init.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import su.a71.tardim_ic.command.CommandInit;
import su.a71.tardim_ic.platform.Services;

@Mixin(value = CommandManager.class, remap = false)
public class CommandsMixin {
    @Inject(method="init()V", at=@At("TAIL"))
    private static void init(CallbackInfo ci) {
        CommandInit.init();
        if (Services.PLATFORM.isModLoaded("computercraft")) {
            CommandInit.addCC();
        }
        System.out.println("TARDIM: IC added commands using mixin");
    }

}
