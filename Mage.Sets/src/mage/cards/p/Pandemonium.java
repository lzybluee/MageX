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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Pandemonium extends CardImpl {

    private final UUID originalId;

    public Pandemonium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever a creature enters the battlefield, that creature's controller may have it deal damage equal to its power to any target of their choice.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new PandemoniumEffect(), StaticFilters.FILTER_PERMANENT_CREATURE, false, SetTargetPointer.PERMANENT, "");
        ability.addTarget(new TargetAnyTarget());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public Pandemonium(final Pandemonium card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            UUID creatureId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null) {
                ability.getTargets().get(0).setTargetController(creature.getControllerId());
            }
        }
    }

    @Override
    public Pandemonium copy() {
        return new Pandemonium(this);
    }
}

class PandemoniumEffect extends OneShotEffect {

    public PandemoniumEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller may have it deal damage equal to its power to any target of their choice";
    }

    public PandemoniumEffect(final PandemoniumEffect effect) {
        super(effect);
    }

    @Override
    public PandemoniumEffect copy() {
        return new PandemoniumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent enteringCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            if (enteringCreature != null) {
                Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetPermanent != null) {
                    targetPermanent.damage(enteringCreature.getPower().getValue(), enteringCreature.getId(), game, false, true);
                } else {
                    Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
                    if (targetPlayer != null) {
                        targetPlayer.damage(enteringCreature.getPower().getValue(), enteringCreature.getId(), game, false, true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
