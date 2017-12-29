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
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author Styxo
 */
public class StarWars extends ExpansionSet {

    private static final StarWars instance = new StarWars();

    public static StarWars getInstance() {
        return instance;
    }

    private StarWars() {
        super("Star Wars", "SWS", ExpansionSet.buildDate(2016, 12, 12), SetType.CUSTOM_SET);
        this.blockName = "Star Wars";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("A-Wing", 96, Rarity.UNCOMMON, mage.cards.a.AWing.class));
        cards.add(new SetCardInfo("AAT-1", 160, Rarity.UNCOMMON, mage.cards.a.AAT1.class));
        cards.add(new SetCardInfo("Acklay of the Arena", 161, Rarity.RARE, mage.cards.a.AcklayOfTheArena.class));
        cards.add(new SetCardInfo("Acquire Target", 65, Rarity.COMMON, mage.cards.a.AcquireTarget.class));
        cards.add(new SetCardInfo("Admiral Ackbar", 35, Rarity.RARE, mage.cards.a.AdmiralAckbar.class));
        cards.add(new SetCardInfo("Adroit Hateflayer", 162, Rarity.COMMON, mage.cards.a.AdroitHateflayer.class));
        cards.add(new SetCardInfo("Anakin Skywalker", 163, Rarity.MYTHIC, mage.cards.a.AnakinSkywalker.class));
        cards.add(new SetCardInfo("Ancient Holocron", 230, Rarity.UNCOMMON, mage.cards.a.AncientHolocron.class));
        cards.add(new SetCardInfo("Aqualish Bounty Hunter", 66, Rarity.COMMON, mage.cards.a.AqualishBountyHunter.class));
        cards.add(new SetCardInfo("Armed Protocol Droid", 36, Rarity.UNCOMMON, mage.cards.a.ArmedProtocolDroid.class));
        cards.add(new SetCardInfo("Arrest", 2, Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Asajj Ventress", 164, Rarity.RARE, mage.cards.a.AsajjVentress.class));
        cards.add(new SetCardInfo("AT-ST", 128, Rarity.RARE, mage.cards.a.ATST.class));
        cards.add(new SetCardInfo("Aurra Sing, Bane of Jedi", 165, Rarity.MYTHIC, mage.cards.a.AurraSingBaneOfJedi.class));
        cards.add(new SetCardInfo("Bantha Herd", 3, Rarity.RARE, mage.cards.b.BanthaHerd.class));
        cards.add(new SetCardInfo("Bathe in Bacta", 129, Rarity.COMMON, mage.cards.b.BatheInBacta.class));
        cards.add(new SetCardInfo("Battle Tactics", 5, Rarity.UNCOMMON, mage.cards.b.BattleTactics.class));
        cards.add(new SetCardInfo("Bib Fortuna", 68, Rarity.RARE, mage.cards.b.BibFortuna.class));
        cards.add(new SetCardInfo("Black Market Dealer", 69, Rarity.UNCOMMON, mage.cards.b.BlackMarketDealer.class));
        cards.add(new SetCardInfo("Blind Worship", 166, Rarity.RARE, mage.cards.b.BlindWorship.class));
        cards.add(new SetCardInfo("Boba Fett", 167, Rarity.MYTHIC, mage.cards.b.BobaFett.class));
        cards.add(new SetCardInfo("Bossk", 131, Rarity.RARE, mage.cards.b.Bossk.class));
        cards.add(new SetCardInfo("Bounty Collector", 132, Rarity.UNCOMMON, mage.cards.b.BountyCollector.class));
        cards.add(new SetCardInfo("Bounty Sniper", 98, Rarity.UNCOMMON, mage.cards.b.BountySniper.class));
        cards.add(new SetCardInfo("Bounty Spotter", 70, Rarity.UNCOMMON, mage.cards.b.BountySpotter.class));
        cards.add(new SetCardInfo("Bull Rancor", 168, Rarity.RARE, mage.cards.b.BullRancor.class));
        cards.add(new SetCardInfo("C-3PO and R2D2", 169, Rarity.RARE, mage.cards.c.C3POAndR2D2.class));
        cards.add(new SetCardInfo("Cantina Band", 6, Rarity.COMMON, mage.cards.c.CantinaBand.class));
        cards.add(new SetCardInfo("Capture", 99, Rarity.COMMON, mage.cards.c.Capture.class));
        cards.add(new SetCardInfo("Carbonite Chamber", 170, Rarity.UNCOMMON, mage.cards.c.CarboniteChamber.class));
        cards.add(new SetCardInfo("Chewbacca", 171, Rarity.RARE, mage.cards.c.Chewbacca.class));
        cards.add(new SetCardInfo("Chief Chirpa", 172, Rarity.RARE, mage.cards.c.ChiefChirpa.class));
        cards.add(new SetCardInfo("Cloaking Device", 38, Rarity.COMMON, mage.cards.c.CloakingDevice.class));
        cards.add(new SetCardInfo("Commander Cody", 173, Rarity.RARE, mage.cards.c.CommanderCody.class));
        cards.add(new SetCardInfo("Condemn", 7, Rarity.UNCOMMON, mage.cards.c.Condemn.class));
        cards.add(new SetCardInfo("Corellian Corvette", 133, Rarity.UNCOMMON, mage.cards.c.CorellianCorvette.class));
        cards.add(new SetCardInfo("Crossfire", 100, Rarity.COMMON, mage.cards.c.Crossfire.class));
        cards.add(new SetCardInfo("Cruelty of the Sith", 174, Rarity.UNCOMMON, mage.cards.c.CrueltyOfTheSith.class));
        cards.add(new SetCardInfo("Cunning Abduction", 175, Rarity.RARE, mage.cards.c.CunningAbduction.class));
        cards.add(new SetCardInfo("Dagobah Maw Slug", 176, Rarity.COMMON, mage.cards.d.DagobahMawSlug.class));
        cards.add(new SetCardInfo("Dark Apprenticeship", 101, Rarity.UNCOMMON, mage.cards.d.DarkApprenticeship.class));
        cards.add(new SetCardInfo("Dark Decision", 177, Rarity.COMMON, mage.cards.d.DarkDecision.class));
        cards.add(new SetCardInfo("Dark Trooper", 231, Rarity.UNCOMMON, mage.cards.d.DarkTrooper.class));
        cards.add(new SetCardInfo("Darth Maul", 178, Rarity.RARE, mage.cards.d.DarthMaul.class));
        cards.add(new SetCardInfo("Darth Sidious, Sith Lord", 179, Rarity.MYTHIC, mage.cards.d.DarthSidiousSithLord.class));
        cards.add(new SetCardInfo("Darth Tyranus", 180, Rarity.MYTHIC, mage.cards.d.DarthTyranus.class));
        cards.add(new SetCardInfo("Darth Vader", 140, Rarity.MYTHIC, mage.cards.d.DarthVader.class));
        cards.add(new SetCardInfo("Death Trooper", 71, Rarity.UNCOMMON, mage.cards.d.DeathTrooper.class));
        cards.add(new SetCardInfo("Deploy The Troops", 8, Rarity.UNCOMMON, mage.cards.d.DeployTheTroops.class));
        cards.add(new SetCardInfo("Doom Blade", 72, Rarity.UNCOMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Droid Commando", 73, Rarity.COMMON, mage.cards.d.DroidCommando.class));
        cards.add(new SetCardInfo("Droid Factory", 239, Rarity.COMMON, mage.cards.d.DroidFactory.class));
        cards.add(new SetCardInfo("Droid Foundry", 240, Rarity.UNCOMMON, mage.cards.d.DroidFoundry.class));
        cards.add(new SetCardInfo("Droideka", 9, Rarity.UNCOMMON, mage.cards.d.Droideka.class));
        cards.add(new SetCardInfo("Drone Holocron", 232, Rarity.COMMON, mage.cards.d.DroneHolocron.class));
        cards.add(new SetCardInfo("Echo Base Commando", 181, Rarity.RARE, mage.cards.e.EchoBaseCommando.class));
        cards.add(new SetCardInfo("EMP Blast", 10, Rarity.UNCOMMON, mage.cards.e.EMPBlast.class));
        cards.add(new SetCardInfo("Escape Pod", 11, Rarity.COMMON, mage.cards.e.EscapePod.class));
        cards.add(new SetCardInfo("Ewok Ambush", 134, Rarity.COMMON, mage.cards.e.EwokAmbush.class));
        cards.add(new SetCardInfo("Ewok Firedancers", 135, Rarity.COMMON, mage.cards.e.EwokFiredancers.class));
        cards.add(new SetCardInfo("Ewok Village", 241, Rarity.UNCOMMON, mage.cards.e.EwokVillage.class));
        cards.add(new SetCardInfo("Exogorth", 136, Rarity.RARE, mage.cards.e.Exogorth.class));
        cards.add(new SetCardInfo("Ferocity of the Underworld", 182, Rarity.UNCOMMON, mage.cards.f.FerocityOfTheUnderworld.class));
        cards.add(new SetCardInfo("Flames of Remembrance", 102, Rarity.RARE, mage.cards.f.FlamesOfRemembrance.class));
        cards.add(new SetCardInfo("Force Choke", 183, Rarity.COMMON, mage.cards.f.ForceChoke.class));
        cards.add(new SetCardInfo("Force Denial", 39, Rarity.COMMON, mage.cards.f.ForceDenial.class));
        cards.add(new SetCardInfo("Force Drain", 74, Rarity.COMMON, mage.cards.f.ForceDrain.class));
        cards.add(new SetCardInfo("Force Healing", 12, Rarity.COMMON, mage.cards.f.ForceHealing.class));
        cards.add(new SetCardInfo("Force Lightning", 103, Rarity.RARE, mage.cards.f.ForceLightning.class));
        cards.add(new SetCardInfo("Force Mastery", 184, Rarity.RARE, mage.cards.f.ForceMastery.class));
        cards.add(new SetCardInfo("Force Pull", 137, Rarity.COMMON, mage.cards.f.ForcePull.class));
        cards.add(new SetCardInfo("Force Push", 40, Rarity.UNCOMMON, mage.cards.f.ForcePush.class));
        cards.add(new SetCardInfo("Force Reflex", 13, Rarity.COMMON, mage.cards.f.ForceReflex.class));
        cards.add(new SetCardInfo("Force Scream", 104, Rarity.UNCOMMON, mage.cards.f.ForceScream.class));
        cards.add(new SetCardInfo("Force Spark", 105, Rarity.COMMON, mage.cards.f.ForceSpark.class));
        cards.add(new SetCardInfo("Forest", 268, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 270, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 271, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Fulfill Contract", 224, Rarity.COMMON, mage.cards.f.FulfillContract.class));
        cards.add(new SetCardInfo("Gamorrean Prison Guard", 106, Rarity.UNCOMMON, mage.cards.g.GamorreanPrisonGuard.class));
        cards.add(new SetCardInfo("General Grievous", 185, Rarity.MYTHIC, mage.cards.g.GeneralGrievous.class));
        cards.add(new SetCardInfo("Gifted Initiate", 14, Rarity.COMMON, mage.cards.g.GiftedInitiate.class));
        cards.add(new SetCardInfo("Grand Moff Tarkin", 75, Rarity.RARE, mage.cards.g.GrandMoffTarkin.class));
        cards.add(new SetCardInfo("Greater Krayt Dragon", 186, Rarity.MYTHIC, mage.cards.g.GreaterKraytDragon.class));
        cards.add(new SetCardInfo("Greedo", 187, Rarity.RARE, mage.cards.g.Greedo.class));
        cards.add(new SetCardInfo("Gundark", 107, Rarity.UNCOMMON, mage.cards.g.Gundark.class));
        cards.add(new SetCardInfo("Gungan Captain", 41, Rarity.COMMON, mage.cards.g.GunganCaptain.class));
        cards.add(new SetCardInfo("Han Solo", 108, Rarity.RARE, mage.cards.h.HanSolo.class));
        cards.add(new SetCardInfo("Hazard Trooper", 76, Rarity.UNCOMMON, mage.cards.h.HazardTrooper.class));
        cards.add(new SetCardInfo("Head Hunting", 77, Rarity.COMMON, mage.cards.h.HeadHunting.class));
        cards.add(new SetCardInfo("Heavy Trooper", 78, Rarity.COMMON, mage.cards.h.HeavyTrooper.class));
        cards.add(new SetCardInfo("Hot Pursuit", 188, Rarity.RARE, mage.cards.h.HotPursuit.class));
        cards.add(new SetCardInfo("Hungry Dragonsnake", 138, Rarity.COMMON, mage.cards.h.HungryDragonsnake.class));
        cards.add(new SetCardInfo("Hunt to Extinction", 189, Rarity.RARE, mage.cards.h.HuntToExtinction.class));
        cards.add(new SetCardInfo("Hutt Crime Lord", 139, Rarity.UNCOMMON, mage.cards.h.HuttCrimeLord.class));
        cards.add(new SetCardInfo("Hutt Palace", 242, Rarity.UNCOMMON, mage.cards.h.HuttPalace.class));
        cards.add(new SetCardInfo("IG-88B", 79, Rarity.RARE, mage.cards.i.IG88B.class));
        cards.add(new SetCardInfo("Images of the Past", 190, Rarity.COMMON, mage.cards.i.ImagesOfThePast.class));
        cards.add(new SetCardInfo("Imperial Gunner", 109, Rarity.COMMON, mage.cards.i.ImperialGunner.class));
        cards.add(new SetCardInfo("Impulsive Wager", 110, Rarity.COMMON, mage.cards.i.ImpulsiveWager.class));
        cards.add(new SetCardInfo("Insatiable Rakghoul", 80, Rarity.COMMON, mage.cards.i.InsatiableRakghoul.class));
        cards.add(new SetCardInfo("Interrogation", 81, Rarity.COMMON, mage.cards.i.Interrogation.class));
        cards.add(new SetCardInfo("Ion Cannon", 15, Rarity.COMMON, mage.cards.i.IonCannon.class));
        cards.add(new SetCardInfo("Iron Fist of the Empire", 191, Rarity.RARE, mage.cards.i.IronFistOfTheEmpire.class));
        cards.add(new SetCardInfo("Island", 256, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 258, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 259, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ithorian Initiate", 140, Rarity.COMMON, mage.cards.i.IthorianInitiate.class));
        cards.add(new SetCardInfo("Jabba the Hutt", 192, Rarity.RARE, mage.cards.j.JabbaTheHutt.class));
        cards.add(new SetCardInfo("Jango Fett", 111, Rarity.RARE, mage.cards.j.JangoFett.class));
        cards.add(new SetCardInfo("Jar Jar Binks", 42, Rarity.RARE, mage.cards.j.JarJarBinks.class));
        cards.add(new SetCardInfo("Jar'Kai Battle Stance", 112, Rarity.COMMON, mage.cards.j.JarKaiBattleStance.class));
        cards.add(new SetCardInfo("Jedi Battle Healer", 16, Rarity.UNCOMMON, mage.cards.j.JediBattleHealer.class));
        cards.add(new SetCardInfo("Jedi Battle Mage", 43, Rarity.UNCOMMON, mage.cards.j.JediBattleMage.class));
        cards.add(new SetCardInfo("Jedi Battle Sage", 141, Rarity.UNCOMMON, mage.cards.j.JediBattleSage.class));
        cards.add(new SetCardInfo("Jedi Enclave", 243, Rarity.COMMON, mage.cards.j.JediEnclave.class));
        cards.add(new SetCardInfo("Jedi Holocron", 233, Rarity.COMMON, mage.cards.j.JediHolocron.class));
        cards.add(new SetCardInfo("Jedi Inquirer", 17, Rarity.COMMON, mage.cards.j.JediInquirer.class));
        cards.add(new SetCardInfo("Jedi Instructor", 18, Rarity.COMMON, mage.cards.j.JediInstructor.class));
        cards.add(new SetCardInfo("Jedi Knight", 193, Rarity.UNCOMMON, mage.cards.j.JediKnight.class));
        cards.add(new SetCardInfo("Jedi Mind Trick", 44, Rarity.RARE, mage.cards.j.JediMindTrick.class));
        cards.add(new SetCardInfo("Jedi Sentinel", 194, Rarity.COMMON, mage.cards.j.JediSentinel.class));
        cards.add(new SetCardInfo("Jedi Starfighter", 19, Rarity.UNCOMMON, mage.cards.j.JediStarfighter.class));
        cards.add(new SetCardInfo("Jedi Temple", 244, Rarity.UNCOMMON, mage.cards.j.JediTemple.class));
        cards.add(new SetCardInfo("Jedi Training", 45, Rarity.UNCOMMON, mage.cards.j.JediTraining.class));
        cards.add(new SetCardInfo("Jump Trooper", 46, Rarity.UNCOMMON, mage.cards.j.JumpTrooper.class));
        cards.add(new SetCardInfo("Jungle Village", 245, Rarity.UNCOMMON, mage.cards.j.JungleVillage.class));
        cards.add(new SetCardInfo("Kamino Cloning Facility", 246, Rarity.RARE, mage.cards.k.KaminoCloningFacility.class));
        cards.add(new SetCardInfo("Ki-Adi-Mundi", 142, Rarity.RARE, mage.cards.k.KiAdiMundi.class));
        cards.add(new SetCardInfo("LAAT Gunship", 20, Rarity.UNCOMMON, mage.cards.l.LAATGunship.class));
        cards.add(new SetCardInfo("Lando Calrissian", 21, Rarity.RARE, mage.cards.l.LandoCalrissian.class));
        cards.add(new SetCardInfo("Legacy of the Beloved", 143, Rarity.RARE, mage.cards.l.LegacyOfTheBeloved.class));
        cards.add(new SetCardInfo("Lightning Bolt", 113, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lightsaber", 234, Rarity.COMMON, mage.cards.l.Lightsaber.class));
        cards.add(new SetCardInfo("Loyal Tauntaun", 22, Rarity.COMMON, mage.cards.l.LoyalTauntaun.class));
        cards.add(new SetCardInfo("Luke Skywalker", 195, Rarity.MYTHIC, mage.cards.l.LukeSkywalker.class));
        cards.add(new SetCardInfo("Mace Windu", 47, Rarity.RARE, mage.cards.m.MaceWindu.class));
        cards.add(new SetCardInfo("Maintenance Droid", 196, Rarity.COMMON, mage.cards.m.MaintenanceDroid.class));
        cards.add(new SetCardInfo("Maintenance Hangar", 23, Rarity.RARE, mage.cards.m.MaintenanceHangar.class));
        cards.add(new SetCardInfo("Mantellian Savrip", 144, Rarity.UNCOMMON, mage.cards.m.MantellianSavrip.class));
        cards.add(new SetCardInfo("March of the Droids", 197, Rarity.RARE, mage.cards.m.MarchOfTheDroids.class));
        cards.add(new SetCardInfo("Massiff Swarm", 145, Rarity.COMMON, mage.cards.m.MassiffSwarm.class));
        cards.add(new SetCardInfo("Might of the Wild", 198, Rarity.UNCOMMON, mage.cards.m.MightOfTheWild.class));
        cards.add(new SetCardInfo("Millennium Falcon", 146, Rarity.RARE, mage.cards.m.MillenniumFalcon.class));
        cards.add(new SetCardInfo("Miraculous Recovery", 24, Rarity.UNCOMMON, mage.cards.m.MiraculousRecovery.class));
        cards.add(new SetCardInfo("Moisture Farm", 247, Rarity.UNCOMMON, mage.cards.m.MoistureFarm.class));
        cards.add(new SetCardInfo("Mon Calamari Cruiser", 48, Rarity.UNCOMMON, mage.cards.m.MonCalamariCruiser.class));
        cards.add(new SetCardInfo("Mon Calamari Initiate", 49, Rarity.COMMON, mage.cards.m.MonCalamariInitiate.class));
        cards.add(new SetCardInfo("Mountain", 264, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 266, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 267, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("N-1 Starfighter", 225, Rarity.COMMON, mage.cards.n.N1Starfighter.class));
        cards.add(new SetCardInfo("Nebulon-B Frigate", 25, Rarity.COMMON, mage.cards.n.NebulonBFrigate.class));
        cards.add(new SetCardInfo("Neophyte Hateflayer", 82, Rarity.COMMON, mage.cards.n.NeophyteHateflayer.class));
        cards.add(new SetCardInfo("Nerf Herder", 147, Rarity.UNCOMMON, mage.cards.n.NerfHerder.class));
        cards.add(new SetCardInfo("Nexu Stalker", 148, Rarity.UNCOMMON, mage.cards.n.NexuStalker.class));
        cards.add(new SetCardInfo("Nightspider", 149, Rarity.COMMON, mage.cards.n.Nightspider.class));
        cards.add(new SetCardInfo("No Contest", 150, Rarity.COMMON, mage.cards.n.NoContest.class));
        cards.add(new SetCardInfo("Novice Bounty Hunter", 114, Rarity.COMMON, mage.cards.n.NoviceBountyHunter.class));
        cards.add(new SetCardInfo("Nute Gunray", 199, Rarity.RARE, mage.cards.n.NuteGunray.class));
        cards.add(new SetCardInfo("Obi-Wan Kenobi", 200, Rarity.MYTHIC, mage.cards.o.ObiWanKenobi.class));
        cards.add(new SetCardInfo("Open Season", 83, Rarity.UNCOMMON, mage.cards.o.OpenSeason.class));
        cards.add(new SetCardInfo("Orbital Bombardment", 26, Rarity.RARE, mage.cards.o.OrbitalBombardment.class));
        cards.add(new SetCardInfo("Order 66", 84, Rarity.RARE, mage.cards.o.Order66.class));
        cards.add(new SetCardInfo("Ortolan Keyboardist", 50, Rarity.COMMON, mage.cards.o.OrtolanKeyboardist.class));
        cards.add(new SetCardInfo("Outer Rim Slaver", 201, Rarity.COMMON, mage.cards.o.OuterRimSlaver.class));
        cards.add(new SetCardInfo("Outlaw Holocron", 235, Rarity.COMMON, mage.cards.o.OutlawHolocron.class));
        cards.add(new SetCardInfo("Personal Energy Shield", 51, Rarity.COMMON, mage.cards.p.PersonalEnergyShield.class));
        cards.add(new SetCardInfo("Plains", 252, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 254, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 255, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plo Koon", 27, Rarity.RARE, mage.cards.p.PloKoon.class));
        cards.add(new SetCardInfo("Precipice of Mortis", 202, Rarity.RARE, mage.cards.p.PrecipiceOfMortis.class));
        cards.add(new SetCardInfo("Predator's Strike", 151, Rarity.COMMON, mage.cards.p.PredatorsStrike.class));
        cards.add(new SetCardInfo("Preordain", 34, Rarity.UNCOMMON, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Primal Instinct", 203, Rarity.COMMON, mage.cards.p.PrimalInstinct.class));
        cards.add(new SetCardInfo("Princess Leia", 204, Rarity.RARE, mage.cards.p.PrincessLeia.class));
        cards.add(new SetCardInfo("Probe Droid", 52, Rarity.COMMON, mage.cards.p.ProbeDroid.class));
        cards.add(new SetCardInfo("Qui-Gon Jinn", 205, Rarity.RARE, mage.cards.q.QuiGonJinn.class));
        cards.add(new SetCardInfo("Raging Reek", 115, Rarity.COMMON, mage.cards.r.RagingReek.class));
        cards.add(new SetCardInfo("Rallying Fire", 116, Rarity.COMMON, mage.cards.r.RallyingFire.class));
        cards.add(new SetCardInfo("Ravenous Wampa", 226, Rarity.UNCOMMON, mage.cards.r.RavenousWampa.class));
        cards.add(new SetCardInfo("Regression", 152, Rarity.UNCOMMON, mage.cards.r.Regression.class));
        cards.add(new SetCardInfo("Republic Frigate", 53, Rarity.COMMON, mage.cards.r.RepublicFrigate.class));
        cards.add(new SetCardInfo("Repurpose", 85, Rarity.COMMON, mage.cards.r.Repurpose.class));
        cards.add(new SetCardInfo("Revenge", 117, Rarity.COMMON, mage.cards.r.Revenge.class));
        cards.add(new SetCardInfo("Riding Ronto", 28, Rarity.UNCOMMON, mage.cards.r.RidingRonto.class));
        cards.add(new SetCardInfo("Rocket Trooper", 118, Rarity.RARE, mage.cards.r.RocketTrooper.class));
        cards.add(new SetCardInfo("Rogue's Passage", 248, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rule of two", 86, Rarity.UNCOMMON, mage.cards.r.RuleOfTwo.class));
        cards.add(new SetCardInfo("Rumination", 54, Rarity.COMMON, mage.cards.r.Rumination.class));
        cards.add(new SetCardInfo("Rumor Monger", 206, Rarity.UNCOMMON, mage.cards.r.RumorMonger.class));
        cards.add(new SetCardInfo("Sabacc Game", 55, Rarity.UNCOMMON, mage.cards.s.SabaccGame.class));
        cards.add(new SetCardInfo("Salvage Squad", 207, Rarity.COMMON, mage.cards.s.SalvageSquad.class));
        cards.add(new SetCardInfo("Sand Trooper", 29, Rarity.COMMON, mage.cards.s.SandTrooper.class));
        cards.add(new SetCardInfo("Sarlacc Pit", 208, Rarity.RARE, mage.cards.s.SarlaccPit.class));
        cards.add(new SetCardInfo("Scout the Perimeter", 153, Rarity.COMMON, mage.cards.s.ScoutThePerimeter.class));
        cards.add(new SetCardInfo("Scout Trooper", 154, Rarity.COMMON, mage.cards.s.ScoutTrooper.class));
        cards.add(new SetCardInfo("Security Droid", 30, Rarity.COMMON, mage.cards.s.SecurityDroid.class));
        cards.add(new SetCardInfo("Senator Bail Organa", 209, Rarity.UNCOMMON, mage.cards.s.SenatorBailOrgana.class));
        cards.add(new SetCardInfo("Senator Lott Dod", 210, Rarity.UNCOMMON, mage.cards.s.SenatorLottDod.class));
        cards.add(new SetCardInfo("Senator Onaconda Farr", 211, Rarity.UNCOMMON, mage.cards.s.SenatorOnacondaFarr.class));
        cards.add(new SetCardInfo("Senator Padmé Amidala", 212, Rarity.UNCOMMON, mage.cards.s.SenatorPadmeAmidala.class));
        cards.add(new SetCardInfo("Senator Passel Argente", 213, Rarity.UNCOMMON, mage.cards.s.SenatorPasselArgente.class));
        cards.add(new SetCardInfo("Shaak Herd", 155, Rarity.COMMON, mage.cards.s.ShaakHerd.class));
        cards.add(new SetCardInfo("Shadow Trooper", 56, Rarity.COMMON, mage.cards.s.ShadowTrooper.class));
        cards.add(new SetCardInfo("Shock Trooper", 119, Rarity.UNCOMMON, mage.cards.s.ShockTrooper.class));
        cards.add(new SetCardInfo("Show of Dominance", 156, Rarity.UNCOMMON, mage.cards.s.ShowOfDominance.class));
        cards.add(new SetCardInfo("Sith Assassin", 87, Rarity.UNCOMMON, mage.cards.s.SithAssassin.class));
        cards.add(new SetCardInfo("Sith Citadel", 249, Rarity.UNCOMMON, mage.cards.s.SithCitadel.class));
        cards.add(new SetCardInfo("Sith Evoker", 88, Rarity.COMMON, mage.cards.s.SithEvoker.class));
        cards.add(new SetCardInfo("Sith Holocron", 236, Rarity.COMMON, mage.cards.s.SithHolocron.class));
        cards.add(new SetCardInfo("Sith Inquisitor", 89, Rarity.COMMON, mage.cards.s.SithInquisitor.class));
        cards.add(new SetCardInfo("Sith Lord", 90, Rarity.RARE, mage.cards.s.SithLord.class));
        cards.add(new SetCardInfo("Sith Magic", 214, Rarity.RARE, mage.cards.s.SithMagic.class));
        cards.add(new SetCardInfo("Sith Manipulator", 57, Rarity.UNCOMMON, mage.cards.s.SithManipulator.class));
        cards.add(new SetCardInfo("Sith Marauder", 120, Rarity.UNCOMMON, mage.cards.s.SithMarauder.class));
        cards.add(new SetCardInfo("Sith Mindseer", 215, Rarity.UNCOMMON, mage.cards.s.SithMindseer.class));
        cards.add(new SetCardInfo("Sith Ravager", 121, Rarity.COMMON, mage.cards.s.SithRavager.class));
        cards.add(new SetCardInfo("Sith Ruins", 250, Rarity.COMMON, mage.cards.s.SithRuins.class));
        cards.add(new SetCardInfo("Sith Sorcerer", 58, Rarity.COMMON, mage.cards.s.SithSorcerer.class));
        cards.add(new SetCardInfo("Sith Thoughtseeker", 91, Rarity.COMMON, mage.cards.s.SithThoughtseeker.class));
        cards.add(new SetCardInfo("Slave I", 216, Rarity.RARE, mage.cards.s.SlaveI.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 122, Rarity.UNCOMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Snow Trooper", 31, Rarity.UNCOMMON, mage.cards.s.SnowTrooper.class));
        cards.add(new SetCardInfo("Speeder Trooper", 123, Rarity.COMMON, mage.cards.s.SpeederTrooper.class));
        cards.add(new SetCardInfo("Star Destroyer", 217, Rarity.RARE, mage.cards.s.StarDestroyer.class));
        cards.add(new SetCardInfo("Strike Team Commando", 227, Rarity.COMMON, mage.cards.s.StrikeTeamCommando.class));
        cards.add(new SetCardInfo("Super Battle Droid", 59, Rarity.COMMON, mage.cards.s.SuperBattleDroid.class));
        cards.add(new SetCardInfo("Surprise Maneuver", 60, Rarity.COMMON, mage.cards.s.SurpriseManeuver.class));
        cards.add(new SetCardInfo("Swamp", 260, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 263, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swarm the Skies", 92, Rarity.COMMON, mage.cards.s.SwarmTheSkies.class));
        cards.add(new SetCardInfo("Syndicate Enforcer", 124, Rarity.COMMON, mage.cards.s.SyndicateEnforcerSWS.class));
        cards.add(new SetCardInfo("Tank Droid", 218, Rarity.RARE, mage.cards.t.TankDroid.class));
        cards.add(new SetCardInfo("Terentatek Cub", 125, Rarity.COMMON, mage.cards.t.TerentatekCub.class));
        cards.add(new SetCardInfo("The Battle of Endor", 130, Rarity.MYTHIC, mage.cards.t.TheBattleOfEndor.class));
        cards.add(new SetCardInfo("The Battle of Geonosis", 97, Rarity.MYTHIC, mage.cards.t.TheBattleOfGeonosis.class));
        cards.add(new SetCardInfo("The Battle of Hoth", 4, Rarity.MYTHIC, mage.cards.t.TheBattleOfHoth.class));
        cards.add(new SetCardInfo("The Battle of Naboo", 37, Rarity.MYTHIC, mage.cards.t.TheBattleOfNaboo.class));
        cards.add(new SetCardInfo("The Battle of Yavin", 67, Rarity.MYTHIC, mage.cards.t.TheBattleOfYavin.class));
        cards.add(new SetCardInfo("The Death Star", 1, Rarity.MYTHIC, mage.cards.t.TheDeathStar.class));
        cards.add(new SetCardInfo("TIE Bomber", 93, Rarity.UNCOMMON, mage.cards.t.TIEBomber.class));
        cards.add(new SetCardInfo("TIE Interceptor", 94, Rarity.COMMON, mage.cards.t.TIEInterceptor.class));
        cards.add(new SetCardInfo("Trade Federation Battleship", 219, Rarity.RARE, mage.cards.t.TradeFederationBattleship.class));
        cards.add(new SetCardInfo("Tri-Fighter", 228, Rarity.COMMON, mage.cards.t.TriFighter.class));
        cards.add(new SetCardInfo("Trooper Armor", 237, Rarity.UNCOMMON, mage.cards.t.TrooperArmor.class));
        cards.add(new SetCardInfo("Trooper Commando", 157, Rarity.UNCOMMON, mage.cards.t.TrooperCommando.class));
        cards.add(new SetCardInfo("Twi'lek Seductress", 158, Rarity.COMMON, mage.cards.t.TwilekSeductress.class));
        cards.add(new SetCardInfo("Ugnaught Scrap Worker", 61, Rarity.COMMON, mage.cards.u.UgnaughtScrapWorker.class));
        cards.add(new SetCardInfo("Underworld Slums", 251, Rarity.COMMON, mage.cards.u.UnderworldSlums.class));
        cards.add(new SetCardInfo("Unity of the Droids", 220, Rarity.UNCOMMON, mage.cards.u.UnityOfTheDroids.class));
        cards.add(new SetCardInfo("Unruly Sureshot", 95, Rarity.UNCOMMON, mage.cards.u.UnrulySureshot.class));
        cards.add(new SetCardInfo("V-Wing", 126, Rarity.COMMON, mage.cards.v.VWing.class));
        cards.add(new SetCardInfo("Vapor Snag", 62, Rarity.COMMON, mage.cards.v.VaporSnag.class));
        cards.add(new SetCardInfo("Weequay Beastmaster", 127, Rarity.UNCOMMON, mage.cards.w.WeequayBeastmaster.class));
        cards.add(new SetCardInfo("Wild Holocron", 238, Rarity.COMMON, mage.cards.w.WildHolocron.class));
        cards.add(new SetCardInfo("Wisdom of the Jedi", 221, Rarity.UNCOMMON, mage.cards.w.WisdomOfTheJedi.class));
        cards.add(new SetCardInfo("Womp Rat", 32, Rarity.COMMON, mage.cards.w.WompRat.class));
        cards.add(new SetCardInfo("Wookiee Bounty Hunter", 159, Rarity.COMMON, mage.cards.w.WookieeBountyHunter.class));
        cards.add(new SetCardInfo("Wookiee Mystic", 222, Rarity.UNCOMMON, mage.cards.w.WookieeMystic.class));
        cards.add(new SetCardInfo("Wookiee Raidleader", 229, Rarity.COMMON, mage.cards.w.WookieeRaidleader.class));
        cards.add(new SetCardInfo("X-Wing", 33, Rarity.COMMON, mage.cards.x.XWing.class));
        cards.add(new SetCardInfo("Y-Wing", 63, Rarity.UNCOMMON, mage.cards.y.YWing.class));
        cards.add(new SetCardInfo("Yoda, Jedi Master", 223, Rarity.MYTHIC, mage.cards.y.YodaJediMaster.class));
        cards.add(new SetCardInfo("Zam Wesell", 64, Rarity.RARE, mage.cards.z.ZamWesell.class));
    }

}
