/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class CerebralEruption extends CardImpl {

    public CerebralEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Target opponent reveals the top card of their library. Cerebral Eruption deals damage equal to the revealed card's converted mana cost to that player and each creature he or she controls. If a land card is revealed this way, return Cerebral Eruption to its owner's hand.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new CerebralEruptionEffect());
    }

    public CerebralEruption(final CerebralEruption card) {
        super(card);
    }

    @Override
    public CerebralEruption copy() {
        return new CerebralEruption(this);
    }
}

class CerebralEruptionEffect extends OneShotEffect {


    CerebralEruptionEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals the top card of their library. {this} deals damage equal to the revealed card's converted mana cost to that player and each creature he or she controls. If a land card is revealed this way, return {this} to its owner's hand";
    }

    CerebralEruptionEffect(final CerebralEruptionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null && player.getLibrary().hasCards()) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            player.revealCards(sourceObject.getIdName(), cards, game);
            game.getState().setValue(source.getSourceId().toString(), card);
            int damage = card.getConvertedManaCost();
            player.damage(damage, source.getSourceId(), game, false, true);
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, player.getId(), game)) {
                perm.damage(damage, source.getSourceId(), game, false, true);
            }
            if (card.isLand()) {
                Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                if (spellCard != null) {
                    player.moveCards(spellCard, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CerebralEruptionEffect copy() {
        return new CerebralEruptionEffect(this);
    }
}
