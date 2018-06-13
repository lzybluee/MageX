
package mage.sets;

/**
 *
 * @author JayDi85
 */
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class Masters25 extends ExpansionSet {

    private static final Masters25 instance = new Masters25();

    public static Masters25 getInstance() {
        return instance;
    }

    private Masters25() {
        super("Masters 25", "A25", ExpansionSet.buildDate(2018, 3, 16), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Act of Heroism", 1, Rarity.COMMON, mage.cards.a.ActOfHeroism.class));
        cards.add(new SetCardInfo("Akroma, Angel of Wrath", 2, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfWrath.class));
        cards.add(new SetCardInfo("Akroma's Vengeance", 3, Rarity.RARE, mage.cards.a.AkromasVengeance.class));
        cards.add(new SetCardInfo("Angelic Page", 4, Rarity.UNCOMMON, mage.cards.a.AngelicPage.class));
        cards.add(new SetCardInfo("Armageddon", 5, Rarity.MYTHIC, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Auramancer", 6, Rarity.COMMON, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Cloudshift", 7, Rarity.COMMON, mage.cards.c.Cloudshift.class));
        cards.add(new SetCardInfo("Congregate", 8, Rarity.UNCOMMON, mage.cards.c.Congregate.class));
        cards.add(new SetCardInfo("Darien, King of Kjeldor", 9, Rarity.RARE, mage.cards.d.DarienKingOfKjeldor.class));
        cards.add(new SetCardInfo("Dauntless Cathar", 10, Rarity.COMMON, mage.cards.d.DauntlessCathar.class));
        cards.add(new SetCardInfo("Decree of Justice", 11, Rarity.RARE, mage.cards.d.DecreeOfJustice.class));
        cards.add(new SetCardInfo("Disenchant", 12, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Fencing Ace", 13, Rarity.COMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Fiend Hunter", 14, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Geist of the Moors", 15, Rarity.COMMON, mage.cards.g.GeistOfTheMoors.class));
        cards.add(new SetCardInfo("Gods Willing", 16, Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Griffin Protector", 17, Rarity.COMMON, mage.cards.g.GriffinProtector.class));
        cards.add(new SetCardInfo("Karona's Zealot", 18, Rarity.UNCOMMON, mage.cards.k.KaronasZealot.class));
        cards.add(new SetCardInfo("Knight of the Skyward Eye", 19, Rarity.COMMON, mage.cards.k.KnightOfTheSkywardEye.class));
        cards.add(new SetCardInfo("Kongming, 'Sleeping Dragon'", 20, Rarity.UNCOMMON, mage.cards.k.KongmingSleepingDragon.class));
        cards.add(new SetCardInfo("Kor Firewalker", 21, Rarity.UNCOMMON, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Loyal Sentry", 22, Rarity.COMMON, mage.cards.l.LoyalSentry.class));
        cards.add(new SetCardInfo("Luminarch Ascension", 23, Rarity.RARE, mage.cards.l.LuminarchAscension.class));
        cards.add(new SetCardInfo("Lunarch Mantle", 24, Rarity.COMMON, mage.cards.l.LunarchMantle.class));
        cards.add(new SetCardInfo("Noble Templar", 25, Rarity.COMMON, mage.cards.n.NobleTemplar.class));
        cards.add(new SetCardInfo("Nyx-Fleece Ram", 26, Rarity.UNCOMMON, mage.cards.n.NyxFleeceRam.class));
        cards.add(new SetCardInfo("Ordeal of Heliod", 27, Rarity.UNCOMMON, mage.cards.o.OrdealOfHeliod.class));
        cards.add(new SetCardInfo("Pacifism", 28, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Path of Peace", 29, Rarity.COMMON, mage.cards.p.PathOfPeace.class));
        cards.add(new SetCardInfo("Promise of Bunrei", 30, Rarity.UNCOMMON, mage.cards.p.PromiseOfBunrei.class));
        cards.add(new SetCardInfo("Renewed Faith", 31, Rarity.COMMON, mage.cards.r.RenewedFaith.class));
        cards.add(new SetCardInfo("Rest in Peace", 32, Rarity.RARE, mage.cards.r.RestInPeace.class));
        cards.add(new SetCardInfo("Savannah Lions", 33, Rarity.COMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Squadron Hawk", 34, Rarity.COMMON, mage.cards.s.SquadronHawk.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 35, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 36, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Urbis Protector", 37, Rarity.UNCOMMON, mage.cards.u.UrbisProtector.class));
        cards.add(new SetCardInfo("Valor in Akros", 38, Rarity.UNCOMMON, mage.cards.v.ValorInAkros.class));
        cards.add(new SetCardInfo("Whitemane Lion", 39, Rarity.COMMON, mage.cards.w.WhitemaneLion.class));
        cards.add(new SetCardInfo("Accumulated Knowledge", 40, Rarity.COMMON, mage.cards.a.AccumulatedKnowledge.class));
        cards.add(new SetCardInfo("Arcane Denial", 41, Rarity.COMMON, mage.cards.a.ArcaneDenial.class));
        cards.add(new SetCardInfo("Bident of Thassa", 42, Rarity.RARE, mage.cards.b.BidentOfThassa.class));
        cards.add(new SetCardInfo("Blue Elemental Blast", 43, Rarity.UNCOMMON, mage.cards.b.BlueElementalBlast.class));
        cards.add(new SetCardInfo("Blue Sun's Zenith", 44, Rarity.RARE, mage.cards.b.BlueSunsZenith.class));
        cards.add(new SetCardInfo("Borrowing 100,000 Arrows", 45, Rarity.COMMON, mage.cards.b.Borrowing100000Arrows.class));
        cards.add(new SetCardInfo("Brainstorm", 46, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brine Elemental", 47, Rarity.UNCOMMON, mage.cards.b.BrineElemental.class));
        cards.add(new SetCardInfo("Choking Tethers", 48, Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Coralhelm Guide", 49, Rarity.COMMON, mage.cards.c.CoralhelmGuide.class));
        cards.add(new SetCardInfo("Counterspell", 50, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Court Hussar", 51, Rarity.COMMON, mage.cards.c.CourtHussar.class));
        cards.add(new SetCardInfo("Curiosity", 52, Rarity.UNCOMMON, mage.cards.c.Curiosity.class));
        cards.add(new SetCardInfo("Cursecatcher", 53, Rarity.UNCOMMON, mage.cards.c.Cursecatcher.class));
        cards.add(new SetCardInfo("Dragon's Eye Savants", 54, Rarity.COMMON, mage.cards.d.DragonsEyeSavants.class));
        cards.add(new SetCardInfo("Exclude", 55, Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Fathom Seer", 56, Rarity.COMMON, mage.cards.f.FathomSeer.class));
        cards.add(new SetCardInfo("Flash", 57, Rarity.RARE, mage.cards.f.Flash.class));
        cards.add(new SetCardInfo("Freed from the Real", 58, Rarity.UNCOMMON, mage.cards.f.FreedFromTheReal.class));
        cards.add(new SetCardInfo("Genju of the Falls", 59, Rarity.UNCOMMON, mage.cards.g.GenjuOfTheFalls.class));
        cards.add(new SetCardInfo("Ghost Ship", 60, Rarity.COMMON, mage.cards.g.GhostShip.class));
        cards.add(new SetCardInfo("Horseshoe Crab", 61, Rarity.COMMON, mage.cards.h.HorseshoeCrab.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 62, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
        cards.add(new SetCardInfo("Jalira, Master Polymorphist", 63, Rarity.UNCOMMON, mage.cards.j.JaliraMasterPolymorphist.class));
        cards.add(new SetCardInfo("Man-o'-War", 64, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Merfolk Looter", 65, Rarity.UNCOMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Murder of Crows", 66, Rarity.UNCOMMON, mage.cards.m.MurderOfCrows.class));
        cards.add(new SetCardInfo("Mystic of the Hidden Way", 67, Rarity.COMMON, mage.cards.m.MysticOfTheHiddenWay.class));
        cards.add(new SetCardInfo("Pact of Negation", 68, Rarity.RARE, mage.cards.p.PactOfNegation.class));
        cards.add(new SetCardInfo("Phantasmal Bear", 69, Rarity.COMMON, mage.cards.p.PhantasmalBear.class));
        cards.add(new SetCardInfo("Reef Worm", 70, Rarity.RARE, mage.cards.r.ReefWorm.class));
        cards.add(new SetCardInfo("Retraction Helix", 71, Rarity.COMMON, mage.cards.r.RetractionHelix.class));
        cards.add(new SetCardInfo("Shoreline Ranger", 72, Rarity.COMMON, mage.cards.s.ShorelineRanger.class));
        cards.add(new SetCardInfo("Sift", 73, Rarity.COMMON, mage.cards.s.Sift.class));
        cards.add(new SetCardInfo("Totally Lost", 74, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Twisted Image", 75, Rarity.UNCOMMON, mage.cards.t.TwistedImage.class));
        cards.add(new SetCardInfo("Vendilion Clique", 76, Rarity.MYTHIC, mage.cards.v.VendilionClique.class));
        cards.add(new SetCardInfo("Vesuvan Shapeshifter", 77, Rarity.RARE, mage.cards.v.VesuvanShapeshifter.class));
        cards.add(new SetCardInfo("Willbender", 78, Rarity.UNCOMMON, mage.cards.w.Willbender.class));
        cards.add(new SetCardInfo("Ancient Craving", 79, Rarity.UNCOMMON, mage.cards.a.AncientCraving.class));
        cards.add(new SetCardInfo("Bloodhunter Bat", 80, Rarity.COMMON, mage.cards.b.BloodhunterBat.class));
        cards.add(new SetCardInfo("Caustic Tar", 81, Rarity.UNCOMMON, mage.cards.c.CausticTar.class));
        cards.add(new SetCardInfo("Dark Ritual", 82, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Deadly Designs", 83, Rarity.UNCOMMON, mage.cards.d.DeadlyDesigns.class));
        cards.add(new SetCardInfo("Death's-Head Buzzard", 84, Rarity.COMMON, mage.cards.d.DeathsHeadBuzzard.class));
        cards.add(new SetCardInfo("Diabolic Edict", 85, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Dirge of Dread", 86, Rarity.COMMON, mage.cards.d.DirgeOfDread.class));
        cards.add(new SetCardInfo("Disfigure", 87, Rarity.COMMON, mage.cards.d.Disfigure.class));
        cards.add(new SetCardInfo("Doomsday", 88, Rarity.MYTHIC, mage.cards.d.Doomsday.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", 89, Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Erg Raiders", 90, Rarity.COMMON, mage.cards.e.ErgRaiders.class));
        cards.add(new SetCardInfo("Fallen Angel", 91, Rarity.UNCOMMON, mage.cards.f.FallenAngel.class));
        cards.add(new SetCardInfo("Hell's Caretaker", 92, Rarity.RARE, mage.cards.h.HellsCaretaker.class));
        cards.add(new SetCardInfo("Horror of the Broken Lands", 93, Rarity.COMMON, mage.cards.h.HorrorOfTheBrokenLands.class));
        cards.add(new SetCardInfo("Ihsan's Shade", 94, Rarity.UNCOMMON, mage.cards.i.IhsansShade.class));
        cards.add(new SetCardInfo("Laquatus's Champion", 95, Rarity.RARE, mage.cards.l.LaquatussChampion.class));
        cards.add(new SetCardInfo("Living Death", 96, Rarity.RARE, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Mesmeric Fiend", 97, Rarity.UNCOMMON, mage.cards.m.MesmericFiend.class));
        cards.add(new SetCardInfo("Murder", 98, Rarity.COMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nezumi Cutthroat", 99, Rarity.COMMON, mage.cards.n.NezumiCutthroat.class));
        cards.add(new SetCardInfo("Phyrexian Ghoul", 100, Rarity.COMMON, mage.cards.p.PhyrexianGhoul.class));
        cards.add(new SetCardInfo("Phyrexian Obliterator", 101, Rarity.MYTHIC, mage.cards.p.PhyrexianObliterator.class));
        cards.add(new SetCardInfo("Plague Wind", 102, Rarity.RARE, mage.cards.p.PlagueWind.class));
        cards.add(new SetCardInfo("Ratcatcher", 103, Rarity.RARE, mage.cards.r.Ratcatcher.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", 104, Rarity.UNCOMMON, mage.cards.r.RavenousChupacabra.class));
        cards.add(new SetCardInfo("Relentless Rats", 105, Rarity.COMMON, mage.cards.r.RelentlessRats.class));
        cards.add(new SetCardInfo("Returned Phalanx", 106, Rarity.COMMON, mage.cards.r.ReturnedPhalanx.class));
        cards.add(new SetCardInfo("Ruthless Ripper", 107, Rarity.COMMON, mage.cards.r.RuthlessRipper.class));
        cards.add(new SetCardInfo("Street Wraith", 108, Rarity.UNCOMMON, mage.cards.s.StreetWraith.class));
        cards.add(new SetCardInfo("Supernatural Stamina", 109, Rarity.COMMON, mage.cards.s.SupernaturalStamina.class));
        cards.add(new SetCardInfo("Triskaidekaphobia", 110, Rarity.RARE, mage.cards.t.Triskaidekaphobia.class));
        cards.add(new SetCardInfo("Twisted Abomination", 111, Rarity.COMMON, mage.cards.t.TwistedAbomination.class));
        cards.add(new SetCardInfo("Undead Gladiator", 112, Rarity.UNCOMMON, mage.cards.u.UndeadGladiator.class));
        cards.add(new SetCardInfo("Unearth", 113, Rarity.COMMON, mage.cards.u.Unearth.class));
        cards.add(new SetCardInfo("Vampire Lacerator", 114, Rarity.COMMON, mage.cards.v.VampireLacerator.class));
        cards.add(new SetCardInfo("Will-o'-the-Wisp", 115, Rarity.UNCOMMON, mage.cards.w.WillOTheWisp.class));
        cards.add(new SetCardInfo("Zombify", 116, Rarity.UNCOMMON, mage.cards.z.Zombify.class));
        cards.add(new SetCardInfo("Zulaport Cutthroat", 117, Rarity.UNCOMMON, mage.cards.z.ZulaportCutthroat.class));
        cards.add(new SetCardInfo("Act of Treason", 118, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Akroma, Angel of Fury", 119, Rarity.MYTHIC, mage.cards.a.AkromaAngelOfFury.class));
        cards.add(new SetCardInfo("Balduvian Horde", 120, Rarity.COMMON, mage.cards.b.BalduvianHorde.class));
        cards.add(new SetCardInfo("Ball Lightning", 121, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Blood Moon", 122, Rarity.RARE, mage.cards.b.BloodMoon.class));
        cards.add(new SetCardInfo("Browbeat", 123, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 124, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Chartooth Cougar", 125, Rarity.COMMON, mage.cards.c.ChartoothCougar.class));
        cards.add(new SetCardInfo("Cinder Storm", 126, Rarity.COMMON, mage.cards.c.CinderStorm.class));
        cards.add(new SetCardInfo("Crimson Mage", 127, Rarity.COMMON, mage.cards.c.CrimsonMage.class));
        cards.add(new SetCardInfo("Eidolon of the Great Revel", 128, Rarity.RARE, mage.cards.e.EidolonOfTheGreatRevel.class));
        cards.add(new SetCardInfo("Enthralling Victor", 129, Rarity.UNCOMMON, mage.cards.e.EnthrallingVictor.class));
        cards.add(new SetCardInfo("Fortune Thief", 130, Rarity.RARE, mage.cards.f.FortuneThief.class));
        cards.add(new SetCardInfo("Frenzied Goblin", 131, Rarity.COMMON, mage.cards.f.FrenziedGoblin.class));
        cards.add(new SetCardInfo("Genju of the Spires", 132, Rarity.UNCOMMON, mage.cards.g.GenjuOfTheSpires.class));
        cards.add(new SetCardInfo("Goblin War Drums", 133, Rarity.UNCOMMON, mage.cards.g.GoblinWarDrums.class));
        cards.add(new SetCardInfo("Hordeling Outburst", 134, Rarity.COMMON, mage.cards.h.HordelingOutburst.class));
        cards.add(new SetCardInfo("Humble Defector", 135, Rarity.UNCOMMON, mage.cards.h.HumbleDefector.class));
        cards.add(new SetCardInfo("Imperial Recruiter", 136, Rarity.MYTHIC, mage.cards.i.ImperialRecruiter.class));
        cards.add(new SetCardInfo("Ire Shaman", 137, Rarity.UNCOMMON, mage.cards.i.IreShaman.class));
        cards.add(new SetCardInfo("Izzet Chemister", 138, Rarity.RARE, mage.cards.i.IzzetChemister.class));
        cards.add(new SetCardInfo("Jackal Pup", 139, Rarity.COMMON, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Kindle", 140, Rarity.COMMON, mage.cards.k.Kindle.class));
        cards.add(new SetCardInfo("Lightning Bolt", 141, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Magus of the Wheel", 142, Rarity.RARE, mage.cards.m.MagusOfTheWheel.class));
        cards.add(new SetCardInfo("Mogg Flunkies", 143, Rarity.COMMON, mage.cards.m.MoggFlunkies.class));
        cards.add(new SetCardInfo("Pillage", 144, Rarity.COMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Pyre Hound", 145, Rarity.COMMON, mage.cards.p.PyreHound.class));
        cards.add(new SetCardInfo("Pyroclasm", 146, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Red Elemental Blast", 147, Rarity.UNCOMMON, mage.cards.r.RedElementalBlast.class));
        cards.add(new SetCardInfo("Simian Spirit Guide", 148, Rarity.UNCOMMON, mage.cards.s.SimianSpiritGuide.class));
        cards.add(new SetCardInfo("Skeletonize", 149, Rarity.COMMON, mage.cards.s.Skeletonize.class));
        cards.add(new SetCardInfo("Skirk Commando", 150, Rarity.COMMON, mage.cards.s.SkirkCommando.class));
        cards.add(new SetCardInfo("Soulbright Flamekin", 151, Rarity.COMMON, mage.cards.s.SoulbrightFlamekin.class));
        cards.add(new SetCardInfo("Spikeshot Goblin", 152, Rarity.UNCOMMON, mage.cards.s.SpikeshotGoblin.class));
        cards.add(new SetCardInfo("Thresher Lizard", 153, Rarity.COMMON, mage.cards.t.ThresherLizard.class));
        cards.add(new SetCardInfo("Trumpet Blast", 154, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Uncaged Fury", 155, Rarity.COMMON, mage.cards.u.UncagedFury.class));
        cards.add(new SetCardInfo("Zada, Hedron Grinder", 156, Rarity.UNCOMMON, mage.cards.z.ZadaHedronGrinder.class));
        cards.add(new SetCardInfo("Ainok Survivalist", 157, Rarity.COMMON, mage.cards.a.AinokSurvivalist.class));
        cards.add(new SetCardInfo("Ambassador Oak", 158, Rarity.COMMON, mage.cards.a.AmbassadorOak.class));
        cards.add(new SetCardInfo("Ancient Stirrings", 159, Rarity.UNCOMMON, mage.cards.a.AncientStirrings.class));
        cards.add(new SetCardInfo("Arbor Elf", 160, Rarity.COMMON, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 161, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class));
        cards.add(new SetCardInfo("Broodhatch Nantuko", 162, Rarity.UNCOMMON, mage.cards.b.BroodhatchNantuko.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 163, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Courser of Kruphix", 164, Rarity.RARE, mage.cards.c.CourserOfKruphix.class));
        cards.add(new SetCardInfo("Cultivate", 165, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Echoing Courage", 166, Rarity.COMMON, mage.cards.e.EchoingCourage.class));
        cards.add(new SetCardInfo("Elvish Aberration", 167, Rarity.COMMON, mage.cards.e.ElvishAberration.class));
        cards.add(new SetCardInfo("Elvish Piper", 168, Rarity.RARE, mage.cards.e.ElvishPiper.class));
        cards.add(new SetCardInfo("Ember Weaver", 169, Rarity.COMMON, mage.cards.e.EmberWeaver.class));
        cards.add(new SetCardInfo("Epic Confrontation", 170, Rarity.COMMON, mage.cards.e.EpicConfrontation.class));
        cards.add(new SetCardInfo("Fierce Empath", 171, Rarity.UNCOMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Giant Growth", 172, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Invigorate", 173, Rarity.UNCOMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Iwamori of the Open Fist", 174, Rarity.UNCOMMON, mage.cards.i.IwamoriOfTheOpenFist.class));
        cards.add(new SetCardInfo("Kavu Climber", 175, Rarity.COMMON, mage.cards.k.KavuClimber.class));
        cards.add(new SetCardInfo("Kavu Predator", 176, Rarity.UNCOMMON, mage.cards.k.KavuPredator.class));
        cards.add(new SetCardInfo("Krosan Colossus", 177, Rarity.UNCOMMON, mage.cards.k.KrosanColossus.class));
        cards.add(new SetCardInfo("Krosan Tusker", 178, Rarity.UNCOMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Living Wish", 179, Rarity.RARE, mage.cards.l.LivingWish.class));
        cards.add(new SetCardInfo("Lull", 180, Rarity.COMMON, mage.cards.l.Lull.class));
        cards.add(new SetCardInfo("Master of the Wild Hunt", 181, Rarity.MYTHIC, mage.cards.m.MasterOfTheWildHunt.class));
        cards.add(new SetCardInfo("Nettle Sentinel", 182, Rarity.COMMON, mage.cards.n.NettleSentinel.class));
        cards.add(new SetCardInfo("Plummet", 183, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Presence of Gond", 184, Rarity.COMMON, mage.cards.p.PresenceOfGond.class));
        cards.add(new SetCardInfo("Protean Hulk", 185, Rarity.RARE, mage.cards.p.ProteanHulk.class));
        cards.add(new SetCardInfo("Rancor", 186, Rarity.UNCOMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Regrowth", 187, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Stampede Driver", 188, Rarity.UNCOMMON, mage.cards.s.StampedeDriver.class));
        cards.add(new SetCardInfo("Summoner's Pact", 189, Rarity.RARE, mage.cards.s.SummonersPact.class));
        cards.add(new SetCardInfo("Timberpack Wolf", 190, Rarity.COMMON, mage.cards.t.TimberpackWolf.class));
        cards.add(new SetCardInfo("Tree of Redemption", 191, Rarity.MYTHIC, mage.cards.t.TreeOfRedemption.class));
        cards.add(new SetCardInfo("Utopia Sprawl", 192, Rarity.UNCOMMON, mage.cards.u.UtopiaSprawl.class));
        cards.add(new SetCardInfo("Vessel of Nascency", 193, Rarity.COMMON, mage.cards.v.VesselOfNascency.class));
        cards.add(new SetCardInfo("Wildheart Invoker", 194, Rarity.COMMON, mage.cards.w.WildheartInvoker.class));
        cards.add(new SetCardInfo("Woolly Loxodon", 195, Rarity.COMMON, mage.cards.w.WoollyLoxodon.class));
        cards.add(new SetCardInfo("Animar, Soul of Elements", 196, Rarity.MYTHIC, mage.cards.a.AnimarSoulOfElements.class));
        cards.add(new SetCardInfo("Baloth Null", 197, Rarity.UNCOMMON, mage.cards.b.BalothNull.class));
        cards.add(new SetCardInfo("Blightning", 198, Rarity.UNCOMMON, mage.cards.b.Blightning.class));
        cards.add(new SetCardInfo("Boros Charm", 199, Rarity.UNCOMMON, mage.cards.b.BorosCharm.class));
        cards.add(new SetCardInfo("Brion Stoutarm", 200, Rarity.RARE, mage.cards.b.BrionStoutarm.class));
        cards.add(new SetCardInfo("Cloudblazer", 201, Rarity.UNCOMMON, mage.cards.c.Cloudblazer.class));
        cards.add(new SetCardInfo("Conflux", 202, Rarity.RARE, mage.cards.c.Conflux.class));
        cards.add(new SetCardInfo("Eladamri's Call", 203, Rarity.RARE, mage.cards.e.EladamrisCall.class));
        cards.add(new SetCardInfo("Gisela, Blade of Goldnight", 204, Rarity.MYTHIC, mage.cards.g.GiselaBladeOfGoldnight.class));
        cards.add(new SetCardInfo("Grenzo, Dungeon Warden", 205, Rarity.RARE, mage.cards.g.GrenzoDungeonWarden.class));
        cards.add(new SetCardInfo("Hanna, Ship's Navigator", 206, Rarity.RARE, mage.cards.h.HannaShipsNavigator.class));
        cards.add(new SetCardInfo("Lorescale Coatl", 207, Rarity.UNCOMMON, mage.cards.l.LorescaleCoatl.class));
        cards.add(new SetCardInfo("Mystic Snake", 208, Rarity.RARE, mage.cards.m.MysticSnake.class));
        cards.add(new SetCardInfo("Nicol Bolas", 209, Rarity.RARE, mage.cards.n.NicolBolas.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 210, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Notion Thief", 211, Rarity.RARE, mage.cards.n.NotionThief.class));
        cards.add(new SetCardInfo("Pernicious Deed", 212, Rarity.RARE, mage.cards.p.PerniciousDeed.class));
        cards.add(new SetCardInfo("Pillory of the Sleepless", 213, Rarity.UNCOMMON, mage.cards.p.PilloryOfTheSleepless.class));
        cards.add(new SetCardInfo("Prossh, Skyraider of Kher", 214, Rarity.MYTHIC, mage.cards.p.ProsshSkyraiderOfKher.class));
        cards.add(new SetCardInfo("Quicksilver Dagger", 215, Rarity.UNCOMMON, mage.cards.q.QuicksilverDagger.class));
        cards.add(new SetCardInfo("Ruric Thar, the Unbowed", 216, Rarity.RARE, mage.cards.r.RuricTharTheUnbowed.class));
        cards.add(new SetCardInfo("Shadowmage Infiltrator", 217, Rarity.UNCOMMON, mage.cards.s.ShadowmageInfiltrator.class));
        cards.add(new SetCardInfo("Stangg", 218, Rarity.UNCOMMON, mage.cards.s.Stangg.class));
        cards.add(new SetCardInfo("Vindicate", 219, Rarity.RARE, mage.cards.v.Vindicate.class));
        cards.add(new SetCardInfo("Watchwolf", 220, Rarity.UNCOMMON, mage.cards.w.Watchwolf.class));
        cards.add(new SetCardInfo("Assembly-Worker", 221, Rarity.COMMON, mage.cards.a.AssemblyWorker.class));
        cards.add(new SetCardInfo("Chalice of the Void", 222, Rarity.MYTHIC, mage.cards.c.ChaliceOfTheVoid.class));
        cards.add(new SetCardInfo("Coalition Relic", 223, Rarity.RARE, mage.cards.c.CoalitionRelic.class));
        cards.add(new SetCardInfo("Ensnaring Bridge", 224, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Heavy Arbalest", 225, Rarity.UNCOMMON, mage.cards.h.HeavyArbalest.class));
        cards.add(new SetCardInfo("Nihil Spellbomb", 226, Rarity.COMMON, mage.cards.n.NihilSpellbomb.class));
        cards.add(new SetCardInfo("Perilous Myr", 227, Rarity.UNCOMMON, mage.cards.p.PerilousMyr.class));
        cards.add(new SetCardInfo("Primal Clay", 228, Rarity.COMMON, mage.cards.p.PrimalClay.class));
        cards.add(new SetCardInfo("Prophetic Prism", 229, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Sai of the Shinobi", 230, Rarity.UNCOMMON, mage.cards.s.SaiOfTheShinobi.class));
        cards.add(new SetCardInfo("Self-Assembler", 231, Rarity.COMMON, mage.cards.s.SelfAssembler.class));
        cards.add(new SetCardInfo("Strionic Resonator", 232, Rarity.RARE, mage.cards.s.StrionicResonator.class));
        cards.add(new SetCardInfo("Sundering Titan", 233, Rarity.RARE, mage.cards.s.SunderingTitan.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 234, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("Treasure Keeper", 235, Rarity.UNCOMMON, mage.cards.t.TreasureKeeper.class));
        cards.add(new SetCardInfo("Ash Barrens", 236, Rarity.UNCOMMON, mage.cards.a.AshBarrens.class));
        cards.add(new SetCardInfo("Cascade Bluffs", 237, Rarity.RARE, mage.cards.c.CascadeBluffs.class));
        cards.add(new SetCardInfo("Fetid Heath", 238, Rarity.RARE, mage.cards.f.FetidHeath.class));
        cards.add(new SetCardInfo("Flooded Grove", 239, Rarity.RARE, mage.cards.f.FloodedGrove.class));
        cards.add(new SetCardInfo("Haunted Fengraf", 240, Rarity.COMMON, mage.cards.h.HauntedFengraf.class));
        cards.add(new SetCardInfo("Mikokoro, Center of the Sea", 241, Rarity.RARE, mage.cards.m.MikokoroCenterOfTheSea.class));
        cards.add(new SetCardInfo("Mishra's Factory", 242, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Myriad Landscape", 243, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Pendelhaven", 244, Rarity.RARE, mage.cards.p.Pendelhaven.class));
        cards.add(new SetCardInfo("Quicksand", 245, Rarity.UNCOMMON, mage.cards.q.Quicksand.class));
        cards.add(new SetCardInfo("Rishadan Port", 246, Rarity.RARE, mage.cards.r.RishadanPort.class));
        cards.add(new SetCardInfo("Rugged Prairie", 247, Rarity.RARE, mage.cards.r.RuggedPrairie.class));
        cards.add(new SetCardInfo("Twilight Mire", 248, Rarity.RARE, mage.cards.t.TwilightMire.class));
        cards.add(new SetCardInfo("Zoetic Cavern", 249, Rarity.UNCOMMON, mage.cards.z.ZoeticCavern.class));
    }
}
