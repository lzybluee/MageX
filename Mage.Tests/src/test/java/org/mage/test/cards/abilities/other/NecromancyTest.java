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
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class NecromancyTest extends CardTestPlayerBase {

    /**
     * Necromancy Enchantment, 2B (3) You may cast Necromancy as though it had
     * flash. If you cast it any time a sorcery couldn't have been cast, the
     * controller of the permanent it becomes sacrifices it at the beginning of
     * the next cleanup step. When Necromancy enters the battlefield, if it's on
     * the battlefield, it becomes an Aura with "enchant creature put onto the
     * battlefield with Necromancy." Put target creature card from a graveyard
     * onto the battlefield under your control and attach Necromancy to it. When
     * Necromancy leaves the battlefield, that creature's controller sacrifices
     * it.
     *
     */
    @Test
    public void testNecromancy() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Necromancy");
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Necromancy");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerA, "Necromancy", 1);
        assertGraveyardCount(playerA, "Craw Wurm", 0);

    }

    @Test
    public void testNecromancyFlash() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Necromancy");
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Necromancy");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerA, "Necromancy", 1);
        assertGraveyardCount(playerA, "Craw Wurm", 0);

    }

    /**
     * Check if Necromancy is sacrificed if cast as instant and if the
     * reanimated creature will be sacrificed.
     */
    @Test
    public void testNecromancyFlashSacrifice() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.
        // When Necromancy leaves the battlefield, that creature's controller sacrifices it.
        addCard(Zone.HAND, playerA, "Necromancy");
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm"); // 6/4

        castSpell(1, PhaseStep.UPKEEP, playerA, "Necromancy");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertPermanentCount(playerA, "Necromancy", 0);
        assertGraveyardCount(playerA, "Craw Wurm", 1);
        assertGraveyardCount(playerA, "Necromancy", 1);
    }

    @Test
    public void testNecromancyLeaves() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Necromancy");
        addCard(Zone.HAND, playerA, "Disenchant");
        addCard(Zone.GRAVEYARD, playerA, "Craw Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Necromancy"); // enchanting the Craw Wurm
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disenchant", "Necromancy"); // if Necromancy leaves, the enchanted creature has to leave too

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disenchant", 1);
        assertPermanentCount(playerA, "Necromancy", 0);
        assertPermanentCount(playerA, "Craw Wurm", 0);
        assertGraveyardCount(playerA, "Necromancy", 1);
        assertGraveyardCount(playerA, "Craw Wurm", 1);
    }

    /**
     * I was playing a legendary cube, flashed in a Necromancy to block and when
     * the creature I reanimated died the game bugged out and I lost.
     */
    @Test
    public void testBlockWithNecromancyCreature() {
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Necromancy"); // {2}{B}
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Necromancy"); // enchanting the Silvercoat Lion
        block(2, playerA, "Silvercoat Lion", "Silvercoat Lion");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Necromancy", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }
}
