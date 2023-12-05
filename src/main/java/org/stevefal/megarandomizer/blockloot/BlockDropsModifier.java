package org.stevefal.megarandomizer.blockloot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.stevefal.megarandomizer.blockloot.condition.MegaLootItemConditions;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

//            context.getLevel().players().get(0).displayClientMessage(ITextComponent.nullToEmpty("Test!"), true);
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
