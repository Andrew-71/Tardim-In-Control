package su.a71.tardim_ic.tardim_ic.create_compat.display_source.fuel_storage;

import com.simibubi.create.content.logistics.block.display.DisplayLinkContext;
import com.simibubi.create.content.logistics.block.display.source.PercentOrProgressBarDisplaySource;
import com.simibubi.create.foundation.gui.ModularGuiLineBuilder;
import com.simibubi.create.foundation.utility.Lang;

import com.swdteam.tardim.common.init.TRDDimensions;
import com.swdteam.tardim.tardim.TardimData;
import com.swdteam.tardim.tardim.TardimManager;
import com.swdteam.tardim.tileentity.TileEntityFuelStorage;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static su.a71.tardim_ic.tardim_ic.Constants.LOG;

public class FuelLevelDisplaySource extends PercentOrProgressBarDisplaySource {
    @Override
    protected Float getProgress(DisplayLinkContext context) {
        if (context.level() != context.level().getServer().getLevel(TRDDimensions.TARDIS)) {
            return null;
        }
        BlockEntity te = context.getSourceTE();
        if (!(te instanceof TileEntityFuelStorage fuelStorage))
            return null;
        TardimData data = TardimManager.getFromPos(fuelStorage.getBlockPos());
        LOG.info(String.valueOf((float) (data.getFuel())));
        return (float) (data.getFuel() / 100);
    }

    @Override
    protected boolean progressBarActive(DisplayLinkContext context) {
        return context.sourceConfig()
                .getInt("Mode") != 0;
    }

    @Override
    protected String getTranslationKey() {
        return "fuel_level";
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void initConfigurationWidgets(DisplayLinkContext context, ModularGuiLineBuilder builder, boolean isFirstLine) {
        super.initConfigurationWidgets(context, builder, isFirstLine);
        if (isFirstLine)
            return;
        builder.addSelectionScrollInput(0, 120,
                (si, l) -> si.forOptions(Lang.translatedOptions("display_source.fuel_level", "percent", "progress_bar"))
                        .titled(Lang.translateDirect("display_source.fuel_level.display")),
                "Mode");
    }

    @Override
    protected boolean allowsLabeling(DisplayLinkContext context) {
        return true;
    }
}
