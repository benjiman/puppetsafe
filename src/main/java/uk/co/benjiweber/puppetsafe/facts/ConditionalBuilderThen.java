package uk.co.benjiweber.puppetsafe.facts;

import uk.co.benjiweber.puppetsafe.core.Identifiable;
import uk.co.benjiweber.puppetsafe.core.Puppetable;

import java.util.regex.Pattern;

public class ConditionalBuilderThen<T extends Puppetable & Identifiable> {
    private final Pattern regex;
    private ConditionalBuilder<T> builder;
    private final Fact fact;

    public ConditionalBuilderThen(Pattern regex, Fact fact, ConditionalBuilder<T> builder) {
        this.regex = regex;
        this.fact = fact;
        this.builder = builder;
    }

    public ConditionalBuilder<T> then(T puppetable) {
        return new ConditionalBuilder<T>(builder, regex, fact, puppetable);
    }
}
