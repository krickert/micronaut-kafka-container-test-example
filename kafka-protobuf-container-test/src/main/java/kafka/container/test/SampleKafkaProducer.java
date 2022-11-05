package kafka.container.test;
import com.krickert.search.model.DemoDocument;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;


@KafkaClient
public interface SampleKafkaProducer {

    @Topic("sample-topic-proto")
    void sendMessage(DemoDocument testString);

}