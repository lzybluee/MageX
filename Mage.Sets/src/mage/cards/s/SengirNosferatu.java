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
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.SengirNosferatuBatToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LoneFox
 *
 */
public class SengirNosferatu extends CardImpl {

    public SengirNosferatu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{B}, Exile Sengir Nosferatu: Create a 1/2 black Bat creature token with flying. It has "{1}{B}, Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control."
        Effect effect = new CreateTokenEffect(new SengirNosferatuBatToken(), 1);
        effect.setText("Create a 1/2 black Bat creature token with flying. It has \"{1}{B}, Sacrifice this creature: Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control.\"");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{1}{B}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public SengirNosferatu(final SengirNosferatu card) {
        super(card);
    }

    @Override
    public SengirNosferatu copy() {
        return new SengirNosferatu(this);
    }
}

class ReturnSengirNosferatuEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("exiled card named Sengir Nosferatu");

    static {
        filter.add(new NamePredicate("Sengir Nosferatu"));
    }

    public ReturnSengirNosferatuEffect() {
        super(Outcome.Benefit);
    }

    public ReturnSengirNosferatuEffect(final ReturnSengirNosferatuEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSengirNosferatuEffect copy() {
        return new ReturnSengirNosferatuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Target target = new TargetCardInExile(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), controllerId, game)) {
            return false;
        }
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            player.chooseTarget(Outcome.PutCreatureInPlay, target, source, game);
            Card card = game.getCard(target.getTargets().get(0));
            if (card != null) {
                return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
            }
        }
        return false;
    }
}
