package a

import net.sf.jremoterun.utilities.JrrClassUtils
import net.sf.jremoterun.utilities.classpath.MavenId
import net.sf.jremoterun.utilities.mdep.ivy.IvyDepResolver2
import net.sf.jremoterun.utilities.nonjdk.SftpUtils
import net.sf.jremoterun.utilities.nonjdk.compile.GenericCompiler
import org.junit.Test
import org.zeroturnaround.zip.ZipUtil

import java.util.logging.Logger;

class FBuild extends GenericCompiler {

    private static final Logger log = JrrClassUtils.getJdkLogForCurrentClass();

    File baeDir = "C:/gitrepo/https/github.com/databricks/learning-spark/git/mini-complete-example" as File

    static List<MavenId> mavenIds = [
            new MavenId(('org.apache.spark:spark-core_2.10:1.1.0')),
//            new MavenId('org.apache.hadoop:hadoop-common:2.9.0'),
//            new MavenId('org.apache.hadoop:hadoop-hdfs-client:2.9.0'),
////            new MavenId('org.apache.hive:hive-exec:2.3.2'),
//            new MavenId('org.apache.parquet:parquet-avro:1.9.0'),
//            new MavenId('org.apache.parquet:parquet-hadoop:1.9.0'),
//            new MavenId('org.apache.spark:spark-mllib_2.11:2.2.1'),

    ]

    @Test
    void f1() {
        all2()
        zipp()
//        upload()
    }

    @Override
    void prepare() {
        assert baeDir.exists()
        params.javaVersion = '1.7'
        IvyDepResolver2.setDepResolver()
        params.outputDir = new File(baeDir,'build/1')
        params.outputDir.mkdirs()
        params.addInDir new File(baeDir, 'src/main/java/')
        mavenIds.each {
            java.lang.Object oo = it
            addM1(oo)
        }
    }

    File destFile  =  new File(baeDir,'build/nikf.jar')

    void zipp(){
        destFile.delete()
        assert !destFile.exists()
        ZipUtil.pack(params.outputDir,destFile)
    }

    void upload(){
        SftpUtils.userDefault = 'root'
        SftpUtils.passwordDefault = '1'
        SftpUtils sftpUtils = new SftpUtils()
        sftpUtils.createSession('172.17.0.2')
        sftpUtils.sftp.put(destFile.absolutePath,'/nik1/nikf.jar')

    }

    void addM1(MavenId oo){
        try {
            client.adder.addMWithDependeciesDownload(oo)
        }catch (Exception e){
            log.info "failed add ${oo} ${e}"
            throw e
        }

    }
}