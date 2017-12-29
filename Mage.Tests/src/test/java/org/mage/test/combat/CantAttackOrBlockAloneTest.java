package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author magenoxx_at_gmail.com
 */
public class CantAttackOrBlockAloneTest extends CardTestPlayerBase {

    /**
     * Try to attack alone
     */
    @Test
    public void testCantAttackAlone() {
        addCard(Zone.BATTLEFIELD, playerB, "Mogg Flunkies");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Mogg Flunkies");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);

        Permanent moggFlunkies = getPermanent("Mogg Flunkies", playerB.getId());
        Assert.assertFalse("Shouldn't be tapped because it can't attack alone", moggFlunkies.isTapped());
    }

    /**
     * This time attack in pair
     */
    @Test
    public void testCantAttackAlone2() {
        addCard(Zone.BATTLEFIELD, playerB, "Mogg Flunkies");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Mogg Flunkies");
        attack(2, playerB, "Elite Vanguard");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
    }

    /**
     * Try to attack with two Flunkies
     */
    @Test
    public void testCanAttackWithTwo() {
        addCard(Zone.BATTLEFIELD, playerB, "Mogg Flunkies", 2); // 3/3

        attack(2, playerB, "Mogg Flunkies");
        attack(2, playerB, "Mogg Flunkies");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 14);

    }

    /**
     * Try to block alone
     */
    @Test
    public void testCantBlockAlone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mogg Flunkies");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Mogg Flunkies", "Elite Vanguard");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
    }

    /**
     * Try to block in pair
     */
    @Test
    public void testCantBlockAlone2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mogg Flunkies");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Mogg Flunkies", "Elite Vanguard");
        block(2, playerA, "Llanowar Elves", "Elite Vanguard");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
    }
}
