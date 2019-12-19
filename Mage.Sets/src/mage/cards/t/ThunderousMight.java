package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThunderousMight extends CardImpl {

    private static final DynamicValue xValue = new DevotionCount(ColoredManaSymbol.R);

    public ThunderousMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks, it gets +X/+0 until end of turn, where X is your devotion to red.
        BoostEnchantedEffect effect = new BoostEnchantedEffect(xValue, new StaticValue(0), Duration.EndOfTurn);
        effect.setText("it gets +X/+0 until end of turn, where X is your devotion to red");
        effect.setLockedIn(true);
        this.addAbility(new AttacksAttachedTriggeredAbility(effect, AttachmentType.AURA, false)
                .addHint(new ValueHint("Devotion to red", xValue)));
    }

    public ThunderousMight(final ThunderousMight card) {
        super(card);
    }

    @Override
    public ThunderousMight copy() {
        return new ThunderousMight(this);
    }
}
