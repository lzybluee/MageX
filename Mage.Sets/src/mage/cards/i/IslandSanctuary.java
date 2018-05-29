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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class IslandSanctuary extends CardImpl {

    public IslandSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // If you would draw a card during your draw step, instead you may skip that draw. If you do, until your next turn, you can't be attacked except by creatures with flying and/or islandwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IslandSanctuaryEffect()));
    }

    public IslandSanctuary(final IslandSanctuary card) {
        super(card);
    }

    @Override
    public IslandSanctuary copy() {
        return new IslandSanctuary(this);
    }
}

class IslandSanctuaryEffect extends ReplacementEffectImpl {

    private final static FilterCreaturePermanent notFlyingorIslandwalkCreatures = new FilterCreaturePermanent("except by creatures with flying and/or islandwalk");

    static {
        notFlyingorIslandwalkCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        notFlyingorIslandwalkCreatures.add(Predicates.not(new AbilityPredicate(IslandwalkAbility.class)));
    }

    IslandSanctuaryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card during your draw step, instead you may skip that draw. If you do, until your next turn, you can't be attacked except by creatures with flying and/or islandwalk";
    }

    IslandSanctuaryEffect(final IslandSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(event.getPlayerId());
        if (controller != null && controller.chooseUse(outcome, "Skip draw card? (If you do, until your next turn, you can't be attacked except by creatures with flying and/or islandwalk)", source, game)) {
            game.informPlayers(controller.getLogName() + " skips their draw card action. Until their next turn, he or she can't be attacked except by creatures with flying and/or islandwalk");
            game.addEffect(new CantAttackYouAllEffect(Duration.UntilYourNextTurn, notFlyingorIslandwalkCreatures), source);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getControllerId().equals(event.getPlayerId()) && game.getTurn().getStepType() == PhaseStep.DRAW;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public IslandSanctuaryEffect copy() {
        return new IslandSanctuaryEffect(this);
    }
}
