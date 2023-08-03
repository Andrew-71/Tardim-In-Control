package su.a71.tardim_ic.mixin;

import com.swdteam.tardim.tardim.TardimManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.swdteam.tardim.tardim.TardimManager.FUEL_MAP;

// This mixin aims to make TARDIM fuel system less awful by allowing users to put standard furnace fuel into it.
@Mixin(value = TardimManager.class, remap = true)
public class BetterFuelMapMixin {

    @Overwrite
    public static boolean isFuel(Item i) {
        return FUEL_MAP.containsKey(i) || AbstractFurnaceBlockEntity.getFuel().containsKey(i);
    }

    @Overwrite
    public static double getFuel(Item i) {
        if (!isFuel(i)) {
            return 0.0;
        }

        if (!AbstractFurnaceBlockEntity.getFuel().containsKey(i)) {
            return (Double)FUEL_MAP.get(i);
        }
        else
            return AbstractFurnaceBlockEntity.getFuel().get(i) / 8000.0;  // Adapt with coal's 1600 ticks -> 0.2 fuel
    }
}