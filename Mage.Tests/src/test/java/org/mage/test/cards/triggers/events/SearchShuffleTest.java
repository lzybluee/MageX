package org.mage.test.cards.triggers.events;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SearchShuffleTest extends CardTestPlayerBase {


    /**
     * even though Leonin Arbiter prevents searching, the library should still 
     * get shuffled and Cosi's Trickster will still get a counter
     */
    @Test
    public void testEvent() {
        addCard(Zone.BATTLEFIELD, playerA, "Cosi's Trickster");
        addCard(Zone.BATTLEFIELD, playerA, "Leonin Arbiter");
        addCard(Zone.BATTLEFIELD, playerA, "Arid Mesa");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay 1 life");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 19);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Arid Mesa", 0);
        assertPermanentCount(playerA, "Mountain", 0);
        assertPermanentCount(playerA, "Plains", 0);
        assertCounterCount("Cosi's Trickster", CounterType.P1P1, 1);
    }
}