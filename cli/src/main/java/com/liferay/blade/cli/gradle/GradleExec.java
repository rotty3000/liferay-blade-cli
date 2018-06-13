/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.cli.gradle;

import com.liferay.blade.cli.BladeCLI;
import com.liferay.blade.cli.util.BladeUtil;

import java.io.File;

/**
 * @author David Truong
 */
public class GradleExec {

	public GradleExec(BladeCLI blade) {
		_blade = blade;

		File gradlew = BladeUtil.getGradleWrapper(blade.getBase());

		if (gradlew != null) {
			try {
				_executable = gradlew.getCanonicalPath();
			}
			catch (Exception e) {
				blade.out("Could not find gradle wrapper, using gradle");

				_executable = "gradle";
			}
		}
		else {
			blade.out("Could not find gradle wrapper, using gradle");

			_executable = "gradle";
		}
	}

	public int executeGradleCommand(String cmd) throws Exception {
		return executeGradleCommand(cmd, _blade.getBase());
	}

	public int executeGradleCommand(String cmd, File dir) throws Exception {
		Process process = BladeUtil.startProcess(_blade, "\"" + _executable + "\" " + cmd, dir, true);

		return process.waitFor();
	}

	private BladeCLI _blade;
	private String _executable;

}