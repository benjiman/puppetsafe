package uk.co.benjiweber.puppetsafe.serializer;

import org.junit.Test;
import uk.co.benjiweber.puppetsafe.examples.Nagios;

import static org.junit.Assert.assertEquals;

public class NagiosExampleSerializationTest {

    ClassSerializer serializer = new ClassSerializer();

    @Test
    public void ensureExampleSerializedAsExpected() {
         assertEquals("class Nagios {\n" +
                 "\n" +
                 "\trequire base,nrpe\n" +
                 "\n" +
                 "\tinclude nagios::common\n" +
                 "\n" +
                 "\tfile { 'nagiosDir':\n" +
                 "\t\tpath => '/etc/nagios/',\n" +
                 "\t\tensure => 'directory',\n" +
                 "\t}\n" +
                 "\n" +
                 "\tfile { 'etc':\n" +
                 "\t\tpath => '/etc/',\n" +
                 "\t\tensure => 'directory',\n" +
                 "\t\tbefore => File['nagiosDir'],\n" +
                 "\t}\n" +
                 "\n" +
                 "\tfile { 'otherConfig':\n" +
                 "\t\tpath => '/etc/nagios/nagios-something.cfg',\n" +
                 "\t\tensure => 'file',\n" +
                 "\t\tsource => 'nagios.cfg',\n" +
                 "\t\trequire => File['nagiosDir'],\n" +
                 "\t}\n" +
                 "\n" +
                 "\tif ($operatingsystem ~= /Fedora/ ) {\n" +
                 " \n" +
                 "\t\tpackage { 'nagios':\n" +
                 "\t\t\tname => 'nagios3',\n" +
                 "\t\t\tensure => 'latest',\n" +
                 "\t\t\tsubscribe => File['otherConfig'],\n" +
                 "\t\t}\n" +
                 "\n" +
                 "\t}\n" +
                 "\n" +
                 "\tif ($operatingsystem ~= /SUSE/ ) {\n" +
                 " \n" +
                 "\t\tpackage { 'nagios':\n" +
                 "\t\t\tname => 'nagios',\n" +
                 "\t\t\tensure => 'latest',\n" +
                 "\t\t\tsubscribe => File['otherConfig'],\n" +
                 "\t\t}\n" +
                 "\n" +
                 "\t}\n" +
                 "\n" +
                 "\tfile { 'nagiosConfig':\n" +
                 "\t\tpath => '/etc/nagios/nagios.cfg',\n" +
                 "\t\tensure => 'file',\n" +
                 "\t\tsource => 'nagios.cfg',\n" +
                 "\t\trequire => File['nagiosDir'],\n" +
                 "\t\tnotify => Package['nagios'],\n" +
                 "\t}\n" +
                 "\n" +
                 "}", serializer.serialize(Nagios.class));
    }
}
