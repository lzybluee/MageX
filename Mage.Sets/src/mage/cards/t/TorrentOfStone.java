
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TorrentOfStone extends CardImpl {
    private static final FilterControlledLandPermanent filterSacrifice = new FilterControlledLandPermanent("two Mountains");

    static {
        filterSacrifice.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public TorrentOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");
        this.subtype.add(SubType.ARCANE);


        // Torrent of Stone deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Splice onto Arcane-Sacrifice two Mountains.
        this.addAbility(new SpliceOntoArcaneAbility(new SacrificeTargetCost(new TargetControlledPermanent(2,2, filterSacrifice, false))));
    }

    public TorrentOfStone(final TorrentOfStone card) {
        super(card);
    }

    @Override
    public TorrentOfStone copy() {
        return new TorrentOfStone(this);
    }
}
