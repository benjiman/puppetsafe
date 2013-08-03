package uk.co.benjiweber.puppetsafe.core;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.core.Package.Ensure;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

public class Exec implements Puppetable{
	public final String name;
    public final Set<Puppetable> dependencies;

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAsDependency(ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAsDependency(this, builder);
    }

    public Exec(ExecBuilder<PRESENT> builder) {
        this(builder.name, builder.dependencies);
    }

    private Exec(String name, Set<Puppetable> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
    }

    public static class ExecBuilder<NAME> {
        String name;
        private Set<Puppetable> dependencies = new LinkedHashSet<Puppetable>();

        public ExecBuilder() {}

        public ExecBuilder(String name, Set<Puppetable> dependencies) {
            this.name = name;
            this.dependencies = dependencies;
        }

        public ExecBuilder<PRESENT> name(String name) {
            return new ExecBuilder<PRESENT>(name, this.dependencies);
        }

        public static ExecBuilder<ABSENT> with() {
            return new ExecBuilder<ABSENT>();
        }

        public ExecBuilder<NAME> ensure(Ensure ensure) {
            return new ExecBuilder<NAME>(this.name, this.dependencies);
        }

        public ExecBuilder<NAME> requires(Puppetable puppetable) {
            LinkedHashSet<Puppetable> newDependencies = new LinkedHashSet<Puppetable>(dependencies);
            newDependencies.add(puppetable);
            return new ExecBuilder<NAME>(this.name, newDependencies);
        }

    }

    public static Exec exec(ExecBuilder<PRESENT> spec) {
        return new Exec(spec);
    }
}
