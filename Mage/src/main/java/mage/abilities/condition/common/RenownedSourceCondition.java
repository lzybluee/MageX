/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Checks if a Permanent is renown
 *
 * @author LevelX2
 */

public enum RenownedSourceCondition implements Condition {

    instance;
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
           return permanent.isRenowned();
        }
        return false;
    }

    @Override
    public String toString() {
        return "it's renowned";
    }
    
    
}