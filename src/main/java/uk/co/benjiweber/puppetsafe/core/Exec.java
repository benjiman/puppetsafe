package uk.co.benjiweber.puppetsafe.core;

import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.core.Package.Ensure;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;
import uk.co.benjiweber.puppetsafe.util.ListUtils;

public class Exec implements Puppetable, Identifiable {
	public final String name;
    public final MetaParameters metaParameters;
    private String identifier;

    @Override
    public String getIdentifier() {
        return ListUtils.coalesce(identifier, name);
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, this, builder);
    }

    public Exec(ExecBuilder<PRESENT> builder) {
        this(builder.name, builder.metaParameters);
    }

    private Exec(String name, MetaParameters metaParameters) {
        this.name = name;
        this.metaParameters = metaParameters;
    }

    public static class ExecBuilder<NAME> {
        String name;
        private MetaParameters metaParameters = new MetaParameters();

        public ExecBuilder() {}

        public ExecBuilder(String name, MetaParameters metaParameters) {
            this.name = name;
            this.metaParameters = metaParameters;
        }

        public ExecBuilder<PRESENT> name(String name) {
            return new ExecBuilder<PRESENT>(name, this.metaParameters);
        }

        public static ExecBuilder<ABSENT> with() {
            return new ExecBuilder<ABSENT>();
        }

        public ExecBuilder<NAME> ensure(Ensure ensure) {
            return new ExecBuilder<NAME>(this.name, this.metaParameters);
        }

        public ExecBuilder<NAME> requires(Puppetable puppetable) {
            return new ExecBuilder<NAME>(this.name, metaParameters.plusRequire(puppetable));
        }

    }

    public static Exec exec(ExecBuilder<PRESENT> spec) {
        return new Exec(spec);
    }
}
