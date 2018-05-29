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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackIfDefenderControlsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author L_J
 */
public final class BrandedBrawlers extends CardImpl {

    static final private FilterLandPermanent filter = new FilterLandPermanent("an untapped land");
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    final static private String rule = "{this} can't block if you control an untapped land";

    public BrandedBrawlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Branded Brawlers can't attack if defending player controls an untapped land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackIfDefenderControlsPermanent(filter)));

        // Branded Brawlers can't block if you control an untapped land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BrandedBrawlersCantBlockEffect(filter)));
    }

    public BrandedBrawlers(final BrandedBrawlers card) {
        super(card);
    }

    @Override
    public BrandedBrawlers copy() {
        return new BrandedBrawlers(this);
    }
}

class BrandedBrawlersCantBlockEffect extends RestrictionEffect {

    private final FilterPermanent filter;

    public BrandedBrawlersCantBlockEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = new StringBuilder("{this} can't attack if you control ").append(filter.getMessage()).toString();
    }

    public BrandedBrawlersCantBlockEffect(final BrandedBrawlersCantBlockEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        Player player = game.getPlayer(blocker.getControllerId());
        if (player != null) {
            if (game.getBattlefield().countAll(filter, player.getId(), game) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public BrandedBrawlersCantBlockEffect copy() {
        return new BrandedBrawlersCantBlockEffect(this);
    }

}
