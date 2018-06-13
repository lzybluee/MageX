

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class RiptideReplicatorToken extends TokenImpl {

    public RiptideReplicatorToken() {
        this(null, null, 1);
    }
    public RiptideReplicatorToken(ObjectColor color, SubType type, int x) {
        super(type != null ? type.getDescription() : "", "X/X creature token of the chosen color and type");
        cardType.add(CardType.CREATURE);
        if (color != null) {
            this.color.setColor(color);
        }
        if (type != null) {
            subtype.add(type);
        }
        power = new MageInt(x);
        toughness = new MageInt(x);
    }

    public RiptideReplicatorToken(final RiptideReplicatorToken token) {
        super(token);
    }

    public RiptideReplicatorToken copy() {
        return new RiptideReplicatorToken(this);
    }
}
