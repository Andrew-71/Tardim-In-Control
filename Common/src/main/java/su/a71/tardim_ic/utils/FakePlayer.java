package su.a71.tardim_ic.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

/**
 * This class is used whenever we need a player for a function but cannot get one
 * (i.g we are on server side and need to execute TARDIM command)
 */
public class FakePlayer extends Player {

    public FakePlayer(Level lvl, BlockPos blockPos) {
        super(lvl, blockPos, 0, new GameProfile(UUID.randomUUID(), "FakePlayer_tardimic"));
    }

    public FakePlayer(Level lvl, BlockPos blockPos, UUID id) {
        super(lvl, blockPos, 0, new GameProfile(id, "FakePlayer_tardimic"));

    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
