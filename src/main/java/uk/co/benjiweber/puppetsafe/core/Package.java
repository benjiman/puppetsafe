package uk.co.benjiweber.puppetsafe.core;

import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

public class Package implements Puppetable {
	public final String name;
    public final Ensure ensure;
    public final MetaParameters metaParameters;

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, this, builder);
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
        this(builder.name, builder.ensure, builder.metaParameters);
    }

    private Package(String name, Ensure ensure, MetaParameters metaParameters) {
        this.name = name;
        this.ensure = ensure;
        this.metaParameters = metaParameters;
    }

    public static class PackageBuilder<NAME> {
        String name;
        private Ensure ensure = Ensure.installed;
        private MetaParameters metaParameters = new MetaParameters();

        public PackageBuilder() {}

        public PackageBuilder(String name, Ensure ensure, MetaParameters metaParameters) {
        	this.name = name;
            this.ensure = ensure;
            this.metaParameters = metaParameters;
        }

        public PackageBuilder<PRESENT> name(String name) {
            return new PackageBuilder<PRESENT>(name, this.ensure, this.metaParameters);
        }

        public static PackageBuilder<ABSENT> pkgWith() {
            return with();
        }

        public static PackageBuilder<ABSENT> with() {
            return new PackageBuilder<ABSENT>();
        }

        public PackageBuilder<NAME> ensure(Ensure ensure) {
            return new PackageBuilder<NAME>(this.name, ensure, this.metaParameters);
        }
        
        public PackageBuilder<NAME> requires(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.ensure, metaParameters.plusRequire(puppetable));
        }

        public PackageBuilder<NAME> before(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.ensure, metaParameters.plusBefore(puppetable));
        }

        public PackageBuilder<NAME> notify(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.ensure, metaParameters.plusNotify(puppetable));
        }

        public PackageBuilder<NAME> subscribe(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.ensure, metaParameters.plusSubscribe(puppetable));
        }

    }

    public static Package pkg(PackageBuilder<PRESENT> spec) {
        return new Package(spec);
    }
	
}
