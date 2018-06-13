
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class GiltLeafPalace extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Elf from your hand");
    static {
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public GiltLeafPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Gilt-Leaf Palace enters the battlefield, you may reveal an Elf card from your hand. If you don't, Gilt-Leaf Palace enters the battlefield tapped.
       this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Elf card from your hand. If you don't, {this} enters the battlefield tapped"));
        // {tap}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

    }

    public GiltLeafPalace(final GiltLeafPalace card) {
        super(card);
    }

    @Override
    public GiltLeafPalace copy() {
        return new GiltLeafPalace(this);
    }
}
