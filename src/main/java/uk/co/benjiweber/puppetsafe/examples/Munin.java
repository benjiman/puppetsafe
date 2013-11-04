package uk.co.benjiweber.puppetsafe.examples;

import uk.co.benjiweber.puppetsafe.core.Class;
import uk.co.benjiweber.puppetsafe.core.Package;

import static uk.co.benjiweber.puppetsafe.core.Package.Ensure.installed;
import static uk.co.benjiweber.puppetsafe.core.Package.Ensure.latest;
import static uk.co.benjiweber.puppetsafe.core.Package.PackageBuilder.with;
import static uk.co.benjiweber.puppetsafe.core.Package.pkg;

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
