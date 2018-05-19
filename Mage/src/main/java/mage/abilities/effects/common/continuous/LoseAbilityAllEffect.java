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
package mage.abilities.effects.common.continuous;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseAbilityAllEffect extends ContinuousEffectImpl {

    protected CompoundAbility ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;

    public LoseAbilityAllEffect(Ability ability, Duration duration) {
        this(ability, duration, new FilterPermanent("permanents"));
    }

    public LoseAbilityAllEffect(CompoundAbility ability, Duration duration) {
        this(ability, duration, new FilterPermanent("permanents"));
    }

    public LoseAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public LoseAbilityAllEffect(CompoundAbility ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public LoseAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(new CompoundAbility(ability), duration, filter, excludeSource);
    }

    public LoseAbilityAllEffect(CompoundAbility ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
        this.filter = filter;
        this.excludeSource = excludeSource;
    }

    public LoseAbilityAllEffect(final LoseAbilityAllEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
    }

    @Override
    public LoseAbilityAllEffect copy() {
        return new LoseAbilityAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent perm = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (perm != null) {
                    for (Ability ability : ability) {
                        perm.getAbilities().removeIf(entry -> entry.getId().equals(ability.getId()));
                    }
                } else {
                    it.remove();
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    System.out.println(game.getTurn() + ", " + game.getPhase() + ": " + "remove from size " + perm.getAbilities().size());
                    for (Ability ability : ability) {
                        perm.getAbilities().removeIf(entry -> entry.getId().equals(ability.getId()));
                    }
                }
            }
            // still as long as the prev. permanent is known to the LKI (e.g. Mikaeus, the Unhallowed) so gained dies triggered ability will trigger
            HashMap<UUID, MageObject> LKIBattlefield = game.getLKI().get(Zone.BATTLEFIELD);
            if (LKIBattlefield != null) {
                for (MageObject mageObject : LKIBattlefield.values()) {
                    Permanent perm = (Permanent) mageObject;
                    if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                        if (filter.match(perm, source.getSourceId(), source.getControllerId(), game)) {
                            for (Ability ability : ability) {
                                perm.getAbilities().removeIf(entry -> entry.getId().equals(ability.getId()));
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}
