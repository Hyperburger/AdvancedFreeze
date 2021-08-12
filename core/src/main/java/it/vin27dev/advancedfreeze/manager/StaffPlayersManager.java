package it.vin27dev.advancedfreeze.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffPlayersManager {

    private final Map<UUID, UUID> staffPlayers;

    public StaffPlayersManager() {
        this.staffPlayers = new HashMap<>();
    }

    public void addToStaffPlayers(UUID target, UUID staff) {
        staffPlayers.put(target, staff);
    }

    public void removeFromStaffPlayers(UUID key) {
        staffPlayers.remove(key);
    }

    public boolean isInMap(UUID key) {
        return staffPlayers.containsKey(key);
    }

    public Map<UUID, UUID> getStaffPlayers() {
        return staffPlayers;
    }
}
