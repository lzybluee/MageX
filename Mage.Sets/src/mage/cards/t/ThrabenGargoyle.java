
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.s.StonewingAntagonizer;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ThrabenGargoyle extends CardImpl {

    public ThrabenGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = StonewingAntagonizer.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {6}: Transform Thraben Gargoyle.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new GenericManaCost(6)));
    }

    public ThrabenGargoyle(final ThrabenGargoyle card) {
        super(card);
    }

    @Override
    public ThrabenGargoyle copy() {
        return new ThrabenGargoyle(this);
    }
}
