package uk.co.benjiweber.puppetsafe.examples;

import com.google.common.collect.Sets;
import uk.co.benjiweber.puppetsafe.core.*;
import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.core.Package;

import static com.google.common.collect.Sets.newHashSet;
import static uk.co.benjiweber.puppetsafe.core.Package.ConfigFileMode.replace;
import static uk.co.benjiweber.puppetsafe.core.Package.PackageBuilder.pkgWith;
import static uk.co.benjiweber.puppetsafe.core.Package.PackageBuilder.with;
import static uk.co.benjiweber.puppetsafe.core.Package.PackageOption.opt;
import static uk.co.benjiweber.puppetsafe.core.Package.Provider.zypper;
import static uk.co.benjiweber.puppetsafe.core.Package.pkg;

public class PackageTypeReference implements Class {

    static Package example = pkg(
        with()
            .identifier("example")
            .name("my-package")
            .source("puppet:///foo/bar.rpm")
            .adminfile("/example/path")
            .allowcdrom(false)
            .configfiles(replace)
            .flavour("sweet")
            .install_options(opt("/S"), opt("INSTALLDIR", "/opt/foo"))
            .uninstall_options(opt("REMOVE", "Sync,VSS"))
            .provider(zypper)
    );
}
