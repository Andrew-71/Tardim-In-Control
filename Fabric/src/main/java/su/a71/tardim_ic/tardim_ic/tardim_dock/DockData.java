package su.a71.tardim_ic.tardim_ic.tardim_dock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class DockData {
    public String name;
    public BlockPos blockPos;
    public Level level;
    public boolean active = true;

    public DockData(int id, Level level, BlockPos blockPos) {
        this.level = level;
        this.blockPos = blockPos;
        this.name = DockManager.addDock(this);
    }

    public void setActive(boolean setting) {
        this.active = setting;
    }
}
