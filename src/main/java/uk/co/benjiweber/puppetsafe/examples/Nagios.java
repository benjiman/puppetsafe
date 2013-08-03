package uk.co.benjiweber.puppetsafe.examples;

import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.core.File;
import uk.co.benjiweber.puppetsafe.core.Include;
import uk.co.benjiweber.puppetsafe.core.Require;

import static uk.co.benjiweber.puppetsafe.core.File.Ensure.directory;
import static uk.co.benjiweber.puppetsafe.core.File.FileBuilder.with;
import static uk.co.benjiweber.puppetsafe.core.File.file;
import static uk.co.benjiweber.puppetsafe.core.Include.include;
import static uk.co.benjiweber.puppetsafe.core.Require.require;

public class Nagios implements Class {

    static Require dependencies = require(Base.class, Nrpe.class);
    static Include include = include(Nagios$$Common.class);

    static File nagiosDir = file(
        with()
            .target("/etc/nagios/")
            .ensure(directory)
    );

    static File nagiosConfig = file(
        with()
            .source("nagios.cfg")
            .target("/etc/nagios/nagios.cfg")
            .requires(nagiosDir)
    );

}
