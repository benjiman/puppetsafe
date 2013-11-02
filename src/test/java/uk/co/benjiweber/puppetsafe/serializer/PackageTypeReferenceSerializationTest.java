package uk.co.benjiweber.puppetsafe.serializer;

import org.junit.Test;
import uk.co.benjiweber.puppetsafe.examples.PackageTypeReference;

public class PackageTypeReferenceSerializationTest {

    ClassSerializer serializer = new ClassSerializer();
    private String expected = "class PackageTypeReference {\n" +
            "\n" +
            "\tpackage { 'example':\n" +
            "\t\tname => 'my-package',\n" +
            "\t\tsource => 'puppet:///foo/bar.rpm',\n" +
            "\t\tensure => 'installed',\n" +
            "\t\tadminfile => '/example/path',\n" +
            "\t\tallowcdrom => false,\n" +
            "\t\tconfigfiles => 'replace',\n" +
            "\t\tflavor => 'sweet',\n" +
            "\t\tprovider => 'zypper',\n" +
            "\t\tinstall_options => ['/S',{'INSTALLDIR' => '/opt/foo'}],\n" +
            "\t\tuninstall_options => [{'REMOVE' => 'Sync,VSS'}],\n" +
            "\t}\n" +
            "\n" +
            "}";

    @Test
    public void ensureExampleSerializedAsExpected() {
        org.junit.Assert.assertEquals(expected, serializer.serialize(PackageTypeReference.class));
    }
}
