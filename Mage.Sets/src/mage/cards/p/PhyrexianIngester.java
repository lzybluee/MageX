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
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public class PhyrexianIngester extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public PhyrexianIngester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Imprint - When Phyrexian Ingester enters the battlefield, you may exile target nontoken creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhyrexianIngesterImprintEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // Phyrexian Ingester gets +X/+Y, where X is the exiled creature card's power and Y is its toughness.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianIngesterBoostEffect()));
    }

    public PhyrexianIngester(final PhyrexianIngester card) {
        super(card);
    }

    @Override
    public PhyrexianIngester copy() {
        return new PhyrexianIngester(this);
    }
}

class PhyrexianIngesterImprintEffect extends OneShotEffect {

    public PhyrexianIngesterImprintEffect() {
        super(Outcome.Exile);
        this.staticText = "exile target nontoken creature";
    }

    public PhyrexianIngesterImprintEffect(final PhyrexianIngesterImprintEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianIngesterImprintEffect copy() {
        return new PhyrexianIngesterImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
            if (sourcePermanent != null && targetPermanent != null) {
                controller.moveCardToExileWithInfo(targetPermanent, getId(), sourceObject.getIdName() + " (Imprint)", source.getSourceId(), game, Zone.BATTLEFIELD, true);
                sourcePermanent.imprint(targetPermanent.getId(), game);
                return true;
            }
        }
        return false;
    }
}

class PhyrexianIngesterBoostEffect extends ContinuousEffectImpl {

    public PhyrexianIngesterBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "{this} gets +X/+Y, where X is the exiled creature card's power and Y is its toughness";
    }

    public PhyrexianIngesterBoostEffect(final PhyrexianIngesterBoostEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianIngesterBoostEffect copy() {
        return new PhyrexianIngesterBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && !permanent.getImprinted().isEmpty()) {
            Card card = game.getCard(permanent.getImprinted().get(0));
            if (card != null) {
                permanent.addPower(card.getPower().getValue());
                permanent.addToughness(card.getToughness().getValue());
                return true;
            }
            return true;
        }
        return false;
    }
}
