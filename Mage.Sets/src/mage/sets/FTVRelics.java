/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class FTVRelics extends ExpansionSet {

    private static final FTVRelics instance = new FTVRelics();

    public static FTVRelics getInstance() {
        return instance;
    }

    private FTVRelics() {
        super("From the Vault: Relics", "V10", ExpansionSet.buildDate(2010, 8, 27), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Aether Vial", 1, Rarity.MYTHIC, mage.cards.a.AetherVial.class));
        cards.add(new SetCardInfo("Black Vise", 2, Rarity.MYTHIC, mage.cards.b.BlackVise.class));
        cards.add(new SetCardInfo("Isochron Scepter", 3, Rarity.MYTHIC, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Ivory Tower", 4, Rarity.MYTHIC, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jester's Cap", 5, Rarity.MYTHIC, mage.cards.j.JestersCap.class));
        cards.add(new SetCardInfo("Karn, Silver Golem", 6, Rarity.MYTHIC, mage.cards.k.KarnSilverGolem.class));
        cards.add(new SetCardInfo("Masticore", 7, Rarity.MYTHIC, mage.cards.m.Masticore.class));
        cards.add(new SetCardInfo("Memory Jar", 8, Rarity.MYTHIC, mage.cards.m.MemoryJar.class));
        cards.add(new SetCardInfo("Mirari", 9, Rarity.MYTHIC, mage.cards.m.Mirari.class));
        cards.add(new SetCardInfo("Mox Diamond", 10, Rarity.MYTHIC, mage.cards.m.MoxDiamond.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 11, Rarity.MYTHIC, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Sol Ring", 12, Rarity.MYTHIC, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Sundering Titan", 13, Rarity.MYTHIC, mage.cards.s.SunderingTitan.class));
        cards.add(new SetCardInfo("Sword of Body and Mind", 14, Rarity.MYTHIC, mage.cards.s.SwordOfBodyAndMind.class));
        cards.add(new SetCardInfo("Zuran Orb", 15, Rarity.MYTHIC, mage.cards.z.ZuranOrb.class));
    }
}
