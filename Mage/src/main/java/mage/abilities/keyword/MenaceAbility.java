/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class MenaceAbility extends StaticAbility { // Menace may not be a Singleton because the source ability is needed in the continuous effect

    public MenaceAbility() {
        super(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(2));
    }

    public MenaceAbility(final MenaceAbility ability) {
        super(ability);
    }

    @Override
    public Ability copy() {
        return new MenaceAbility(this);
    }

    @Override
    public String getRule() {
        return "Menace <i>(This creature can't be blocked except by two or more creatures.)</i>";
    }

}
