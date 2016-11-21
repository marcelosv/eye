package br.com.eye.monitor.redis;

import java.io.IOException;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import br.com.eye.data.SonarData;
import br.com.eye.monitor.process.DataSaveProcess;
import br.com.eye.monitor.websocket.WebSocketExecute;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisMessageSubscriber  implements MessageListener {

    private DataSaveProcess dataSave;

    private WebSocketExecute webSocketExecute;

    public RedisMessageSubscriber(DataSaveProcess dataSave, WebSocketExecute webSocketExecute){
        this.dataSave = dataSave;
        this.webSocketExecute = webSocketExecute;
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {

        ObjectMapper mapper = new ObjectMapper();
        SonarData sonarData = null;
        try {

            sonarData = mapper.readValue(message.toString().replaceAll("\\\\\"", "\\\"").replaceAll("\"\\{", "{").replaceAll("\\}\"", "}"), SonarData.class);
        } catch (IOException e) {
            return;
        }

        if( sonarData == null ){
            return;
        }

        dataSave.add(sonarData);

        webSocketExecute.runAll();
    }
}