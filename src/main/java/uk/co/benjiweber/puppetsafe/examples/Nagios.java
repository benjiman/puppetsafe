package uk.co.benjiweber.puppetsafe.examples;

import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.core.File;

import static uk.co.benjiweber.puppetsafe.core.File.Ensure.directory;
import static uk.co.benjiweber.puppetsafe.core.File.FileBuilder.with;
import static uk.co.benjiweber.puppetsafe.core.File.file;

public class Nagios implements Class {

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
