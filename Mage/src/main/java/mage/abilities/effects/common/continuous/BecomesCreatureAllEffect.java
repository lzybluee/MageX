/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LevelX2
 *
 */
public class BecomesCreatureAllEffect extends ContinuousEffectImpl {

    protected Token token;
    protected String theyAreStillType;
    private final FilterPermanent filter;
    private boolean loseColor = true;

    public BecomesCreatureAllEffect(Token token, String theyAreStillType, FilterPermanent filter, Duration duration, boolean loseColor) {
        super(duration, Outcome.BecomeCreature);
        this.token = token;
        this.theyAreStillType = theyAreStillType;
        this.filter = filter;
        this.loseColor = loseColor;
    }

    public BecomesCreatureAllEffect(final BecomesCreatureAllEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.theyAreStillType = effect.theyAreStillType;
        this.filter = effect.filter.copy();
        this.loseColor = effect.loseColor;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                affectedObjectList.add(new MageObjectReference(perm, game));
            }
        }
    }

    @Override
    public BecomesCreatureAllEffect copy() {
        return new BecomesCreatureAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Set<Permanent> affectedPermanents = new HashSet<>();
        if (this.affectedObjectsSet) {
            for(MageObjectReference ref : affectedObjectList) {
                affectedPermanents.add(ref.getPermanent(game));
            }
        } else {
            affectedPermanents = new HashSet<>(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game));
        }

        for(Permanent permanent : affectedPermanents) {
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            if (!token.getCardType().isEmpty()) {
                                for (CardType t : token.getCardType()) {
                                    if (!permanent.getCardType().contains(t)) {
                                        permanent.addCardType(t);
                                    }
                                }
                            }
                            if (theyAreStillType == null) {
                                permanent.getSubtype(game).clear();
                            }
                            if (!token.getSubtype(game).isEmpty()) {
                                permanent.getSubtype(game).addAll(token.getSubtype(game));
                            }
                        }
                        break;

                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            if (this.loseColor) {
                                permanent.getColor(game).setBlack(false);
                                permanent.getColor(game).setGreen(false);
                                permanent.getColor(game).setBlue(false);
                                permanent.getColor(game).setWhite(false);
                                permanent.getColor(game).setRed(false);
                            }
                            if (token.getColor(game).hasColor()) {
                                permanent.getColor(game).addColor(token.getColor(game));
                            }
                        }
                        break;

                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            if (!token.getAbilities().isEmpty()) {
                                for (Ability ability : token.getAbilities()) {
                                    permanent.addAbility(ability, source.getSourceId(), game);
                                }
                            }
                        }
                        break;

                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            int power = token.getPower().getValue();
                            int toughness = token.getToughness().getValue();
                            if (power != 0 && toughness != 0) {
                                permanent.getPower().setValue(power);
                                permanent.getToughness().setValue(toughness);
                            }
                        }
                        break;
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
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (duration.toString() != null && !duration.toString().isEmpty()) {
            sb.append(duration.toString()).append(", ");
        }
        sb.append(filter.getMessage());
        if (duration.toString() != null && duration.toString().isEmpty()) {
            sb.append(" are ");
        } else {
            sb.append(" become ");
        }
        sb.append(token.getDescription());
        if (theyAreStillType != null && !theyAreStillType.isEmpty()) {
            sb.append(". They're still ").append(theyAreStillType);
        }
        return sb.toString();
    }

}
