package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class EvernightShadeTest extends CardTestPlayerBase {

    /**
     * Tests boost disappeared after creature died
     */
    @Test
    public void testBoostWithUndying() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Evernight Shade  Creature - Shade 1/1  {3}{B}
        // {B}: Evernight Shade gets +1/+1 until end of turn.
        // Undying (When this creature dies, if it had no +1/+1 counters on it, return it to the battlefield under its owner's control with a +1/+1 counter on it.)
        addCard(Zone.BATTLEFIELD, playerA, "Evernight Shade");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Evernight Shade");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Evernight Shade", 2, 2);
    }

}
