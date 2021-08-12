package it.vin27dev.advancedfreeze.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FrozenPlayersManager {

    private final List<UUID> frozenPlayers;

    public FrozenPlayersManager() {
        this.frozenPlayers = new ArrayList<>();
    }

    public void addToFrozenPlayers(UUID uuid) {
        frozenPlayers.add(uuid);
    }

    public void removeFromFrozenPlayers(UUID uuid) {
        frozenPlayers.remove(uuid);
    }

    public boolean isFrozen(UUID uuid) {
        return frozenPlayers.contains(uuid);
    }
}
