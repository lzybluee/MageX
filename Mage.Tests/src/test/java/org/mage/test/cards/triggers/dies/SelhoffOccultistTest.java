package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *   Whenever Selhoff Occultist or another creature dies, target player puts the top card of his or her library into his or her graveyard.
 */
public class SelhoffOccultistTest extends CardTestPlayerBase {

    /**
     * Tests Selhoff Occultist's ability triggered
     */
    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Selhoff Occultist", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Selhoff Occultist");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // Lightning Bolt + 1 card
        assertGraveyardCount(playerA, 2);
        // Selhoff Occultist
        assertGraveyardCount(playerB, 1);
    }

    /**
     * Tests that both Selhoff Occultist would trigger
     */
    @Test
    public void testDiesTriggeredAbilityForTwoCopies() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Selhoff Occultist", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Ana Disciple", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ana Disciple");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // Lightning Bolt + 2 cards
        assertGraveyardCount(playerA, 3);
        // Ana Disciple
        assertGraveyardCount(playerB, 1);
    }

    /**
     * Tests that Selhoff Occultist cards in other than battlefield zones don't cause any effect
     */
    @Test
    public void testDiesTriggeredAbilityInOtherZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Selhoff Occultist", 2);
        addCard(Zone.HAND, playerB, "Selhoff Occultist", 1);
        addCard(Zone.GRAVEYARD, playerB, "Selhoff Occultist", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ana Disciple", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ana Disciple");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // Lightning Bolt + 2 cards
        assertGraveyardCount(playerA, 3);
        // Selhoff Occultist + Ana Disciple
        assertGraveyardCount(playerB, 2);
    }

}
