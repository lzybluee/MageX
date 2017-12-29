package org.mage.test.clientside;

import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.clientside.base.MageAPIExtended;

import static org.mage.test.clientside.base.MageAPI.Owner.mine;

public class LandTestExtended extends MageAPIExtended {

    @Test
    public void testPlayingLandInMainPhase() throws Exception {
        addCard("Mountain", Zone.HAND);
        setPhase("Precombat Main", mine);
        play("Mountain");
        assertBattlefield("Mountain");
        assertGraveyardsCount(0);
    }
}
