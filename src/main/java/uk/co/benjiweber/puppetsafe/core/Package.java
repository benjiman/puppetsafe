package uk.co.benjiweber.puppetsafe.core;

import com.google.common.collect.Sets;
import uk.co.benjiweber.puppetsafe.builder.ABSENT;
import uk.co.benjiweber.puppetsafe.builder.PRESENT;
import uk.co.benjiweber.puppetsafe.serializer.ClassSerializer;
import uk.co.benjiweber.puppetsafe.util.SelfNaming;

import java.util.Arrays;
import java.util.Set;

import static uk.co.benjiweber.puppetsafe.util.ListUtils.coalesce;

public class Package extends SelfNaming implements Puppetable, Identifiable {

	public final String name;
    private String identifier;
    public final Ensure ensure;
    public final MetaParameters metaParameters;

    public final String adminfile;
    public final Boolean allowcdrom;
    public final ConfigFileMode configfiles;
    public final String flavour;
    public final Set<PackageOption> install_options;
    public final Set<PackageOption> uninstall_options;
    public final Provider provider;
    public final String responseFile;
    public final String source;

    public enum ConfigFileMode { keep, replace }
    public enum Provider {
        aix,
        appdmg,
        apple,
        apt,
        aptitude,
        aptrpm,
        blastwave,
        dpkg,
        fink,
        freebsd,
        gem,
        hpux,
        macports,
        msi,
        nim,
        openbsd,
        opkg,
        pacman,
        pip,
        pkg,
        pkgdmg,
        pkgin,
        pkgutil,
        portage,
        ports,
        portupgrade,
        rpm,
        rug,
        sun,
        sunfreeware,
        up2date,
        urpmi,
        windows,
        yum,
        zypper
    }

    public static class PackageOption {
        static class PackageOptionString extends PackageOption {
            private final String option;

            PackageOptionString(String option) {
                this.option = option;
            }

            @Override public String toString() {
                return "'"+this.option+"'";
            }
        }

        static class PackageOptionHash extends PackageOption {
            private final String key;
            private final String value;

            PackageOptionHash(String key, String value) {
                this.key = key;
                this.value = value;
            }

            @Override public String toString() {
                return "{'"+key+"' => '"+value+"'}";
            }
        }
        public static PackageOption opt(String option) {
            return new PackageOptionString(option);
        }
        public static PackageOption opt(String key, String value) {
            return new PackageOptionHash(key, value);
        }
    }

    public void serialize(ClassSerializer serializer, StringBuilder builder) {
        serializer.serialize(this, builder);
    }

    public void serializeAs(MetaParameters.Type type, ClassSerializer serializer, StringBuilder builder) {
        serializer.serializeAs(type, this, builder);
    }

    @Override
    public String getIdentifier() {
        return identifier == null ? getName() : identifier;
    }

    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
        this(builder.name, builder.identifier, builder.adminfile, builder.allowcdrom, builder.configfiles, builder.flavour, builder.install_options, builder.uninstall_options, builder.provider, builder.responseFile, builder.source, builder.ensure, builder.metaParameters);
    }

    private Package(String name, String identifier, String adminfile, Boolean allowcdrom, ConfigFileMode configfiles, String flavour, Set<PackageOption> install_options, Set<PackageOption> uninstall_options, Provider provider, String responseFile, String source, Ensure ensure, MetaParameters metaParameters) {
        this.name = name;
        this.adminfile = adminfile;
        this.allowcdrom = allowcdrom;
        this.configfiles = configfiles;
        this.flavour = flavour;
        this.install_options = install_options;
        this.uninstall_options = uninstall_options;
        this.provider = provider;
        this.responseFile = responseFile;
        this.source = source;
        this.identifier = identifier;
        this.ensure = ensure;
        this.metaParameters = metaParameters;
    }

    public static class PackageBuilder<NAME> {
        String name;
        String identifier;
        private String adminfile;
        private Boolean allowcdrom;
        private ConfigFileMode configfiles;
        private String flavour;
        private Set<PackageOption> install_options;
        private Set<PackageOption> uninstall_options;
        private Provider provider;
        private String responseFile;
        private String source;
        private Ensure ensure = Ensure.installed;
        private MetaParameters metaParameters = new MetaParameters();

        public PackageBuilder() {}

        public PackageBuilder(String name, String identifier, String adminfile, Boolean allowcdrom, ConfigFileMode configFiles, String flavour, Set<PackageOption> install_options, Set<PackageOption> uninstall_options, Provider provider, String responseFile, String source, Ensure ensure, MetaParameters metaParameters) {
        	this.name = name;
            this.identifier = identifier;
            this.adminfile = adminfile;
            this.allowcdrom = allowcdrom;
            this.configfiles = configFiles;
            this.flavour = flavour;
            this.install_options = install_options;
            this.uninstall_options = uninstall_options;
            this.provider = provider;
            this.responseFile = responseFile;
            this.source = source;
            this.ensure = ensure;
            this.metaParameters = metaParameters;
        }

        public PackageBuilder<PRESENT> name(String name) {
            return new PackageBuilder<PRESENT>(name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source, this.ensure, this.metaParameters);
        }

        public static PackageBuilder<ABSENT> pkgWith() {
            return with();
        }

        public static PackageBuilder<ABSENT> with() {
            return new PackageBuilder<ABSENT>();
        }

        public PackageBuilder<NAME> ensure(Ensure ensure) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,ensure, this.metaParameters);
        }
        
        public PackageBuilder<NAME> requires(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, metaParameters.plusRequire(puppetable));
        }

        public PackageBuilder<NAME> before(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, metaParameters.plusBefore(puppetable));
        }

        public PackageBuilder<NAME> notify(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, metaParameters.plusNotify(puppetable));
        }

        public PackageBuilder<NAME> subscribe(Puppetable puppetable) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, metaParameters.plusSubscribe(puppetable));
        }

        public PackageBuilder<NAME> identifier(String identifier) {
            return new PackageBuilder<NAME>(this.name, identifier, this.adminfile,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> adminfile(String path) {
            return new PackageBuilder<NAME>(this.name, this.identifier, path,  this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> allowcdrom(boolean allow) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, allow, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> configfiles(ConfigFileMode configFileMode) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, configFileMode,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> flavour(String flavour) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> install_options(PackageOption... options) {
            return install_options(Sets.newLinkedHashSet(Arrays.asList(options)));
        }

        public PackageBuilder<NAME> install_options(Set<PackageOption> options) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,this.flavour,options,this.uninstall_options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> uninstall_options(PackageOption... options) {
            return uninstall_options(Sets.newLinkedHashSet(Arrays.asList(options)));
        }

        public PackageBuilder<NAME> uninstall_options(Set<PackageOption> options) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,this.flavour,this.install_options,options,this.provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> provider(Provider provider) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,provider,this.responseFile,this.source,this.ensure, this.metaParameters);
        }


        public PackageBuilder<NAME> provider(String responseFile) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,responseFile,this.source,this.ensure, this.metaParameters);
        }

        public PackageBuilder<NAME> source(String source) {
            return new PackageBuilder<NAME>(this.name, this.identifier, this.adminfile, this.allowcdrom, this.configfiles,this.flavour,this.install_options,this.uninstall_options,this.provider,this.responseFile,source,this.ensure, this.metaParameters);
        }
    }

    public static Package pkg(PackageBuilder<PRESENT> spec) {
        return new Package(spec);
    }

}
