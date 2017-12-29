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
package org.mage.test.cards.continuous;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PaintersServantTest extends CardTestPlayerBase {

    /**
     * Test that the added color is applied as Painter's Servant is on the battlefield
     */
    @Test
    public void testColorSet() {
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant", 1);        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Blue");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Painter's Servant", 1);
        
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isWhite());
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isBlue());
        
        silvercoatLion = getPermanent("Silvercoat Lion", playerB);
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isWhite());
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isBlue());

        for(Card card: playerA.getLibrary().getCards(currentGame)) {
            Assert.assertEquals(card.getName() + " should be blue",true, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerB.getLibrary().getCards(currentGame)) {
            Assert.assertEquals(card.getName() + " should be blue",true, card.getColor(currentGame).isBlue());
        }
        
        for(Card card: playerA.getHand().getCards(currentGame)) {
            Assert.assertEquals(true, card.getColor(currentGame).isRed());
            Assert.assertEquals(true, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerB.getHand().getCards(currentGame)) {
            Assert.assertEquals(true, card.getColor(currentGame).isRed());
            Assert.assertEquals(true, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerA.getGraveyard().getCards(currentGame)) {
            Assert.assertEquals(true, card.getColor(currentGame).isWhite());
            Assert.assertEquals(true, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerB.getGraveyard().getCards(currentGame)) {
            Assert.assertEquals(true, card.getColor(currentGame).isWhite());
            Assert.assertEquals(true, card.getColor(currentGame).isBlue());
        }

    }

    /**
     * Test that the added color is no longer applied as Painter's Servant has left the battlefield
     */
    @Test
    public void testColorReset() {
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant", 1);        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Lightning Bolt",2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Blue");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Painter's Servant");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Painter's Servant", 1);
        
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isWhite());
        Assert.assertEquals(false, silvercoatLion.getColor(currentGame).isBlue());
        
        silvercoatLion = getPermanent("Silvercoat Lion", playerB);
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isWhite());
        Assert.assertEquals(false, silvercoatLion.getColor(currentGame).isBlue());

        for(Card card: playerA.getLibrary().getCards(currentGame)) {
            Assert.assertEquals(card.getName() + " should not be blue",false, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerB.getLibrary().getCards(currentGame)) {
            Assert.assertEquals(card.getName() + " should not be blue",false, card.getColor(currentGame).isBlue());
        }

        for(Card card: playerA.getHand().getCards(currentGame)) {
            Assert.assertEquals(true, card.getColor(currentGame).isRed());
            Assert.assertEquals(false, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerB.getHand().getCards(currentGame)) {
            Assert.assertEquals(true, card.getColor(currentGame).isRed());
            Assert.assertEquals(false, card.getColor(currentGame).isBlue());
        }
        for(Card card: playerA.getGraveyard().getCards(currentGame)) {
            if(card.getName().equals("Silvercoat Lion")) {
                Assert.assertEquals(true, card.getColor(currentGame).isWhite());
                Assert.assertEquals(false, card.getColor(currentGame).isBlue());
            }
        }
        for(Card card: playerB.getGraveyard().getCards(currentGame)) {
            if(card.getName().equals("Silvercoat Lion")) {
                Assert.assertEquals(true, card.getColor(currentGame).isWhite());
                Assert.assertEquals(false, card.getColor(currentGame).isBlue());
            }
        }

    }

    /**
     * 5/1/2008 While Painter's Servant is on the battlefield, an effect that
     * changes an object's colors will overwrite Painter's Servant's effect. For
     * example, casting Cerulean Wisps on a creature will turn it blue,
     * regardless of the color chosen for Painter's Servant.
     */
    @Test
    public void testColorOverwrite() {
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant", 1);        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Target creature becomes blue until end of turn. Untap that creature.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Cerulean Wisps", 1); // Instant {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);    
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Red");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cerulean Wisps", "Silvercoat Lion", "Painter's Servant", StackClause.WHILE_NOT_ON_STACK);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Painter's Servant", 1);
        
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(false, silvercoatLion.getColor(currentGame).isWhite());
        Assert.assertEquals(false, silvercoatLion.getColor(currentGame).isRed());
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isBlue());
    }
    
   /**
     * Check color of spells 
     */
    @Test
    public void testColorSpell() {
        // As Painter's Servant enters the battlefield, choose a color.
        // All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.
        addCard(Zone.HAND, playerA, "Painter's Servant", 1);        
        // Draw two cards.
        addCard(Zone.HAND, playerA, "Divination", 1); // {U}{2}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);              
        // Whenever a player casts a red spell, you may gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Dragon's Claw");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // Target creature becomes blue until end of turn. Untap that creature.
        // Draw a card.
        addCard(Zone.HAND, playerB, "Cerulean Wisps", 1); // Instant {U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);    
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Painter's Servant");
        setChoice(playerA, "Red");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cerulean Wisps", "Silvercoat Lion", "Painter's Servant", StackClause.WHILE_NOT_ON_STACK);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination", NO_TARGET, "Painter's Servant", StackClause.WHILE_NOT_ON_STACK);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Painter's Servant", 1);
        
        assertGraveyardCount(playerA, "Divination", 1);
        assertGraveyardCount(playerB, "Cerulean Wisps", 1);
        assertLife(playerA, 22); // + 1 from Cerulean Wisps + 1 from Divination
        
        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(false, silvercoatLion.getColor(currentGame).isWhite());
        Assert.assertEquals(false, silvercoatLion.getColor(currentGame).isRed());
        Assert.assertEquals(true, silvercoatLion.getColor(currentGame).isBlue());
        
        
    }
 
    
}
