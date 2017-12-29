package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ArchangelsLightTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Archangel's Light");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Archangel's Light");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 32);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerA, "Archangel's Light", 1);
        Assert.assertEquals(77, currentGame.getPlayer(playerA.getId()).getLibrary().size());
    }


}
