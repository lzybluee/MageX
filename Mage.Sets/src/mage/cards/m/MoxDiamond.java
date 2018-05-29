/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public final class MoxDiamond extends CardImpl {

    public MoxDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MoxDiamondReplacementEffect()));
        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    public MoxDiamond(final MoxDiamond card) {
        super(card);
    }

    @Override
    public MoxDiamond copy() {
        return new MoxDiamond(this);
    }
}

class MoxDiamondReplacementEffect extends ReplacementEffectImpl {

    public MoxDiamondReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would enter the battlefield, you may discard a land card instead. If you do, put {this} onto the battlefield. If you don't, put it into its owner's graveyard";
    }

    public MoxDiamondReplacementEffect(final MoxDiamondReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MoxDiamondReplacementEffect copy() {
        return new MoxDiamondReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInHand target = new TargetCardInHand(new FilterLandCard());
            Cost cost = new DiscardTargetCost(target);
            if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                    && player.chooseUse(outcome, "Discard land? (Otherwise Mox Diamond goes to graveyard)", source, game)
                    && player.chooseTarget(Outcome.Discard, target, source, game)) {
                player.discard(game.getCard(target.getFirstTarget()), source, game);
                return false;
            } else {
                Permanent permanent = game.getPermanentEntering(event.getTargetId());
                if (permanent != null) {
                    player.moveCards(permanent, Zone.GRAVEYARD, source, game);
                }
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }

}
