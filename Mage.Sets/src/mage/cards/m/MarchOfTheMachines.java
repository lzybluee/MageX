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
package mage.cards.m;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class MarchOfTheMachines extends CardImpl {

    public MarchOfTheMachines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Each noncreature artifact is an artifact creature with power and toughness each equal to its converted mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MarchOfTheMachinesEffect()));
    }

    public MarchOfTheMachines(final MarchOfTheMachines card) {
        super(card);
    }

    @Override
    public MarchOfTheMachines copy() {
        return new MarchOfTheMachines(this);
    }
}

class MarchOfTheMachinesEffect extends ContinuousEffectImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public MarchOfTheMachinesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "Each noncreature artifact is an artifact creature with power and toughness each equal to its converted mana cost";
        dependendToTypes.add(DependencyType.ArtifactAddingRemoving);
    }

    public MarchOfTheMachinesEffect(final MarchOfTheMachinesEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfTheMachinesEffect copy() {
        return new MarchOfTheMachinesEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    affectedObjectList.clear();
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                        if (permanent != null) {
                            affectedObjectList.add(new MageObjectReference(permanent, game));
                            permanent.addCardType(CardType.CREATURE);
                        }
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                        Permanent permanent = it.next().getPermanent(game);
                        if (permanent != null) {
                            int manaCost = permanent.getConvertedManaCost();
                            permanent.getPower().setValue(manaCost);
                            permanent.getToughness().setValue(manaCost);
                        }
                    }
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }

}
