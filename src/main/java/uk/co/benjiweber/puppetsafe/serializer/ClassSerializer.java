package uk.co.benjiweber.puppetsafe.serializer;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import uk.co.benjiweber.puppetsafe.core.*;
import uk.co.benjiweber.puppetsafe.core.Package;
import uk.co.benjiweber.puppetsafe.examples.Nagios;
import uk.co.benjiweber.puppetsafe.facts.Conditional;
import uk.co.benjiweber.puppetsafe.facts.RuntimeSelected;

import java.lang.Class;
import java.lang.reflect.Field;
import java.util.Collection;

public class ClassSerializer {

    private int indentLevel = 1;

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
                serializePuppetable(puppetClass, field, builder);
            }
        }
    }

    public void serializePuppetable(java.lang.Class<? extends uk.co.benjiweber.puppetsafe.core.Class> puppetClass, Field field, StringBuilder builder) {
        try {
            Puppetable puppetable = (Puppetable) field.get(null);
            puppetable.serialize(this, builder);
        } catch (NullPointerException e) {
            throw new NonStaticDeclarationException(puppetClass, field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {
        System.out.println(new ClassSerializer().serialize(Nagios.class));
    }

    public void serialize(File file, StringBuilder builder) {
        builder
            .append("\n").append(indent()).append("file { ").append("'").append(file.target).append("':\n")
            .append(indentMore()).append("path => '").append(file.target).append("',\n")
            .append(indent()).append("ensure => '").append(file.ensure).append("',\n");
        if (file.source != null)
            builder.append(indent()).append("source => '").append(file.source).append("',\n");
        serializeDependencies(file.metaParameters, builder);
        builder.append(indentLess()).append("}\n");
    }

    public void serialize(RuntimeSelected<? extends Puppetable> runtimeSelected, StringBuilder stringBuilder) {
        for (Conditional conditional : runtimeSelected.conditionals) {
            stringBuilder.append("\n").append(indent()).append("if (").append(conditional.fact).append(" ~= /").append(conditional.regex).append("/ ) {\n ");
            indentMore();
            conditional.puppetable.serialize(this, stringBuilder);
            indentLess();
            stringBuilder.append("\n").append(indent()).append("}\n");
        }
    }

    private String indentMore() {
        indentLevel++;
        return indent();
    }

    private String indent() {
        StringBuilder indenter = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) indenter.append("\t");
        return indenter.toString();
    }

    private String indentLess() {
        indentLevel--;
        return indent();
    }


    public void serialize(Package pkg, StringBuilder builder) {
        builder
            .append("\n").append(indent()).append("package { ").append("'").append(pkg.getIdentifier()).append("':\n")
            .append(indentMore()).append("name => '").append(pkg.name).append("',\n")
            .append(indent()).append("ensure => '").append(pkg.ensure).append("',\n");
        serializeDependencies(pkg.metaParameters, builder);
        builder.append(indentLess()).append("}\n");
    }

    public void serializeAs(MetaParameters.Type type, Identifiable identifiable, StringBuilder builder) {
        builder.append(indent()).append(type).append(" => ").append(identifiable.getClass().getSimpleName()).append("['").append(identifiable.getIdentifier()).append("'],\n");
    }

    private void serializeDependencies(MetaParameters dependencies, StringBuilder builder) {
        for (Puppetable puppetable : dependencies.befores()) {
            puppetable.serializeAs(MetaParameters.Type.Before, this, builder);
        }
        for (Puppetable puppetable : dependencies.requires()) {
            puppetable.serializeAs(MetaParameters.Type.Require, this, builder);
        }
        for (Puppetable puppetable : dependencies.notifies()) {
            puppetable.serializeAs(MetaParameters.Type.Notify, this, builder);
        }
        for (Puppetable puppetable : dependencies.subscribes()) {
            puppetable.serializeAs(MetaParameters.Type.Subscribe, this, builder);
        }
    }

	public void serialize(Exec exec, StringBuilder builder) {
        builder
            .append("\n\texec { ").append("'").append(exec.name).append("':\n");
        serializeDependencies(exec.metaParameters, builder);
        builder.append("\t}\n");
	}

	public void serialize(ClassDependency include, StringBuilder builder) {
		Collection<String> names = Collections2.transform(include.getDependencies(), new ToClassNames());
		builder.append("\n\t").append(include.getClass().getSimpleName().toLowerCase()).append(" ").append(Joiner.on(",").join(names)).append("\n");
	}

    static class ToClassNames implements Function<Class<?>, String> {
        public String apply(Class<?> aClass) {
            return aClass.getSimpleName().toLowerCase().replaceAll("\\$\\$","::");
        }
    }

}
