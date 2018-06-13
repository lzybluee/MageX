
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.GoldToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KingMacarTheGoldCursed extends CardImpl {

    public KingMacarTheGoldCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Inspired - Whenever King Macar, the Gold-Cursed becomes untapped, you may exile target creature. If you do, create a colorless artifact token named Gold. It has "Sacrifice this artifact: Add one mana of any color."
        Ability ability = new InspiredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        Effect effect = new CreateTokenEffect(new GoldToken());
        effect.setText("If you do, create a colorless artifact token named Gold. It has \"Sacrifice this artifact: Add one mana of any color.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public KingMacarTheGoldCursed(final KingMacarTheGoldCursed card) {
        super(card);
    }

    @Override
    public KingMacarTheGoldCursed copy() {
        return new KingMacarTheGoldCursed(this);
    }
}

//
