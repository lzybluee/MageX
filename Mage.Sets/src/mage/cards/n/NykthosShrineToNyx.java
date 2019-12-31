package mage.cards.n;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class NykthosShrineToNyx extends CardImpl {

    public NykthosShrineToNyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        addSuperType(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Choose a color. Add an amount of mana of that color equal to your devotion to that color.
        this.addAbility(new NykthosShrineToNyxManaAbility());
    }

    private NykthosShrineToNyx(final NykthosShrineToNyx card) {
        super(card);
    }

    @Override
    public NykthosShrineToNyx copy() {
        return new NykthosShrineToNyx(this);
    }
}

class NykthosShrineToNyxManaAbility extends ActivatedManaAbilityImpl {

    NykthosShrineToNyxManaAbility() {
        super(Zone.BATTLEFIELD, new NykthosDynamicManaEffect(), new GenericManaCost(2));
        this.addCost(new TapSourceCost());
        this.addHint(DevotionCount.W.getHint());
        this.addHint(DevotionCount.U.getHint());
        this.addHint(DevotionCount.B.getHint());
        this.addHint(DevotionCount.R.getHint());
        this.addHint(DevotionCount.G.getHint());
    }

    private NykthosShrineToNyxManaAbility(final NykthosShrineToNyxManaAbility ability) {
        super(ability);
    }

    @Override
    public NykthosShrineToNyxManaAbility copy() {
        return new NykthosShrineToNyxManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        ArrayList<Mana> netManaCopy = new ArrayList<>();
        if (game == null) {
            return netManaCopy;
        }
        netManaCopy.addAll(((ManaEffect) this.getEffects().get(0)).getNetMana(game, this));
        return netManaCopy;
    }
}

class NykthosDynamicManaEffect extends ManaEffect {

    NykthosDynamicManaEffect() {
        super();
        this.staticText = "Choose a color. Add an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>";
    }

    private NykthosDynamicManaEffect(final NykthosDynamicManaEffect effect) {
        super(effect);
    }

    @Override
    public NykthosDynamicManaEffect copy() {
        return new NykthosDynamicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        checkToFirePossibleEvents(getMana(game, source), game, source);
        controller.getManaPool().addMana(getMana(game, source), game, source);
        return true;

    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return ChoiceColor.getBaseColors()
                .stream()
                .map(s -> computeMana(s, game, source))
                .filter(mana -> mana.count() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }
        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose a color for devotion of Nykthos");
        if (!controller.choose(outcome, choice, game)) {
            return null;
        }
        return computeMana(choice.getChoice(), game, source);
    }

    private Mana computeMana(String color, Game game, Ability source) {
        Mana mana = new Mana();
        if (color == null || color.isEmpty()) {
            return mana;
        }
        switch (color) {
            case "White":
                mana.setWhite(DevotionCount.W.calculate(game, source, this));
                break;
            case "Blue":
                mana.setBlue(DevotionCount.U.calculate(game, source, this));
                break;
            case "Black":
                mana.setBlack(DevotionCount.B.calculate(game, source, this));
                break;
            case "Red":
                mana.setRed(DevotionCount.R.calculate(game, source, this));
                break;
            case "Green":
                mana.setGreen(DevotionCount.G.calculate(game, source, this));
                break;
        }
        return mana;
    }
}
