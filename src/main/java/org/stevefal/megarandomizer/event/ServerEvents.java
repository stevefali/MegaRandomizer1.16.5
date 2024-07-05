package org.stevefal.megarandomizer.event;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.stevefal.megarandomizer.MegaRandomizer1_16_5;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.megadrops.RandomDrops;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MegaRandomizer1_16_5.MODID)
public class ServerEvents {


    // Setup and Shuffle the drops list when the server is ready
    @SubscribeEvent
    public static void onServerReady(FMLServerStartedEvent event) {
        RandomDrops.shuffleItems(event.getServer().getWorldData().worldGenSettings().seed());
    }


    // Randomize entity drops
    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        World world = event.getEntity().level;
        LivingEntity ent = event.getEntityLiving();
        if (!world.isClientSide) {
            if (ent instanceof PlayerEntity) {
                if (world.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)) {
                    randomizeEntityDrops(event, world, ent);
                }
            } else {
                if (world.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS)) {
                    randomizeEntityDrops(event, world, ent);
                }
            }
        }
    }

    private static void randomizeEntityDrops(LivingDropsEvent event, World world, LivingEntity ent) {
        ArrayList<ItemEntity> randomizedDrops = new ArrayList<>();
        event.getDrops().forEach(vanillaDrop -> {
            for (int i = 0; i < vanillaDrop.getItem().getCount(); i++) {
                randomizedDrops.add(new ItemEntity(world, ent.getX(), ent.getY(), ent.getZ(), RandomDrops.getRandomizedItem(vanillaDrop.getItem())));
            }
        });
        event.getDrops().clear();
        event.getDrops().addAll(randomizedDrops);
    }


}
