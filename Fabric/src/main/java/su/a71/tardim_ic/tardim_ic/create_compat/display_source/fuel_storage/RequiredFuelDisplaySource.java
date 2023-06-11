package su.a71.tardim_ic.tardim_ic.create_compat.display_source.fuel_storage;

import com.simibubi.create.content.logistics.block.display.DisplayLinkContext;
import com.simibubi.create.content.logistics.block.display.source.NumericSingleLineDisplaySource;
import com.simibubi.create.content.logistics.block.display.target.DisplayTargetStats;
import com.simibubi.create.foundation.utility.Components;

import com.swdteam.tardim.common.init.TRDDimensions;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tileentity.TileEntityFuelStorage;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class RequiredFuelDisplaySource extends NumericSingleLineDisplaySource {
    @Override
    protected MutableComponent provideLine(DisplayLinkContext displayLinkContext, DisplayTargetStats displayTargetStats) {
        if (displayLinkContext.level() != displayLinkContext.level().getServer().getLevel(TRDDimensions.TARDIS))
            return null;
        BlockEntity te = displayLinkContext.getSourceTE();
        if (!(te instanceof TileEntityFuelStorage fuelStorage))
            return null;
        TardimData data = TardimManager.getFromPos(fuelStorage.getBlockPos());

        if (data.getTravelLocation() == null) return ZERO.copy();

        TardimData.Location curr = data.getCurrentLocation();
        TardimData.Location dest = data.getTravelLocation();

        double fuel = 0.0;

        if (curr.getLevel() != dest.getLevel())
        {
            fuel = 10.0;
        }

        Vec3 posA = new Vec3(curr.getPos().getX(), curr.getPos().getY(), curr.getPos().getZ());
        Vec3 posB = new Vec3(dest.getPos().getX(), dest.getPos().getY(), dest.getPos().getZ());
        fuel += posA.distanceTo(posB) / 100.0;
        if (fuel > 100.0) fuel = 100.0;

        return Components.literal(String.valueOf(fuel));
    }

    protected String getTranslationKey() {
        return "required_fuel";
    }

    protected boolean allowsLabeling(DisplayLinkContext context) {
        return true;
    }
}
