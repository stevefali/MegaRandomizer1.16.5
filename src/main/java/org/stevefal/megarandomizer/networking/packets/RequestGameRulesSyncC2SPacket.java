package org.stevefal.megarandomizer.networking.packets;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import org.stevefal.megarandomizer.gamerules.MegaGameRules;
import org.stevefal.megarandomizer.networking.MegaMessages;

import java.util.function.Supplier;

public class RequestGameRulesSyncC2SPacket {

    public RequestGameRulesSyncC2SPacket() {

    }

    public RequestGameRulesSyncC2SPacket(PacketBuffer buf) {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side
            ServerPlayerEntity player = context.getSender();
            ServerWorld level = player.getLevel();

            MegaMessages.sendToPlayer(new GameRulesSyncS2CPacket(level.getGameRules().getBoolean(MegaGameRules.RULE_DOBLOCKRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOENTITYRANDOMDROPS),
                    level.getGameRules().getBoolean(MegaGameRules.RULE_DOPLAYERRANDOMDROPS)), player);


        });
        return true;
    }

}
