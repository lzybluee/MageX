
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class CrusherZendikon extends CardImpl {

    public CrusherZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted land is a 4/2 red Beast creature with trample. It's still a land.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new BeastToken(), "Enchanted land is a 4/2 red Beast creature with trample. It's still a land.", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);
        // When enchanted land dies, return that card to its owner's hand.
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false);
        this.addAbility(ability3);
    }

    public CrusherZendikon(final CrusherZendikon card) {
        super(card);
    }

    @Override
    public CrusherZendikon copy() {
        return new CrusherZendikon(this);
    }
}

class BeastToken extends TokenImpl {

    BeastToken() {
        super("", "4/2 red Beast creature with trample");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(2);
        this.addAbility(TrampleAbility.getInstance());
    }
    public BeastToken(final BeastToken token) {
        super(token);
    }

    public BeastToken copy() {
        return new BeastToken(this);
    }
}
