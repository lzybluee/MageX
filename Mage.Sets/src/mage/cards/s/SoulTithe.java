

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class SoulTithe extends CardImpl {

    static final String rule = "At the beginning of the upkeep of enchanted permanent's controller, that player sacrifices it unless he or she pays {X}, where X is its converted mana cost";

    public SoulTithe (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant nonland permanent
        TargetPermanent auraTarget = new TargetNonlandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted permanent's controller,
        // that player sacrifices it unless he or she pays {X},
        // where X is its converted mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SoulTitheEffect(), TargetController.CONTROLLER_ATTACHED_TO, false));
    }

    public SoulTithe (final SoulTithe card) {
        super(card);
    }

    @Override
    public SoulTithe copy() {
        return new SoulTithe(this);
    }
}

class SoulTitheEffect extends OneShotEffect {

    public SoulTitheEffect() {
        super(Outcome.Sacrifice);
        staticText = "that player sacrifices it unless he or she pays {X}, where X is its converted mana cost";
     }

    public SoulTitheEffect(final SoulTitheEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if(aura != null) {
            Permanent permanent = game.getPermanent(aura.getAttachedTo());
            if(permanent != null) {
                Player player = game.getPlayer(permanent.getControllerId());
                if(player != null) {
                    int cmc = permanent.getConvertedManaCost();
                    if (player.chooseUse(Outcome.Benefit, "Pay {" + cmc + "} for " + permanent.getName() + "? (otherwise you sacrifice it)", source, game)) {
                        Cost cost = new GenericManaCost(cmc);
                        if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                            return true;
                        }
                    }
                    permanent.sacrifice(source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SoulTitheEffect copy() {
        return new SoulTitheEffect(this);
    }
 }
