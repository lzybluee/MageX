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
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author nantuko
 */
public class Smallpox extends CardImpl {
    
    private static final FilterControlledPermanent filterCreature = new FilterControlledCreaturePermanent();
    private static final FilterControlledPermanent filterLand = new FilterControlledLandPermanent();
    
    public Smallpox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Each player loses 1 life, discards a card, sacrifices a creature, then sacrifices a land.
        this.getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(1));
        Effect effect = new DiscardEachPlayerEffect();
        effect.setText(", discards a card");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(1, filterCreature);
        effect.setText(", sacrifices a creature");
        this.getSpellAbility().addEffect(effect);
        effect = new SacrificeAllEffect(1, filterLand);
        effect.setText(", then sacrifices a land");
        this.getSpellAbility().addEffect(effect);
    }

    public Smallpox(final Smallpox card) {
        super(card);
    }

    @Override
    public Smallpox copy() {
        return new Smallpox(this);
    }
}
