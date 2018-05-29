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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public final class SynodSanctum extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SynodSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {tap}: Exile target permanent you control.
        SynodSanctumEffect effect = new SynodSanctumEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);

        // {2}, Sacrifice Synod Sanctum: Return all cards exiled with Synod Sanctum to the battlefield under your control.
        SynodSanctumEffect2 effect2 = new SynodSanctumEffect2();
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect2, new ManaCostsImpl("{2}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    public SynodSanctum(final SynodSanctum card) {
        super(card);
    }

    @Override
    public SynodSanctum copy() {
        return new SynodSanctum(this);
    }
}

class SynodSanctumEffect extends OneShotEffect {

    SynodSanctumEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target permanent you control";
    }

    SynodSanctumEffect(SynodSanctumEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                    if (exileZone != null) {
                        controller.moveCardToExileWithInfo(permanent, exileZone, sourceObject.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SynodSanctumEffect copy() {
        return new SynodSanctumEffect(this);
    }
}

class SynodSanctumEffect2 extends OneShotEffect {

    SynodSanctumEffect2() {
        super(Outcome.Benefit);
        staticText = "Return all cards exiled with {this} to the battlefield under your control";
    }

    SynodSanctumEffect2(SynodSanctumEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        if (exileZone == null) {
            return true;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(exileZone, Zone.BATTLEFIELD, source, game);
        }
        return false;
    }

    @Override
    public SynodSanctumEffect2 copy() {
        return new SynodSanctumEffect2(this);
    }
}
