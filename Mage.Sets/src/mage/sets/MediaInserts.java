
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class MediaInserts extends ExpansionSet {

    private static final MediaInserts instance = new MediaInserts();

    public static MediaInserts getInstance() {
        return instance;
    }

    private MediaInserts() {
        super("Media Inserts", "MBP", ExpansionSet.buildDate(1990, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Mana Crypt", 1, Rarity.SPECIAL, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Nalathni Dragon", 2, Rarity.SPECIAL, mage.cards.n.NalathniDragon.class));
        cards.add(new SetCardInfo("Windseeker Centaur", 3, Rarity.SPECIAL, mage.cards.w.WindseekerCentaur.class));
    }
}
