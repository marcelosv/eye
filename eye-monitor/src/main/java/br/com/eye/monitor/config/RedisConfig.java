package br.com.eye.monitor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import br.com.eye.monitor.process.DataSaveProcess;
import br.com.eye.monitor.redis.RedisMessageSubscriber;
import br.com.eye.monitor.websocket.WebSocketExecute;

@Configuration
public class RedisConfig {

	public static final String NAME_LIST_FORM_PROCESS = "listForProcess";
	
    @Autowired
    private DataSaveProcess dataSave;

    @Autowired
    private WebSocketExecute webSocketExecute;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, topic());

        return container;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber(dataSave, webSocketExecute));
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(NAME_LIST_FORM_PROCESS);
    }


}
