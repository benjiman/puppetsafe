package uk.co.benjiweber.puppetsafe.facts;

import uk.co.benjiweber.puppetsafe.core.Identifiable;
import uk.co.benjiweber.puppetsafe.core.MetaParameters;
import uk.co.benjiweber.puppetsafe.core.Puppetable;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;
import uk.co.benjiweber.puppetsafe.util.SelfNaming;

import java.util.Set;

public class RuntimeSelected<T extends Puppetable & Identifiable> implements Puppetable, WrapsPuppetable {

    public final Set<Conditional<T>> conditionals;

    public RuntimeSelected(Set<Conditional<T>> conditionals) {
        this.conditionals = conditionals;
    }

    @Override
    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    @Override
    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, conditionals.iterator().next().puppetable, builder);
    }

    @Override
    public boolean wraps(SelfNaming puppetable) {
        for (Conditional<T> conditional : conditionals)
            if (conditional.puppetable == puppetable)
                return true;

        return false;
    }
}
