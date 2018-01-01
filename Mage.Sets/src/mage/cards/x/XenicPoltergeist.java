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
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author MarcoMarin
 */
public class XenicPoltergeist extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public XenicPoltergeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Until your next upkeep, target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new XenicPoltergeistEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filter));
        this.addAbility(ability);

    }

    public XenicPoltergeist(final XenicPoltergeist card) {
        super(card);
    }

    @Override
    public XenicPoltergeist copy() {
        return new XenicPoltergeist(this);
    }
}

class XenicPoltergeistEffect extends ContinuousEffectImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public XenicPoltergeistEffect() {
        super(Duration.Custom, Outcome.BecomeCreature);
        staticText = "Until your next upkeep, target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost";
    }

    public XenicPoltergeistEffect(final XenicPoltergeistEffect effect) {
        super(effect);
    }

    @Override
    public XenicPoltergeistEffect copy() {
        return new XenicPoltergeistEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    UUID permanentId = targetPointer.getFirst(game, source);
                    Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
                    if (permanent != null) {
                        if (!permanent.isArtifact()) {
                            permanent.addCardType(CardType.ARTIFACT);
                        }
                        if (!permanent.isCreature()) {
                            permanent.addCardType(CardType.CREATURE);
                        }
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    UUID permanentId = targetPointer.getFirst(game, source);
                    Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
                    if (permanent != null) {
                        int manaCost = permanent.getConvertedManaCost();
                        permanent.getPower().setValue(manaCost);
                        permanent.getToughness().setValue(manaCost);
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
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.TypeChangingEffects_4;
    }

}
