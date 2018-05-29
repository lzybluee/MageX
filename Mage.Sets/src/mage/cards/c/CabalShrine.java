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
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class CabalShrine extends CardImpl {

    public CabalShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        

        // Whenever a player casts a spell, that player discards X cards, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new CabalShrineTriggeredAbility());
    }

    public CabalShrine(final CabalShrine card) {
        super(card);
    }

    @Override
    public CabalShrine copy() {
        return new CabalShrine(this);
    }
}

class CabalShrineTriggeredAbility extends TriggeredAbilityImpl {

    public CabalShrineTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CabalShrineEffect(), false);
    }

    public CabalShrineTriggeredAbility(final CabalShrineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CabalShrineTriggeredAbility copy() {
        return new CabalShrineTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        MageObject mageObject = game.getObject(sourceId);
        if (spell != null) {
            game.getState().setValue("cabalShrine" + mageObject, spell);
            return true;
        }
        return false;
    }

}

class CabalShrineEffect extends OneShotEffect {

    public CabalShrineEffect() {
        super(Outcome.Discard);
        staticText = "Whenever a player casts a spell, that player discards X cards, where X is the number of cards in all graveyards with the same name as that spell";
    }

    public CabalShrineEffect(final CabalShrineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        MageObject mageObject = game.getObject(source.getSourceId());
        Spell spell = (Spell) game.getState().getValue("cabalShrine" + mageObject);
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null) {
                String name = spell.getName();
                FilterCard filterCardName = new FilterCard();
                filterCardName.add(new NamePredicate(name));
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        count += player.getGraveyard().count(filterCardName, game);
                    }
                }
                controller.discard(count, false, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public CabalShrineEffect copy() {
        return new CabalShrineEffect(this);
    }
}
