package org.omidbiz.fanpardaz.maven.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * @author Omid Pourhadi
 *
 */
public class PluginUtil
{

    public static void includeFiles(File file, OutputStream os, List<String> includes) throws IOException
    {
        if (includes != null && includes.size() > 0)
        {
            for (String inclusion : includes)
            {
                if (inclusion.equals(file.getName()))
                {
                    InputStream is = new FileInputStream(file);
                    IOUtils.copy(is, os);
                    IOUtils.closeQuietly(is);
                }
            }
        }
    }

    public static boolean exclusion(File file, List<String> excludes, List<String> includes)
    {
        if (excludes != null && excludes.size() > 0)
        {
            for (String exclusion : excludes)
            {
                if (exclusion.equals(file.getName()))
                {
                    return true;
                }
            }
        }
        if (includes != null && includes.size() > 0)
        {
            for (String exclusion : includes)
            {
                if (exclusion.equals(file.getName()))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
