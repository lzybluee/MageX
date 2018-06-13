/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common;

import java.util.Objects;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 * 702.49. Epic 702.49a Epic represents two spell abilities, one of which
 * creates a delayed triggered ability. “Epic” means “For the rest of the game,
 * you can't cast spells,” and “At the beginning of each of your upkeeps for the
 * rest of the game, copy this spell except for its epic ability. If the spell
 * has any targets, you may choose new targets for the copy.” See rule 706.10.
 * 702.49b A player can't cast spells once a spell with epic he or she controls
 * resolves, but effects (such as the epic ability itself) can still put copies
 * of spells onto the stack. *
 */
public class EpicEffect extends OneShotEffect {

    static final String rule = "<br>Epic <i>(For the rest of the game, you can't cast spells. At the beginning of each of your upkeeps for the rest of the game, copy this spell except for its epic ability. If the spell has targets, you may choose new targets for the copy)";

    public EpicEffect() {
        super(Outcome.Benefit);
        staticText = rule;
    }

    public EpicEffect(final EpicEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            StackObject stackObject = game.getStack().getStackObject(source.getId());
            Spell spell = (Spell) stackObject;
            if (spell == null) {
                return false;
            }
            spell = spell.copySpell(source.getControllerId());
            // Remove Epic effect from the spell
            Effect epicEffect = null;
            for (Effect effect : spell.getSpellAbility().getEffects()) {
                if (effect instanceof EpicEffect) {
                    epicEffect = effect;
                    break;
                }
            }
            spell.getSpellAbility().getEffects().remove(epicEffect);
            DelayedTriggeredAbility ability = new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(new EpicPushEffect(spell, rule), Duration.EndOfGame, false);
            game.addDelayedTriggeredAbility(ability, source);
            game.addEffect(new EpicReplacementEffect(), source);
            return true;
        }
        return false;
    }

    @Override
    public EpicEffect copy() {
        return new EpicEffect(this);
    }
}

class EpicReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public EpicReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Neutral);
        staticText = "For the rest of the game, you can't cast spells";
    }

    public EpicReplacementEffect(final EpicReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EpicReplacementEffect copy() {
        return new EpicReplacementEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "For the rest of the game, you can't cast spells (Epic - " + mageObject.getName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (Objects.equals(source.getControllerId(), event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null) {
                return true;
            }
        }
        return false;
    }
}

class EpicPushEffect extends OneShotEffect {

    final private Spell spell;

    public EpicPushEffect(Spell spell, String ruleText) {
        super(Outcome.Copy);
        this.spell = spell;
        staticText = ruleText;
    }

    public EpicPushEffect(final EpicPushEffect effect) {
        super(effect);
        this.spell = effect.spell;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
            return true;
        }

        return false;
    }

    @Override
    public EpicPushEffect copy() {
        return new EpicPushEffect(this);
    }
}
