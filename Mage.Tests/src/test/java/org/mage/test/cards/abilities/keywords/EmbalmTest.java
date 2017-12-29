/*
 *  Copyright 2017 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class EmbalmTest extends CardTestPlayerBase {

    /*
     * Tests that Embalm doesn't affect card to be case as usual card with ETB trigger
     */
    @Test
    public void testCreatureWithEmbalmJustCastAndTarget() {

        /*
        Angel of Sanctions {3}{W}{W}
        Creature - Angel
        Flying
        When Angel of Sanctions enters the battlefield, you may exile target nonland permanent an opponent controls until Angel of Sanctions leaves the battlefield.
        Embalm {5}{W}
         */
        String aSanctions = "Angel of Sanctions"; // {W} 3/4
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2

        addCard(Zone.HAND, playerA, aSanctions);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        addCard(Zone.BATTLEFIELD, playerB, yOx);
        addCard(Zone.BATTLEFIELD, playerB, wKnight);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aSanctions);
        addTarget(playerA, yOx);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, aSanctions, 1);
        assertPermanentCount(playerB, yOx, 0);
        assertPermanentCount(playerB, wKnight, 1);

    }

    /*
     * Tests that creature after using Embalm will be able to use ETB effect from original card
     */
    @Test
    public void testCreatureETBAfterEmbalm() {

        /*
        Angel of Sanctions {3}{W}{W}
        Creature - Angel
        Flying
        When Angel of Sanctions enters the battlefield, you may exile target nonland permanent an opponent controls until Angel of Sanctions leaves the battlefield.
        Embalm {5}{W}
         */
        String aSanctions = "Angel of Sanctions"; // {3}{W}{W} 3/4
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2
        String dBlade = "Doom Blade"; // {1}{B}

        addCard(Zone.HAND, playerA, aSanctions);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 11);

        addCard(Zone.HAND, playerB, dBlade);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, yOx);
        addCard(Zone.BATTLEFIELD, playerB, wKnight);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aSanctions);
        addTarget(playerA, yOx);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, dBlade);
        addTarget(playerB, aSanctions);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Embalm");
        addTarget(playerA, wKnight);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, 0);
        assertPermanentCount(playerA, aSanctions, 1);
        assertPermanentCount(playerB, yOx, 1);
        assertPermanentCount(playerB, wKnight, 0);
        assertGraveyardCount(playerA, aSanctions, 0);
    }

    /*
     * Tests that not only creature targeted by original creature is returned.
     * After using Embalm creature will exile another creature and should return it back when leaves battlefield.
     *
     * Bug: #3144
     */
    @Test
    public void testCreatureExiledByEmbalmCreatureReturns() {

        /*
        Angel of Sanctions {3}{W}{W}
        Creature - Angel
        Flying
        When Angel of Sanctions enters the battlefield, you may exile target nonland permanent an opponent controls until Angel of Sanctions leaves the battlefield.
        Embalm {5}{W}
         */
        String aSanctions = "Angel of Sanctions"; // {3}{W}{W} 3/4
        String yOx = "Yoked Ox"; // {W} 0/4
        String wKnight = "White Knight"; // {W}{W} 2/2
        String dBlade = "Doom Blade"; // {1}{B}

        addCard(Zone.HAND, playerA, aSanctions);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 11);

        addCard(Zone.HAND, playerB, dBlade, 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerB, yOx);
        addCard(Zone.BATTLEFIELD, playerB, wKnight);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aSanctions);
        addTarget(playerA, yOx);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, dBlade);
        addTarget(playerB, aSanctions);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Embalm");
        addTarget(playerA, wKnight);
        castSpell(1, PhaseStep.END_TURN, playerB, dBlade);
        addTarget(playerB, aSanctions);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertHandCount(playerB, 0);
        assertPermanentCount(playerA, aSanctions, 0);
        assertPermanentCount(playerB, yOx, 1);
        // second creature should also return after embalm token leaves battlefield
        // Bug: #3144
        assertPermanentCount(playerB, wKnight, 1);
        assertGraveyardCount(playerA, aSanctions, 0);
        assertGraveyardCount(playerB, dBlade, 2);
    }
}
