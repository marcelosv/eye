import br.com.eye.data.SonarData;
import br.com.eye.data.TypesData;
import br.com.eye.monitor.ApplicationMonitor;
import br.com.eye.monitor.data.VersionsTotal;
import br.com.eye.monitor.process.DataSaveProcess;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMonitor.class)
public class MonitorTest {

    @Autowired
    private DataSaveProcess dataSave;

    @BeforeClass
    public static void init() throws IOException {
        RedisServerTes.start();
    }

    @AfterClass
    public static void finish() {
        RedisServerTes.stop();
    }

    @Test
    public void testVersoes(){

        SonarData sd = criar("func1", new String[]{"func1"}, TypesData.API_ENDPOINT.getValue(),
            new Date().getTime(), 0,
            false, null,
            "cliente1", "Sistema1", null, "0.0.0.1");

        SonarData sd2 = criar("func2", new String[]{"func1"}, TypesData.API_ENDPOINT.getValue(),
                new Date().getTime(), 0,
                false, null,
                "cliente1", "Sistema1", null, "0.0.0.1");

        SonarData sd3 = criar("func3", new String[]{"func1"}, TypesData.API_ENDPOINT.getValue(),
                new Date().getTime(), 0,
                false, null,
                "cliente1", "Sistema1", null, "0.0.0.2");

        dataSave.add(sd);
        dataSave.add(sd);
        dataSave.add(sd);
        dataSave.add(sd2);
        dataSave.add(sd2);
        dataSave.add(sd3);
        dataSave.add(sd3);

        Map<String, VersionsTotal> versoesTotais = dataSave.getVersionsAndTotal();

        VersionsTotal versoesSstema = versoesTotais.get("Sistema1");

        Assert.assertEquals(versoesSstema.getLista().get("0.0.0.1").getTotal(), 5);
        Assert.assertEquals(versoesSstema.getLista().get("0.0.0.1").getPerc(), "71.43");


        Map<String, VersionsTotal> funcoesTotais = dataSave.getFeaturesAndPerc();
        Assert.assertEquals(funcoesTotais.get("Sistema1").getLista().get("func1").getTotal(), 3);
        Assert.assertEquals(funcoesTotais.get("Sistema1").getLista().get("func2").getTotal(), 2);

        Assert.assertEquals(funcoesTotais.get("Sistema1").getLista().get("func1").getPerc(), "42.86");


    }


    public SonarData criar(String description, String[] tags, int type, long timeInit, long timeExec, boolean error, String messageError, String client, String server, String exception, String version) {

        SonarData sb = new SonarData();
        sb.setDescription(description);
        sb.setTags(tags);
        sb.setType(type);
        sb.setTimeInit(timeInit);
        sb.setTimeExec(timeExec);
        sb.setError(error);
        sb.setMessageError(messageError);
        sb.setClient(client);
        sb.setServer(server);
        sb.setException(exception);
        sb.setVersion(version);

        return sb;
    }


}
