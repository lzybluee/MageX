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
package mage.counters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Counters extends HashMap<String, Counter> implements Serializable {

    public Counters() {
    }

    public Counters(final Counters counters) {
        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public Counters copy() {
        return new Counters(this);
    }

    public void addCounter(String name, int amount) {
        putIfAbsent(name, new Counter(name));
        this.get(name).add(amount);
    }

    public void addCounter(Counter counter) {
        if (!containsKey(counter.name)) {
            put(counter.name, counter);
        } else {

            get(counter.name).add(counter.getCount());
        }

    }

    public boolean removeCounter(String name) {
        return removeCounter(name, 1);
    }

    public boolean removeCounter(CounterType counterType, int amount) {
        if (this.containsKey(counterType.getName())) {
            get(counterType.getName()).remove(amount);
            if (get(counterType.getName()).count == 0) {
                this.remove(counterType.getName());
            }
            return true;
        }
        return false;
    }

    public boolean removeCounter(String name, int amount) {
        if (this.containsKey(name)) {
            this.get(name).remove(amount);
            if (this.get(name).getCount() == 0) {
                this.remove(name);
            }
            return true;
        }
        return false;
    }

    public void removeAllCounters(CounterType counterType) {
        removeAllCounters(counterType.getName());
    }

    public void removeAllCounters(String name) {
        if (this.containsKey(name)) {
            this.remove(name);

        }
    }

    public int getCount(String name) {
        if (this.containsKey(name)) {
            return this.get(name).getCount();
        }
        return 0;
    }

    public boolean containsKey(CounterType counterType) {
        return getCount(counterType) > 0;
    }

    public int getCount(CounterType type) {
        if (this.containsKey(type.getName())) {
            return this.get(type.getName()).getCount();
        }
        return 0;
    }

    public List<BoostCounter> getBoostCounters() {
        return values().stream().
                filter(counter -> counter instanceof BoostCounter).
                map(counter -> (BoostCounter) counter).
                collect(Collectors.toList());
    }
}
