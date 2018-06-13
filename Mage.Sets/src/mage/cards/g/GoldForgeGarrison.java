
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.GoldForgeGarrisonGolemToken;

/**
 *
 * @author LevelX2
 */
public final class GoldForgeGarrison extends CardImpl {

    public GoldForgeGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;

        // <i>(Transforms from Golden Guardian.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Transforms from Golden Guardian.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {T}: Add two mana of any one color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()));

        // {4}, {T}: Create a 4/4 colorless Golem artifact creature token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new GoldForgeGarrisonGolemToken(), 1), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public GoldForgeGarrison(final GoldForgeGarrison card) {
        super(card);
    }

    @Override
    public GoldForgeGarrison copy() {
        return new GoldForgeGarrison(this);
    }
}
