package uk.co.benjiweber.puppetsafe.core;

import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;

public interface Puppetable {
    public void serialize(ClassSerializer serializer, StringBuilder builder);
    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder);
}
