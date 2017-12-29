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
package org.mage.test.cards.restriction;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CantAttackTest extends CardTestPlayerBase {

    /**
     * Tests "If all other elves get the Forestwalk ability and can't be blocked
     * from creatures whose controller has a forest in game"
     */
    @Test
    public void testAttack() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Myr Enforcer"); // 4/4

        // Except for creatures named Akron Legionnaire and artifact creatures, creatures you control can't attack.
        addCard(Zone.BATTLEFIELD, playerB, "Akron Legionnaire"); // 8/4
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Myr Enforcer"); // 4/4

        attack(2, playerB, "Akron Legionnaire");
        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Myr Enforcer");

        attack(3, playerA, "Silvercoat Lion");
        attack(3, playerA, "Myr Enforcer");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 8); // 8 + 4
        assertLife(playerB, 14); // 4 + 2
    }

    @Test
    public void testAttackHarborSerpent() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        // Islandwalk (This creature is unblockable as long as defending player controls an Island.)
        // Harbor Serpent can't attack unless there are five or more Islands on the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Harbor Serpent"); // 5/5
        addCard(Zone.HAND, playerA, "Island");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Harbor Serpent"); // 5/5

        attack(2, playerB, "Harbor Serpent");
        attack(2, playerB, "Silvercoat Lion");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        attack(3, playerA, "Harbor Serpent");
        attack(3, playerA, "Silvercoat Lion");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 13);
        assertLife(playerA, 18);
    }

    @Test
    public void testBlazingArchon() {
        // Flying
        // Creatures can't attack you.
        addCard(Zone.BATTLEFIELD, playerA, "Blazing Archon");

        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // Planeswalker 4 loyality counter

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox"); // 2/4

        attack(2, playerB, "Pillarfield Ox", "Ajani Goldmane");
        attack(2, playerB, "Silvercoat Lion", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);

        assertTapped("Silvercoat Lion", false);
        assertTapped("Pillarfield Ox", true);
        assertCounterCount("Ajani Goldmane", CounterType.LOYALTY, 2);
    }

    @Test
    public void testCowedByWisdom() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Enchant creature
        // Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand.
        addCard(Zone.HAND, playerA, "Cowed by Wisdom"); // Planeswalker 4 loyality counter

        // Bushido 2 (When this blocks or becomes blocked, it gets +2/+2 until end of turn.)
        // Battle-Mad Ronin attacks each turn if able.
        addCard(Zone.BATTLEFIELD, playerB, "Battle-Mad Ronin");
        addCard(Zone.HAND, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cowed by Wisdom", "Battle-Mad Ronin");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);

        assertTapped("Battle-Mad Ronin", false);
    }

    // Orzhov Advokist's ability does not work. Your opponents get the counters but they can still attack you.
    @Test
    public void testOrzhovAdvokist() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // At the beginning of your upkeep, each player may put two +1/+1 counters on a creature he or she controls.
        // If a player does, creatures that player controls can't attack you or a planeswalker you control until your next turn.
        addCard(Zone.HAND, playerA, "Orzhov Advokist"); // Creature {2}{W} 1/4

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Orzhov Advokist");
        setChoice(playerA, "Yes");
        setChoice(playerB, "Yes");
        attack(2, playerB, "Silvercoat Lion");
        attack(4, playerB, "Silvercoat Lion");
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertPermanentCount(playerA, "Orzhov Advokist", 1);
        assertPowerToughness(playerA, "Orzhov Advokist", 3, 6);
        assertLife(playerA, 18);
        assertTapped("Silvercoat Lion", false);
        assertPowerToughness(playerB, "Silvercoat Lion", 4, 4);
    }
    
    /*
    Reported bug: Medomai was able to attack on an extra turn when cheated into play.
    */
    @Test
    public void testMedomaiShouldNotAttackOnExtraTurns() {
        
        /*
        Medomai the Ageless {4}{W}{U}
        Legendary Creature — Sphinx 4/4
        Flying
        Whenever Medomai the Ageless deals combat damage to a player, take an extra turn after this one.
        Medomai the Ageless can't attack during extra turns.
        */
        String medomai = "Medomai the Ageless";
        
        /*
         Cauldron Dance {4}{B}{R} Instant
        Cast Cauldron Dance only during combat.
        Return target creature card from your graveyard to the battlefield. That creature gains haste. Return it to your hand at the beginning of the next end step.
        You may put a creature card from your hand onto the battlefield. That creature gains haste. Its controller sacrifices it at the beginning of the next end step.
        */
        String cDance = "Cauldron Dance";
        String dBlade = "Doom Blade"; // {1}{B} instant destroy target creature        
        addCard(Zone.BATTLEFIELD, playerA, medomai);
        addCard(Zone.HAND, playerA, dBlade);
        addCard(Zone.HAND, playerA, cDance);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        
        // attack with Medomai, connect, and destroy him after combat
        attack(1, playerA, medomai);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, dBlade, medomai);
        
        // next turn granted, return Medomai to field with Cauldron and try to attack again
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, cDance);
        addTarget(playerA, medomai);
        attack(2, playerA, medomai);
        
        // medomai should not have been allowed to attack, but returned to hand at beginning of next end step still
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        
        assertLife(playerB, 16); // one hit from medomai
        assertGraveyardCount(playerA, dBlade, 1);
        assertGraveyardCount(playerA, cDance, 1);
        assertGraveyardCount(playerA, medomai, 0);
        assertHandCount(playerA, medomai, 1);
    }
    
    @Test
    public void basicMedomaiTestForExtraTurn() {
        /*
        Medomai the Ageless {4}{W}{U}
        Legendary Creature — Sphinx 4/4
        Flying
        Whenever Medomai the Ageless deals combat damage to a player, take an extra turn after this one.
        Medomai the Ageless can't attack during extra turns.
        */
        String medomai = "Medomai the Ageless";
        
        /*
         Exquisite Firecraft {1}{R}{R}
            Sorcery
            Exquisite Firecraft deals 4 damage to target creature or player.
        */
        String eFirecraft = "Exquisite Firecraft";
        
        addCard(Zone.BATTLEFIELD, playerA, medomai);
        addCard(Zone.HAND, playerA, eFirecraft);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        
        // attack with medomai, get extra turn, confirm cannot attack again with medomai and can cast sorcery
        attack(1, playerA, medomai);
        attack(2, playerA, medomai); // should not be allowed to
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, eFirecraft, playerB);
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        
        assertLife(playerB, 12); // 1 hit from medomai and firecraft = 8 damage
        assertGraveyardCount(playerA, eFirecraft, 1);
        assertPermanentCount(playerA, medomai, 1);
    }
    
    @Test
    public void sphereOfSafetyPaidCostAllowsAttack() {        
        /*
        Sphere of Safety {4}{W}
         Enchantment
        Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
        */
        String sphere = "Sphere of Safety";
        String memnite = "Memnite";
               
        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, sphere);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        
        attack(1, playerA, memnite);
        setChoice(playerA, "Yes");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerB, sphere, 1);
        assertLife(playerB, 19); // took the hit from memnite
        assertTapped("Forest", true); // forest had to be tapped
    }
    
    @Test
    public void sphereOfSafetyCostNotPaid_NoAttackAllowed() {
        /*
        Sphere of Safety {4}{W}
         Enchantment
        Creatures can't attack you or a planeswalker you control unless their controller pays {X} for each of those creatures, where X is the number of enchantments you control.
        */
        String sphere = "Sphere of Safety";
        String memnite = "Memnite";
               
        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, sphere);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        
        attack(1, playerA, memnite);
        setChoice(playerA, "No");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerB, sphere, 1);
        assertLife(playerB, 20); // no damage went through, did not elect to pay
        assertTapped("Forest", false); // forest not tapped
    }
    
    @Test
    public void collectiveResistanceCostPaid_AttackAllowed()
    {
        /*
        Collective Restraint {3}{U}
        Enchantment
        Domain — Creatures can't attack you unless their controller pays {X} for each creature he or she controls that's attacking you, where X is the number of basic land types among lands you control.
        */
        String cRestraint = "Collective Restraint";
        String memnite = "Memnite";
               
        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, cRestraint);
        addCard(Zone.BATTLEFIELD, playerB, "Island"); // 1 basic land type = pay 1 to attack
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        
        attack(1, playerA, memnite);
        setChoice(playerA, "Yes");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerB, cRestraint, 1);
        assertLife(playerB, 19); // took the hit from memnite
        assertTapped("Forest", true); // forest had to be tapped
    }
    
    @Test
    public void collectiveResistanceCostNotPaid_NoAttackAllowed()
    {
        /*
        Collective Restraint {3}{U}
        Enchantment
        Domain — Creatures can't attack you unless their controller pays {X} for each creature he or she controls that's attacking you, where X is the number of basic land types among lands you control.
        */
        String cRestraint = "Collective Restraint";
        String memnite = "Memnite";
               
        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, cRestraint);
        addCard(Zone.BATTLEFIELD, playerB, "Island"); // 1 basic land type = pay 1 to attack
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        
        attack(1, playerA, memnite);
        setChoice(playerA, "No");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerB, cRestraint, 1);
        assertLife(playerB, 20); // no damage went through, did not elect to pay
        assertTapped("Forest", false); // forest not tapped
    }
    
    @Test
    public void ghostlyPrison_PaidCost_AllowsAttack() {        
        /*
        Ghostly Prison {2}{W}
        Enchantment
        Creatures can't attack you unless their controller pays {2} for each creature he or she controls that's attacking you.
        */
        String gPrison = "Ghostly Prison";
        String memnite = "Memnite";
               
        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, gPrison);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        
        attack(1, playerA, memnite);
        setChoice(playerA, "Yes");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerB, gPrison, 1);
        assertLife(playerB, 19); // took the hit from memnite
        assertTappedCount("Forest", true, 2);  // forests had to be tapped
    }
    
    @Test
    public void ghostlyPrison_CostNotPaid_NoAttackAllowed() {
        /*
        Ghostly Prison {2}{W}
        Enchantment
        Creatures can't attack you unless their controller pays {2} for each creature he or she controls that's attacking you.
        */
        String gPrison = "Ghostly Prison";
        String memnite = "Memnite";
               
        addCard(Zone.BATTLEFIELD, playerA, memnite); // {0} 1/1
        addCard(Zone.BATTLEFIELD, playerB, gPrison);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        
        attack(1, playerA, memnite);
        setChoice(playerA, "No");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerB, gPrison, 1);
        assertLife(playerB, 20); // no damage went through, did not elect to pay
        assertTapped("Forest", false); // no forests tapped
    }
}
