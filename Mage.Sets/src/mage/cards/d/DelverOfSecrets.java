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
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.cards.i.InsectileAberration;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Alvin
 */
public final class DelverOfSecrets extends CardImpl {

    public DelverOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.transformable = true;
        this.secondSideCardClazz = InsectileAberration.class;

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DelverOfSecretsEffect(), TargetController.YOU, false));
    }

    public DelverOfSecrets(final DelverOfSecrets card) {
        super(card);
    }

    @Override
    public DelverOfSecrets copy() {
        return new DelverOfSecrets(this);
    }
}

class DelverOfSecretsEffect extends OneShotEffect {
    
    private static final FilterCard filter = new FilterInstantOrSorceryCard();
    
    public DelverOfSecretsEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform {this}";
    }
    
    public DelverOfSecretsEffect(final DelverOfSecretsEffect effect) {
        super(effect);
    }
    
    @Override
    public DelverOfSecretsEffect copy() {
        return new DelverOfSecretsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {            
            if (player.getLibrary().hasCards()) {
                Card card = player.getLibrary().getFromTop(game);
                Cards cards = new CardsImpl();
                cards.add(card);
                player.lookAtCards(sourcePermanent.getName(), cards, game);
                if (player.chooseUse(Outcome.DrawCard, "Do you wish to reveal the card at the top of the library?", source, game)) {
                    player.revealCards(sourcePermanent.getName(), cards, game);
                    if (filter.match(card, game)) {
                        return new TransformSourceEffect(true, true).apply(game, source);
                    }
                }
                
            }
            return true;
        }        
        return false;
    }
}
