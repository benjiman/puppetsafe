package uk.co.benjiweber.puppetsafe.examples;

import static uk.co.benjiweber.puppetsafe.core.Package.pkg;
import static uk.co.benjiweber.puppetsafe.core.Package.Ensure.*;
import static uk.co.benjiweber.puppetsafe.core.Package.PackageBuilder.with;
import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.core.Package;

public class Munin implements Class {

	static Package munin = pkg(
			with()
				.name("munin")
				.ensure(installed)
			);
	
	static Package muninCommon = pkg(
			with()
				.name("munin-common")
				.ensure(latest)
				.requires(munin)
			);
	
}
