package uk.co.benjiweber.puppetsafe.facts;

import uk.co.benjiweber.puppetsafe.core.Identifiable;
import uk.co.benjiweber.puppetsafe.core.MetaParameters;
import uk.co.benjiweber.puppetsafe.core.Puppetable;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

import java.util.Set;

public class RuntimeSelected<T extends Puppetable & Identifiable> implements Puppetable {

    public final Set<Conditional<T>> conditionals;

    public RuntimeSelected(Set<Conditional<T>> conditionals) {
        this.conditionals = conditionals;
        String identifier = null;
        for (Conditional<T> conditional : conditionals) {
            identifier = conditional.puppetable.getIdentifier();
        }
        for (Conditional<T> conditional : conditionals) {
            conditional.puppetable.setIdentifier(identifier);
        }
    }

    @Override
    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    @Override
    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, conditionals.iterator().next().puppetable, builder);
    }
}
