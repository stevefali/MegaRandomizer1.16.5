package org.stevefal.megarandomizer.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.stevefal.megarandomizer.MegaRandomizer1_16_5;
import org.stevefal.megarandomizer.gui.ModPauseScreen;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @Mod.EventBusSubscriber(modid = MegaRandomizer1_16_5.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        // Intercept the stock IngameMenuScreen and instead display ModPauseScreen
        @SubscribeEvent
        public static void onPauseMenuTriggered(GuiScreenEvent.InitGuiEvent event) {
            boolean isSinglePlayer = Minecraft.getInstance().isLocalServer();
            if (event.getGui() instanceof IngameMenuScreen && !(event.getGui() instanceof ModPauseScreen)) {
                Minecraft.getInstance().setScreen(new ModPauseScreen(true));
            }
        }
    }
}
