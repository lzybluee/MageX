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

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class PrismaticOmen extends CardImpl {

    public PrismaticOmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // Lands you control are every basic land type in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesBasicLandTypeAllEffect("Swamp", "Mountain", "Forest", "Island", "Plains")));
    }

    public PrismaticOmen(final PrismaticOmen card) {
        super(card);
    }

    @Override
    public PrismaticOmen copy() {
        return new PrismaticOmen(this);
    }
}

class BecomesBasicLandTypeAllEffect extends ContinuousEffectImpl {

    protected List<SubType> landTypes = new ArrayList<>();

    public BecomesBasicLandTypeAllEffect(String... landNames) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        landTypes.addAll(SubType.getBasicLands(false));
        this.staticText = "Lands you control are every basic land type in addition to their other types";
    }

    public BecomesBasicLandTypeAllEffect(final BecomesBasicLandTypeAllEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesBasicLandTypeAllEffect copy() {
        return new BecomesBasicLandTypeAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getState().getBattlefield().getAllActivePermanents(new FilterLandPermanent(), source.getControllerId(), game)) {
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        Mana mana = new Mana();
                        for (Ability ability : land.getAbilities()) {
                            if (ability instanceof BasicManaAbility) {
                                for (Mana netMana : ((BasicManaAbility) ability).getNetMana(game)) {
                                    mana.add(netMana);
                                }
                            }
                        }
                        if (mana.getGreen() == 0 && landTypes.contains(SubType.FOREST)) {
                            land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getRed() == 0 && landTypes.contains(SubType.MOUNTAIN)) {
                            land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getBlue() == 0 && landTypes.contains(SubType.ISLAND)) {
                            land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getWhite() == 0 && landTypes.contains(SubType.PLAINS)) {
                            land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                        }
                        if (mana.getBlack() == 0 && landTypes.contains(SubType.SWAMP)) {
                            land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case TypeChangingEffects_4:
                        for (SubType subtype : landTypes) {
                            if (!land.hasSubtype(subtype, game)) {
                                land.getSubtype(game).add(subtype);
                            }
                        }
                        break;
                }
            }

        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
