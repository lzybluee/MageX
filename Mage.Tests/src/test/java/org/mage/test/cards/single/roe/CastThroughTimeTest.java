package org.mage.test.cards.single.roe;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class CastThroughTimeTest extends CardTestPlayerBase {

    /**
     * Tests Rebound works with a card that has no Rebound itself
     */
    @Test
    public void testCastWithRebound() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        /*
         * Instant and sorcery spells you control have rebound. (Exile the spell as
         * it resolves if you cast it from your hand. At the beginning of your next
         * upkeep, you may cast that card from exile without paying its mana cost.)
         *
         */
        addCard(Zone.BATTLEFIELD, playerA, "Cast Through Time");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    /**
     * Tests rebound from two Cast Through Time instances
     * Should have no effect for second copy
     */
    @Test
    public void testCastWithDoubleRebound() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Cast Through Time", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 14);
    }

    /**
     * Tests rebound tooltip
     */
    @Test
    public void testReboundTooltipExists() {
        addCard(Zone.BATTLEFIELD, playerA, "Cast Through Time");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        boolean found = false;
        for (Card card : currentGame.getPlayer(playerA.getId()).getHand().getCards(currentGame)) {
            if (card.getName().equals("Lightning Bolt")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Rebound")) {
                        found = true;
                    }
                }
            }
        }

        Assert.assertTrue("Couldn't find Rebound rule text displayed for the card", found);
    }

    /**
     * Tests Rebound disappeared
     */
    @Test
    public void testCastWithoutRebound() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Cast Through Time");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Naturalize");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Naturalize", "Cast Through Time");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
    }

    /**
     * Tests other than Battlefield zone
     */
    @Test
    public void testInAnotherZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.GRAVEYARD, playerA, "Cast Through Time");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
    }

}
