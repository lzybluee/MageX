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
package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.a.AbbeyMatron;
import mage.cards.a.AlibansTower;
import mage.cards.c.CemeteryGate;
import mage.cards.d.DrySpell;
import mage.cards.d.DwarvenTrader;
import mage.cards.f.FeastOfTheUnicorn;
import mage.cards.f.FolkOfAnHavva;
import mage.cards.m.MesaFalcon;
import mage.cards.r.ReefPirates;
import mage.cards.s.SengirBats;
import mage.cards.t.Torture;
import mage.cards.w.WillowFaerie;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public class Homelands extends ExpansionSet {

    private static final Homelands instance = new Homelands();

    public static Homelands getInstance() {
        return instance;
    }

    private Homelands() {
        super("Homelands", "HML", ExpansionSet.buildDate(1995, 9, 1), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Abbey Gargoyles", 1, Rarity.UNCOMMON, mage.cards.a.AbbeyGargoyles.class));
        cards.add(new SetCardInfo("Abbey Matron", "2a", Rarity.COMMON, AbbeyMatron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Abbey Matron", "2b", Rarity.COMMON, AbbeyMatron.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aether Storm", 21, Rarity.UNCOMMON, mage.cards.a.AetherStorm.class));
        cards.add(new SetCardInfo("Aliban's Tower", "61a", Rarity.COMMON, AlibansTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aliban's Tower", "61b", Rarity.COMMON, AlibansTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ambush Party", "63a", Rarity.COMMON, mage.cards.a.AmbushParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ambush Party", "63b", Rarity.COMMON, mage.cards.a.AmbushParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ambush", 62, Rarity.COMMON, mage.cards.a.Ambush.class));
        cards.add(new SetCardInfo("An-Havva Constable", 81, Rarity.RARE, mage.cards.a.AnHavvaConstable.class));
        cards.add(new SetCardInfo("An-Havva Inn", 82, Rarity.UNCOMMON, mage.cards.a.AnHavvaInn.class));
        cards.add(new SetCardInfo("An-Havva Township", 111, Rarity.UNCOMMON, mage.cards.a.AnHavvaTownship.class));
        cards.add(new SetCardInfo("An-Zerrin Ruins", 64, Rarity.RARE, mage.cards.a.AnZerrinRuins.class));
        cards.add(new SetCardInfo("Anaba Ancestor", 65, Rarity.RARE, mage.cards.a.AnabaAncestor.class));
        cards.add(new SetCardInfo("Anaba Bodyguard", "66a", Rarity.COMMON, mage.cards.a.AnabaBodyguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Bodyguard", "66b", Rarity.COMMON, mage.cards.a.AnabaBodyguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Shaman", "67a", Rarity.COMMON, mage.cards.a.AnabaShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Shaman", "67b", Rarity.COMMON, mage.cards.a.AnabaShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anaba Spirit Crafter", 68, Rarity.RARE, mage.cards.a.AnabaSpiritCrafter.class));
        cards.add(new SetCardInfo("Apocalypse Chime", 101, Rarity.RARE, mage.cards.a.ApocalypseChime.class));
        cards.add(new SetCardInfo("Autumn Willow", 83, Rarity.RARE, mage.cards.a.AutumnWillow.class));
        cards.add(new SetCardInfo("Aysen Abbey", 112, Rarity.UNCOMMON, mage.cards.a.AysenAbbey.class));
        cards.add(new SetCardInfo("Aysen Bureaucrats", "3a", Rarity.COMMON, mage.cards.a.AysenBureaucrats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aysen Bureaucrats", "3b", Rarity.COMMON, mage.cards.a.AysenBureaucrats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aysen Crusader", 4, Rarity.RARE, mage.cards.a.AysenCrusader.class));
        cards.add(new SetCardInfo("Aysen Highway", 5, Rarity.RARE, mage.cards.a.AysenHighway.class));
        cards.add(new SetCardInfo("Baki's Curse", 22, Rarity.RARE, mage.cards.b.BakisCurse.class));
        cards.add(new SetCardInfo("Baron Sengir", 41, Rarity.RARE, mage.cards.b.BaronSengir.class));
        cards.add(new SetCardInfo("Beast Walkers", 6, Rarity.RARE, mage.cards.b.BeastWalkers.class));
        cards.add(new SetCardInfo("Black Carriage", 42, Rarity.RARE, mage.cards.b.BlackCarriage.class));
        cards.add(new SetCardInfo("Broken Visage", 43, Rarity.RARE, mage.cards.b.BrokenVisage.class));
        cards.add(new SetCardInfo("Carapace", "84a", Rarity.COMMON, mage.cards.c.Carapace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Carapace", "84b", Rarity.COMMON, mage.cards.c.Carapace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Castle Sengir", 113, Rarity.UNCOMMON, mage.cards.c.CastleSengir.class));
        cards.add(new SetCardInfo("Cemetery Gate", "44a", Rarity.COMMON, CemeteryGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cemetery Gate", "44b", Rarity.COMMON, CemeteryGate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chain Stasis", 23, Rarity.RARE, mage.cards.c.ChainStasis.class));
        cards.add(new SetCardInfo("Chandler", 69, Rarity.COMMON, mage.cards.c.Chandler.class));
        cards.add(new SetCardInfo("Clockwork Gnomes", 102, Rarity.COMMON, mage.cards.c.ClockworkGnomes.class));
        cards.add(new SetCardInfo("Clockwork Steed", 103, Rarity.UNCOMMON, mage.cards.c.ClockworkSteed.class));
        cards.add(new SetCardInfo("Clockwork Swarm", 104, Rarity.COMMON, mage.cards.c.ClockworkSwarm.class));
        cards.add(new SetCardInfo("Coral Reef", 24, Rarity.COMMON, mage.cards.c.CoralReef.class));
        cards.add(new SetCardInfo("Dark Maze", "25a", Rarity.COMMON, mage.cards.d.DarkMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dark Maze", "25b", Rarity.COMMON, mage.cards.d.DarkMaze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daughter of Autumn", 85, Rarity.RARE, mage.cards.d.DaughterOfAutumn.class));
        cards.add(new SetCardInfo("Death Speakers", 7, Rarity.UNCOMMON, mage.cards.d.DeathSpeakers.class));
        cards.add(new SetCardInfo("Didgeridoo", 105, Rarity.RARE, mage.cards.d.Didgeridoo.class));
        cards.add(new SetCardInfo("Drudge Spell", 45, Rarity.UNCOMMON, mage.cards.d.DrudgeSpell.class));
        cards.add(new SetCardInfo("Dry Spell", "46a", Rarity.COMMON, DrySpell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dry Spell", "46b", Rarity.COMMON, DrySpell.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwarven Pony", 70, Rarity.RARE, mage.cards.d.DwarvenPony.class));
        cards.add(new SetCardInfo("Dwarven Trader", "72a", Rarity.COMMON, DwarvenTrader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dwarven Trader", "72b", Rarity.COMMON, DwarvenTrader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ebony Rhino", 106, Rarity.COMMON, mage.cards.e.EbonyRhino.class));
        cards.add(new SetCardInfo("Eron the Relentless", 73, Rarity.UNCOMMON, mage.cards.e.EronTheRelentless.class));
        cards.add(new SetCardInfo("Evaporate", 74, Rarity.UNCOMMON, mage.cards.e.Evaporate.class));
        cards.add(new SetCardInfo("Faerie Noble", 86, Rarity.RARE, mage.cards.f.FaerieNoble.class));
        cards.add(new SetCardInfo("Feast of the Unicorn", "47a", Rarity.COMMON, FeastOfTheUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feast of the Unicorn", "47b", Rarity.COMMON, FeastOfTheUnicorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feroz's Ban", 107, Rarity.RARE, mage.cards.f.FerozsBan.class));
        cards.add(new SetCardInfo("Folk of An-Havva", "87a", Rarity.COMMON, FolkOfAnHavva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Folk of An-Havva", "87b", Rarity.COMMON, FolkOfAnHavva.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forget", 26, Rarity.RARE, mage.cards.f.Forget.class));
        cards.add(new SetCardInfo("Funeral March", 48, Rarity.UNCOMMON, mage.cards.f.FuneralMarch.class));
        cards.add(new SetCardInfo("Ghost Hounds", 49, Rarity.UNCOMMON, mage.cards.g.GhostHounds.class));
        cards.add(new SetCardInfo("Grandmother Sengir", 50, Rarity.RARE, mage.cards.g.GrandmotherSengir.class));
        cards.add(new SetCardInfo("Greater Werewolf", 51, Rarity.UNCOMMON, mage.cards.g.GreaterWerewolf.class));
        cards.add(new SetCardInfo("Hazduhr the Abbot", 8, Rarity.RARE, mage.cards.h.HazduhrTheAbbot.class));
        cards.add(new SetCardInfo("Headstone", 52, Rarity.COMMON, mage.cards.h.Headstone.class));
        cards.add(new SetCardInfo("Hungry Mist", "88a", Rarity.COMMON, mage.cards.h.HungryMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hungry Mist", "88b", Rarity.COMMON, mage.cards.h.HungryMist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ihsan's Shade", 53, Rarity.UNCOMMON, mage.cards.i.IhsansShade.class));
        cards.add(new SetCardInfo("Irini Sengir", 54, Rarity.UNCOMMON, mage.cards.i.IriniSengir.class));
        cards.add(new SetCardInfo("Ironclaw Curse", 76, Rarity.RARE, mage.cards.i.IronclawCurse.class));
        cards.add(new SetCardInfo("Jinx", 29, Rarity.COMMON, mage.cards.j.Jinx.class));
        cards.add(new SetCardInfo("Joven", 77, Rarity.COMMON, mage.cards.j.Joven.class));
        cards.add(new SetCardInfo("Joven's Tools", 108, Rarity.UNCOMMON, mage.cards.j.JovensTools.class));
        cards.add(new SetCardInfo("Koskun Falls", 55, Rarity.RARE, mage.cards.k.KoskunFalls.class));
        cards.add(new SetCardInfo("Koskun Keep", 114, Rarity.UNCOMMON, mage.cards.k.KoskunKeep.class));
        cards.add(new SetCardInfo("Labyrinth Minotaur", "30a", Rarity.COMMON, mage.cards.l.LabyrinthMinotaur.class));
        cards.add(new SetCardInfo("Leaping Lizard", 90, Rarity.COMMON, mage.cards.l.LeapingLizard.class));
        cards.add(new SetCardInfo("Leeches", 9, Rarity.RARE, mage.cards.l.Leeches.class));
        cards.add(new SetCardInfo("Mammoth Harness", 91, Rarity.RARE, mage.cards.m.MammothHarness.class));
        cards.add(new SetCardInfo("Marjhan", 31, Rarity.RARE, mage.cards.m.Marjhan.class));
        cards.add(new SetCardInfo("Memory Lapse", "32a", Rarity.COMMON, mage.cards.m.MemoryLapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Memory Lapse", "32b", Rarity.COMMON, mage.cards.m.MemoryLapse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merchant Scroll", 33, Rarity.COMMON, mage.cards.m.MerchantScroll.class));
        cards.add(new SetCardInfo("Mesa Falcon", "10a", Rarity.COMMON, MesaFalcon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mesa Falcon", "10b", Rarity.COMMON, MesaFalcon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Decree", 34, Rarity.RARE, mage.cards.m.MysticDecree.class));
        cards.add(new SetCardInfo("Narwhal", 35, Rarity.RARE, mage.cards.n.Narwhal.class));
        cards.add(new SetCardInfo("Primal Order", 92, Rarity.RARE, mage.cards.p.PrimalOrder.class));
        cards.add(new SetCardInfo("Rashka the Slayer", 12, Rarity.RARE, mage.cards.r.RashkaTheSlayer.class));
        cards.add(new SetCardInfo("Reef Pirates", "36a", Rarity.COMMON, ReefPirates.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reef Pirates", "36b", Rarity.COMMON, ReefPirates.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Renewal", 93, Rarity.COMMON, mage.cards.r.Renewal.class));
        cards.add(new SetCardInfo("Retribution", 79, Rarity.UNCOMMON, mage.cards.r.Retribution.class));
        cards.add(new SetCardInfo("Reveka, Wizard Savant", 37, Rarity.RARE, mage.cards.r.RevekaWizardSavant.class));
        cards.add(new SetCardInfo("Root Spider", 94, Rarity.UNCOMMON, mage.cards.r.RootSpider.class));
        cards.add(new SetCardInfo("Roots", 95, Rarity.UNCOMMON, mage.cards.r.Roots.class));
        cards.add(new SetCardInfo("Roterothopter", 109, Rarity.COMMON, mage.cards.r.Roterothopter.class));
        cards.add(new SetCardInfo("Sea Sprite", 38, Rarity.UNCOMMON, mage.cards.s.SeaSprite.class));
        cards.add(new SetCardInfo("Sengir Autocrat", 56, Rarity.UNCOMMON, mage.cards.s.SengirAutocrat.class));
        cards.add(new SetCardInfo("Sengir Bats", "57a", Rarity.COMMON, SengirBats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sengir Bats", "57b", Rarity.COMMON, SengirBats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Aviary", 14, Rarity.RARE, mage.cards.s.SerraAviary.class));
        cards.add(new SetCardInfo("Serra Bestiary", 15, Rarity.UNCOMMON, mage.cards.s.SerraBestiary.class));
        cards.add(new SetCardInfo("Serra Paladin", 17, Rarity.COMMON, mage.cards.s.SerraPaladin.class));
        cards.add(new SetCardInfo("Serrated Arrows", 110, Rarity.COMMON, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Shrink", "97a", Rarity.COMMON, mage.cards.s.Shrink.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shrink", "97b", Rarity.COMMON, mage.cards.s.Shrink.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soraya the Falconer", 18, Rarity.RARE, mage.cards.s.SorayaTheFalconer.class));
        cards.add(new SetCardInfo("Spectral Bears", 98, Rarity.UNCOMMON, mage.cards.s.SpectralBears.class));
        cards.add(new SetCardInfo("Torture", "59a", Rarity.COMMON, Torture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Torture", "59b", Rarity.COMMON, Torture.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trade Caravan", "19a", Rarity.COMMON, mage.cards.t.TradeCaravan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trade Caravan", "19b", Rarity.COMMON, mage.cards.t.TradeCaravan.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Truce", 20, Rarity.RARE, mage.cards.t.Truce.class));
        cards.add(new SetCardInfo("Veldrane of Sengir", 60, Rarity.RARE, mage.cards.v.VeldraneOfSengir.class));
        cards.add(new SetCardInfo("Wall of Kelp", 40, Rarity.RARE, mage.cards.w.WallOfKelp.class));
        cards.add(new SetCardInfo("Willow Faerie", "99a", Rarity.COMMON, WillowFaerie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Faerie", "99b", Rarity.COMMON, WillowFaerie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Willow Priestess", 100, Rarity.RARE, mage.cards.w.WillowPriestess.class));
        cards.add(new SetCardInfo("Winter Sky", 80, Rarity.RARE, mage.cards.w.WinterSky.class));
        cards.add(new SetCardInfo("Wizards' School", 115, Rarity.UNCOMMON, mage.cards.w.WizardsSchool.class));
    }
}
