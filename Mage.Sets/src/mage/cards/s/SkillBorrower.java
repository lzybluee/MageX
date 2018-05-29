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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class SkillBorrower extends CardImpl {

    public SkillBorrower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card.
        this.addAbility(new SkillBorrowerAbility());
    }

    public SkillBorrower(final SkillBorrower card) {
        super(card);
    }

    @Override
    public SkillBorrower copy() {
        return new SkillBorrower(this);
    }
}


class SkillBorrowerAbility extends StaticAbility {

    public SkillBorrowerAbility() {
        super(Zone.BATTLEFIELD, new SkillBorrowerEffect());
    }

    public SkillBorrowerAbility(SkillBorrowerAbility ability) {
        super(ability);
    }

    @Override
    public SkillBorrowerAbility copy() {
        return new SkillBorrowerAbility(this);
    }

    @Override
    public String getRule() {
        return "As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card";
    }
}

class SkillBorrowerEffect extends ContinuousEffectImpl {

    public SkillBorrowerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "As long as the top card of your library is an artifact or creature card, Skill Borrower has all activated abilities of that card";
    }

    public SkillBorrowerEffect(final SkillBorrowerEffect effect) {
        super(effect);
    }


    @Override
    public SkillBorrowerEffect copy() {
        return new SkillBorrowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            Card card = player.getLibrary().getFromTop(game);
            if(card != null && (card.isCreature() || card.isArtifact())){
                Permanent permanent = game.getPermanent(source.getSourceId());
                if(permanent != null){
                    for(Ability ability : card.getAbilities()){
                        if(ability instanceof ActivatedAbility){
                            permanent.addAbility(ability, source.getSourceId(), game);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
