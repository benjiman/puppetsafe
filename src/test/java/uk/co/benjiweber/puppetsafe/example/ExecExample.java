package uk.co.benjiweber.puppetsafe.example;

import static uk.co.benjiweber.puppetsafe.core.Exec.exec;
import static uk.co.benjiweber.puppetsafe.core.Exec.ExecBuilder.with;
import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.core.Exec;

public class ExecExample implements Class {
	
	static Exec echo = exec(
				with()
					.name("/bin/cat /dev/null")
			);

}
