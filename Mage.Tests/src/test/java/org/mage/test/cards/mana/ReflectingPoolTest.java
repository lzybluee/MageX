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

import mage.abilities.mana.ManaOptions;
import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.*;

/**
 *
 * @author LevelX2, JayDi85
 */
public class ReflectingPoolTest extends CardTestPlayerBase {

    /**
     * Reflecting Pool does not count Crumbling Vestige as a source of all mana
     * colors
     */
    @Test
    public void testTriggeredManaAbility() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // {R}

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        // Crumbling Vestige enters the battlefield tapped.
        // When Crumbling Vestige enters the battlefield, add one mana of any color to your mana pool.
        // {T}: Add {C} to you mana pool.
        addCard(Zone.HAND, playerA, "Crumbling Vestige", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crumbling Vestige");
        setChoice(playerA, "Red");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Crumbling Vestige", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    /**
     * Reflecting Pool does not see what mana Exotic Orchard can produce
     */
    @Test
    public void testWithExoticOrchard() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Exotic Orchard", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 2 red mana", "{R}{R}", options.get(0).toString());

    }

    /**
     * Test 2 Reflecting Pools
     */
    @Test
    public void test2WithExoticOrchard() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 2);
        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Exotic Orchard", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 3 red mana", "{R}{R}{R}", options.get(0).toString());

    }

    /**
     * Test 2 Reflecting Pools
     */
    @Test
    public void testWith2ExoticOrchard() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Exotic Orchard", 2);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 3 red mana", "{R}{R}{R}", options.get(0).toString());

    }

    /**
     * Reflecting Pool does not see Gaea's Cradle or Serra's Sanctum as
     * producing mana
     */
    @Test
    public void testWithGaeasCradle() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        // {T}: Add {G} to your mana pool for each creature you control.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Cradle", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player should be able to create 2 red mana", "{G}{G}", options.get(0).toString());

    }

    /**
     * Reflecting Pool does not see Gaea's Cradle or Serra's Sanctum as
     * producing mana
     */
    @Test
    public void testWithDifferentLands() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        // {T}: Add to your mana pool one mana of any type that a land you control could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerA, "Exotic Orchard", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // {T}: Add to your mana pool one mana of any color that a land an opponent controls could produce.
        addCard(Zone.BATTLEFIELD, playerB, "Exotic Orchard", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions options = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player A should be able to create only 3 different mana options", 3, options.size());
        assertManaOptions("{G}{G}{G}", options);
        assertManaOptions("{W}{G}{G}", options);
        assertManaOptions("{W}{W}{G}", options);

        options = playerB.getAvailableManaTest(currentGame);
        Assert.assertEquals("Player B should be able to create only 2 different mana options", 2, options.size());
        assertManaOptions("{W}{G}", options);
        assertManaOptions("{W}{W}", options);
    }


    @Test
    public void testReflectingPoolGiveNonMana() {
        addCard(Zone.HAND, playerA, bear1, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear1); // do not have any mana
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals(0, playerA.getManaPool().getMana().count());
        assertPermanentCount(playerA, bear1, 0);
    }

    @Test
    public void testReflectingPoolGiveNonMana2() {
        addCard(Zone.HAND, playerA, bear1, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear1); // do not have any mana
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        Assert.assertEquals(0, playerA.getManaPool().getMana().count());
        assertPermanentCount(playerA, bear1, 0);
    }

    @Test
    public void testReflectingPoolGiveBasicManaNeed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, bear1G, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear1G); // have {G} mana to cast
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bear1G, 1);
    }

    @Test
    public void testReflectingPoolGiveBasicManaNotNeed() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, bear1G, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear1G); // have only {W} mana, can't cast
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bear1G, 0);
    }

    @Test
    public void testReflectingPoolAnyManaNeedWithoutCondition() {
        // any mana source without conditions (use any mana at any time)
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "City of Brass", 1);
        String bear2GG = "Razorclaw Bear";
        addCard(Zone.HAND, playerA, bear2GG, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear2GG); // 2 plains + 2 any -- can cast
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bear2GG, 1);
    }

    @Test
    public void testReflectingPoolAnyManaNeedWithCondition() {
        // any mana source have condition to use (Reflecting Pool must ignore that condition)
        addCard(Zone.BATTLEFIELD, playerA, "Cavern of Souls", 1); // {C} or {any}
        addCard(Zone.HAND, playerA, bear1G, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bear1G); // {C} from cavern and {any} (green) from reflection
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bear1G, 1);
    }

    @Test
    public void testReflectingPoolAnyManaTapped() {
        // any mana source with tapped must allow use any too
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "City of Brass", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}");
        setChoice(playerA,"Black");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        logger.info(playerA.getManaPool().getMana().toString());
        logger.info(playerA.getManaAvailable(currentGame).toString());
        assertTapped("City of Brass", true);
        assertTapped("Plains", true);
        assertTapped("Reflecting Pool", false);
        Assert.assertEquals(1, playerA.getManaPool().get(ManaType.BLACK));
    }
}
