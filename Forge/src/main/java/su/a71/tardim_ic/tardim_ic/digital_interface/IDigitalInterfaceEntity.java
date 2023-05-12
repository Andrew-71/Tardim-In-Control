package su.a71.tardim_ic.tardim_ic.digital_interface;

import com.swdteam.tardim.TardimData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IDigitalInterfaceEntity {
    public BlockPos getPos();
    public Level getLevel();

    public TardimData getTardim();
}
