package a;

import net.sf.jremoterun.utilities.JrrClassUtils
import net.sf.jremoterun.utilities.classpath.AddFilesToClassLoaderGroovySave
import net.sf.jremoterun.utilities.nonjdk.rstacore.RstaLangSupportStatic;

import java.util.logging.Logger;
import groovy.transform.CompileStatic;


@CompileStatic
class Init3 implements Runnable{

    private static final Logger log = JrrClassUtils.getJdkLogForCurrentClass();

    @Override
    void run() {
        AddFilesToClassLoaderGroovySave adder = RstaLangSupportStatic.langSupport.classPathCalculatorGroovy.addFilesToClassLoaderGroovySave

        FBuild.mavenIds.each {
            adder.addMWithDependeciesDownload(it)
        }

    }
}
