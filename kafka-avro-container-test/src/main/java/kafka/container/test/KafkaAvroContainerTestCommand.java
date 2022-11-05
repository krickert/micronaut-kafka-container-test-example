package kafka.container.test;

import com.krickert.avro.model.DemoDocument;
import io.micronaut.configuration.picocli.PicocliRunner;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Map;

@Command(name = "kafka-avro-container-test", description = "...",
        mixinStandardHelpOptions = true)
public class KafkaAvroContainerTestCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Inject
    SampleKafkaProducer producer;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(KafkaAvroContainerTestCommand.class, args);
    }

    public void run() {
        producer.sendMessage(DemoDocument.newBuilder()
                .setBody("This is a body.")
                .setId("Identifer")
                .setTitle("Title: Sample Titles for Sample Demos")
                .setCustom(Map.of(
                        "a", "b",
                        "c", "d"
                )).build());
        // business logic here
        if (verbose) {
            {
                System.out.println("Hi!");
            }
        }
    }

}
