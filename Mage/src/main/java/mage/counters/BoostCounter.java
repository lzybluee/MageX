

package mage.counters;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoostCounter extends Counter {

    protected final int power;
    protected final int toughness;

    public BoostCounter(int power, int toughness) {
        this(power, toughness, 1);
    }

    public BoostCounter(int power, int toughness, int count) {
        super(String.format("%1$+d/%2$+d", power, toughness), count);
        this.power = power;
        this.toughness = toughness;
    }

    public BoostCounter(final BoostCounter counter) {
        super(counter);
        this.power = counter.power;
        this.toughness = counter.toughness;
    }

    public int getPower() {
        return power;
    }

    public int getToughness() {
        return toughness;
    }

    @Override
    public BoostCounter copy() {
        return new BoostCounter(this);
    }
}
