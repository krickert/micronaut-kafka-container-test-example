package kafka.container.test;
import com.krickert.avro.model.DemoDocument;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

@KafkaClient
public interface SampleKafkaProducer {

    @Topic("sample-topic")
    void sendMessage(DemoDocument testString);

}