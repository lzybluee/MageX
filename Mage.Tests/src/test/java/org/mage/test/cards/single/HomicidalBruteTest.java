package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HomicidalBruteTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 0);
        assertPermanentCount(playerA, "Homicidal Brute", 1);
        assertTapped("Homicidal Brute", false);
    }

    @Test
    public void testCardNegative() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 1);
        assertTapped("Civilized Scholar", true);
        assertPermanentCount(playerA, "Homicidal Brute", 0);
    }

    @Test
    public void testCardTransform() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Civilized Scholar", 1);
        assertTapped("Civilized Scholar", true);
        assertPermanentCount(playerA, "Homicidal Brute", 0);
    }

    @Test
    public void testCardNotTransform() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Civilized Scholar");
        addCard(Zone.LIBRARY, playerA, "Sejiri Merfolk", 2);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw a card, then discard a card.");
        attack(3, playerA, "Homicidal Brute");
        setStopAt(4, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 15);
        assertPermanentCount(playerA, "Civilized Scholar", 0);
        assertPermanentCount(playerA, "Homicidal Brute", 1);
        assertTapped("Homicidal Brute", true);
    }

}
