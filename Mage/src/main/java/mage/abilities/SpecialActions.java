/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpecialActions extends AbilitiesImpl<SpecialAction> {

    public SpecialActions() {}

    public SpecialActions(final SpecialActions actions) {
        super(actions);
    }

    /**
     *
     * @param controllerId
     * @param manaAction true  = if mana actions should get returned
     *                   false = only non mana actions get returned
     * @return
     */
    public LinkedHashMap<UUID, SpecialAction> getControlledBy(UUID controllerId, boolean manaAction) {
        LinkedHashMap<UUID, SpecialAction> controlledBy = new LinkedHashMap<>();
        for (SpecialAction action: this) {
            if (action.getControllerId().equals(controllerId) && action.isManaAction() == manaAction) {
                controlledBy.put(action.id, action);
            }
        }
        return controlledBy;
    }

    @Override
    public SpecialActions copy() {
        return new SpecialActions(this);
    }

    public void removeManaActions() {
        this.removeIf(SpecialAction::isManaAction);
    }
}
