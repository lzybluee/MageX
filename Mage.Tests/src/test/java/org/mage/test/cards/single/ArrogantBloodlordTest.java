package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class ArrogantBloodlordTest extends CardTestPlayerBase {
    // blocked trigger
    @Test
    public void testCardBlocked() {
        // Arrogant Bloodlord
        // 4/4
        // Whenever Arrogant Bloodlord blocks or becomes blocked by a creature with power 1 or less,
        // destroy Arrogant Bloodlord at end of combat.
        addCard(Zone.BATTLEFIELD, playerA, "Arrogant Bloodlord");
        // Wall of Air
        // 1/5
        // Defender, flying
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Air");

        attack(1, playerA, "Arrogant Bloodlord");
        block(1, playerB, "Wall of Air", "Arrogant Bloodlord");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Arrogant Bloodlord", 0);
        assertPermanentCount(playerB, "Wall of Air", 1);
    } 
    // blocks trigger
    @Test
    public void testCardBlocker() {
        addCard(Zone.BATTLEFIELD, playerA, "Caravan Hurda");
        addCard(Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord");

        attack(1, playerA, "Caravan Hurda");
        block(1, playerB, "Arrogant Bloodlord", "Caravan Hurda");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 21);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Caravan Hurda", 1);
        assertPermanentCount(playerB, "Arrogant Bloodlord", 0);
    }
    // blocked no trigger
    @Test
    public void testCardBlockedNonTrigger() {
       addCard(Zone.BATTLEFIELD, playerA, "Arrogant Bloodlord");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(1, playerA, "Arrogant Bloodlord");
        block(1, playerB, "Grizzly Bears", "Arrogant Bloodlord");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Arrogant Bloodlord", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
    }
    // blocks no trigger
    @Test
    public void testCardBlocksNonTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Acid Web Spider");
        addCard(Zone.BATTLEFIELD, playerB, "Arrogant Bloodlord");

        attack(1, playerA, "Acid Web Spider");
        block(1, playerB, "Arrogant Bloodlord", "Acid Web Spider");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Acid Web Spider", 1);
        assertPermanentCount(playerB, "Arrogant Bloodlord", 1);
    }
}
