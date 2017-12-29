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
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HauntTest extends CardTestPlayerBase {

    /**
     * Blind Hunter - 2WB
     * Creature — Bat
     * 2/2
     * Flying
     * Haunt (When this creature dies, exile it haunting target creature.)
     * When Blind Hunter enters the battlefield or the creature it haunts dies, target player loses 2 life and you gain 2 life.
     *
     */
    
    // test that Haunting and Haunted by rules are added to cards
    @Test
    public void testAddHaunt() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Blind Hunter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blind Hunter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertExileCount("Blind Hunter", 1);
        
        boolean found = false;
        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals("Blind Hunter")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Haunting") &&  rule.contains("Goblin Roughrider")) {
                        found = true;
                    }
                }
            }
        }
        Assert.assertTrue("Couldn't find Haunting rule text displayed for the card", found);

        found = false;
        for (Card card : currentGame.getBattlefield().getAllActivePermanents()) {
            if (card.getName().equals("Goblin Roughrider")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Haunted by") &&  rule.contains("Blind Hunter")) {
                        found = true;
                    }
                }
            }
        }
        Assert.assertTrue("Couldn't find Haunted by rule text displayed for the card", found);
        
    }

    // test that Haunted by rule is removed from cards (it is only added to permanent)
    @Test
    public void testRemoveHaunt() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Blind Hunter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blind Hunter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Goblin Roughrider");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertExileCount("Blind Hunter", 1);
        assertGraveyardCount(playerA, "Goblin Roughrider", 1);
        

        boolean found = false;
        for (Card card : currentGame.getPlayer(playerA.getId()).getGraveyard().getCards(currentGame)) {
            if (card.getName().equals("Goblin Roughrider")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Haunted by") &&  rule.contains("Blind Hunter")) {
                        found = true;
                    }
                }
            }
        }
        Assert.assertFalse("Found Haunted by rule text displayed for the card", found);
        
    }
    
}
