package org.stevefal.megarandomizer.gui;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.RequestGameRulesSyncC2SPacket;

@OnlyIn(Dist.CLIENT)
public class ModPauseScreen  extends Screen{
    private final boolean showPauseMenu;

    public ModPauseScreen(boolean pShowPauseMenu) {
        super(pShowPauseMenu ? new TranslationTextComponent("menu.game") : new TranslationTextComponent("menu.paused"));
        this.showPauseMenu = pShowPauseMenu;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createPauseMenu();
        }

    }

    private void createPauseMenu() {
        int i = -16;
        int j = 98;
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslationTextComponent("menu.returnToGame"), (p_213070_1_) -> {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
        }));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.advancements"), (p_213065_1_) -> {
            this.minecraft.setScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancements()));
        }));
        this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.stats"), (p_213066_1_) -> {
            this.minecraft.setScreen(new StatsScreen(this, this.minecraft.player.getStats()));
        }));
        String s = SharedConstants.getCurrentVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.sendFeedback"), (p_213072_2_) -> {
            this.minecraft.setScreen(new ConfirmOpenLinkScreen((p_213069_2_) -> {
                if (p_213069_2_) {
                    Util.getPlatform().openUri(s);
                }

                this.minecraft.setScreen(this);
            }, s, true));
        }));
        this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.reportBugs"), (p_213063_1_) -> {
            this.minecraft.setScreen(new ConfirmOpenLinkScreen((p_213064_1_) -> {
                if (p_213064_1_) {
                    Util.getPlatform().openUri("https://aka.ms/snapshotbugs?ref=game");
                }

                this.minecraft.setScreen(this);
            }, "https://aka.ms/snapshotbugs?ref=game", true));
        }));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.options"), (p_213071_1_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }));
        Button button = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.shareToLan"), (p_213068_1_) -> {
            this.minecraft.setScreen(new ShareToLanScreen(this));
        }));
        button.active = this.minecraft.hasSingleplayerServer() && !this.minecraft.getSingleplayerServer().isPublished();


        // MegaRandomizer Options Button
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, new TranslationTextComponent("menu.megarandomoptions"), (megBut) -> {
            MegaMessages.sendToServer(new RequestGameRulesSyncC2SPacket());
            this.minecraft.setScreen(new MegaRandomOptionsScreen(this, this.minecraft.level, true));
        }));



        Button button1 = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 144 + -16, 204, 20, new TranslationTextComponent("menu.returnToMenu"), (p_213067_1_) -> {
            boolean flag = this.minecraft.isLocalServer();
            boolean flag1 = this.minecraft.isConnectedToRealms();
            p_213067_1_.active = false;
            this.minecraft.level.disconnect();
            if (flag) {
                this.minecraft.clearLevel(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
            } else {
                this.minecraft.clearLevel();
            }

            if (flag) {
                this.minecraft.setScreen(new MainMenuScreen());
            } else if (flag1) {
                RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
                realmsbridgescreen.switchToRealms(new MainMenuScreen());
            } else {
                this.minecraft.setScreen(new MultiplayerScreen(new MainMenuScreen()));
            }

        }));
        if (!this.minecraft.isLocalServer()) {
            button1.setMessage(new TranslationTextComponent("menu.disconnect"));
        }

    }

    public void tick() {
        super.tick();
    }

    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        if (this.showPauseMenu) {
            this.renderBackground(pMatrixStack);
            drawCenteredString(pMatrixStack, this.font, this.title, this.width / 2, 40, 16777215);
        } else {
            drawCenteredString(pMatrixStack, this.font, this.title, this.width / 2, 10, 16777215);
        }

        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }


}
