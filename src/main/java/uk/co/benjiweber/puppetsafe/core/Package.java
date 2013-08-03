package uk.co.benjiweber.puppetsafe.core;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

public class Package implements Puppetable {
	public final String name;
    public final Ensure ensure;
    public final Set<Puppetable> dependencies;

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAsDependency(ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAsDependency(this, builder);
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
        this(builder.name, builder.ensure, builder.dependencies);
    }

    private Package(String name, Ensure ensure, Set<Puppetable> dependencies) {
        this.name = name;
        this.ensure = ensure;
        this.dependencies = dependencies;
    }

    public static class PackageBuilder<NAME> {
        String name;
        private Ensure ensure = Ensure.installed;
        private Set<Puppetable> dependencies = new LinkedHashSet<Puppetable>();

        public PackageBuilder() {}

        public PackageBuilder(String name, Ensure ensure, Set<Puppetable> dependencies) {
        	this.name = name;
            this.ensure = ensure;
            this.dependencies = dependencies;
        }

        public PackageBuilder<PRESENT> name(String name) {
            return new PackageBuilder<PRESENT>(name, this.ensure, this.dependencies);
        }

        public static PackageBuilder<ABSENT> with() {
            return new PackageBuilder<ABSENT>();
        }

        public PackageBuilder<NAME> ensure(Ensure ensure) {
            return new PackageBuilder<NAME>(this.name, ensure, this.dependencies);
        }
        
        public PackageBuilder<NAME> requires(Puppetable puppetable) {
            LinkedHashSet<Puppetable> newDependencies = new LinkedHashSet<Puppetable>(dependencies);
            newDependencies.add(puppetable);
            return new PackageBuilder<NAME>(this.name, this.ensure, newDependencies);
        }

    }

    public static Package pkg(PackageBuilder<PRESENT> spec) {
        return new Package(spec);
    }
	
}
