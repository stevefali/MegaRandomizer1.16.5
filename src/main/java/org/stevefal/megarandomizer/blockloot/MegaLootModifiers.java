package org.stevefal.megarandomizer.blockloot;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.stevefal.megarandomizer.MegaRandomizer1_16_5;

public class MegaLootModifiers {


    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MOD_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MegaRandomizer1_16_5.MODID);


    // This will be referenced from the random_drop_from_block.json file.
    public static final RegistryObject<BlockDropsModifier.Serializer> BLOCK_MEGA_RAND_LOOT = LOOT_MOD_SERIALIZER.register("randomize_block_drops", BlockDropsModifier.Serializer::new);



    public static void register(IEventBus bus) {
        LOOT_MOD_SERIALIZER.register(bus);
    }

}
