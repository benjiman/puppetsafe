package uk.co.benjiweber.puppetsafe.core;

import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;
import uk.co.benjiweber.puppetsafe.util.ListUtils;

import static uk.co.benjiweber.puppetsafe.util.ListUtils.coalesce;

public class Package implements Puppetable, Identifiable {
	public final String name;
    private String identifier;
    public final Ensure ensure;
    public final MetaParameters metaParameters;

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, this, builder);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public static enum Ensure {
    	present, 
    	installed, 
    	absent, 
    	purged, 
    	held, 
    	latest
    }

    public Package(PackageBuilder<PRESENT> builder) {
        this(builder.name, builder.identifier, builder.ensure, builder.metaParameters);
    }

    private Package(String name, String identifier, Ensure ensure, MetaParameters metaParameters) {
        this.name = name;
        this.identifier = coalesce(identifier, name);
        this.ensure = ensure;
        this.metaParameters = metaParameters;
    }

    public static class PackageBuilder<NAME> {
        String name;
        String identifier;
        private Ensure ensure = Ensure.installed;
        private MetaParameters metaParameters = new MetaParameters();

        public PackageBuilder() {}

        public PackageBuilder(String name, String identifier, Ensure ensure, MetaParameters metaParameters) {
        	this.name = name;
            this.identifier = identifier;
            this.ensure = ensure;
            this.metaParameters = metaParameters;
        }

        public PackageBuilder<PRESENT> name(String name) {
            return new PackageBuilder<PRESENT>(name, this.identifier,  this.ensure, this.metaParameters);
        }

        public static PackageBuilder<ABSENT> pkgWith() {
            return with();
        }

        public static PackageBuilder<ABSENT> with() {
            return new PackageBuilder<ABSENT>();
        }

        public PackageBuilder<NAME> ensure(Ensure ensure) {
            return new PackageBuilder<NAME>(this.name, this.identifier, ensure, this.metaParameters);
        }
        
        public PackageBuilder<NAME> requires(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.ensure, metaParameters.plusRequire(puppetable));
        }

        public PackageBuilder<NAME> before(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.ensure, metaParameters.plusBefore(puppetable));
        }

        public PackageBuilder<NAME> notify(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.ensure, metaParameters.plusNotify(puppetable));
        }

        public PackageBuilder<NAME> subscribe(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.ensure, metaParameters.plusSubscribe(puppetable));
        }

        public PackageBuilder<NAME> identifier(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.ensure, metaParameters.plusNotify(puppetable));
        }

    }

    public static Package pkg(PackageBuilder<PRESENT> spec) {
        return new Package(spec);
    }
	
}
