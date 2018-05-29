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

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class Tromokratis extends CardImpl {

    public Tromokratis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Tromokratis has hexproof unless it's attacking or blocking.
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                new InvertCondition(new SourceMatchesFilterCondition(new FilterAttackingOrBlockingCreature())),
                "{this} has hexproof unless it's attacking or blocking");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Tromokratis can't be blocked unless all creatures defending player controls block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedUnlessAllEffect()));
    }

    public Tromokratis(final Tromokratis card) {
        super(card);
    }

    @Override
    public Tromokratis copy() {
        return new Tromokratis(this);
    }
}

class CantBeBlockedUnlessAllEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public CantBeBlockedUnlessAllEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't be blocked unless all creatures defending player controls block it";
    }

    public CantBeBlockedUnlessAllEffect(final CantBeBlockedUnlessAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        // check if all creatures of defender are able to block this permanent
        // permanent.canBlock() can't be used because causing recursive call
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, blocker.getControllerId(), game)) {
            if (permanent.isTapped() && null == game.getState().getContinuousEffects().asThough(this.getId(), AsThoughEffectType.BLOCK_TAPPED, source, blocker.getControllerId(), game)) {
                return false;
            }
            // check blocker restrictions
            for (Map.Entry<RestrictionEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRestrictionEffects(permanent, game).entrySet()) {
                for (Ability ability : entry.getValue()) {
                    if (!entry.getKey().canBlock(attacker, permanent, ability, game)) {
                        return false;
                    }
                }
            }
            // check also attacker's restriction effects
            for (Map.Entry<RestrictionEffect, Set<Ability>> restrictionEntry : game.getContinuousEffects().getApplicableRestrictionEffects(attacker, game).entrySet()) {
                for (Ability ability : restrictionEntry.getValue()) {
                    if (!(restrictionEntry.getKey() instanceof CantBeBlockedUnlessAllEffect)
                            && !restrictionEntry.getKey().canBeBlocked(attacker, permanent, ability, game)) {
                        return false;
                    }
                }
            }
            if (attacker.hasProtectionFrom(permanent, game)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canBeBlockedCheckAfter(Permanent attacker, Ability source, Game game) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(source.getSourceId())) {
                for (UUID blockerId : combatGroup.getBlockers()) {
                    Permanent blockingCreature = game.getPermanent(blockerId);
                    if (blockingCreature != null) {
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, blockingCreature.getControllerId(), game)) {
                            if (!combatGroup.getBlockers().contains(permanent.getId())) {
                                // not all creatures block Tromokratis
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public CantBeBlockedUnlessAllEffect copy() {
        return new CantBeBlockedUnlessAllEffect(this);
    }
}
