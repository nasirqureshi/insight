// $codepro.audit.disable
package com.bvas.insight.utilities;

import org.apache.tools.ant.DirectoryScanner;

public class ListOutFilesInDir {

	int i = 0;

	public ListOutFilesInDir(int i) {
		this.i = 0;
	}

	public String getNextFilename(String dirPath, String filename) {

		String filenamewithoutextn = filename.replaceAll(".pdf", "");

		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[] { "**/*" + filenamewithoutextn + "*.pdf" });
		scanner.setBasedir(dirPath);
		scanner.setCaseSensitive(false);
		scanner.scan();
		String[] files = scanner.getIncludedFiles();
		int size = files.length;
		return filenamewithoutextn + "_" + size + ".pdf";

	}

	@Override
	public String toString() {

		return "ListOutFilesInDir [i=" + i + "]";
	}
}
