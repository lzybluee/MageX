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
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfTokenWithDeathtouch;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 * @author nantuko
 */
public class GarrukTheVeilCursed extends CardImpl {

    public GarrukTheVeilCursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.color.setGreen(true);
        this.color.setBlack(true);

        // +1 : Create a 1/1 black Wolf creature token with deathtouch.
        LoyaltyAbility ability1 = new LoyaltyAbility(new CreateTokenEffect(new WolfTokenWithDeathtouch()), 1);
        this.addAbility(ability1);

        // -1 : Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library.
        LoyaltyAbility ability2 = new LoyaltyAbility(new GarrukTheVeilCursedEffect(), -1);
        this.addAbility(ability2);

        // -3 : Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        Effects effects1 = new Effects();
        BoostControlledEffect effect = new BoostControlledEffect(new GarrukTheVeilCursedValue(), new GarrukTheVeilCursedValue(), Duration.EndOfTurn);
        // +X/+X should be counted only once
        effect.setLockedIn(true);
        effect.setRule("Creatures you control get +X/+X until end of turn, where X is the number of creature cards in your graveyard");
        effects1.add(effect);
        effects1.add(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent()));
        this.addAbility(new LoyaltyAbility(effects1, -3));
    }

    public GarrukTheVeilCursed(final GarrukTheVeilCursed card) {
        super(card);
    }

    @Override
    public GarrukTheVeilCursed copy() {
        return new GarrukTheVeilCursed(this);
    }
}

class GarrukTheVeilCursedValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            return player.getGraveyard().getCards(new FilterCreatureCard(), game).size();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of creature cards in your graveyard";
    }

    @Override
    public String toString() {
        return "+X";
    }
}

class GarrukTheVeilCursedEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("a creature you control");

    public GarrukTheVeilCursedEffect() {
        super(Outcome.Benefit);
        staticText = "Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library";
    }

    public GarrukTheVeilCursedEffect(final GarrukTheVeilCursedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        // sacrifice a creature
        Target target = new TargetControlledPermanent(1, 1, filterCreature, false);
        boolean sacrificed = false;
        if (target.canChoose(controller.getId(), game)) {
            while (controller.canRespond() && !target.isChosen() && target.canChoose(controller.getId(), game)) {
                controller.chooseTarget(Outcome.Sacrifice, target, source, game);
            }

            for (int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent(target.getTargets().get(idx));
                if (permanent != null) {
                    sacrificed |= permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }

        if (sacrificed) {
            // search
            FilterCreatureCard filter = new FilterCreatureCard();
            TargetCardInLibrary targetInLibrary = new TargetCardInLibrary(filter);
            Cards cards = new CardsImpl();
            if (controller.searchLibrary(targetInLibrary, game)) {
                for (UUID cardId : targetInLibrary.getTargets()) {
                    Card card = controller.getLibrary().remove(cardId, game);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        cards.add(card);
                    }
                }
            }
            // reveal
            if (!cards.isEmpty()) {
                controller.revealCards("Garruk, the Veil-Cursed", cards, game);
            }
            // shuffle
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public GarrukTheVeilCursedEffect copy() {
        return new GarrukTheVeilCursedEffect(this);
    }
}
