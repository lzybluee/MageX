
package mage.abilities.effects.common;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnToHandTargetEffect extends OneShotEffect {

    protected boolean multitargetHandling;

    public ReturnToHandTargetEffect() {
        this(false);
    }

    public ReturnToHandTargetEffect(boolean multitargetHandling) {
        super(Outcome.ReturnToHand);
        this.multitargetHandling = multitargetHandling;
    }

    public ReturnToHandTargetEffect(final ReturnToHandTargetEffect effect) {
        super(effect);
        this.multitargetHandling = effect.multitargetHandling;
    }

    @Override
    public ReturnToHandTargetEffect copy() {
        return new ReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<UUID> copyIds = new ArrayList<>();
        Set<Card> cards = new LinkedHashSet<>();
        if (multitargetHandling) {
            for (Target target : source.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    MageObject mageObject = game.getObject(targetId);
                    if (mageObject instanceof Spell && mageObject.isCopy()) {
                        copyIds.add(targetId);
                    } else if (mageObject instanceof Card) {
                        cards.add((Card) mageObject);
                    }
                }
            }
        } else {
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                MageObject mageObject = game.getObject(targetId);
                if (mageObject != null) {
                    if (mageObject instanceof Spell &&  mageObject.isCopy()) {
                        copyIds.add(targetId);
                    } else {
                        cards.add((Card) mageObject);
                    }
                }
            }
        }
        for (UUID copyId : copyIds) {
            game.getStack().remove(game.getSpell(copyId), game);
        }
        return controller.moveCards(cards, Zone.HAND, source, game);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().size() < 1) {
            return "";
        }
        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder("return ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName()).append(" to their owners' hand");
            return sb.toString();
        } else {
            if (target.getNumberOfTargets() > 1) {
                sb.append(CardUtil.numberToText(target.getNumberOfTargets())).append(' ');
            }
            if (!target.getTargetName().startsWith("another")) {
                sb.append("target ");
            }
            sb.append(target.getTargetName()).append(" to its owner's hand");
            return sb.toString();
        }
    }

}
