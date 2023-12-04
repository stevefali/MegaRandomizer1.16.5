package org.stevefal.megarandomizer.event;


import net.minecraft.client.gui.screen.GPUWarningScreen;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.stevefal.megarandomizer.MegaRandomizer1_16_5;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

@Mod.EventBusSubscriber(modid = MegaRandomizer1_16_5.MODID)
public class ModEvents {


    // Setup and Shuffle the drops list when the server is ready
    @SubscribeEvent
    public static void onServerReady(FMLServerStartedEvent event) {
        RandomDrops.shuffleItems(event.getServer().getWorldData().worldGenSettings().seed());
    }



}
