package org.omidbiz.fanpardaz.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/**
 * @author omidp
 * 
 */
@Mojo(name = "jsCompressor")
public class JsCompressor extends AbstractMojo
{

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File basedir;

    @Parameter(defaultValue = "${project.basedir}/src/main/webapp", readonly = true)
    private File warSourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private String outputDirectory;

    @Parameter(readonly = true)
    private List<String> excludes;

    @Parameter(readonly = true)
    private List<String> includes;

    public void execute() throws MojoExecutionException
    {
        
        File distDirectory = new File(warSourceDirectory + "/dist");
        distDirectory.mkdirs();
        File outputFile = new File(distDirectory + "/seam-min.js");
        OutputStream os = null;
        try
        {
            outputFile.createNewFile();
            os = new FileOutputStream(outputFile);
            Collection<File> cssFiles = FileUtils.listFiles(warSourceDirectory, new String[] { "js" }, true);
            if (cssFiles != null)
            {
                System.out.println("Merging all js files");
                for (Iterator iterator = cssFiles.iterator(); iterator.hasNext();)
                {
                    File file = (File) iterator.next();
                    if (PluginUtil.exclusion(file, excludes, includes) == false)
                    {
                        InputStream is = new FileInputStream(file);
                        IOUtils.copy(is, os);
                        IOUtils.closeQuietly(is);
                    }
                    //
                    PluginUtil.includeFiles(file, os, includes);
                }
                FileInputStream inputStream = new FileInputStream(outputFile);
                JavaScriptCompressor js = new JavaScriptCompressor(new InputStreamReader(inputStream, "UTF-8"), new ErrorReporter4Mojo(
                        getLog(), true));
                FileWriter sw = new FileWriter(outputFile);
                js.compress(sw, 0, false, false, true, false);
                sw.flush();
                sw.close();
            }

        }
        catch (FileNotFoundException e)
        {
        }
        catch (IOException e)
        {
        }
        finally
        {
            IOUtils.closeQuietly(os);
        }

    }
}
