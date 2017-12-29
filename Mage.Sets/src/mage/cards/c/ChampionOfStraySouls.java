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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class ChampionOfStraySouls extends CardImpl {

    private final UUID originalId;
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(new AnotherPredicate());
    }

    public ChampionOfStraySouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        /**
         * You choose the targets of the first ability as you activate that
         * ability, before you pay any costs. You can't target any of the
         * creatures you sacrifice.
         */
        //TODO: Make ability properly copiable
        // {3}{B}{B}, {T}, Sacrifice X other creatures: Return X target creatures from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creatures from your graveyard to the battlefield");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{3}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard")));
        originalId = ability.getOriginalId();
        this.addAbility(ability);

        // {5}{B}{B}: Put Champion of Stray Souls on top of your library from your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD,
                new PutOnLibrarySourceEffect(true, "Put {this} on top of your library from your graveyard"),
                new ManaCostsImpl("{5}{B}{B}")));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                    int xValue = new GetXValue().calculate(game, ability, null);
                    ability.getTargets().clear();
                    ability.addTarget(new TargetCardInYourGraveyard(xValue, xValue, new FilterCreatureCard("creature cards from your graveyard")));
                }
            }
        }
    }

    public ChampionOfStraySouls(final ChampionOfStraySouls card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public ChampionOfStraySouls copy() {
        return new ChampionOfStraySouls(this);
    }
}
