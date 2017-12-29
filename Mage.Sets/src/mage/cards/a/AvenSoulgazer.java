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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class AvenSoulgazer extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face down creature");

    static {
        filter.add(new FaceDownPredicate());
    }

    public AvenSoulgazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {2}{W}: Look at target face-down creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AvenSoulgazerLookFaceDownEffect(), new ManaCostsImpl("{2}{W}")); 
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public AvenSoulgazer(final AvenSoulgazer card) {
        super(card);
    }

    @Override
    public AvenSoulgazer copy() {
        return new AvenSoulgazer(this);
    }
}

class AvenSoulgazerLookFaceDownEffect extends OneShotEffect {

    public AvenSoulgazerLookFaceDownEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at target face-down creature";
    }

    public AvenSoulgazerLookFaceDownEffect(final AvenSoulgazerLookFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public AvenSoulgazerLookFaceDownEffect copy() {
        return new AvenSoulgazerLookFaceDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player == null || mageObject == null) {
            return false;
        }
        Permanent faceDownCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (faceDownCreature != null) {
            Permanent copyFaceDown = faceDownCreature.copy();
            copyFaceDown.setFaceDown(false, game);
            Cards cards = new CardsImpl();
            cards.add(copyFaceDown);
            player.lookAtCards("face down card - " + mageObject.getName(), cards, game);
        } else {
            return false;
        }
        return true;
    }
}