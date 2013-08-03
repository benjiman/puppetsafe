package uk.co.benjiweber.puppetsafe.serializer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.benjiweber.puppetsafe.example.ExecExample;

public class ExecSerializationTest {
	
	private ClassSerializer serializer = new ClassSerializer();
	
    @Test
    public void ensureExampleSerializedAsExpected() {
         assertEquals("class ExecExample {\n" +
                 "\n" +
                 "\texec { '/bin/cat /dev/null':\n" +
                 "\t}\n" +
                 "\n" +
         "}", serializer.serialize(ExecExample.class));
    }
}
