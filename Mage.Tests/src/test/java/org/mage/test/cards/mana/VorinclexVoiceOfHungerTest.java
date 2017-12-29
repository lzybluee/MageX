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
package org.mage.test.cards.mana;

import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class VorinclexVoiceOfHungerTest extends CardTestPlayerBase {

    /**
     * Vorinclex, Voice of Hunger is not mana doubling River of Tears.
     */
    @Test
    public void testRiverOfTears() {
        // Trample
        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Vorinclex, Voice of Hunger", 1);
        // {T}: Add {U} to your mana pool. If you played a land this turn, add {B} to your mana pool instead.
        addCard(Zone.BATTLEFIELD, playerA, "River of Tears", 1);
        addCard(Zone.HAND, playerA, "Vedalken Mastermind", 1);

        // because available mana calculation does not work correctly with Vorinclex, Voice of Hunger we have to tap the land manually
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U} to your mana pool");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vedalken Mastermind");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vedalken Mastermind", 1);
    }

    /**
     * Vorinclex, Voice of Hunger is not mana doubling River of Tears.
     */
    @Test
    // @Ignore // TODO: need to fix Vorinclex, Voice of Hunger -- it's double fireup mana tap event
    public void testVorinclexVoiceofHungerRiverOfTearsManaMultiplier() {
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);
        addCard(Zone.HAND, playerA, "River of Tears", 1);
        // Trample
        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Vorinclex, Voice of Hunger", 1);
        // {T}: Add {U} to your mana pool. If you played a land this turn, add {B} to your mana pool instead.
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "River of Tears");

        activateManaAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U} to your mana pool");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertManaPool(playerA, ManaType.BLUE, 2);
    }

    /**
     * Vorinclex glitches with Gemstone Cavern
     */
    @Test
    public void testGemstoneCavern() {
        // Trample
        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerB, "Vorinclex, Voice of Hunger", 1); // {6}{G}{G}

        // If Gemstone Caverns is in your opening hand and you're not playing first, you may begin the game with Gemstone Caverns on the battlefield with a luck counter on it. If you do, exile a card from your hand.
        // {T}: Add {C} to your mana pool. If Gemstone Caverns has a luck counter on it, instead add one mana of any color to your mana pool.
        addCard(Zone.HAND, playerB, "Gemstone Caverns", 1);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add");
        setChoice(playerB, "White");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Silvercoat Lion");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Gemstone Caverns", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
        assertExileCount("Silvercoat Lion", 1);
        assertTapped("Gemstone Caverns", true);

    }

    @Test
    public void testCastWithGemstoneCavern() {
        // Trample
        // Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.HAND, playerB, "Vorinclex, Voice of Hunger", 1); // {6}{G}{G}
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 7);

        // If Gemstone Caverns is in your opening hand and you're not playing first, you may begin the game with Gemstone Caverns on the battlefield with a luck counter on it. If you do, exile a card from your hand.
        // {T}: Add {C} to your mana pool. If Gemstone Caverns has a luck counter on it, instead add one mana of any color to your mana pool.
        addCard(Zone.HAND, playerB, "Gemstone Caverns", 1);

        addCard(Zone.HAND, playerB, "Silvercoat Lion", 2);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add");
        setChoice(playerB, "White");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Vorinclex, Voice of Hunger");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Gemstone Caverns", 1);
        assertCounterCount("Gemstone Caverns", CounterType.LUCK, 1);
        assertPermanentCount(playerB, "Vorinclex, Voice of Hunger", 1);
        assertTapped("Gemstone Caverns", true);

    }
}
