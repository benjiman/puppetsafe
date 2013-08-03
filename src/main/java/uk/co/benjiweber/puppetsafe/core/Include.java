package uk.co.benjiweber.puppetsafe.core;

import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Include implements Puppetable, ClassDependency {
    public final Set<java.lang.Class<? extends Class>> puppetClasses;

    public Include(java.lang.Class<? extends Class>... puppetClasses) {
        this.puppetClasses = new LinkedHashSet<java.lang.Class<? extends Class>>(Arrays.asList(puppetClasses));
    }

    public static Include include(java.lang.Class<? extends Class>... puppetClasses) {
        return new Include(puppetClasses);
    }

    @Override
    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    @Override
    public void serializeAsDependency(ClassSerializer serializer, StringBuilder builder) {
    }

    @Override
    public Set<java.lang.Class<? extends Class>> getDependencies() {
        return puppetClasses;
    }
}
