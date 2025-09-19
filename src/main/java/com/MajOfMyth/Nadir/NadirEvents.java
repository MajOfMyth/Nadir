package com.MajOfMyth.Nadir;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class NadirEvents {

    @SubscribeEvent
    public void sync(OnDatapackSyncEvent e) {
        Config.ConfigPayload payload = new Config.ConfigPayload();
        e.getRelevantPlayers().forEach(p -> PacketDistributor.sendToPlayer(p, payload));
    }
}
