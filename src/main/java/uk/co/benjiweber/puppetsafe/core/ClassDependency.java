package uk.co.benjiweber.puppetsafe.core;

import java.util.Set;

public interface ClassDependency {
    public Set<java.lang.Class<? extends uk.co.benjiweber.puppetsafe.core.Class>> getDependencies();
}
