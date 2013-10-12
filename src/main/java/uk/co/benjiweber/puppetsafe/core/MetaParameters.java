package uk.co.benjiweber.puppetsafe.core;

import java.util.LinkedHashSet;
import java.util.Set;

public class MetaParameters {

    public enum Type {
        Before,
        Require,
        Notify,
        Subscribe    ;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final Set<Puppetable> befores;
    private final Set<Puppetable> requires;
    private final Set<Puppetable> notifies;
    private final Set<Puppetable> subscribes;

    public MetaParameters() {
        this.befores = new LinkedHashSet<>();
        this.requires = new LinkedHashSet<>();
        this.notifies = new LinkedHashSet<>();
        this.subscribes = new LinkedHashSet<>();
    }

    public MetaParameters(MetaParameters toCopy) {
        this.befores = new LinkedHashSet<>(toCopy.befores);
        this.requires = new LinkedHashSet<>(toCopy.requires);
        this.notifies = new LinkedHashSet<>(toCopy.notifies);
        this.subscribes = new LinkedHashSet<>(toCopy.subscribes);
    }

    public Iterable<Puppetable> befores() {
        return this.befores;
    }

    public Iterable<Puppetable> requires() {
        return this.requires;
    }

    public Iterable<Puppetable> notifies() {
        return this.notifies;
    }

    public Iterable<Puppetable> subscribes() {
        return this.subscribes;
    }

    public MetaParameters plusBefore(Puppetable before) {
        return new MetaParameters(this).addBefore(before);
    }

    public MetaParameters plusRequire(Puppetable require) {
        return new MetaParameters(this).addRequire(require);
    }

    public MetaParameters plusNotify(Puppetable notify) {
        return new MetaParameters(this).addNotify(notify);
    }

    public MetaParameters plusSubscribe(Puppetable subscribe) {
        return new MetaParameters(this).addSubscribe(subscribe);
    }

    private MetaParameters addBefore(Puppetable before) {
        befores.add(before);
        return this;
    }

    private MetaParameters addRequire(Puppetable require) {
        requires.add(require);
        return this;
    }

    private MetaParameters addNotify(Puppetable notify) {
        notifies.add(notify);
        return this;
    }

    private MetaParameters addSubscribe(Puppetable subscribe) {
        subscribes.add(subscribe);
        return this;
    }
}
