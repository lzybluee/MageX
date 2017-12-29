package org.mage.card.arcane;

import java.util.ArrayList;

/**
 * Created by stravant@gmail.com on 2016-09-14.
 */
public class TextboxBasicManaRule extends TextboxRule {
    private final String basicManaSymbol;

    public TextboxBasicManaRule(String rule, ArrayList<AttributeRegion> regions, String basicManaSymbol) {
        super(rule, regions, TextboxRuleType.BASIC_MANA);

        this.basicManaSymbol = basicManaSymbol;
    }

    public String getBasicManaSymbol() {
        return basicManaSymbol;
    }
}
