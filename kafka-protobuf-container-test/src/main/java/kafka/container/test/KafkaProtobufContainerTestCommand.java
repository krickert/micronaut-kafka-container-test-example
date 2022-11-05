package kafka.container.test;

import com.krickert.search.model.DemoDocument;
import io.micronaut.configuration.picocli.PicocliRunner;

import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Map;

@Command(name = "kafka-protobuf-container-test", description = "...",
        mixinStandardHelpOptions = true)
public class KafkaProtobufContainerTestCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Inject
    SampleKafkaProducer producer;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(KafkaProtobufContainerTestCommand.class, args);
    }

    public void run() {
        producer.sendMessage(DemoDocument.newBuilder()
                .setBody("This is a body.")
                .setId("Identifer")
                .setTitle("Title: Sample Titles for Sample Demos")
                .putAllCustom(Map.of(
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
