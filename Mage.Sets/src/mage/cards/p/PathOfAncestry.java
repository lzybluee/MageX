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

import java.util.Iterator;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class PathOfAncestry extends CardImpl {

    public PathOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Path of Ancestry enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // T: Add one mana of any color in your commander's color identity.
        Ability ability = new CommanderColorIdentityManaAbility();
        this.addAbility(ability);

        // When that mana is spent to cast a creature spell that shares a creature type with your commander, scry 1.
        this.addAbility(new PathOfAncestryTriggeredAbility(new ScryEffect(1)));
    }

    public PathOfAncestry(final PathOfAncestry card) {
        super(card);
    }

    @Override
    public PathOfAncestry copy() {
        return new PathOfAncestry(this);
    }
}

class PathOfAncestryTriggeredAbility extends TriggeredAbilityImpl {

    public PathOfAncestryTriggeredAbility(Effect effect) {
        super(Zone.ALL, effect, true);
    }

    public PathOfAncestryTriggeredAbility(final PathOfAncestryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PathOfAncestryTriggeredAbility copy() {
        return new PathOfAncestryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
            if (sourcePermanent != null) {
                boolean found = false;
                for (Ability ability : sourcePermanent.getAbilities()) {
                    if (ability instanceof CommanderColorIdentityManaAbility && ability.getOriginalId().toString().equals(event.getData())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    Spell spell = game.getStack().getSpell(event.getTargetId());
                    if (spell != null && spell.isCreature()) {
                        Player controller = game.getPlayer(getControllerId());
                        if (controller != null && controller.getCommandersIds() != null && !controller.getCommandersIds().isEmpty()) {
                            if (spell.getAbilities().contains(ChangelingAbility.getInstance())) {
                                for (UUID cmdr : controller.getCommandersIds()) {
                                    MageObject commander = game.getObject(cmdr);
                                    if (commander != null) {
                                        if (commander.getAbilities().contains(ChangelingAbility.getInstance())) {
                                            return true;
                                        }
                                        Iterator<SubType> cmdrSubs = commander.getSubtype(game).iterator();
                                        while (cmdrSubs.hasNext()) {
                                            SubType sType = cmdrSubs.next();
                                            if (sType.getSubTypeSet() == SubTypeSet.CreatureType) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                            Iterator<SubType> spellSubs = spell.getSubtype(game).iterator();
                            while (spellSubs.hasNext()) {
                                SubType sType = spellSubs.next();
                                if (sType.getSubTypeSet() == SubTypeSet.CreatureType) {
                                    for (UUID cmdr : controller.getCommandersIds()) {
                                        MageObject commander = game.getObject(cmdr);
                                        if (commander != null && (commander.hasSubtype(sType, game) || commander.getAbilities().contains(ChangelingAbility.getInstance()))) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When that mana is used to cast a creature spell that shares a creature type with your commander, " + super.getRule();
    }
}
