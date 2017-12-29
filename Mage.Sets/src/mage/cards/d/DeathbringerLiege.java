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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class DeathbringerLiege extends CardImpl {
    private static final FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("white creatures");
    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("black creatures");
    private static final FilterSpell filterWhiteSpellCard = new FilterSpell("a white spell");
    private static final FilterSpell filterBlackSpellCard = new FilterSpell("a black spell");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterWhiteSpellCard.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlackSpellCard.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DeathbringerLiege (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W/B}{W/B}{W/B}");
        this.subtype.add(SubType.HORROR);


        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterWhite, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBlack, true)));
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filterWhiteSpellCard, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        ability = new SpellCastControllerTriggeredAbility(new DeathbringerLiegeEffect(), filterBlackSpellCard, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DeathbringerLiege (final DeathbringerLiege card) {
        super(card);
    }

    @Override
    public DeathbringerLiege copy() {
        return new DeathbringerLiege(this);
    }

}

class DeathbringerLiegeEffect extends OneShotEffect {
    DeathbringerLiegeEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy target creature if it's tapped";
    }

    DeathbringerLiegeEffect(final DeathbringerLiegeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        if (p != null && p.isTapped()) {
            p.destroy(source.getSourceId(), game, false);
        }
        return false;
    }

    @Override
    public DeathbringerLiegeEffect copy() {
        return new DeathbringerLiegeEffect(this);
    }

}
