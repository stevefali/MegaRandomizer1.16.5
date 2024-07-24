package org.stevefal.megarandomizer.gui;

import com.mojang.blaze3d.matrix.MatrixStack;;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.RequestGameRulesSyncC2SPacket;

@OnlyIn(Dist.CLIENT)
public class ModPauseScreen extends IngameMenuScreen {
    private final boolean showPauseMenu;

    public ModPauseScreen(boolean pShowPauseMenu) {
        super(pShowPauseMenu);
        this.showPauseMenu = pShowPauseMenu;
    }

    @Override
    protected void init() {
        super.init();
        if (this.showPauseMenu) {
            this.createModPauseMenu();
        }
    }

    private void createModPauseMenu() {

        this.buttons.forEach(button -> {
            if (button.getMessage().equals(new TranslationTextComponent("menu.returnToMenu"))) {
                button.y = this.height / 4 + 144 - 16;
            }
        });

        // MegaRandomizer Options Button
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, new TranslationTextComponent("menu.megarandomoptions"), (megBut) -> {
            MegaMessages.sendToServer(new RequestGameRulesSyncC2SPacket());
            this.minecraft.setScreen(new MegaRandomOptionsScreen(this, this.minecraft.level, true));
        }));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }
}
