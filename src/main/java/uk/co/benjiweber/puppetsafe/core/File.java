package uk.co.benjiweber.puppetsafe.core;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;
import uk.co.benjiweber.puppetsafe.util.ListUtils;

public class File implements Puppetable, Identifiable {

    public final String source;
    public final String target;
    public final Ensure ensure;
    public final MetaParameters metaParameters;
    private String identifier;

    @Override
    public String getIdentifier() {
        return ListUtils.coalesce(this.identifier, target);
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, this, builder);
    }

    public static enum Ensure {
        directory,
        link,
        file
    }

    public File(FileBuilder<PRESENT, PRESENT> builder) {
        this(builder.source, builder.target, builder.ensure, builder.metaParameters);
    }

    private File(String source, String target, Ensure ensure, MetaParameters metaParameters) {
        this.source = source;
        this.target = target;
        this.ensure = ensure;
        this.metaParameters = metaParameters;
    }

    public static class FileBuilder<SOURCE, TARGET> {
        String source;
        String target;
        private Ensure ensure = Ensure.file;
        private MetaParameters metaParameters = new MetaParameters();

        public FileBuilder() {}

        public FileBuilder(String source, String target, Ensure ensure, MetaParameters metaParameters) {
            this.source = source;
            this.target = target;
            this.ensure = ensure;
            this.metaParameters = metaParameters;
        }

        public FileBuilder<SOURCE, PRESENT> target(String target) {
            return new FileBuilder<SOURCE, PRESENT>(this.source, target, this.ensure, this.metaParameters);
        }

        public FileBuilder<PRESENT, TARGET> source(String source) {
            return new FileBuilder<PRESENT, TARGET>(source, this.target, this.ensure, this.metaParameters);
        }

        public static FileBuilder<ABSENT, ABSENT> with() {
            return new FileBuilder<ABSENT, ABSENT>();
        }

        public static FileBuilder<ABSENT,ABSENT> fileWith() {
            return with();
        }

        // Links and Directories do not need a Source
        public FileBuilder<PRESENT, TARGET> ensure(Ensure ensure) {
            return new FileBuilder<PRESENT, TARGET>(this.source, this.target, ensure, this.metaParameters);
        }

        public FileBuilder<SOURCE, TARGET> requires(Puppetable puppetable) {
            return new FileBuilder<SOURCE, TARGET>(this.source, this.target, this.ensure, metaParameters.plusRequire(puppetable));
        }

        public FileBuilder<SOURCE, TARGET> before(Puppetable puppetable) {
            return new FileBuilder<SOURCE, TARGET>(this.source, this.target, this.ensure, metaParameters.plusBefore(puppetable));
        }

        public FileBuilder<SOURCE, TARGET> notify(Puppetable puppetable) {
            return new FileBuilder<SOURCE, TARGET>(this.source, this.target, this.ensure, metaParameters.plusNotify(puppetable));
        }

        public FileBuilder<SOURCE, TARGET> subscribe(Puppetable puppetable) {
            return new FileBuilder<SOURCE, TARGET>(this.source, this.target, this.ensure, metaParameters.plusSubscribe(puppetable));
        }
    }

    public static File file(FileBuilder<PRESENT, PRESENT> spec) {
        return new File(spec);
    }


}
