package org.stevefal.megarandomizer.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;
import org.stevefal.megarandomizer.networking.packets.SetGameRulesC2SPacket;

@OnlyIn(Dist.CLIENT)
public class MegaRandomOptionsScreen extends Screen {

    private final Screen LAST_SCREEN;
    private final World WORLD;
    private final boolean DO_SHOW_MEGARANDOM_OPTIONS;

    private Button doneButton;
    private Button blocksRandomButton;
    private Button entitiesRandomButton;
    private Button playerRandomButton;

    private static final TranslationTextComponent ENTITY_DROPS_ON = new TranslationTextComponent("menu.megarandomoptions.entity_drops_on");
    private static final TranslationTextComponent ENTITY_DROPS_OFF = new TranslationTextComponent("menu.megarandomoptions.entity_drops_off");
    private static final TranslationTextComponent BLOCK_DROPS_ON = new TranslationTextComponent("menu.megarandomoptions.block_drops_on");
    private static final TranslationTextComponent BLOCK_DROPS_OFF = new TranslationTextComponent("menu.megarandomoptions.block_drops_off");
    private static final TranslationTextComponent PLAYER_DROPS_ON = new TranslationTextComponent("menu.megarandomoptions.player_drops_on");
    private static final TranslationTextComponent PLAYER_DROPS_OFF = new TranslationTextComponent("menu.megarandomoptions.player_drops_off");
    private static final TranslationTextComponent DONE = new TranslationTextComponent("menu.megarandomoptions.done");


    protected MegaRandomOptionsScreen(Screen lastScreen, World world, boolean showMegaRandomOptions) {
        super(new TranslationTextComponent("menu.megarandomoptions"));

        this.LAST_SCREEN = lastScreen;
        this.WORLD = world;
        this.DO_SHOW_MEGARANDOM_OPTIONS = showMegaRandomOptions;
    }


    protected void init() {
        if (this.DO_SHOW_MEGARANDOM_OPTIONS) {
            this.setupMegaRandomizerMenu();
        }
    }

    private void setupMegaRandomizerMenu() {

        this.blocksRandomButton = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, getBlocksComponent(), (button) -> {
            MegaMessages.sendToServer(new SetGameRulesC2SPacket(
                    !WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)));
        }));

        this.entitiesRandomButton = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 48 + -16, 204, 20, getEntityComponent(), (button) -> {
            MegaMessages.sendToServer(new SetGameRulesC2SPacket(
                    WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    !WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)));
        }));

        this.playerRandomButton = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 204, 20, getPlayerComponent(), (button) -> {
            MegaMessages.sendToServer(new SetGameRulesC2SPacket(
                    WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    !WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)));
        }));

        this.doneButton = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 96 + -16, 204, 20, DONE, (button) -> {
            button.active = false;
            this.minecraft.setScreen(this.LAST_SCREEN);
        }));
    }


    private TranslationTextComponent getBlocksComponent() {
        if (WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS)) {
            return BLOCK_DROPS_ON;
        } else {
            return BLOCK_DROPS_OFF;
        }
    }

    private TranslationTextComponent getEntityComponent() {
        if (WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS)) {
            return ENTITY_DROPS_ON;
        } else {
            return ENTITY_DROPS_OFF;
        }
    }

    private TranslationTextComponent getPlayerComponent() {
        if (WORLD.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)) {
            return PLAYER_DROPS_ON;
        } else {
            return PLAYER_DROPS_OFF;
        }
    }

    public void tick() {
        super.tick();

        blocksRandomButton.setMessage(getBlocksComponent());
        entitiesRandomButton.setMessage(getEntityComponent());
        playerRandomButton.setMessage(getPlayerComponent());
    }


    public void render(MatrixStack matirixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.DO_SHOW_MEGARANDOM_OPTIONS) {
            this.renderBackground(matirixStack);
        }

        super.render(matirixStack, mouseX, mouseY, partialTicks);

    }


}
