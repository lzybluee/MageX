
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public class YodaEmblem extends Emblem {
    // You get an emblem with "Hexproof, you and your creatures have."

    public YodaEmblem() {
        this.setName("Emblem Yoda, Jedi Master");
        Effect effect = new GainAbilityControllerEffect(HexproofAbility.getInstance(), Duration.EndOfGame);
        effect.setText("Hexproof, you");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        effect = new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfGame, new FilterCreaturePermanent());
        effect.setText(" you and your creatures have");
        ability.addEffect(effect);
        getAbilities().add(ability);
    }
}
