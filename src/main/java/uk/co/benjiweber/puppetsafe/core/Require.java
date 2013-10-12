package uk.co.benjiweber.puppetsafe.core;

import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class Require implements Puppetable, ClassDependency {
    public final Set<java.lang.Class<? extends Class>> puppetClasses;

    public Require(java.lang.Class<? extends uk.co.benjiweber.puppetsafe.core.Class>... puppetClasses) {
        this.puppetClasses = new LinkedHashSet<java.lang.Class<? extends Class>>(Arrays.asList(puppetClasses));
    }

    public static Require require(java.lang.Class<? extends uk.co.benjiweber.puppetsafe.core.Class>... puppetClasses) {
        return new Require(puppetClasses);
    }

    @Override
    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    @Override
    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {

    }

    @Override
    public Set<java.lang.Class<? extends Class>> getDependencies() {
        return puppetClasses;
    }
}
