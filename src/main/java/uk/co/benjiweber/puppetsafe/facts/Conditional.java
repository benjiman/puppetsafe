package uk.co.benjiweber.puppetsafe.facts;

import uk.co.benjiweber.puppetsafe.core.Identifiable;
import uk.co.benjiweber.puppetsafe.core.Puppetable;

import java.util.regex.Pattern;

public class Conditional<T extends Puppetable & Identifiable> {
    public final Pattern regex;
    public final Fact fact;
    public final T puppetable;

    public Conditional(Pattern regex, Fact fact, T puppetable) {
        this.regex = regex;
        this.fact = fact;
        this.puppetable = puppetable;
    }

}
