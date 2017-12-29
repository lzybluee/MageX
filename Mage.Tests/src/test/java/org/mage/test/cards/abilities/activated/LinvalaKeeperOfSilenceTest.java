package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

/**
 * Created by Derek M on 3/6/2017.
 */
public class LinvalaKeeperOfSilenceTest extends CardTestPlayerBase {

    /*
     * Reported bug: Linvala, Keeper of Silence, played by my opponent, did not prevent me from activating abilities with the "tap" symbol.
     * Examples included Jagged-Scar Archers and Imperious Perfect;
     */
    @Test
    public void linvalaSilencesActivatedAbilities()
    {
        String linvala = "Linvala, Keeper of Silence";
        String jScarArcher = "Jagged-Scar Archers";
        String imperiousPerfect = "Imperious Perfect";

          /*
            Linvala, Keeper of Silence {2}{W}{W}
            Legendary Creature - Angel 3/4
            Flying
            Activated abilities of creatures your opponents control can't be activated.
         */
        addCard(Zone.BATTLEFIELD, playerA, linvala);

        /*
        {1}{G}{G} Elf Archer * / *
        Jagged-Scar Archers's power and toughness are each equal to the number of Elves you control.
        Tap: Jagged-Scar Archers deals damage equal to its power to target creature with flying.
       */
        addCard(Zone.BATTLEFIELD, playerB, jScarArcher);

        /*
        {2}{G} Creature - Elf Warrior 2/2
        Other Elf creatures you control get +1/+1.
        {G}, Tap: Create a 1/1 green Elf Warrior creature token.
       */
        addCard(Zone.BATTLEFIELD, playerB, imperiousPerfect);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{G}:"); // Imperious Perfect: create 1/1 elf warrior token
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerB, "Jagged", linvala); // Jagged-scar: deal damage to Linvala equal to elves in play

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, linvala, 1);
        assertPowerToughness(playerB, jScarArcher, 3, 3);
        Permanent p = getPermanent(linvala, playerA);
        Assert.assertEquals("Linvala should have no damage dealt to her", 0, p.getDamage());
    }
}
