package org.stevefal.megarandomizer.blockloot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.block.BlockState;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;

import java.util.Set;

public class BlockDropSourceCondition implements ILootCondition {
    @Override
    public LootConditionType getType() {
        return LootConditionManager.BLOCK_STATE_PROPERTY;
    }

    /**
     * Get the parameters used by this object.
     */
    public Set<LootParameter<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootParameters.BLOCK_STATE);
    }


    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockState = lootContext.getParamOrNull(LootParameters.BLOCK_STATE);
        // Check if the drops are from a block
        if (blockState != null) {
            return true;
        } else {
            return false;
        }
    }

    public static class Serializer implements net.minecraft.loot.ILootSerializer<BlockDropSourceCondition> {
        public void serialize(JsonObject jsonObject, BlockDropSourceCondition blockDropSourceCondition, JsonSerializationContext jsonSerializationContext) {
        }

        public BlockDropSourceCondition deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return new BlockDropSourceCondition();
        }
    }
}
