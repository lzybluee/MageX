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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class PrimalCommand extends CardImpl {

    private static final FilterPermanent filterNonCreature = new FilterPermanent("noncreature permanent");
    static {
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public PrimalCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Target player gains 7 life;
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPlayer());
        // or put target noncreature permanent on top of its owner's library;
        Mode mode = new Mode();
        mode.getEffects().add(new PutOnLibraryTargetEffect(true));
        Target target = new TargetPermanent(filterNonCreature);
        mode.getTargets().add(target);
        this.getSpellAbility().getModes().addMode(mode);
        // or target player shuffles their graveyard into their library;
        mode = new Mode();
        mode.getEffects().add(new PrimalCommandShuffleGraveyardEffect());
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);        
        // or search your library for a creature card, reveal it, put it into your hand, then shuffle your library.
        mode = new Mode();
        mode.getEffects().add(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterCreatureCard()), true, true));
        this.getSpellAbility().getModes().addMode(mode);
        
    }

    public PrimalCommand(final PrimalCommand card) {
        super(card);
    }

    @Override
    public PrimalCommand copy() {
        return new PrimalCommand(this);
    }
}

class PrimalCommandShuffleGraveyardEffect extends OneShotEffect {

    public PrimalCommandShuffleGraveyardEffect() {
        super(Outcome.Neutral);
        this.staticText = "target player shuffles their graveyard into their library";
    }

    public PrimalCommandShuffleGraveyardEffect(final PrimalCommandShuffleGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public PrimalCommandShuffleGraveyardEffect copy() {
        return new PrimalCommandShuffleGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }                 
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
