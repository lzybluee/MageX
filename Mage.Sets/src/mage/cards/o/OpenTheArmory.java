package mage.cards.o;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class OpenTheArmory extends CardImpl {

    private static final FilterCard auraOrEquipmentTarget = new FilterCard("Aura or Equipment card");

    static {
        auraOrEquipmentTarget.add(Predicates.or(
                new SubtypePredicate(SubType.EQUIPMENT),
                new SubtypePredicate(SubType.AURA)));
    }

    public OpenTheArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Search your library for an Aura or Equipment card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, auraOrEquipmentTarget), true, true));
    }

    public OpenTheArmory(final OpenTheArmory card) {
        super(card);
    }

    @Override
    public OpenTheArmory copy() {
        return new OpenTheArmory(this);
    }
}