
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author cbt33, Plopman (Circle of Protection: Red)
 */
public final class PilgrimOfJustice extends CardImpl {
    
    public PilgrimOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // {W}, Sacrifice Pilgrim of Justice: The next time a red source of your choice would deal damage this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PilgrimOfJusticeEffect(), new ManaCostsImpl("{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public PilgrimOfJustice(final PilgrimOfJustice card) {
        super(card);
    }

    @Override
    public PilgrimOfJustice copy() {
        return new PilgrimOfJustice(this);
    }
}

class PilgrimOfJusticeEffect extends PreventionEffectImpl {

    private static final FilterObject filter = new FilterObject("red source");
    static{
        filter.add(new ColorPredicate(ObjectColor.RED));
    }
    private TargetSource target;

    public PilgrimOfJusticeEffect() {
        super(Duration.EndOfTurn);
        target = new TargetSource(filter);
        
        staticText = "The next time a red source of your choice would deal damage to you this turn, prevent that damage";
    }

    public PilgrimOfJusticeEffect(final PilgrimOfJusticeEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public PilgrimOfJusticeEffect copy() {
        return new PilgrimOfJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
            preventDamage(event, source, target.getFirstTarget(), game);
            return true;
        }
        return false;
    }

    private void preventDamage(GameEvent event, Ability source, UUID target, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, target, source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            this.used = true;
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getSourceId(), source.getControllerId(), damage));
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(target.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

}
