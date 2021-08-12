package it.vin27dev.advancedfreeze.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TempAnvilManager {

    private final List<UUID> tempAnvilPlayers;

    public TempAnvilManager() {
        this.tempAnvilPlayers = new ArrayList<>();
    }

    public void addToTempAnvilPlayers(UUID uuid) {
        tempAnvilPlayers.add(uuid);
    }

    public void removeFromTempAnvilPlayers(UUID uuid) {
        tempAnvilPlayers.remove(uuid);
    }

    public boolean isInAnvil(UUID uuid) {
        return tempAnvilPlayers.contains(uuid);
    }
}
