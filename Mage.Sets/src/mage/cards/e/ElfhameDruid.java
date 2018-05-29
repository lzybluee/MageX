package mage.cards.e;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

public final class ElfhameDruid extends CardImpl {

    public ElfhameDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {T}: Add {G}{G}. Spend this mana only to cast kicked spells.
        this.addAbility(new ConditionalColoredManaAbility(new TapSourceCost(), Mana.GreenMana(2), new ElfhameDruidManaBuilder()));
    }

    public ElfhameDruid(final ElfhameDruid card) {
        super(card);
    }

    @Override
    public ElfhameDruid copy() {
        return new ElfhameDruid(this);
    }


}

class ElfhameDruidManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new mage.cards.e.ElfhameDruidConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast kicked spells.";
    }
}

class ElfhameDruidConditionalMana extends ConditionalMana {

    public ElfhameDruidConditionalMana(Mana mana) {
        super(mana);
        addCondition(KickedCondition.instance);
    }
}