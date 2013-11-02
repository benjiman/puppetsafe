package uk.co.benjiweber.puppetsafe.facts;

import com.google.common.collect.Sets;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.core.*;
import uk.co.benjiweber.puppetsafe.core.Package;

import java.util.Set;
import java.util.regex.Pattern;

public class ConditionalBuilder<T extends Puppetable & Identifiable> {

    private Set<Conditional<T>> conditionals = Sets.newLinkedHashSet();
    private Fact fact;

    public ConditionalBuilder(ConditionalBuilder<T> builder, Pattern regex, Fact fact, T puppetable) {
        conditionals.addAll(builder.conditionals);
        conditionals.add(new Conditional<T>(regex, fact, puppetable));
        this.fact = fact;
    }

    public ConditionalBuilder() {

    }

    public ConditionalBuilder(Fact fact) {
        this.fact = fact;
    }

    public ConditionalBuilderThen<T> matches(Pattern regex) {
        return new ConditionalBuilderThen<T>(regex, fact, this);
    }

    public RuntimeSelected<T> toPuppetable() {
        return new RuntimeSelected<T>(this.conditionals);
    }
}
