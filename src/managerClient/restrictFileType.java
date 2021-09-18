/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package managerClient;



import java.io.File;

import javax.swing.filechooser.FileFilter;

public class restrictFileType extends FileFilter{
    private String ends;
    private String description;

    public restrictFileType(String ends, String description) {
        this.ends = ends;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) return true;
        String fileName = file.getName();
        if (fileName.toUpperCase().endsWith(this.ends.toUpperCase())) return true;
        return false;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public String getEnds() {
        return this.ends;
    }
}

