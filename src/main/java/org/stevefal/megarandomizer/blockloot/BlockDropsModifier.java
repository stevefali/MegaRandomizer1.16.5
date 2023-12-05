package org.stevefal.megarandomizer.blockloot;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BlockDropsModifier extends LootModifier {

    protected BlockDropsModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        if (context.getLevel().getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS)) {
            // Replace the loot items.
            ArrayList<ItemStack> randomizedLoot = new ArrayList<>();
            generatedLoot.forEach(vanillaLootItem -> {
                randomizedLoot.add(new ItemStack(RandomDrops.getRandomizedItem(vanillaLootItem).getItem(), vanillaLootItem.getCount()));
            });
            generatedLoot.clear();
            generatedLoot.addAll(randomizedLoot);
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BlockDropsModifier> {

        @Override
        public BlockDropsModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ilootcondition) {
            return new BlockDropsModifier(ilootcondition);
        }

        @Override
        public JsonObject write(BlockDropsModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
