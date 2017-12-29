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
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public class CurseOfEchoes extends CardImpl {

    public CurseOfEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted player casts an instant or sorcery spell, each other player may copy that spell and may choose new targets for the copy he or she controls.
        this.addAbility(new CurseOfEchoesCopyTriggeredAbility());
    }

    public CurseOfEchoes(final CurseOfEchoes card) {
        super(card);
    }

    @Override
    public CurseOfEchoes copy() {
        return new CurseOfEchoes(this);
    }
}

class CurseOfEchoesCopyTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public CurseOfEchoesCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfEchoesEffect(), false);
    }

    public CurseOfEchoesCopyTriggeredAbility(final CurseOfEchoesCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfEchoesCopyTriggeredAbility copy() {
        return new CurseOfEchoesCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && (spell.isInstant() || spell.isSorcery())) {
            Permanent enchantment = game.getPermanent(sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Player player = game.getPlayer(enchantment.getAttachedTo());
                if (player != null && spell.getControllerId().equals(player.getId())) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted player casts an instant or sorcery spell, each other player may copy that spell and may choose new targets for the copy he or she controls.";
    }
}

class CurseOfEchoesEffect extends OneShotEffect {

    public CurseOfEchoesEffect() {
        super(Outcome.Copy);
    }

    public CurseOfEchoesEffect(final CurseOfEchoesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            String chooseMessage = "Copy target spell?  You may choose new targets for the copy.";
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                if (!playerId.equals(spell.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    if (player.chooseUse(Outcome.Copy, chooseMessage, source, game)) {
                        spell.createCopyOnStack(game, source, player.getId(), true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CurseOfEchoesEffect copy() {
        return new CurseOfEchoesEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (!mode.getTargets().isEmpty()) {
            return "Copy target " + mode.getTargets().get(0).getTargetName() + ". You may choose new targets for the copy";
        }
        return "No target";
    }
}
