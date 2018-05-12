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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class TrepanationBlade extends CardImpl {

    public TrepanationBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, defending player reveals cards from the top of their library until he or she reveals a land card.
        // The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into their graveyard.
        this.addAbility(new AttacksAttachedTriggeredAbility(new TrepanationBladeDiscardEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public TrepanationBlade(final TrepanationBlade card) {
        super(card);
    }

    @Override
    public TrepanationBlade copy() {
        return new TrepanationBlade(this);
    }
}

class TrepanationBladeDiscardEffect extends OneShotEffect {

    public TrepanationBladeDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player reveals cards from the top of their library until he or she reveals a land card. The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into their graveyard";
    }

    public TrepanationBladeDiscardEffect(final TrepanationBladeDiscardEffect effect) {
        super(effect);
    }

    @Override
    public TrepanationBladeDiscardEffect copy() {
        return new TrepanationBladeDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature == null) {
                return false;
            }
            UUID defenderId = game.getCombat().getDefenderId(creature.getId());
            Player defendingPlayer = game.getPlayer(defenderId);
            if (defendingPlayer == null) {
                return false;
            }
            CardsImpl cards = new CardsImpl();
            for (Card card : defendingPlayer.getLibrary().getCards(game)) {
                cards.add(card);
                if (card.isLand()) {
                    break;
                }
            }
            defendingPlayer.revealCards(equipment.getName(), cards, game);
            defendingPlayer.moveCards(cards, Zone.GRAVEYARD, source, game);
            if (!cards.isEmpty()) {
                ContinuousEffect effect = new BoostTargetEffect(cards.size(), 0, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
