package uk.co.benjiweber.puppetsafe.facts;

import uk.co.benjiweber.puppetsafe.core.Identifiable;
import uk.co.benjiweber.puppetsafe.core.Puppetable;
import uk.co.benjiweber.puppetsafe.util.SelfNaming;

public class Fact extends SelfNaming {

    @Override public boolean equals(Object other) {
        if (other instanceof String) return ((String)other).equals(this.toString());

        return super.equals(other);
    }

    public <T extends Puppetable & Identifiable> ConditionalBuilder<T> when(Class<T> cls) {
        return new ConditionalBuilder<T>(this);
    }

}
