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
package mage.abilities.costs;

/**
 * @author LevelX2
 */
public interface OptionalAdditionalCost extends Cost {

    String getName();

    /**
     * Returns the complete text for the addional cost or if onlyCost is true
     * only the pure text fore the included native cost
     *
     * @param onlyCost
     * @return
     */
    String getText(boolean onlyCost);

    /**
     * Returns a reminder text, if the cost has one
     *
     * @return
     */
    String getReminderText();

    /**
     * Returns a text suffix for the game log, that can be added to the cast
     * message.
     *
     * @param position - if there are multiple costs, it's the postion the cost
     * is set (starting with 0)
     * @return
     */
    String getCastSuffixMessage(int position);

    /**
     * If the player intends to pay the cost, the cost will be activated
     *
     */
    void activate();

    /**
     * Reset the activate and count information
     *
     */
    void reset();

    /**
     * Set if the cost be multiple times activated
     *
     * @param repeatable
     */
    void setRepeatable(boolean repeatable);

    /**
     * Can the cost be multiple times activated
     *
     * @return
     */
    boolean isRepeatable();

    /**
     * Returns if the cost was activated
     *
     * @return
     */
    boolean isActivated();

    /**
     * Returns the number of times the cost was activated
     *
     * @return
     */
    int getActivateCount();

}
