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
package mage.cards.a;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ArchfiendOfDepravity extends CardImpl {

    public ArchfiendOfDepravity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each opponent's end step, that player chooses up to two creatures he or she controls, then sacrifices the rest.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ArchfiendOfDepravityEffect(), TargetController.OPPONENT, false));
    }

    public ArchfiendOfDepravity(final ArchfiendOfDepravity card) {
        super(card);
    }

    @Override
    public ArchfiendOfDepravity copy() {
        return new ArchfiendOfDepravity(this);
    }
}

class ArchfiendOfDepravityEffect extends OneShotEffect {

    public ArchfiendOfDepravityEffect() {
        super(Outcome.Benefit); // AI should select two creatures if possible so it has to be a benefit
        this.staticText = "that player chooses up to two creatures he or she controls, then sacrifices the rest";
    }

    public ArchfiendOfDepravityEffect(final ArchfiendOfDepravityEffect effect) {
        super(effect);
    }

    @Override
    public ArchfiendOfDepravityEffect copy() {
        return new ArchfiendOfDepravityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            List<Permanent> creaturesToSacrifice = new ArrayList<>();
            TargetControlledPermanent target = new TargetControlledPermanent(0, 2, new FilterControlledCreaturePermanent("creatures to keep"), true);
            if (opponent.chooseTarget(outcome, target, source, game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), opponent.getId(), source.getSourceId(), game)) {
                    if (permanent != null && !target.getTargets().contains(permanent.getId())) {
                        creaturesToSacrifice.add(permanent);
                    }
                }
            }
            for (Permanent creature : creaturesToSacrifice) {
                creature.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
