package org.mage.test.cards.single;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HavengulLichTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        addCard(Zone.GRAVEYARD, playerA, "Prodigal Pyromancer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Prodigal Pyromancer");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prodigal Pyromancer");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: {source} deals", playerB);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertPermanentCount(playerA, "Prodigal Pyromancer", 1);
        assertTapped("Havengul Lich", true);
        assertTapped("Prodigal Pyromancer", false);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        addCard(Zone.GRAVEYARD, playerA, "Black Cat");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}", "Black Cat");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Black Cat");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertPermanentCount(playerA, "Black Cat", 0);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // {1}: You may cast target creature card in a graveyard this turn. When you cast that card this turn, Havengul Lich
        // gains all activated abilities of that card until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        // {T}: Prodigal Pyromancer deals 1 damage to target creature or player.
        addCard(Zone.GRAVEYARD, playerA, "Prodigal Pyromancer");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: You may", "Prodigal Pyromancer");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Prodigal Pyromancer");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals", playerB);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals", playerB); // only inm turn 1, so Havengul Lich has the abilit ylost now
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertPermanentCount(playerA, "Prodigal Pyromancer", 1);
        assertTapped("Prodigal Pyromancer", true);
        assertTapped("Havengul Lich", false);
        assertGraveyardCount(playerA, 0);
    }

    /**
     * hey i play a Havengul Lich and Heartless Summoning deck. and every time
     * the lich is on the field as the same time as heartless summoning i cant
     * use the lich's ability correctly. because if you know magic you know that
     * a creature with one toughness will still enter the battlefield and
     * immediately die but the creature's effect will still trigger. like when i
     * "cast" Perilous Myr from the graveyard with the lich it doesn't actually
     * enter the battlefield and its death ability doesn't trigger! and that is
     * one of my decks i cast from the graveyard with the lich and things are
     * SUPPOSED to enter the battlefield and die triggering whatever ability the
     * creature might have alot like "myr retriever"! plz fix it, this is the
     * second time i have had to mention this problem!
     */
    @Test
    public void testCardHeartlessSummoning() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // {1}: You may cast target creature card in a graveyard this turn. When you cast that card this turn, Havengul Lich
        // gains all activated abilities of that card until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Lich");
        // Creature spells you cast cost {2} less to cast.
        // Creatures you control get -1/-1.
        addCard(Zone.BATTLEFIELD, playerA, "Heartless Summoning"); // Enchantment
        // When Perilous Myr dies, it deals 2 damage to target creature or player.
        addCard(Zone.GRAVEYARD, playerA, "Perilous Myr");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}: You may", "Perilous Myr");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Perilous Myr");
        addTarget(playerA, playerB);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);

        assertPermanentCount(playerA, "Havengul Lich", 1);
        assertGraveyardCount(playerA, "Perilous Myr", 1);

    }

}
