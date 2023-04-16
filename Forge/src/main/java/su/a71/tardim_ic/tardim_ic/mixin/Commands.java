package su.a71.tardim_ic.tardim_ic.mixin;

import org.spongepowered.asm.mixin.Mixin;
import com.swdteam.common.init.CommandManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import su.a71.tardim_ic.tardim_ic.registration.CommandInit;

@Mixin(value = CommandManager.class, remap = false)
public abstract class Commands {
    @Inject(method="init()V", at=@At("TAIL"))
    private static void init(CallbackInfo ci) {
        CommandInit.init();
        System.out.println("TARDIM: IC added commands using mixin");
    }
}
