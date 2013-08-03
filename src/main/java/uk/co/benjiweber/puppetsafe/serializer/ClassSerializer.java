package uk.co.benjiweber.puppetsafe.serializer;

import uk.co.benjiweber.puppetsafe.core.*;
import uk.co.benjiweber.puppetsafe.core.Package;
import uk.co.benjiweber.puppetsafe.examples.Nagios;

import java.lang.reflect.Field;
import java.util.Set;

public class ClassSerializer {

    public String serialize(java.lang.Class<? extends uk.co.benjiweber.puppetsafe.core.Class> puppetClass) {
        StringBuilder builder = new StringBuilder();
        builder.append("class ")
        .append(puppetClass.getSimpleName())
                .append(" {\n");

        serializeMembers(puppetClass, builder);

        builder.append("\n}");
        return builder.toString();
    }

    private void serializeMembers(java.lang.Class<? extends uk.co.benjiweber.puppetsafe.core.Class> puppetClass, StringBuilder builder) {
        for (Field field : puppetClass.getDeclaredFields()) {
            if (Puppetable.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                serializePuppetable(field, builder);
            }
        }
    }

    public void serializePuppetable(Field field, StringBuilder builder) {
        try {
            Puppetable puppetable = (Puppetable) field.get(null);
            puppetable.serialize(this, builder);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        System.out.println(new ClassSerializer().serialize(Nagios.class));
    }

    public void serialize(File file, StringBuilder builder) {
        builder
            .append("\n\tfile { ").append("'").append(file.target).append("':\n")
            .append("\t\tpath => '").append(file.target).append("',\n")
            .append("\t\tensure => '").append(file.ensure).append("',\n");
        if (file.source != null)
            builder.append("\t\tsource => '").append(file.source).append("',\n");
        serializeDependencies(file.dependencies, builder);
        builder.append("\t}\n");
    }
    
    public void serialize(Package pkg, StringBuilder builder) {
        builder
            .append("\n\tpackage { ").append("'").append(pkg.name).append("':\n")
            .append("\t\tensure => '").append(pkg.ensure).append("',\n");
        serializeDependencies(pkg.dependencies, builder);
        builder.append("\t}\n");
    }
    

    private void serializeDependencies(Set<Puppetable> dependencies, StringBuilder builder) {
        for (Puppetable puppetable : dependencies) {
            puppetable.serializeAsDependency(this, builder);
        }
    }

    public void serializeAsDependency(File file, StringBuilder builder) {
        builder.append("\t\trequire => File['").append(file.target).append("'],\n");
    }

	public void serializeAsDependency(Package pkg, StringBuilder builder) {
		 builder.append("\t\trequire => Package['").append(pkg.name).append("'],\n");
	}
}
