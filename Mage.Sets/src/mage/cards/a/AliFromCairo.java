
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author KholdFuzion
 */
public final class AliFromCairo extends CardImpl {

    public AliFromCairo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AliFromCairoReplacementEffect()));
    }

    public AliFromCairo(final AliFromCairo card) {
        super(card);
    }

    @Override
    public AliFromCairo copy() {
        return new AliFromCairo(this);
    }
}

class AliFromCairoReplacementEffect extends ReplacementEffectImpl {

    public AliFromCairoReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public AliFromCairoReplacementEffect(final AliFromCairoReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AliFromCairoReplacementEffect copy() {
        return new AliFromCairoReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() > 0) &&(controller.getLife() - event.getAmount()) < 1
                    && event.getPlayerId().equals(controller.getId())
                    ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // 10/1/2008: The ability doesn't change how much damage is dealt;
            // it just changes how much life that damage makes you lose.
            // An effect such as Spirit Link will see the full amount of damage being dealt.
            event.setAmount(controller.getLife() - 1);
        }
        return false;
    }
}
