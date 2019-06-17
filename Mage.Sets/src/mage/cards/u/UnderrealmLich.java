package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class UnderrealmLich extends CardImpl {

    public UnderrealmLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // If you would draw a card, instead look at the top three cards of your library, then put one into your hand and the rest into your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new UnderrealmLichReplacementEffect()
        ));

        // Pay 4 life: Underrealm Lich gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(),
                        Duration.EndOfTurn
                ), new PayLifeCost(4)
        );
        ability.addEffect(new TapSourceEffect().setText("Tap it"));
        this.addAbility(ability);
    }

    public UnderrealmLich(final UnderrealmLich card) {
        super(card);
    }

    @Override
    public UnderrealmLich copy() {
        return new UnderrealmLich(this);
    }
}

class UnderrealmLichReplacementEffect extends ReplacementEffectImpl {

    public UnderrealmLichReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card, instead look at the top "
                + "three cards of your library, then put one into your hand "
                + "and the rest into your graveyard.";
    }

    public UnderrealmLichReplacementEffect(final UnderrealmLichReplacementEffect effect) {
        super(effect);
    }

    @Override
    public UnderrealmLichReplacementEffect copy() {
        return new UnderrealmLichReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return new LookLibraryAndPickControllerEffect(
                new StaticValue(3), false, new StaticValue(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD,
                false, false, false, Zone.HAND, false
        ).apply(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
