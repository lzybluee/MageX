/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.filter;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class FilterAbility extends FilterImpl<Ability> {

    public FilterAbility() {
        super("");
    }

    public FilterAbility(String name) {
        super(name);
    }

    public FilterAbility(FilterAbility filter) {
        super(filter);
    }

    @Override
    public FilterAbility copy() {
        return new FilterAbility(this);
    }

    public static Predicate<Ability> zone(Zone zone) {
        return new AbilityZonePredicate(zone);
    }

    public static Predicate<Ability> type(AbilityType type) {
        return new AbilityTypePredicate(type);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Ability;
    }

    private static final class AbilityZonePredicate implements Predicate<Ability> {

        private final Zone zone;

        public AbilityZonePredicate(Zone zone) {
            this.zone = zone;
        }

        @Override
        public boolean apply(Ability input, Game game) {
            return input.getZone().match(zone);
        }

        @Override
        public String toString() {
            return "Zone(" + zone.toString() + ')';
        }
    }

    private static final class AbilityTypePredicate implements Predicate<Ability> {

        private final AbilityType type;

        public AbilityTypePredicate(AbilityType type) {
            this.type = type;
        }

        @Override
        public boolean apply(Ability input, Game game) {
            return input.getAbilityType() == type;
        }

        @Override
        public String toString() {
            return "AbilityType(" + type + ')';
        }
    }
}
