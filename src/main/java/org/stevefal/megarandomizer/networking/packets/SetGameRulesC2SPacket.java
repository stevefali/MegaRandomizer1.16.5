package org.stevefal.megarandomizer.networking.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SetGameRulesC2SPacket {

    private final boolean isDoBlockRandomDrops;
    private final boolean isDoEntityRandomDrops;
    private final boolean isDoPlayerRandomDrops;

    public SetGameRulesC2SPacket(boolean isDoBlocks, boolean isDoEntities, boolean isDoPlayer) {
        this.isDoBlockRandomDrops = isDoBlocks;
        this.isDoEntityRandomDrops = isDoEntities;
        this.isDoPlayerRandomDrops = isDoPlayer;
    }

    public SetGameRulesC2SPacket(PacketBuffer buf) {
        this.isDoBlockRandomDrops = buf.readBoolean();
        this.isDoEntityRandomDrops = buf.readBoolean();
        this.isDoPlayerRandomDrops = buf.readBoolean();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(isDoBlockRandomDrops);
        buf.writeBoolean(isDoEntityRandomDrops);
        buf.writeBoolean(isDoPlayerRandomDrops);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side
            ServerPlayerEntity player = context.getSender();
            ServerWorld level = player.getLevel();

            // Set the rules
            level.getGameRules().getRule(MegaGameRules.RULE_DOBLOCKRANDOMDROPS).set(isDoBlockRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DOENTITYRANDOMDROPS).set(isDoEntityRandomDrops, level.getServer());
            level.getGameRules().getRule(MegaGameRules.RULE_DOPLAYERRANDOMDROPS).set(isDoPlayerRandomDrops, level.getServer());


            // Sync the data back to the client
            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(isDoBlockRandomDrops, isDoEntityRandomDrops, isDoPlayerRandomDrops), player);
        });
        return true;
    }
}
