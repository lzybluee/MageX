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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author Loki
 */
public final class BalefireLiege extends CardImpl {

    private static final FilterCreaturePermanent filterRedCreature = new FilterCreaturePermanent("red creatures");
    private static final FilterCreaturePermanent filterWhiteCreature = new FilterCreaturePermanent("white creatures");
    private static final FilterSpell filterRedSpell = new FilterSpell("a red spell");
    private static final FilterSpell filterWhiteSpell = new FilterSpell("a white spell");

    static {
        filterRedCreature.add(new ColorPredicate(ObjectColor.RED));
        filterWhiteCreature.add(new ColorPredicate(ObjectColor.WHITE));
        filterRedSpell.add(new ColorPredicate(ObjectColor.RED));
        filterWhiteSpell.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BalefireLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}{R/W}");
        this.subtype.add(SubType.SPIRIT, SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterRedCreature, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterWhiteCreature, true)));
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(3), filterRedSpell, false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(3), filterWhiteSpell, false));
    }

    public BalefireLiege(final BalefireLiege card) {
        super(card);
    }

    @Override
    public BalefireLiege copy() {
        return new BalefireLiege(this);
    }
}
