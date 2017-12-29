package mage.client.util;

import java.util.UUID;

/**
 * Controls game state on client side.
 *
 * @author nantuko
 */
public enum GameManager {
    instance;

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public int getStackSize() {
        return stackSize;
    }

    public UUID getCurrentPlayerUUID() {
        return currentPlayerUUID;
    }

    public void setCurrentPlayerUUID(UUID currentPlayerUUID) {
        this.currentPlayerUUID = currentPlayerUUID;
    }

    private int stackSize;
    private UUID currentPlayerUUID;
}
