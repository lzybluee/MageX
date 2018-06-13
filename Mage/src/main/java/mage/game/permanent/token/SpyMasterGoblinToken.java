

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author spjspj
 */
public final class SpyMasterGoblinToken extends TokenImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SpyMasterGoblinToken() {
        super("Goblin", "1/1 red Goblin creature token with \"Creatures you control attack each combat if able.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GOBLIN);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Effect effect = new AttacksIfAbleAllEffect(filter, Duration.WhileOnBattlefield, true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public SpyMasterGoblinToken(final SpyMasterGoblinToken token) {
        super(token);
    }

    public SpyMasterGoblinToken copy() {
        return new SpyMasterGoblinToken(this);
    }
}
