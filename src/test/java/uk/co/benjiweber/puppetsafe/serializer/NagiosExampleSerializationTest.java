package uk.co.benjiweber.puppetsafe.serializer;

import org.junit.Test;
import uk.co.benjiweber.puppetsafe.examples.Nagios;

import static junit.framework.Assert.assertEquals;

public class NagiosExampleSerializationTest {

    ClassSerializer serializer = new ClassSerializer();

    @Test
    public void ensureExampleSerializedAsExpected() {
        ;
         assertEquals("class Nagios {\n" +
                 "\n" +
                 "\trequire base,nrpe\n" +
                 "\n" +
                 "\tinclude nagios::common\n" +
                 "\n" +
                 "\tfile { '/etc/nagios/':\n" +
                 "\t\tpath => '/etc/nagios/',\n" +
                 "\t\tensure => 'directory',\n" +
                 "\t}\n" +
                 "\n" +
                 "\tfile { '/etc/nagios/nagios.cfg':\n" +
                 "\t\tpath => '/etc/nagios/nagios.cfg',\n" +
                 "\t\tensure => 'file',\n" +
                 "\t\tsource => 'nagios.cfg',\n" +
                 "\t\tinclude => File['/etc/nagios/'],\n" +
                 "\t}\n" +
                 "\n" +
                 "}", serializer.serialize(Nagios.class));
    }
}
