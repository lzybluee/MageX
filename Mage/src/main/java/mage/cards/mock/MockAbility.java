package mage.cards.mock;

import mage.abilities.AbilityImpl;
import mage.constants.AbilityType;
import mage.constants.Zone;

class MockAbility extends AbilityImpl {

    private final String text;

    public MockAbility(String text) {
        super(AbilityType.STATIC, Zone.ALL);
        this.text = text;
    }

    @Override
    public MockAbility copy() {
        return this;
    }

    @Override
    public String getRule(boolean all) {
        return text;
    }
}
