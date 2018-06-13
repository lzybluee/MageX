
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.MonasteryMentorToken;

/**
 *
 * @author fireshoes
 */
public final class MonasteryMentor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public MonasteryMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever you cast a noncreature spell, create a 1/1 white Monk creature token with prowess.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new MonasteryMentorToken()), filter, false));
    }

    public MonasteryMentor(final MonasteryMentor card) {
        super(card);
    }

    @Override
    public MonasteryMentor copy() {
        return new MonasteryMentor(this);
    }
}
