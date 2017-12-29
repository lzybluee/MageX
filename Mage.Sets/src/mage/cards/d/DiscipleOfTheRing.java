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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class DiscipleOfTheRing extends CardImpl {
    
    private static final FilterSpell filterSpell = new FilterSpell("noncreature spell");

    static {
        filterSpell.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public DiscipleOfTheRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {1}, Exile an instant or sorcery card from your graveyard: Choose one - Counter target noncreature spell unless its controller pay {2};
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(2)), new GenericManaCost(1));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(1, new FilterInstantOrSorceryCard("an instant or sorcery card from your graveyard"))));
        ability.addTarget(new TargetSpell(filterSpell));
        
        // or Disciple of the Ring gets +1/+1 until end of turn; 
        Mode mode = new Mode();
        mode.getEffects().add(new BoostSourceEffect(1, 1, Duration.EndOfTurn));
        ability.addMode(mode);
        
        // or Tap target creature;
        mode = new Mode();
        mode.getEffects().add(new TapTargetEffect());
        mode.getTargets().add(new TargetCreaturePermanent());
        ability.addMode(mode);
        
        // or Untap target creature.
        mode = new Mode();
        mode.getEffects().add(new UntapTargetEffect());
        mode.getTargets().add(new TargetCreaturePermanent());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    public DiscipleOfTheRing(final DiscipleOfTheRing card) {
        super(card);
    }

    @Override
    public DiscipleOfTheRing copy() {
        return new DiscipleOfTheRing(this);
    }
}
