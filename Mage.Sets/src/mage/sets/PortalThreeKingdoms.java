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
 * @author LevelX2
 */
public final class PortalThreeKingdoms extends ExpansionSet {

    private static final PortalThreeKingdoms instance = new PortalThreeKingdoms();

    public static PortalThreeKingdoms getInstance() {
        return instance;
    }

    private PortalThreeKingdoms() {
        super("Portal Three Kingdoms", "PTK", ExpansionSet.buildDate(1999, 5, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Beginner";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 2;
        this.numBoosterCommon = 5;
        this.numBoosterUncommon = 2;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Alert Shu Infantry", 1, Rarity.UNCOMMON, mage.cards.a.AlertShuInfantry.class));
        cards.add(new SetCardInfo("Ambition's Cost", 67, Rarity.RARE, mage.cards.a.AmbitionsCost.class));
        cards.add(new SetCardInfo("Balance of Power", 34, Rarity.RARE, mage.cards.b.BalanceOfPower.class));
        cards.add(new SetCardInfo("Barbarian General", 100, Rarity.UNCOMMON, mage.cards.b.BarbarianGeneral.class));
        cards.add(new SetCardInfo("Barbarian Horde", 101, Rarity.COMMON, mage.cards.b.BarbarianHorde.class));
        cards.add(new SetCardInfo("Blaze", 102, Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Borrowing 100,000 Arrows", 35, Rarity.UNCOMMON, mage.cards.b.Borrowing100000Arrows.class));
        cards.add(new SetCardInfo("Borrowing the East Wind", 133, Rarity.RARE, mage.cards.b.BorrowingTheEastWind.class));
        cards.add(new SetCardInfo("Brilliant Plan", 36, Rarity.UNCOMMON, mage.cards.b.BrilliantPlan.class));
        cards.add(new SetCardInfo("Broken Dam", 37, Rarity.COMMON, mage.cards.b.BrokenDam.class));
        cards.add(new SetCardInfo("Burning Fields", 103, Rarity.COMMON, mage.cards.b.BurningFields.class));
        cards.add(new SetCardInfo("Burning of Xinye", 104, Rarity.RARE, mage.cards.b.BurningOfXinye.class));
        cards.add(new SetCardInfo("Cao Cao, Lord of Wei", 68, Rarity.RARE, mage.cards.c.CaoCaoLordOfWei.class));
        cards.add(new SetCardInfo("Cao Ren, Wei Commander", 69, Rarity.RARE, mage.cards.c.CaoRenWeiCommander.class));
        cards.add(new SetCardInfo("Capture of Jingzhou", 38, Rarity.RARE, mage.cards.c.CaptureOfJingzhou.class));
        cards.add(new SetCardInfo("Champion's Victory", 39, Rarity.UNCOMMON, mage.cards.c.ChampionsVictory.class));
        cards.add(new SetCardInfo("Coercion", 70, Rarity.UNCOMMON, mage.cards.c.Coercion.class));
        cards.add(new SetCardInfo("Control of the Court", 105, Rarity.UNCOMMON, mage.cards.c.ControlOfTheCourt.class));
        cards.add(new SetCardInfo("Corrupt Court Official", 71, Rarity.UNCOMMON, mage.cards.c.CorruptCourtOfficial.class));
        cards.add(new SetCardInfo("Corrupt Eunuchs", 106, Rarity.UNCOMMON, mage.cards.c.CorruptEunuchs.class));
        cards.add(new SetCardInfo("Council of Advisors", 40, Rarity.UNCOMMON, mage.cards.c.CouncilOfAdvisors.class));
        cards.add(new SetCardInfo("Counterintelligence", 41, Rarity.UNCOMMON, mage.cards.c.Counterintelligence.class));
        cards.add(new SetCardInfo("Cunning Advisor", 72, Rarity.UNCOMMON, mage.cards.c.CunningAdvisor.class));
        cards.add(new SetCardInfo("Deception", 73, Rarity.COMMON, mage.cards.d.Deception.class));
        cards.add(new SetCardInfo("Desert Sandstorm", 107, Rarity.COMMON, mage.cards.d.DesertSandstorm.class));
        cards.add(new SetCardInfo("Desperate Charge", 74, Rarity.UNCOMMON, mage.cards.d.DesperateCharge.class));
        cards.add(new SetCardInfo("Diaochan, Artful Beauty", 108, Rarity.RARE, mage.cards.d.DiaochanArtfulBeauty.class));
        cards.add(new SetCardInfo("Dong Zhou, the Tyrant", 109, Rarity.RARE, mage.cards.d.DongZhouTheTyrant.class));
        cards.add(new SetCardInfo("Eightfold Maze", 2, Rarity.RARE, mage.cards.e.EightfoldMaze.class));
        cards.add(new SetCardInfo("Empty City Ruse", 3, Rarity.UNCOMMON, mage.cards.e.EmptyCityRuse.class));
        cards.add(new SetCardInfo("Eunuchs' Intrigues", 110, Rarity.UNCOMMON, mage.cards.e.EunuchsIntrigues.class));
        cards.add(new SetCardInfo("Exhaustion", 42, Rarity.RARE, mage.cards.e.Exhaustion.class));
        cards.add(new SetCardInfo("Extinguish", 43, Rarity.COMMON, mage.cards.e.Extinguish.class));
        cards.add(new SetCardInfo("False Defeat", 4, Rarity.COMMON, mage.cards.f.FalseDefeat.class));
        cards.add(new SetCardInfo("False Mourning", 134, Rarity.UNCOMMON, mage.cards.f.FalseMourning.class));
        cards.add(new SetCardInfo("Famine", 75, Rarity.UNCOMMON, mage.cards.f.Famine.class));
        cards.add(new SetCardInfo("Fire Ambush", 111, Rarity.COMMON, mage.cards.f.FireAmbush.class));
        cards.add(new SetCardInfo("Fire Bowman", 112, Rarity.UNCOMMON, mage.cards.f.FireBowman.class));
        cards.add(new SetCardInfo("Flanking Troops", 5, Rarity.UNCOMMON, mage.cards.f.FlankingTroops.class));
        cards.add(new SetCardInfo("Forced Retreat", 44, Rarity.COMMON, mage.cards.f.ForcedRetreat.class));
        cards.add(new SetCardInfo("Forest", 178, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 179, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 180, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest Bear", 135, Rarity.COMMON, mage.cards.f.ForestBear.class));
        cards.add(new SetCardInfo("Ghostly Visit", 76, Rarity.COMMON, mage.cards.g.GhostlyVisit.class));
        cards.add(new SetCardInfo("Guan Yu's 1,000-Li March", 7, Rarity.RARE, mage.cards.g.GuanYus1000LiMarch.class));
        cards.add(new SetCardInfo("Guan Yu, Sainted Warrior", 6, Rarity.RARE, mage.cards.g.GuanYuSaintedWarrior.class));
        cards.add(new SetCardInfo("Heavy Fog", 136, Rarity.UNCOMMON, mage.cards.h.HeavyFog.class));
        cards.add(new SetCardInfo("Huang Zhong, Shu General", 8, Rarity.RARE, mage.cards.h.HuangZhongShuGeneral.class));
        cards.add(new SetCardInfo("Hua Tuo, Honored Physician", 137, Rarity.RARE, mage.cards.h.HuaTuoHonoredPhysician.class));
        cards.add(new SetCardInfo("Hunting Cheetah", 138, Rarity.UNCOMMON, mage.cards.h.HuntingCheetah.class));
        cards.add(new SetCardInfo("Imperial Edict", 77, Rarity.COMMON, mage.cards.i.ImperialEdict.class));
        cards.add(new SetCardInfo("Imperial Recruiter", 113, Rarity.UNCOMMON, mage.cards.i.ImperialRecruiter.class));
        cards.add(new SetCardInfo("Imperial Seal", 78, Rarity.RARE, mage.cards.i.ImperialSeal.class));
        cards.add(new SetCardInfo("Independent Troops", 114, Rarity.COMMON, mage.cards.i.IndependentTroops.class));
        cards.add(new SetCardInfo("Island", 169, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 170, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 171, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kongming, 'Sleeping Dragon'", 9, Rarity.RARE, mage.cards.k.KongmingSleepingDragon.class));
        cards.add(new SetCardInfo("Kongming's Contraptions", 10, Rarity.RARE, mage.cards.k.KongmingsContraptions.class));
        cards.add(new SetCardInfo("Lady Sun", 45, Rarity.RARE, mage.cards.l.LadySun.class));
        cards.add(new SetCardInfo("Lady Zhurong, Warrior Queen", 139, Rarity.RARE, mage.cards.l.LadyZhurongWarriorQueen.class));
        cards.add(new SetCardInfo("Liu Bei, Lord of Shu", 11, Rarity.RARE, mage.cards.l.LiuBeiLordOfShu.class));
        cards.add(new SetCardInfo("Lone Wolf", 140, Rarity.UNCOMMON, mage.cards.l.LoneWolf.class));
        cards.add(new SetCardInfo("Loyal Retainers", 12, Rarity.UNCOMMON, mage.cards.l.LoyalRetainers.class));
        cards.add(new SetCardInfo("Lu Bu, Master-at-Arms", 115, Rarity.RARE, mage.cards.l.LuBuMasterAtArms.class));
        cards.add(new SetCardInfo("Lu Meng, Wu General", 46, Rarity.RARE, mage.cards.l.LuMengWuGeneral.class));
        cards.add(new SetCardInfo("Lu Su, Wu Advisor", 47, Rarity.RARE, mage.cards.l.LuSuWuAdvisor.class));
        cards.add(new SetCardInfo("Lu Xun, Scholar General", 48, Rarity.RARE, mage.cards.l.LuXunScholarGeneral.class));
        cards.add(new SetCardInfo("Ma Chao, Western Warrior", 116, Rarity.RARE, mage.cards.m.MaChaoWesternWarrior.class));
        cards.add(new SetCardInfo("Marshaling the Troops", 141, Rarity.RARE, mage.cards.m.MarshalingTheTroops.class));
        cards.add(new SetCardInfo("Meng Huo, Barbarian King", 142, Rarity.RARE, mage.cards.m.MengHuoBarbarianKing.class));
        cards.add(new SetCardInfo("Meng Huo's Horde", 143, Rarity.COMMON, mage.cards.m.MengHuosHorde.class));
        cards.add(new SetCardInfo("Misfortune's Gain", 13, Rarity.COMMON, mage.cards.m.MisfortunesGain.class));
        cards.add(new SetCardInfo("Mountain", 175, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 176, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 177, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain Bandit", 117, Rarity.COMMON, mage.cards.m.MountainBandit.class));
        cards.add(new SetCardInfo("Mystic Denial", 49, Rarity.UNCOMMON, mage.cards.m.MysticDenial.class));
        cards.add(new SetCardInfo("Overwhelming Forces", 79, Rarity.RARE, mage.cards.o.OverwhelmingForces.class));
        cards.add(new SetCardInfo("Pang Tong, 'Young Phoenix'", 14, Rarity.RARE, mage.cards.p.PangTongYoungPhoenix.class));
        cards.add(new SetCardInfo("Peach Garden Oath", 15, Rarity.UNCOMMON, mage.cards.p.PeachGardenOath.class));
        cards.add(new SetCardInfo("Plains", 166, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 167, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 168, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Poison Arrow", 80, Rarity.UNCOMMON, mage.cards.p.PoisonArrow.class));
        cards.add(new SetCardInfo("Preemptive Strike", 50, Rarity.COMMON, mage.cards.p.PreemptiveStrike.class));
        cards.add(new SetCardInfo("Rally the Troops", 16, Rarity.UNCOMMON, mage.cards.r.RallyTheTroops.class));
        cards.add(new SetCardInfo("Ravages of War", 17, Rarity.RARE, mage.cards.r.RavagesOfWar.class));
        cards.add(new SetCardInfo("Ravaging Horde", 118, Rarity.UNCOMMON, mage.cards.r.RavagingHorde.class));
        cards.add(new SetCardInfo("Red Cliffs Armada", 51, Rarity.UNCOMMON, mage.cards.r.RedCliffsArmada.class));
        cards.add(new SetCardInfo("Relentless Assault", 119, Rarity.RARE, mage.cards.r.RelentlessAssault.class));
        cards.add(new SetCardInfo("Renegade Troops", 120, Rarity.UNCOMMON, mage.cards.r.RenegadeTroops.class));
        cards.add(new SetCardInfo("Return to Battle", 81, Rarity.COMMON, mage.cards.r.ReturnToBattle.class));
        cards.add(new SetCardInfo("Riding Red Hare", 18, Rarity.COMMON, mage.cards.r.RidingRedHare.class));
        cards.add(new SetCardInfo("Riding the Dilu Horse", 144, Rarity.RARE, mage.cards.r.RidingTheDiluHorse.class));
        cards.add(new SetCardInfo("Rockslide Ambush", 121, Rarity.UNCOMMON, mage.cards.r.RockslideAmbush.class));
        cards.add(new SetCardInfo("Rolling Earthquake", 122, Rarity.RARE, mage.cards.r.RollingEarthquake.class));
        cards.add(new SetCardInfo("Sage's Knowledge", 52, Rarity.COMMON, mage.cards.s.SagesKnowledge.class));
        cards.add(new SetCardInfo("Shu Cavalry", 19, Rarity.COMMON, mage.cards.s.ShuCavalry.class));
        cards.add(new SetCardInfo("Shu Defender", 20, Rarity.COMMON, mage.cards.s.ShuDefender.class));
        cards.add(new SetCardInfo("Shu Elite Companions", 21, Rarity.UNCOMMON, mage.cards.s.ShuEliteCompanions.class));
        cards.add(new SetCardInfo("Shu Elite Infantry", 22, Rarity.COMMON, mage.cards.s.ShuEliteInfantry.class));
        cards.add(new SetCardInfo("Shu Farmer", 23, Rarity.COMMON, mage.cards.s.ShuFarmer.class));
        cards.add(new SetCardInfo("Shu Foot Soldiers", 24, Rarity.COMMON, mage.cards.s.ShuFootSoldiers.class));
        cards.add(new SetCardInfo("Shu General", 25, Rarity.UNCOMMON, mage.cards.s.ShuGeneral.class));
        cards.add(new SetCardInfo("Shu Grain Caravan", 26, Rarity.COMMON, mage.cards.s.ShuGrainCaravan.class));
        cards.add(new SetCardInfo("Shu Soldier-Farmers", 27, Rarity.UNCOMMON, mage.cards.s.ShuSoldierFarmers.class));
        cards.add(new SetCardInfo("Sima Yi, Wei Field Marshal", 82, Rarity.RARE, mage.cards.s.SimaYiWeiFieldMarshal.class));
        cards.add(new SetCardInfo("Slashing Tiger", 145, Rarity.RARE, mage.cards.s.SlashingTiger.class));
        cards.add(new SetCardInfo("Southern Elephant", 146, Rarity.COMMON, mage.cards.s.SouthernElephant.class));
        cards.add(new SetCardInfo("Spoils of Victory", 147, Rarity.UNCOMMON, mage.cards.s.SpoilsOfVictory.class));
        cards.add(new SetCardInfo("Spring of Eternal Peace", 148, Rarity.COMMON, mage.cards.s.SpringOfEternalPeace.class));
        cards.add(new SetCardInfo("Stalking Tiger", 149, Rarity.COMMON, mage.cards.s.StalkingTiger.class));
        cards.add(new SetCardInfo("Stolen Grain", 83, Rarity.UNCOMMON, mage.cards.s.StolenGrain.class));
        cards.add(new SetCardInfo("Stone Catapult", 84, Rarity.RARE, mage.cards.s.StoneCatapult.class));
        cards.add(new SetCardInfo("Stone Rain", 123, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Strategic Planning", 53, Rarity.UNCOMMON, mage.cards.s.StrategicPlanning.class));
        cards.add(new SetCardInfo("Straw Soldiers", 54, Rarity.COMMON, mage.cards.s.StrawSoldiers.class));
        cards.add(new SetCardInfo("Sun Ce, Young Conquerer", 55, Rarity.RARE, mage.cards.s.SunCeYoungConquerer.class));
        cards.add(new SetCardInfo("Sun Quan, Lord of Wu", 56, Rarity.RARE, mage.cards.s.SunQuanLordOfWu.class));
        cards.add(new SetCardInfo("Swamp", 172, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 173, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 174, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taoist Hermit", 150, Rarity.UNCOMMON, mage.cards.t.TaoistHermit.class));
        cards.add(new SetCardInfo("Taoist Mystic", 151, Rarity.RARE, mage.cards.t.TaoistMystic.class));
        cards.add(new SetCardInfo("Taunting Challenge", 152, Rarity.RARE, mage.cards.t.TauntingChallenge.class));
        cards.add(new SetCardInfo("Three Visits", 153, Rarity.COMMON, mage.cards.t.ThreeVisits.class));
        cards.add(new SetCardInfo("Trained Cheetah", 154, Rarity.UNCOMMON, mage.cards.t.TrainedCheetah.class));
        cards.add(new SetCardInfo("Trained Jackal", 155, Rarity.COMMON, mage.cards.t.TrainedJackal.class));
        cards.add(new SetCardInfo("Trip Wire", 156, Rarity.UNCOMMON, mage.cards.t.TripWire.class));
        cards.add(new SetCardInfo("Vengeance", 28, Rarity.UNCOMMON, mage.cards.v.Vengeance.class));
        cards.add(new SetCardInfo("Virtuous Charge", 29, Rarity.COMMON, mage.cards.v.VirtuousCharge.class));
        cards.add(new SetCardInfo("Volunteer Militia", 30, Rarity.COMMON, mage.cards.v.VolunteerMilitia.class));
        cards.add(new SetCardInfo("Warrior's Oath", 124, Rarity.RARE, mage.cards.w.WarriorsOath.class));
        cards.add(new SetCardInfo("Warrior's Stand", 31, Rarity.UNCOMMON, mage.cards.w.WarriorsStand.class));
        cards.add(new SetCardInfo("Wei Ambush Force", 85, Rarity.COMMON, mage.cards.w.WeiAmbushForce.class));
        cards.add(new SetCardInfo("Wei Assassins", 86, Rarity.UNCOMMON, mage.cards.w.WeiAssassins.class));
        cards.add(new SetCardInfo("Wei Elite Companions", 87, Rarity.UNCOMMON, mage.cards.w.WeiEliteCompanions.class));
        cards.add(new SetCardInfo("Wei Infantry", 88, Rarity.COMMON, mage.cards.w.WeiInfantry.class));
        cards.add(new SetCardInfo("Wei Night Raiders", 89, Rarity.UNCOMMON, mage.cards.w.WeiNightRaiders.class));
        cards.add(new SetCardInfo("Wei Scout", 90, Rarity.COMMON, mage.cards.w.WeiScout.class));
        cards.add(new SetCardInfo("Wei Strike Force", 91, Rarity.COMMON, mage.cards.w.WeiStrikeForce.class));
        cards.add(new SetCardInfo("Wielding the Green Dragon", 157, Rarity.COMMON, mage.cards.w.WieldingTheGreenDragon.class));
        cards.add(new SetCardInfo("Wolf Pack", 158, Rarity.RARE, mage.cards.w.WolfPack.class));
        cards.add(new SetCardInfo("Wu Admiral", 57, Rarity.UNCOMMON, mage.cards.w.WuAdmiral.class));
        cards.add(new SetCardInfo("Wu Elite Cavalry", 58, Rarity.COMMON, mage.cards.w.WuEliteCavalry.class));
        cards.add(new SetCardInfo("Wu Infantry", 59, Rarity.COMMON, mage.cards.w.WuInfantry.class));
        cards.add(new SetCardInfo("Wu Light Cavalry", 60, Rarity.COMMON, mage.cards.w.WuLightCavalry.class));
        cards.add(new SetCardInfo("Wu Longbowman", 61, Rarity.UNCOMMON, mage.cards.w.WuLongbowman.class));
        cards.add(new SetCardInfo("Wu Scout", 62, Rarity.COMMON, mage.cards.w.WuScout.class));
        cards.add(new SetCardInfo("Wu Spy", 63, Rarity.UNCOMMON, mage.cards.w.WuSpy.class));
        cards.add(new SetCardInfo("Wu Warship", 64, Rarity.COMMON, mage.cards.w.WuWarship.class));
        cards.add(new SetCardInfo("Xiahou Dun, the One-Eyed", 92, Rarity.RARE, mage.cards.x.XiahouDunTheOneEyed.class));
        cards.add(new SetCardInfo("Xun Yu, Wei Advisor", 93, Rarity.RARE, mage.cards.x.XunYuWeiAdvisor.class));
        cards.add(new SetCardInfo("Yellow Scarves Cavalry", 125, Rarity.COMMON, mage.cards.y.YellowScarvesCavalry.class));
        cards.add(new SetCardInfo("Yellow Scarves General", 126, Rarity.RARE, mage.cards.y.YellowScarvesGeneral.class));
        cards.add(new SetCardInfo("Yellow Scarves Troops", 127, Rarity.COMMON, mage.cards.y.YellowScarvesTroops.class));
        cards.add(new SetCardInfo("Young Wei Recruits", 94, Rarity.COMMON, mage.cards.y.YoungWeiRecruits.class));
        cards.add(new SetCardInfo("Yuan Shao's Infantry", 129, Rarity.UNCOMMON, mage.cards.y.YuanShaosInfantry.class));
        cards.add(new SetCardInfo("Yuan Shao, the Indecisive", 128, Rarity.RARE, mage.cards.y.YuanShaoTheIndecisive.class));
        cards.add(new SetCardInfo("Zhang Fei, Fierce Warrior", 32, Rarity.RARE, mage.cards.z.ZhangFeiFierceWarrior.class));
        cards.add(new SetCardInfo("Zhang He, Wei General", 95, Rarity.RARE, mage.cards.z.ZhangHeWeiGeneral.class));
        cards.add(new SetCardInfo("Zhang Liao, Hero of Hefei", 96, Rarity.RARE, mage.cards.z.ZhangLiaoHeroOfHefei.class));
        cards.add(new SetCardInfo("Zhao Zilong, Tiger General", 33, Rarity.RARE, mage.cards.z.ZhaoZilongTigerGeneral.class));
        cards.add(new SetCardInfo("Zhou Yu, Chief Commander", 65, Rarity.RARE, mage.cards.z.ZhouYuChiefCommander.class));
        cards.add(new SetCardInfo("Zhuge Jin, Wu Strategist", 66, Rarity.RARE, mage.cards.z.ZhugeJinWuStrategist.class));
        cards.add(new SetCardInfo("Zodiac Dog", 130, Rarity.COMMON, mage.cards.z.ZodiacDog.class));
        cards.add(new SetCardInfo("Zodiac Dragon", 131, Rarity.RARE, mage.cards.z.ZodiacDragon.class));
        cards.add(new SetCardInfo("Zodiac Goat", 132, Rarity.COMMON, mage.cards.z.ZodiacGoat.class));
        cards.add(new SetCardInfo("Zodiac Horse", 159, Rarity.UNCOMMON, mage.cards.z.ZodiacHorse.class));
        cards.add(new SetCardInfo("Zodiac Monkey", 160, Rarity.COMMON, mage.cards.z.ZodiacMonkey.class));
        cards.add(new SetCardInfo("Zodiac Ox", 161, Rarity.UNCOMMON, mage.cards.z.ZodiacOx.class));
        cards.add(new SetCardInfo("Zodiac Pig", 97, Rarity.UNCOMMON, mage.cards.z.ZodiacPig.class));
        cards.add(new SetCardInfo("Zodiac Rabbit", 162, Rarity.COMMON, mage.cards.z.ZodiacRabbit.class));
        cards.add(new SetCardInfo("Zodiac Rat", 98, Rarity.COMMON, mage.cards.z.ZodiacRat.class));
        cards.add(new SetCardInfo("Zodiac Rooster", 163, Rarity.COMMON, mage.cards.z.ZodiacRooster.class));
        cards.add(new SetCardInfo("Zodiac Snake", 99, Rarity.COMMON, mage.cards.z.ZodiacSnake.class));
        cards.add(new SetCardInfo("Zodiac Tiger", 164, Rarity.UNCOMMON, mage.cards.z.ZodiacTiger.class));
        cards.add(new SetCardInfo("Zuo Ci, the Mocking Sage", 165, Rarity.RARE, mage.cards.z.ZuoCiTheMockingSage.class));
    }
}
