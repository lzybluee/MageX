/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class StaticFilters {

    public static final FilterSpiritOrArcaneCard SPIRIT_OR_ARCANE_CARD = new FilterSpiritOrArcaneCard();

    public static final FilterEnchantmentPermanent FILTER_ENCHANTMENT_PERMANENT = new FilterEnchantmentPermanent();

    public static final FilterArtifactCard FILTER_CARD_ARTIFACT = new FilterArtifactCard();
    public static final FilterCard FILTER_CARD_ARTIFACT_OR_CREATURE = new FilterCard("artifact or creature card");
    public static final FilterCreatureCard FILTER_CARD_CREATURE = new FilterCreatureCard();
    public static final FilterCreatureCard FILTER_CARD_CREATURE_YOUR_GRAVEYARD = new FilterCreatureCard("creature card from your graveyard");
    public static final FilterCard FILTER_CARD_LAND = new FilterLandCard();
    public static final FilterNonlandCard FILTER_CARD_NON_LAND = new FilterNonlandCard();
    public static final FilterNonlandCard FILTER_CARD_A_NON_LAND = new FilterNonlandCard("a nonland card");

    public static final FilterPermanent FILTER_PERMANENT = new FilterPermanent();
    public static final FilterArtifactOrEnchantmentPermanent FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT = new FilterArtifactOrEnchantmentPermanent();
    public static final FilterCreaturePermanent FILTER_ARTIFACT_CREATURE_PERMANENT = new FilterArtifactCreaturePermanent();
    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_OR_CREATURE = new FilterPermanent("artifact or creature");
    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT = new FilterPermanent("artifact, creature, or enchantment");
    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND = new FilterPermanent("artifact, creature, enchantment, or land");

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT = new FilterControlledPermanent();
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT = new FilterControlledArtifactPermanent();
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE = new FilterControlledPermanent("artifact or creature you control");
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_LAND = new FilterControlledLandPermanent();

    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT = new FilterPermanent("permanent an opponent controls");
    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT_CREATURE = new FilterCreaturePermanent("creature an opponent controls");
    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT_ARTIFACT = new FilterPermanent("artifact an opponent controls");
    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE = new FilterPermanent("artifact or creature an opponent controls");

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_CREATURE = new FilterControlledCreaturePermanent();
    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_A_CREATURE = new FilterControlledCreaturePermanent("a creature you control");
    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_ANOTHER_CREATURE = new FilterControlledCreaturePermanent("another creature");
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_NON_LAND = new FilterControlledPermanent("nonland permanent");
    public static final FilterLandPermanent FILTER_LAND = new FilterLandPermanent();
    public static final FilterLandPermanent FILTER_LANDS = new FilterLandPermanent("lands");
    public static final FilterBasicLandCard FILTER_BASIC_LAND_CARD = new FilterBasicLandCard();

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE = new FilterCreaturePermanent();
    public static final FilterCreaturePermanent FILTER_PERMANENT_A_CREATURE = new FilterCreaturePermanent("a creature");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_CONTROLLED = new FilterCreaturePermanent("creature you control");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES = new FilterCreaturePermanent("creatures");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES_CONTROLLED = new FilterCreaturePermanent("creatures you control");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_GOBLINS = new FilterCreaturePermanent(SubType.GOBLIN, "Goblin creatures");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_SLIVERS = new FilterCreaturePermanent(SubType.SLIVER, "all Sliver creatures");
    public static final FilterPlaneswalkerPermanent FILTER_PERMANENT_PLANESWALKER = new FilterPlaneswalkerPermanent();

    public static final FilterPermanent FILTER_PERMANENT_NON_LAND = new FilterNonlandPermanent();

    public static final FilterCreatureSpell FILTER_SPELL_A_CREATURE = new FilterCreatureSpell("a creature spell");
    public static final FilterSpell FILTER_SPELL_NON_CREATURE
            = (FilterSpell) new FilterSpell("noncreature spell").add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));

    public static final FilterSpell FILTER_SPELL = new FilterSpell();
    public static final FilterSpell FILTER_A_SPELL = new FilterSpell("a spell");

    public static final FilterSpell FILTER_INSTANT_OR_SORCERY_SPELL = new FilterSpell("instant or sorcery spell");
    public static final FilterSpell FILTER_INSTANT_OR_SORCERY_SPELLS = new FilterSpell("instant or sorcery spells");

    public static final FilterPermanent FILTER_CREATURE_TOKENS = new FilterCreaturePermanent("creature tokens");

    public static final FilterCreaturePermanent FILTER_ATTACKING_CREATURES = new FilterCreaturePermanent("attacking creatures");

    public static final FilterPermanent FILTER_PERMANENT_AURA = new FilterPermanent();
    public static final FilterPermanent FILTER_PERMANENT_EQUIPMENT = new FilterPermanent();
    public static final FilterPermanent FILTER_PERMANENT_FORTIFICATION = new FilterPermanent();
    public static final FilterPermanent FILTER_PERMANENT_LEGENDARY = new FilterPermanent();

    static {
        FILTER_PERMANENT_AURA.add(new CardTypePredicate(CardType.ENCHANTMENT));
        FILTER_PERMANENT_AURA.add(new SubtypePredicate(SubType.AURA));

        FILTER_PERMANENT_EQUIPMENT.add(new CardTypePredicate(CardType.ARTIFACT));
        FILTER_PERMANENT_EQUIPMENT.add(new SubtypePredicate(SubType.EQUIPMENT));

        FILTER_PERMANENT_FORTIFICATION.add(new CardTypePredicate(CardType.ARTIFACT));
        FILTER_PERMANENT_FORTIFICATION.add(new SubtypePredicate(SubType.FORTIFICATION));

        FILTER_PERMANENT_LEGENDARY.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    static {
        FILTER_CONTROLLED_PERMANENT_NON_LAND.add(
                Predicates.not(new CardTypePredicate(CardType.LAND))
        );

        FILTER_CREATURE_TOKENS.add(new TokenPredicate());

        FILTER_ATTACKING_CREATURES.add(new AttackingPredicate());

        FILTER_PERMANENT_CREATURE_CONTROLLED.add(new ControllerPredicate(TargetController.YOU));
        FILTER_PERMANENT_CREATURES_CONTROLLED.add(new ControllerPredicate(TargetController.YOU));

        FILTER_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
        FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)
        ));
        FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)
        ));
        FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));

        FILTER_OPPONENTS_PERMANENT.add(new ControllerPredicate(TargetController.OPPONENT));

        FILTER_OPPONENTS_PERMANENT_CREATURE.add(new ControllerPredicate(TargetController.OPPONENT));

        FILTER_OPPONENTS_PERMANENT_ARTIFACT.add(new ControllerPredicate(TargetController.OPPONENT));
        FILTER_OPPONENTS_PERMANENT_ARTIFACT.add(new CardTypePredicate(CardType.ARTIFACT));

        FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE.add(new ControllerPredicate(TargetController.OPPONENT));
        FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));

        FILTER_CONTROLLED_ANOTHER_CREATURE.add(new AnotherPredicate());

        FILTER_CARD_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));

        FILTER_INSTANT_OR_SORCERY_SPELL.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));

        FILTER_INSTANT_OR_SORCERY_SPELLS.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    private StaticFilters() {
    }

}
