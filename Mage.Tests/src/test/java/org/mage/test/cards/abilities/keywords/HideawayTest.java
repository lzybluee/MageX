/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HideawayTest extends CardTestPlayerBase {

    /**
     * 702.74. Hideaway 702.74a Hideaway represents a static ability and a
     * triggered ability. “Hideaway” means “This permanent enters the
     * battlefield tapped” and “When this permanent enters the battlefield, look
     * at the top four cards of your library. Exile one of them face down and
     * put the rest on the bottom of your library in any order. The exiled card
     * gains ‘Any player who has controlled the permanent that exiled this card
     * may look at this card in the exile zone.’”
     *
     */
    /**
     * Shelldock Isle Land Hideaway (This land enters the battlefield tapped.
     * When it does, look at the top four cards of your library, exile one face
     * down, then put the rest on the bottom of your library.) {T}: Add {U} to
     * your mana pool. {U}, {T}: You may play the exiled card without paying its
     * mana cost if a library has twenty or fewer cards in it.
     *
     */
    @Test
    public void testHideaway() {
        addCard(Zone.HAND, playerA, "Shelldock Isle");

        this.playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shelldock Isle");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Shelldock Isle", 1);
        assertExileCount(playerA, 1);
        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            Assert.assertTrue("Exiled card is not face down", card.isFaceDown(currentGame));
        }
    }

    /**
     * In commander, an opponent cast Ulamog, the Ceaseless Hunger off of
     * Mosswort Bridge. After it resolved, another opponent exile Ulamog with a
     * Quarantine Field. Ulamog was shown as exile face down, as it had been
     * from the Mosswort Bridge.
     */
    @Test
    public void testMosswortBridge() {
        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        // {T}: Add {G} to your mana pool.
        // {G}, {T}: You may play the exiled card without paying its mana cost if creatures you control have total power 10 or greater.
        addCard(Zone.HAND, playerA, "Mosswort Bridge");
        // When you cast Ulamog, the Ceaseless Hunger, exile two target permanents.
        // Indestructible
        // Whenever Ulamog attacks, defending player exiles the top twenty cards of his or her library.
        addCard(Zone.LIBRARY, playerA, "Ulamog, the Ceaseless Hunger");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile", 2);// 5/1

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mosswort Bridge");
        setChoice(playerA, "Ulamog, the Ceaseless Hunger");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{G},");
        addTarget(playerA, "Dross Crocodile^Dross Crocodile");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Dross Crocodile", 2);
        assertPermanentCount(playerA, "Mosswort Bridge", 1);
        assertExileCount(playerA, 2);
        assertExileCount("Ulamog, the Ceaseless Hunger", 0);

        assertPermanentCount(playerA, "Ulamog, the Ceaseless Hunger", 1);

        assertTapped("Mosswort Bridge", true);

        Permanent permanent = getPermanent("Ulamog, the Ceaseless Hunger", playerA);
        Card card = currentGame.getCard(permanent.getId());
        Assert.assertFalse("Previous exiled card may be no longer face down", card.isFaceDown(currentGame));
    }

    @Test
    public void testCannotPlayLandIfPlayedLand() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Ghost Quarter");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 0);
        assertTapped("Windbrisk Heights", true);
    }

    @Test
    public void testCannotPlayLandIfNotOwnTurn() {
        addCard(Zone.HAND, playerA, "Mosswort Bridge");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Dross Crocodile", 2);// 5/1

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mosswort Bridge");
        setChoice(playerA, "Ghost Quarter");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{G},");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 0);
        assertTapped("Mosswort Bridge", true);
    }

    @Test
    public void testCanPlayLandIfNotPlayedLand() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Ghost Quarter");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 1);
        assertTapped("Windbrisk Heights", true);
        Assert.assertEquals(playerA.getLandsPlayed(), 1);
    }

    @Test
    public void testCanPlayMoreLandsIfAble() {
        addCard(Zone.HAND, playerA, "Windbrisk Heights");
        addCard(Zone.LIBRARY, playerA, "Ghost Quarter");
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Auriok Champion", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Fastbond", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Windbrisk Heights");
        setChoice(playerA, "Ghost Quarter");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");

        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");
        attack(3, playerA, "Auriok Champion");

        activateAbility(3, PhaseStep.DECLARE_BLOCKERS, playerA, "{W},");

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Ghost Quarter", 1);
        assertTapped("Windbrisk Heights", true);
        Assert.assertEquals(playerA.getLandsPlayed(), 2);
    }

    /**
     * Reported bug issue #3310: Shelldock's hideaway requirement is for any library to have 20 or fewer cards, it only allows itself to be activated
     * sometimes when the owner of Shelldock's library has 20 or fewer cards, never the opponents is 20 or fewer
     */
    @Test
    public void shelldockIsleHideawayConditionOwnLibrary() {

         /*
         Shelldock Isle
         Land Hideaway
         {T}: Add {U} to your mana pool.
         {U}, {T}: You may play the exiled card without paying its mana cost if a library has twenty or fewer cards in it.
        */
        String sIsle = "Shelldock Isle";
        String ulamog = "Ulamog's Crusher"; // {8} 8/8 annihilator 2 attacks each turn if able

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, sIsle);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, ulamog, 4);
        skipInitShuffling();

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, sIsle);
        setChoice(playerA, ulamog);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Island", true, 1);
        assertPermanentCount(playerA, sIsle, 1);
        assertLibraryCount(playerA, 2);
        assertPermanentCount(playerA, ulamog, 1);
    }

    /**
     * Reported bug issue #3310: Shelldock's hideaway requirement is for any library to have 20 or fewer cards, it only allows itself to be activated
     * sometimes when the owner of Shelldock's library has 20 or fewer cards, never the opponents is 20 or fewer
     *
     * NOTE: test is currently failing due to bug in code. see issue #3310
     */
    @Test
    public void shelldockIsleHideawayConditionOpponentsLibrary() {

         /*
         Shelldock Isle
         Land Hideaway
         {T}: Add {U} to your mana pool.
         {U}, {T}: You may play the exiled card without paying its mana cost if a library has twenty or fewer cards in it.
        */
        String sIsle = "Shelldock Isle";
        String ulamog = "Ulamog's Crusher"; // {8} 8/8 annihilator 2 attacks each turn if able
        String bSable = "Bronze Sable"; // {2} 2/1 artifact creature

        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, sIsle);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, ulamog, 4);
        addCard(Zone.LIBRARY, playerB, bSable, 4);
        skipInitShuffling();

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, sIsle);
        setChoice(playerA, ulamog);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{U}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, sIsle, 1);
        assertLibraryCount(playerB, 3); // opponents library less than 20 so should be able to activate shelldock
        assertTappedCount("Island", true, 1);
        assertPermanentCount(playerA, ulamog, 1);
    }
}
