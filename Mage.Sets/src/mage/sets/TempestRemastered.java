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
public class TempestRemastered extends ExpansionSet {

    private static final TempestRemastered instance = new TempestRemastered();

    public static TempestRemastered getInstance() {
        return instance;
    }

    private TempestRemastered() {
        super("Tempest Remastered", "TPR", ExpansionSet.buildDate(2015, 5, 6), SetType.MAGIC_ONLINE);
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Acidic Sliver", 206, Rarity.UNCOMMON, mage.cards.a.AcidicSliver.class));
        cards.add(new SetCardInfo("Aftershock", 124, Rarity.COMMON, mage.cards.a.Aftershock.class));
        cards.add(new SetCardInfo("Aluren", 165, Rarity.RARE, mage.cards.a.Aluren.class));
        cards.add(new SetCardInfo("Anarchist", 125, Rarity.COMMON, mage.cards.a.Anarchist.class));
        cards.add(new SetCardInfo("Angelic Blessing", 1, Rarity.COMMON, mage.cards.a.AngelicBlessing.class));
        cards.add(new SetCardInfo("Angelic Protector", 2, Rarity.UNCOMMON, mage.cards.a.AngelicProtector.class));
        cards.add(new SetCardInfo("Anoint", 3, Rarity.COMMON, mage.cards.a.Anoint.class));
        cards.add(new SetCardInfo("Armor Sliver", 4, Rarity.COMMON, mage.cards.a.ArmorSliver.class));
        cards.add(new SetCardInfo("Armored Pegasus", 5, Rarity.COMMON, mage.cards.a.ArmoredPegasus.class));
        cards.add(new SetCardInfo("Avenging Angel", 6, Rarity.UNCOMMON, mage.cards.a.AvengingAngel.class));
        cards.add(new SetCardInfo("Bandage", 7, Rarity.COMMON, mage.cards.b.Bandage.class));
        cards.add(new SetCardInfo("Barbed Sliver", 126, Rarity.COMMON, mage.cards.b.BarbedSliver.class));
        cards.add(new SetCardInfo("Bottle Gnomes", 217, Rarity.COMMON, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Caldera Lake", 235, Rarity.UNCOMMON, mage.cards.c.CalderaLake.class));
        cards.add(new SetCardInfo("Cannibalize", 83, Rarity.UNCOMMON, mage.cards.c.Cannibalize.class));
        cards.add(new SetCardInfo("Canopy Spider", 166, Rarity.COMMON, mage.cards.c.CanopySpider.class));
        cards.add(new SetCardInfo("Canyon Wildcat", 127, Rarity.COMMON, mage.cards.c.CanyonWildcat.class));
        cards.add(new SetCardInfo("Capsize", 42, Rarity.UNCOMMON, mage.cards.c.Capsize.class));
        cards.add(new SetCardInfo("Carnassid", 167, Rarity.UNCOMMON, mage.cards.c.Carnassid.class));
        cards.add(new SetCardInfo("Carnophage", 84, Rarity.COMMON, mage.cards.c.Carnophage.class));
        cards.add(new SetCardInfo("Cataclysm", 8, Rarity.MYTHIC, mage.cards.c.Cataclysm.class));
        cards.add(new SetCardInfo("Charging Paladin", 9, Rarity.COMMON, mage.cards.c.ChargingPaladin.class));
        cards.add(new SetCardInfo("Cinder Marsh", 236, Rarity.UNCOMMON, mage.cards.c.CinderMarsh.class));
        cards.add(new SetCardInfo("City of Traitors", 237, Rarity.RARE, mage.cards.c.CityOfTraitors.class));
        cards.add(new SetCardInfo("Clot Sliver", 85, Rarity.COMMON, mage.cards.c.ClotSliver.class));
        cards.add(new SetCardInfo("Coat of Arms", 218, Rarity.RARE, mage.cards.c.CoatOfArms.class));
        cards.add(new SetCardInfo("Coercion", 86, Rarity.COMMON, mage.cards.c.Coercion.class));
        cards.add(new SetCardInfo("Coffin Queen", 87, Rarity.RARE, mage.cards.c.CoffinQueen.class));
        cards.add(new SetCardInfo("Coiled Tinviper", 219, Rarity.COMMON, mage.cards.c.CoiledTinviper.class));
        cards.add(new SetCardInfo("Commander Greven il-Vec", 88, Rarity.RARE, mage.cards.c.CommanderGrevenIlVec.class));
        cards.add(new SetCardInfo("Conviction", 10, Rarity.COMMON, mage.cards.c.Conviction.class));
        cards.add(new SetCardInfo("Corpse Dance", 89, Rarity.RARE, mage.cards.c.CorpseDance.class));
        cards.add(new SetCardInfo("Counterspell", 43, Rarity.UNCOMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crashing Boars", 168, Rarity.UNCOMMON, mage.cards.c.CrashingBoars.class));
        cards.add(new SetCardInfo("Craven Giant", 128, Rarity.COMMON, mage.cards.c.CravenGiant.class));
        cards.add(new SetCardInfo("Crovax the Cursed", 90, Rarity.RARE, mage.cards.c.CrovaxTheCursed.class));
        cards.add(new SetCardInfo("Crystalline Sliver", 207, Rarity.UNCOMMON, mage.cards.c.CrystallineSliver.class));
        cards.add(new SetCardInfo("Curiosity", 44, Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Cursed Flesh", 91, Rarity.COMMON, mage.cards.c.CursedFlesh.class));
        cards.add(new SetCardInfo("Cursed Scroll", 220, Rarity.MYTHIC, mage.cards.c.CursedScroll.class));
        cards.add(new SetCardInfo("Dark Banishing", 92, Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Dark Ritual", 93, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Dauthi Horror", 94, Rarity.COMMON, mage.cards.d.DauthiHorror.class));
        cards.add(new SetCardInfo("Dauthi Jackal", 95, Rarity.COMMON, mage.cards.d.DauthiJackal.class));
        cards.add(new SetCardInfo("Dauthi Marauder", 96, Rarity.UNCOMMON, mage.cards.d.DauthiMarauder.class));
        cards.add(new SetCardInfo("Dauthi Slayer", 97, Rarity.UNCOMMON, mage.cards.d.DauthiSlayer.class));
        cards.add(new SetCardInfo("Dauthi Warlord", 98, Rarity.UNCOMMON, mage.cards.d.DauthiWarlord.class));
        cards.add(new SetCardInfo("Deadshot", 129, Rarity.UNCOMMON, mage.cards.d.Deadshot.class));
        cards.add(new SetCardInfo("Death Pits of Rath", 99, Rarity.RARE, mage.cards.d.DeathPitsOfRath.class));
        cards.add(new SetCardInfo("Death Stroke", 100, Rarity.COMMON, mage.cards.d.DeathStroke.class));
        cards.add(new SetCardInfo("Death's Duet", 101, Rarity.COMMON, mage.cards.d.DeathsDuet.class));
        cards.add(new SetCardInfo("Diabolic Edict", 102, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Disenchant", 11, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dismiss", 45, Rarity.UNCOMMON, mage.cards.d.Dismiss.class));
        cards.add(new SetCardInfo("Dracoplasm", 208, Rarity.RARE, mage.cards.d.Dracoplasm.class));
        cards.add(new SetCardInfo("Dream Halls", 46, Rarity.MYTHIC, mage.cards.d.DreamHalls.class));
        cards.add(new SetCardInfo("Dream Prowler", 47, Rarity.UNCOMMON, mage.cards.d.DreamProwler.class));
        cards.add(new SetCardInfo("Dungeon Shade", 103, Rarity.COMMON, mage.cards.d.DungeonShade.class));
        cards.add(new SetCardInfo("Elven Rite", 169, Rarity.COMMON, mage.cards.e.ElvenRite.class));
        cards.add(new SetCardInfo("Elvish Fury", 170, Rarity.UNCOMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Emmessi Tome", 221, Rarity.UNCOMMON, mage.cards.e.EmmessiTome.class));
        cards.add(new SetCardInfo("Endangered Armodon", 171, Rarity.COMMON, mage.cards.e.EndangeredArmodon.class));
        cards.add(new SetCardInfo("Ephemeron", 48, Rarity.RARE, mage.cards.e.Ephemeron.class));
        cards.add(new SetCardInfo("Erratic Portal", 222, Rarity.RARE, mage.cards.e.ErraticPortal.class));
        cards.add(new SetCardInfo("Evincar's Justice", 104, Rarity.UNCOMMON, mage.cards.e.EvincarsJustice.class));
        cards.add(new SetCardInfo("Exalted Dragon", 12, Rarity.RARE, mage.cards.e.ExaltedDragon.class));
        cards.add(new SetCardInfo("Fanning the Flames", 130, Rarity.RARE, mage.cards.f.FanningTheFlames.class));
        cards.add(new SetCardInfo("Field of Souls", 13, Rarity.RARE, mage.cards.f.FieldOfSouls.class));
        cards.add(new SetCardInfo("Fighting Drake", 49, Rarity.UNCOMMON, mage.cards.f.FightingDrake.class));
        cards.add(new SetCardInfo("Flame Wave", 131, Rarity.RARE, mage.cards.f.FlameWave.class));
        cards.add(new SetCardInfo("Flowstone Blade", 132, Rarity.COMMON, mage.cards.f.FlowstoneBlade.class));
        cards.add(new SetCardInfo("Flowstone Mauler", 133, Rarity.UNCOMMON, mage.cards.f.FlowstoneMauler.class));
        cards.add(new SetCardInfo("Flowstone Wyvern", 134, Rarity.UNCOMMON, mage.cards.f.FlowstoneWyvern.class));
        cards.add(new SetCardInfo("Forbid", 50, Rarity.RARE, mage.cards.f.Forbid.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 267, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fugue", 105, Rarity.UNCOMMON, mage.cards.f.Fugue.class));
        cards.add(new SetCardInfo("Furnace Brood", 135, Rarity.COMMON, mage.cards.f.FurnaceBrood.class));
        cards.add(new SetCardInfo("Gallantry", 14, Rarity.UNCOMMON, mage.cards.g.Gallantry.class));
        cards.add(new SetCardInfo("Gaseous Form", 51, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Gerrard's Battle Cry", 15, Rarity.RARE, mage.cards.g.GerrardsBattleCry.class));
        cards.add(new SetCardInfo("Goblin Bombardment", 136, Rarity.UNCOMMON, mage.cards.g.GoblinBombardment.class));
        cards.add(new SetCardInfo("Gravedigger", 106, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Grindstone", 223, Rarity.MYTHIC, mage.cards.g.Grindstone.class));
        cards.add(new SetCardInfo("Hammerhead Shark", 52, Rarity.COMMON, mage.cards.h.HammerheadShark.class));
        cards.add(new SetCardInfo("Harrow", 172, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Heartwood Dryad", 173, Rarity.COMMON, mage.cards.h.HeartwoodDryad.class));
        cards.add(new SetCardInfo("Heartwood Giant", 174, Rarity.RARE, mage.cards.h.HeartwoodGiant.class));
        cards.add(new SetCardInfo("Hermit Druid", 175, Rarity.UNCOMMON, mage.cards.h.HermitDruid.class));
        cards.add(new SetCardInfo("Hibernation Sliver", 209, Rarity.UNCOMMON, mage.cards.h.HibernationSliver.class));
        cards.add(new SetCardInfo("Horned Sliver", 176, Rarity.COMMON, mage.cards.h.HornedSliver.class));
        cards.add(new SetCardInfo("Horned Turtle", 53, Rarity.COMMON, mage.cards.h.HornedTurtle.class));
        cards.add(new SetCardInfo("Humility", 16, Rarity.MYTHIC, mage.cards.h.Humility.class));
        cards.add(new SetCardInfo("Intuition", 54, Rarity.RARE, mage.cards.i.Intuition.class));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 255, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jinxed Idol", 224, Rarity.RARE, mage.cards.j.JinxedIdol.class));
        cards.add(new SetCardInfo("Kezzerdrix", 107, Rarity.RARE, mage.cards.k.Kezzerdrix.class));
        cards.add(new SetCardInfo("Killer Whale", 55, Rarity.UNCOMMON, mage.cards.k.KillerWhale.class));
        cards.add(new SetCardInfo("Kindle", 137, Rarity.COMMON, mage.cards.k.Kindle.class));
        cards.add(new SetCardInfo("Kor Chant", 17, Rarity.UNCOMMON, mage.cards.k.KorChant.class));
        cards.add(new SetCardInfo("Krakilin", 177, Rarity.RARE, mage.cards.k.Krakilin.class));
        cards.add(new SetCardInfo("Lab Rats", 108, Rarity.COMMON, mage.cards.l.LabRats.class));
        cards.add(new SetCardInfo("Legacy's Allure", 56, Rarity.RARE, mage.cards.l.LegacysAllure.class));
        cards.add(new SetCardInfo("Legerdemain", 57, Rarity.UNCOMMON, mage.cards.l.Legerdemain.class));
        cards.add(new SetCardInfo("Lightning Blast", 138, Rarity.COMMON, mage.cards.l.LightningBlast.class));
        cards.add(new SetCardInfo("Living Death", 109, Rarity.MYTHIC, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Lotus Petal", 225, Rarity.UNCOMMON, mage.cards.l.LotusPetal.class));
        cards.add(new SetCardInfo("Lowland Basilisk", 178, Rarity.UNCOMMON, mage.cards.l.LowlandBasilisk.class));
        cards.add(new SetCardInfo("Lowland Giant", 139, Rarity.COMMON, mage.cards.l.LowlandGiant.class));
        cards.add(new SetCardInfo("Mage il-Vec", 140, Rarity.UNCOMMON, mage.cards.m.MageIlVec.class));
        cards.add(new SetCardInfo("Magmasaur", 141, Rarity.RARE, mage.cards.m.Magmasaur.class));
        cards.add(new SetCardInfo("Mana Leak", 58, Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Manabond", 179, Rarity.RARE, mage.cards.m.Manabond.class));
        cards.add(new SetCardInfo("Maniacal Rage", 142, Rarity.COMMON, mage.cards.m.ManiacalRage.class));
        cards.add(new SetCardInfo("Master Decoy", 18, Rarity.COMMON, mage.cards.m.MasterDecoy.class));
        cards.add(new SetCardInfo("Mawcor", 59, Rarity.RARE, mage.cards.m.Mawcor.class));
        cards.add(new SetCardInfo("Maze of Shadows", 238, Rarity.UNCOMMON, mage.cards.m.MazeOfShadows.class));
        cards.add(new SetCardInfo("Meditate", 60, Rarity.RARE, mage.cards.m.Meditate.class));
        cards.add(new SetCardInfo("Merfolk Looter", 61, Rarity.COMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Metallic Sliver", 226, Rarity.COMMON, mage.cards.m.MetallicSliver.class));
        cards.add(new SetCardInfo("Mindless Automaton", 227, Rarity.RARE, mage.cards.m.MindlessAutomaton.class));
        cards.add(new SetCardInfo("Mirri, Cat Warrior", 180, Rarity.RARE, mage.cards.m.MirriCatWarrior.class));
        cards.add(new SetCardInfo("Mnemonic Sliver", 62, Rarity.COMMON, mage.cards.m.MnemonicSliver.class));
        cards.add(new SetCardInfo("Mogg Conscripts", 143, Rarity.COMMON, mage.cards.m.MoggConscripts.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 144, Rarity.COMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mogg Flunkies", 145, Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Mogg Hollows", 239, Rarity.UNCOMMON, mage.cards.m.MoggHollows.class));
        cards.add(new SetCardInfo("Mogg Infestation", 146, Rarity.RARE, mage.cards.m.MoggInfestation.class));
        cards.add(new SetCardInfo("Mogg Maniac", 147, Rarity.UNCOMMON, mage.cards.m.MoggManiac.class));
        cards.add(new SetCardInfo("Mountain", 262, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 263, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mounted Archers", 19, Rarity.COMMON, mage.cards.m.MountedArchers.class));
        cards.add(new SetCardInfo("Mox Diamond", 228, Rarity.MYTHIC, mage.cards.m.MoxDiamond.class));
        cards.add(new SetCardInfo("Mulch", 181, Rarity.COMMON, mage.cards.m.Mulch.class));
        cards.add(new SetCardInfo("Muscle Sliver", 182, Rarity.COMMON, mage.cards.m.MuscleSliver.class));
        cards.add(new SetCardInfo("Necrologia", 110, Rarity.RARE, mage.cards.n.Necrologia.class));
        cards.add(new SetCardInfo("Needle Storm", 183, Rarity.UNCOMMON, mage.cards.n.NeedleStorm.class));
        cards.add(new SetCardInfo("Nomads en-Kor", 20, Rarity.COMMON, mage.cards.n.NomadsEnKor.class));
        cards.add(new SetCardInfo("Oath of Druids", 184, Rarity.MYTHIC, mage.cards.o.OathOfDruids.class));
        cards.add(new SetCardInfo("Ogre Shaman", 148, Rarity.RARE, mage.cards.o.OgreShaman.class));
        cards.add(new SetCardInfo("Orim, Samite Healer", 21, Rarity.RARE, mage.cards.o.OrimSamiteHealer.class));
        cards.add(new SetCardInfo("Overrun", 185, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Pacifism", 22, Rarity.UNCOMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Paladin en-Vec", 23, Rarity.RARE, mage.cards.p.PaladinEnVec.class));
        cards.add(new SetCardInfo("Pandemonium", 149, Rarity.RARE, mage.cards.p.Pandemonium.class));
        cards.add(new SetCardInfo("Patchwork Gnomes", 229, Rarity.COMMON, mage.cards.p.PatchworkGnomes.class));
        cards.add(new SetCardInfo("Pegasus Stampede", 24, Rarity.UNCOMMON, mage.cards.p.PegasusStampede.class));
        cards.add(new SetCardInfo("Phyrexian Hulk", 230, Rarity.UNCOMMON, mage.cards.p.PhyrexianHulk.class));
        cards.add(new SetCardInfo("Pine Barrens", 240, Rarity.UNCOMMON, mage.cards.p.PineBarrens.class));
        cards.add(new SetCardInfo("Plains", 250, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 251, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Provoke", 186, Rarity.COMMON, mage.cards.p.Provoke.class));
        cards.add(new SetCardInfo("Rampant Growth", 187, Rarity.COMMON, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Rathi Dragon", 150, Rarity.RARE, mage.cards.r.RathiDragon.class));
        cards.add(new SetCardInfo("Rats of Rath", 111, Rarity.COMMON, mage.cards.r.RatsOfRath.class));
        cards.add(new SetCardInfo("Reality Anchor", 188, Rarity.COMMON, mage.cards.r.RealityAnchor.class));
        cards.add(new SetCardInfo("Reanimate", 112, Rarity.UNCOMMON, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Recurring Nightmare", 113, Rarity.MYTHIC, mage.cards.r.RecurringNightmare.class));
        cards.add(new SetCardInfo("Recycle", 189, Rarity.RARE, mage.cards.r.Recycle.class));
        cards.add(new SetCardInfo("Renegade Warlord", 151, Rarity.UNCOMMON, mage.cards.r.RenegadeWarlord.class));
        cards.add(new SetCardInfo("Repentance", 25, Rarity.UNCOMMON, mage.cards.r.Repentance.class));
        cards.add(new SetCardInfo("Revenant", 114, Rarity.UNCOMMON, mage.cards.r.Revenant.class));
        cards.add(new SetCardInfo("Rolling Thunder", 152, Rarity.UNCOMMON, mage.cards.r.RollingThunder.class));
        cards.add(new SetCardInfo("Rootbreaker Wurm", 190, Rarity.UNCOMMON, mage.cards.r.RootbreakerWurm.class));
        cards.add(new SetCardInfo("Rootwalla", 191, Rarity.COMMON, mage.cards.r.Rootwalla.class));
        cards.add(new SetCardInfo("Rootwater Depths", 241, Rarity.UNCOMMON, mage.cards.r.RootwaterDepths.class));
        cards.add(new SetCardInfo("Rootwater Hunter", 63, Rarity.UNCOMMON, mage.cards.r.RootwaterHunter.class));
        cards.add(new SetCardInfo("Sabertooth Wyvern", 153, Rarity.UNCOMMON, mage.cards.s.SabertoothWyvern.class));
        cards.add(new SetCardInfo("Salt Flats", 242, Rarity.UNCOMMON, mage.cards.s.SaltFlats.class));
        cards.add(new SetCardInfo("Sandstone Warrior", 154, Rarity.COMMON, mage.cards.s.SandstoneWarrior.class));
        cards.add(new SetCardInfo("Sarcomancy", 115, Rarity.UNCOMMON, mage.cards.s.Sarcomancy.class));
        cards.add(new SetCardInfo("Scabland", 243, Rarity.UNCOMMON, mage.cards.s.Scabland.class));
        cards.add(new SetCardInfo("Screeching Harpy", 116, Rarity.UNCOMMON, mage.cards.s.ScreechingHarpy.class));
        cards.add(new SetCardInfo("Scrivener", 64, Rarity.COMMON, mage.cards.s.Scrivener.class));
        cards.add(new SetCardInfo("Sea Monster", 65, Rarity.COMMON, mage.cards.s.SeaMonster.class));
        cards.add(new SetCardInfo("Searing Touch", 155, Rarity.UNCOMMON, mage.cards.s.SearingTouch.class));
        cards.add(new SetCardInfo("Seething Anger", 156, Rarity.COMMON, mage.cards.s.SeethingAnger.class));
        cards.add(new SetCardInfo("Selenia, Dark Angel", 210, Rarity.RARE, mage.cards.s.SeleniaDarkAngel.class));
        cards.add(new SetCardInfo("Serpent Warrior", 117, Rarity.COMMON, mage.cards.s.SerpentWarrior.class));
        cards.add(new SetCardInfo("Shackles", 26, Rarity.COMMON, mage.cards.s.Shackles.class));
        cards.add(new SetCardInfo("Shadow Rift", 66, Rarity.COMMON, mage.cards.s.ShadowRift.class));
        cards.add(new SetCardInfo("Shadowstorm", 157, Rarity.UNCOMMON, mage.cards.s.Shadowstorm.class));
        cards.add(new SetCardInfo("Shaman en-Kor", 27, Rarity.RARE, mage.cards.s.ShamanEnKor.class));
        cards.add(new SetCardInfo("Shard Phoenix", 158, Rarity.MYTHIC, mage.cards.s.ShardPhoenix.class));
        cards.add(new SetCardInfo("Shatter", 159, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Sift", 67, Rarity.COMMON, mage.cards.s.Sift.class));
        cards.add(new SetCardInfo("Silver Wyvern", 68, Rarity.RARE, mage.cards.s.SilverWyvern.class));
        cards.add(new SetCardInfo("Skyshaper", 231, Rarity.COMMON, mage.cards.s.Skyshaper.class));
        cards.add(new SetCardInfo("Skyshroud Elf", 192, Rarity.COMMON, mage.cards.s.SkyshroudElf.class));
        cards.add(new SetCardInfo("Skyshroud Forest", 244, Rarity.UNCOMMON, mage.cards.s.SkyshroudForest.class));
        cards.add(new SetCardInfo("Skyshroud Troll", 193, Rarity.COMMON, mage.cards.s.SkyshroudTroll.class));
        cards.add(new SetCardInfo("Skyshroud Vampire", 118, Rarity.UNCOMMON, mage.cards.s.SkyshroudVampire.class));
        cards.add(new SetCardInfo("Sliver Queen", 211, Rarity.MYTHIC, mage.cards.s.SliverQueen.class));
        cards.add(new SetCardInfo("Smite", 28, Rarity.COMMON, mage.cards.s.Smite.class));
        cards.add(new SetCardInfo("Soltari Champion", 29, Rarity.UNCOMMON, mage.cards.s.SoltariChampion.class));
        cards.add(new SetCardInfo("Soltari Guerrillas", 212, Rarity.RARE, mage.cards.s.SoltariGuerrillas.class));
        cards.add(new SetCardInfo("Soltari Lancer", 30, Rarity.COMMON, mage.cards.s.SoltariLancer.class));
        cards.add(new SetCardInfo("Soltari Monk", 31, Rarity.UNCOMMON, mage.cards.s.SoltariMonk.class));
        cards.add(new SetCardInfo("Soltari Priest", 32, Rarity.UNCOMMON, mage.cards.s.SoltariPriest.class));
        cards.add(new SetCardInfo("Soltari Trooper", 33, Rarity.COMMON, mage.cards.s.SoltariTrooper.class));
        cards.add(new SetCardInfo("Spell Blast", 69, Rarity.COMMON, mage.cards.s.SpellBlast.class));
        cards.add(new SetCardInfo("Spellshock", 160, Rarity.UNCOMMON, mage.cards.s.Spellshock.class));
        cards.add(new SetCardInfo("Spike Breeder", 194, Rarity.UNCOMMON, mage.cards.s.SpikeBreeder.class));
        cards.add(new SetCardInfo("Spike Colony", 195, Rarity.COMMON, mage.cards.s.SpikeColony.class));
        cards.add(new SetCardInfo("Spike Feeder", 196, Rarity.UNCOMMON, mage.cards.s.SpikeFeeder.class));
        cards.add(new SetCardInfo("Spike Hatcher", 197, Rarity.RARE, mage.cards.s.SpikeHatcher.class));
        cards.add(new SetCardInfo("Spinal Graft", 119, Rarity.COMMON, mage.cards.s.SpinalGraft.class));
        cards.add(new SetCardInfo("Spined Sliver", 213, Rarity.UNCOMMON, mage.cards.s.SpinedSliver.class));
        cards.add(new SetCardInfo("Spined Wurm", 198, Rarity.COMMON, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Spirit en-Kor", 34, Rarity.COMMON, mage.cards.s.SpiritEnKor.class));
        cards.add(new SetCardInfo("Spirit Mirror", 35, Rarity.RARE, mage.cards.s.SpiritMirror.class));
        cards.add(new SetCardInfo("Spitting Hydra", 161, Rarity.RARE, mage.cards.s.SpittingHydra.class));
        cards.add(new SetCardInfo("Stalking Stones", 245, Rarity.UNCOMMON, mage.cards.s.StalkingStones.class));
        cards.add(new SetCardInfo("Standing Troops", 36, Rarity.COMMON, mage.cards.s.StandingTroops.class));
        cards.add(new SetCardInfo("Starke of Rath", 162, Rarity.MYTHIC, mage.cards.s.StarkeOfRath.class));
        cards.add(new SetCardInfo("Staunch Defenders", 37, Rarity.COMMON, mage.cards.s.StaunchDefenders.class));
        cards.add(new SetCardInfo("Stronghold Assassin", 120, Rarity.RARE, mage.cards.s.StrongholdAssassin.class));
        cards.add(new SetCardInfo("Stun", 163, Rarity.COMMON, mage.cards.s.Stun.class));
        cards.add(new SetCardInfo("Survival of the Fittest", 199, Rarity.MYTHIC, mage.cards.s.SurvivalOfTheFittest.class));
        cards.add(new SetCardInfo("Swamp", 258, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 259, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Telethopter", 232, Rarity.COMMON, mage.cards.t.Telethopter.class));
        cards.add(new SetCardInfo("Thalakos Drifters", 70, Rarity.UNCOMMON, mage.cards.t.ThalakosDrifters.class));
        cards.add(new SetCardInfo("Thalakos Lowlands", 246, Rarity.UNCOMMON, mage.cards.t.ThalakosLowlands.class));
        cards.add(new SetCardInfo("Thalakos Scout", 71, Rarity.COMMON, mage.cards.t.ThalakosScout.class));
        cards.add(new SetCardInfo("Thalakos Seer", 72, Rarity.COMMON, mage.cards.t.ThalakosSeer.class));
        cards.add(new SetCardInfo("Thopter Squadron", 233, Rarity.RARE, mage.cards.t.ThopterSquadron.class));
        cards.add(new SetCardInfo("Thrull Surgeon", 121, Rarity.COMMON, mage.cards.t.ThrullSurgeon.class));
        cards.add(new SetCardInfo("Time Ebb", 73, Rarity.COMMON, mage.cards.t.TimeEbb.class));
        cards.add(new SetCardInfo("Time Warp", 74, Rarity.MYTHIC, mage.cards.t.TimeWarp.class));
        cards.add(new SetCardInfo("Tradewind Rider", 75, Rarity.RARE, mage.cards.t.TradewindRider.class));
        cards.add(new SetCardInfo("Trained Armodon", 200, Rarity.COMMON, mage.cards.t.TrainedArmodon.class));
        cards.add(new SetCardInfo("Tranquility", 201, Rarity.UNCOMMON, mage.cards.t.Tranquility.class));
        cards.add(new SetCardInfo("Twitch", 76, Rarity.COMMON, mage.cards.t.Twitch.class));
        cards.add(new SetCardInfo("Vampire Hounds", 122, Rarity.COMMON, mage.cards.v.VampireHounds.class));
        cards.add(new SetCardInfo("Vec Townships", 247, Rarity.UNCOMMON, mage.cards.v.VecTownships.class));
        cards.add(new SetCardInfo("Verdant Force", 202, Rarity.RARE, mage.cards.v.VerdantForce.class));
        cards.add(new SetCardInfo("Verdant Touch", 203, Rarity.UNCOMMON, mage.cards.v.VerdantTouch.class));
        cards.add(new SetCardInfo("Verdigris", 204, Rarity.COMMON, mage.cards.v.Verdigris.class));
        cards.add(new SetCardInfo("Vhati il-Dal", 214, Rarity.RARE, mage.cards.v.VhatiIlDal.class));
        cards.add(new SetCardInfo("Victual Sliver", 215, Rarity.UNCOMMON, mage.cards.v.VictualSliver.class));
        cards.add(new SetCardInfo("Volrath's Curse", 77, Rarity.UNCOMMON, mage.cards.v.VolrathsCurse.class));
        cards.add(new SetCardInfo("Volrath's Laboratory", 234, Rarity.RARE, mage.cards.v.VolrathsLaboratory.class));
        cards.add(new SetCardInfo("Volrath's Stronghold", 248, Rarity.MYTHIC, mage.cards.v.VolrathsStronghold.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 205, Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("Wall of Diffusion", 164, Rarity.COMMON, mage.cards.w.WallOfDiffusion.class));
        cards.add(new SetCardInfo("Wall of Essence", 38, Rarity.UNCOMMON, mage.cards.w.WallOfEssence.class));
        cards.add(new SetCardInfo("Wall of Souls", 123, Rarity.UNCOMMON, mage.cards.w.WallOfSouls.class));
        cards.add(new SetCardInfo("Warrior en-Kor", 39, Rarity.UNCOMMON, mage.cards.w.WarriorEnKor.class));
        cards.add(new SetCardInfo("Wasteland", 249, Rarity.RARE, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Wayward Soul", 78, Rarity.COMMON, mage.cards.w.WaywardSoul.class));
        cards.add(new SetCardInfo("Whispers of the Muse", 79, Rarity.COMMON, mage.cards.w.WhispersOfTheMuse.class));
        cards.add(new SetCardInfo("Wind Dancer", 80, Rarity.UNCOMMON, mage.cards.w.WindDancer.class));
        cards.add(new SetCardInfo("Wind Drake", 81, Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Winds of Rath", 40, Rarity.RARE, mage.cards.w.WindsOfRath.class));
        cards.add(new SetCardInfo("Winged Sliver", 82, Rarity.COMMON, mage.cards.w.WingedSliver.class));
        cards.add(new SetCardInfo("Wood Sage", 216, Rarity.RARE, mage.cards.w.WoodSage.class));
        cards.add(new SetCardInfo("Youthful Knight", 41, Rarity.COMMON, mage.cards.y.YouthfulKnight.class));
    }
}
