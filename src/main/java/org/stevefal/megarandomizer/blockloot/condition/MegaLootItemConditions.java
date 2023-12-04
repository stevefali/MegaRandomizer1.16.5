package org.stevefal.megarandomizer.blockloot.condition;

import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.LootConditionManager;

public class MegaLootItemConditions {

    public static LootConditionType  BLOCK_DROP_SOURCE;


    // This will be referenced from the random_drop_from_block.json file.
    public static void register() {
        BLOCK_DROP_SOURCE = LootConditionManager.register("block_drop_source", new BlockDropSourceCondition.Serializer());
    }
}
