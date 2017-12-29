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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public class ExclusionRitual extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ExclusionRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W}{W}");

        // Imprint - When Exclusion Ritual enters the battlefield, exile target nonland permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExclusionRitualImprintEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // Players can't cast spells with the same name as the exiled card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExclusionRitualReplacementEffect()));
    }

    public ExclusionRitual(final ExclusionRitual card) {
        super(card);
    }

    @Override
    public ExclusionRitual copy() {
        return new ExclusionRitual(this);
    }
}

class ExclusionRitualImprintEffect extends OneShotEffect {

    ExclusionRitualImprintEffect() {
        super(Outcome.Exile);
        staticText = "exile target nonland permanent";
    }

    ExclusionRitualImprintEffect(final ExclusionRitualImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && sourcePermanent != null && targetPermanent != null) {
            controller.moveCardToExileWithInfo(targetPermanent, getId(), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
            sourcePermanent.imprint(targetPermanent.getId(), game);
        }
        return true;
    }

    @Override
    public ExclusionRitualImprintEffect copy() {
        return new ExclusionRitualImprintEffect(this);
    }
}

class ExclusionRitualReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    ExclusionRitualReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cast spells with the same name as the exiled card";
    }

    ExclusionRitualReplacementEffect(final ExclusionRitualReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(event.getSourceId());
        if (sourcePermanent != null && card != null) {
            if (!sourcePermanent.getImprinted().isEmpty()) {
                Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
                if (imprintedCard != null) {
                    return card.getName().equals(imprintedCard.getName());
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ExclusionRitualReplacementEffect copy() {
        return new ExclusionRitualReplacementEffect(this);
    }
}
