package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AfflictTest extends CardTestPlayerBase {

    private final String khenra = "Khenra Eternal";
    private final String elves = "Llanowar Elves";

    @Test
    public void testBecomesBlocked() {

        addCard(Zone.BATTLEFIELD, playerA, khenra);
        addCard(Zone.BATTLEFIELD, playerB, elves);

        attack(1, playerA, khenra);
        block(1, playerB, elves, khenra);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 19);

    }

    @Test
    public void testNotBlocked() {

        addCard(Zone.BATTLEFIELD, playerA, khenra);
        addCard(Zone.BATTLEFIELD, playerB, elves);

        attack(1, playerA, khenra);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 18);

    }

    // My afflict didn't come through after using Endless Sands on my own creature. The afflict ability was on the stack already.
    @Test
    public void testRemoveAfflictCreatureAfterAfflictIsOnTheStack() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Afflict 2 (Whenever this creature becomes blocked, defending player loses 2 life.)
        // {1}{R}: Frontline Devastator gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Frontline Devastator");

        // {T}: Add {C} to your mana pool.
        // {2}, {T}: Exile target creature you control.
        // {4}, {T}, Sacrifice Endless Sands: Return each creature card exiled with Endless Sands to the battlefield under its owner’s control.
        addCard(Zone.BATTLEFIELD, playerA, "Endless Sands");

        // Deathtouch
        // When Ruin Rat dies, exile target card from an opponent's graveyard.
        addCard(Zone.BATTLEFIELD, playerB, "Ruin Rat"); // Creature 1/1

        attack(1, playerA, "Frontline Devastator");
        block(1, playerB, "Ruin Rat", "Frontline Devastator");

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "{2},", "Frontline Devastator");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, "Frontline Devastator", 1);
        assertPermanentCount(playerB, "Ruin Rat", 1);

        assertLife(playerB, 18);

    }
}
