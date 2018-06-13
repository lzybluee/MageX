
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class PhyrexianScriptures extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creatures");
    private static final FilterCard filter2 = new FilterCard("opponents' cards");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter2.add(new OwnerPredicate(TargetController.OPPONENT));
    }

    public PhyrexianScriptures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I — Put a +1/+1 counter on up to one target creature. That creature becomes an artifact in addition to its other types.
        Effects effects = new Effects();
        effects.add(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        effects.add(new AddCardTypeTargetEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT)
                .setText("That creature becomes an artifact in addition to its other types")
        );
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I, effects, new TargetCreaturePermanent(0, 1));

        // II — Destroy all nonartifact creatures.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DestroyAllEffect(filter));

        // III — Exile all cards from all opponents' graveyards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
                new ExileGraveyardAllPlayersEffect(StaticFilters.FILTER_CARD_CARDS, TargetController.OPPONENT)
        );
        this.addAbility(sagaAbility);
    }

    public PhyrexianScriptures(final PhyrexianScriptures card) {
        super(card);
    }

    @Override
    public PhyrexianScriptures copy() {
        return new PhyrexianScriptures(this);
    }
}
