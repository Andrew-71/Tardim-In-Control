package su.a71.tardim_ic.tardim_ic.jammer;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class PersonalJammerMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    private static final int[] PROTECTION_VALUES = new int[] {1, 1, 1, 1};

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getIndex()] * 33;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(Items.IRON_INGOT);
    }

    @Override
    public @NotNull String getName() {
        // Must be all lowercase
        return "personal_jammer";
    }

    @Override
    public float getToughness() {
        return 0.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }
}