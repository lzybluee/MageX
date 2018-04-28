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

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public class GlintHawkIdol extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifact");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    public GlintHawkIdol (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        
        // Whenever another artifact enters the battlefield under your control, you may have {this} become a 2/2 Bird artifact creature with flying until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GlintHawkIdolToken(), "", Duration.EndOfTurn), filter, true));

        // {W}: Glint Hawk Idol becomes a 2/2 Bird artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GlintHawkIdolToken(), "", Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.W)));
    }

    public GlintHawkIdol (final GlintHawkIdol card) {
        super(card);
    }

    @Override
    public GlintHawkIdol copy() {
        return new GlintHawkIdol(this);
    }

}

class GlintHawkIdolToken extends TokenImpl {
    GlintHawkIdolToken() {
        super("", "2/2 Bird artifact creature with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }
    public GlintHawkIdolToken(final GlintHawkIdolToken token) {
        super(token);
    }

    public GlintHawkIdolToken copy() {
        return new GlintHawkIdolToken(this);
    }
}
