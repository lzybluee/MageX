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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class RimehornAurochs extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.AUROCHS, "other attacking Aurochs");

    static {
        filter.add(new AttackingPredicate());
        filter.add(new AnotherPredicate());
    }

    public RimehornAurochs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.AUROCHS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Rimehorn Aurochs attacks, it gets +1/+0 until end of turn for each other attacking Aurochs.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new StaticValue(0), Duration.EndOfTurn, true), false));
        
        // {2}{S}: Target creature blocks target creature this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RimehornAurochsEffect(), new ManaCostsImpl("{2}{S}"));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature that must block")));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature that is to be blocked")));
        this.addAbility(ability, new BlockedAttackerWatcher());
    }

    public RimehornAurochs(final RimehornAurochs card) {
        super(card);
    }

    @Override
    public RimehornAurochs copy() {
        return new RimehornAurochs(this);
    }
}

class RimehornAurochsEffect extends RequirementEffect {

    public RimehornAurochsEffect() {
        this(Duration.EndOfTurn);
    }

    public RimehornAurochsEffect(Duration duration) {
        super(duration);
        staticText = "Target creature blocks target creature this turn if able";
    }

    public RimehornAurochsEffect(final RimehornAurochsEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getTargets().get(0).getFirstTarget())) {
            Permanent blocker = game.getPermanent(source.getTargets().get(0).getFirstTarget());
            if (blocker != null 
                    && blocker.canBlock(source.getTargets().get(1).getFirstTarget(), game)) {              
                Permanent attacker = (Permanent) game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (attacker != null) {
                    BlockedAttackerWatcher blockedAttackerWatcher = (BlockedAttackerWatcher) game.getState().getWatchers().get(BlockedAttackerWatcher.class.getSimpleName());
                    if (blockedAttackerWatcher != null 
                            && blockedAttackerWatcher.creatureHasBlockedAttacker(attacker, blocker, game)) {
                        // has already blocked this turn, so no need to do again
                        return false;
                    }                
                    return true;
                } else {
                    discard();
                }
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {  
        return source.getTargets().get(1).getFirstTarget();
    }

    @Override
    public RimehornAurochsEffect copy() {
        return new RimehornAurochsEffect(this);
    }

}
