package org.stevefal.megarandomizer.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MegaRandomOptionsScreen extends Screen {

    private final Screen lastScreen;

    private final World world;

    private final boolean showMegaRandomOptions;


    protected MegaRandomOptionsScreen(Screen lastScreen, World world, boolean showMegaRandomOptions) {
        super(new TranslationTextComponent("menu.megarandomoptions"));

        this.lastScreen = lastScreen;
        this.world = world;
        this.showMegaRandomOptions = showMegaRandomOptions;
    }
}
