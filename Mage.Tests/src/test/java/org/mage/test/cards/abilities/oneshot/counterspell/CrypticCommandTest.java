/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Cryptic Command Instant, 1UUU Choose two — Counter target spell; or return
 * target permanent to its owner's hand; or tap all creatures your opponents
 * control; or draw a card.
 *
 * @author LevelX2
 */
public class CrypticCommandTest extends CardTestPlayerBase {

    /**
     * Test that if command has only one target and that targets is not valid on
     * resolution, Cryptic Command fizzeles The player does not draw a card
     */
    @Test
    public void testCommand() {
        addCard(Zone.HAND, playerA, "Thoughtseize");
        // Counter target spell. If that spell is countered this way, put it into its owner's hand instead of into that player's graveyard.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Remand");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.HAND, playerB, "Cryptic Command");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtseize", playerB);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cryptic Command", "Thoughtseize");
        setModeChoice(playerB, "1"); // Counter target spell
        setModeChoice(playerB, "4"); // Draw a card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Remand", "Thoughtseize", "Cast Cryptic Command");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Remand", 1);
        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, "Cryptic Command", 1);
        assertGraveyardCount(playerB, 1);
        assertHandCount(playerA, 2); // Thoughtsize + card drawn from Remand
        assertHandCount(playerB, 0); // Because Cryptic Command has no legal target playerB does not draw a card and has 0 cards in hand

    }

    /**
     * Game is not letting me play Ricochet Trap targetting oponent's Cryptic
     * Command, modes 1 and 4. It only has one target and should be allowed
     */
    @Test
    public void testCommandChangeTarget() {
        // Target player reveals his or her hand. You choose a nonland card from it. That player discards that card. You lose 2 life.
        addCard(Zone.HAND, playerA, "Thoughtseize");
        // Counter target spell. If that spell is countered this way, put it into its owner's hand instead of into that player's graveyard.
        // Draw a card.
        addCard(Zone.HAND, playerA, "Ricochet Trap");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        addCard(Zone.HAND, playerB, "Cryptic Command");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thoughtseize", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cryptic Command", "mode=1Thoughtseize", "Thoughtseize", StackClause.WHILE_ON_STACK);
        setModeChoice(playerB, "1"); // Counter target spell
        setModeChoice(playerB, "4"); // Draw a card

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ricochet Trap", "Cryptic Command", "Cryptic Command", StackClause.WHILE_ON_STACK);
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 18); // -2 from Thoughtseize
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Ricochet Trap", 1);
        assertGraveyardCount(playerA, "Thoughtseize", 1);
        assertGraveyardCount(playerB, "Cryptic Command", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertHandCount(playerB, 1); // card drawn from Cryptic Command

    }
}
