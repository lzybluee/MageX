
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfThirst extends CardImpl {

    public CurseOfThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // At the beginning of enchanted player's upkeep, Curse of Thirst deals damage to that player equal to the number of Curses attached to him or her.
        this.addAbility(new CurseOfThirstAbility());

    }

    public CurseOfThirst(final CurseOfThirst card) {
        super(card);
    }

    @Override
    public CurseOfThirst copy() {
        return new CurseOfThirst(this);
    }
}

class CurseOfThirstAbility extends TriggeredAbilityImpl {

    public CurseOfThirstAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(new CursesAttachedCount()));
    }

    public CurseOfThirstAbility(final CurseOfThirstAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfThirstAbility copy() {
        return new CurseOfThirstAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null && game.getActivePlayerId().equals(player.getId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, Curse of Thirst deals damage to that player equal to the number of Curses attached to him or her.";
    }

}

class CursesAttachedCount implements DynamicValue {

    public CursesAttachedCount() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent enchantment = game.getPermanent(sourceAbility.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null) {
                for (UUID attachmentId: player.getAttachments()) {
                    Permanent attachment = game.getPermanent(attachmentId);
                    if (attachment != null && attachment.hasSubtype(SubType.CURSE, game))
                        count++;
                }
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return new CursesAttachedCount();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "number of Curses attached to him or her";
    }
}
