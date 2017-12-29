package org.mage.test.cards.conditional;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayrat
 */
public class SejiriMerfolkTest extends CardTestPlayerBase {

    @Test
    public void testWithoutPlains() {
        addCard(Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        Permanent merfolk = getPermanent("Sejiri Merfolk", playerA.getId());
        Assert.assertNotNull(merfolk);
        Assert.assertFalse(merfolk.getAbilities().contains(FirstStrikeAbility.getInstance()));
        Assert.assertFalse(merfolk.getAbilities().contains(LifelinkAbility.getInstance()));
    }

    @Test
    public void testWithPlains() {
        addCard(Zone.BATTLEFIELD, playerA, "Sejiri Merfolk");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        setStopAt(1, PhaseStep.DRAW);
        execute();

        Permanent merfolk = getPermanent("Sejiri Merfolk", playerA.getId());
        Assert.assertNotNull(merfolk);
        Assert.assertTrue(merfolk.getAbilities().contains(FirstStrikeAbility.getInstance()));
        Assert.assertTrue(merfolk.getAbilities().contains(LifelinkAbility.getInstance()));
    }
}
