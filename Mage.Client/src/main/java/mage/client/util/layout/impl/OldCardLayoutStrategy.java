package mage.client.util.layout.impl;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Map;
import java.util.UUID;
import javax.swing.JLayeredPane;
import mage.cards.MagePermanent;
import mage.client.game.BattlefieldPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.layout.CardLayoutStrategy;
import mage.view.PermanentView;

/**
 * Card layout for client version 1.3.0 and earlier.
 *
 * Save it here for a while.
 *
 * @author noxx
 */
public class OldCardLayoutStrategy implements CardLayoutStrategy {

    /**
     * This offset is used once to shift all attachments
     */
    private static final int ATTACHMENTS_MIN_DX_OFFSET = 12;

    /**
     * This offset is used for each attachment
     */
    private static final int ATTACHMENT_MIN_DY_OFFSET = 12;

    @Override
    public void doLayout(BattlefieldPanel battlefieldPanel, int width) {
        Map<UUID, MagePermanent> permanents = battlefieldPanel.getPermanents();
        JLayeredPane jPanel = battlefieldPanel.getMainPanel();

        int height = Plugins.instance.sortPermanents(battlefieldPanel.getUiComponentsList(), permanents.values(), battlefieldPanel.isTopPanelBattlefield());
        jPanel.setPreferredSize(new Dimension(width - 30, height));

        for (PermanentView permanent : battlefieldPanel.getBattlefield().values()) {
            if (permanent.getAttachments() != null) {
                groupAttachments(battlefieldPanel, jPanel, permanents, permanent);
            }
        }

    }

    private void groupAttachments(JLayeredPane jLayeredPane, JLayeredPane jPanel, Map<UUID, MagePermanent> permanents, PermanentView permanent) {
        MagePermanent perm = permanents.get(permanent.getId());
        if (perm == null) {
            return;
        }
        int position = jLayeredPane.getPosition(perm);
        perm.getLinks().clear();
        Rectangle rectangleBaseCard = perm.getBounds();
        if (!Plugins.instance.isCardPluginLoaded()) {
            for (UUID attachmentId : permanent.getAttachments()) {
                MagePermanent link = permanents.get(attachmentId);
                if (link != null) {
                    perm.getLinks().add(link);
                    rectangleBaseCard.translate(20, 20);
                    link.setBounds(rectangleBaseCard);
                    jLayeredPane.setPosition(link, ++position);
                }
            }
        } else {
            int index = permanent.getAttachments().size();
            for (UUID attachmentId : permanent.getAttachments()) {
                MagePermanent link = permanents.get(attachmentId);
                if (link != null) {
                    link.setBounds(rectangleBaseCard);
                    perm.getLinks().add(link);
                    int dyOffset = Math.max(perm.getHeight() / 10, ATTACHMENT_MIN_DY_OFFSET);
                    if (index == 1) {
                        rectangleBaseCard.translate(Math.max(perm.getWidth() / 10, ATTACHMENTS_MIN_DX_OFFSET), dyOffset); // do it once
                    } else {
                        rectangleBaseCard.translate(0, dyOffset);
                    }
                    perm.setBounds(rectangleBaseCard);
                    jLayeredPane.moveToFront(link);
                    jLayeredPane.moveToFront(perm);
                    jPanel.setComponentZOrder(link, index);
                    index--;
                }
            }
            jPanel.setComponentZOrder(perm, index);
        }

    }

    @Override
    public int getDefaultZOrder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onAdd(BattlefieldPanel jLayeredPane) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
