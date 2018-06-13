
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.i.InsidiousMist;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ElusiveTormentor extends CardImpl {

    public ElusiveTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.transformable = true;
        this.secondSideCardClazz = InsidiousMist.class;

        // {1}, Discard a card: Transform Elusive Tormentor.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public ElusiveTormentor(final ElusiveTormentor card) {
        super(card);
    }

    @Override
    public ElusiveTormentor copy() {
        return new ElusiveTormentor(this);
    }
}
