package mage.client.util;

import java.awt.Point;
import java.awt.Rectangle;
import org.mage.card.arcane.CardPanel;

/**
 * Contains dynamic settings for client.
 *
 * @author nantuko
 */
public enum SettingsManager {
    instance;

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setScreenWidthAndHeight(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public Rectangle getCardSize() {
        return cardSize;
    }

    /**
     * Get centered component position. Depends on screen width and height.
     *
     * @param dialogWidth
     * @param dialogHeight
     * @return
     */
    public Point getComponentPosition(int dialogWidth, int dialogHeight) {
        if (dialogWidth == 0) {
            throw new IllegalArgumentException("dialogWidth can't be 0");
        }
        if (dialogHeight == 0) {
            throw new IllegalArgumentException("dialogHeight can't be 0");
        }

        int width = Math.max(screenWidth, dialogWidth);
        int height = Math.max(screenHeight, dialogHeight);

        int x = ((width - dialogWidth) / 2);
        int y = ((height - dialogHeight) / 2);

        return new Point(x, y);
    }

    private int screenWidth;
    private int screenHeight;

    private final Rectangle cardSize = CardPanel.CARD_SIZE_FULL;
}
