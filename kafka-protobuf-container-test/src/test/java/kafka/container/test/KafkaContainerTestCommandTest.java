package kafka.container.test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.krickert.search.model.DemoDocument;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

import static org.awaitility.Awaitility.await;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@MicronautTest
@TestInstance(PER_CLASS)
public class KafkaContainerTestCommandTest {
    private static final ConcurrentLinkedQueue<DemoDocument> messages = new ConcurrentLinkedQueue<>();


    @Inject
    public SampleKafkaProducer producer;

    private final DemoDocument expected = DemoDocument.newBuilder()
            .setBody("This is a body.")
            .setId("Identifer")
            .setTitle("Title: Sample Titles for Sample Demos")
            .putAllCustom(Map.of(
                    "a", "b",
                    "c", "d"
            )).build();

    @Test
    public void testWithCommandLineOption() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        try (ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)) {
            String[] args = new String[] { "-v" };
            PicocliRunner.run(KafkaContainerTestCommand.class, ctx, args);
            // kafka-container-test
            assertTrue(baos.toString().contains("Hi!"));
            await().atMost(10, SECONDS).until(() -> !messages.isEmpty());
            assertThat(messages.iterator().next()).isEqualTo(expected);
        }
    }

    @BeforeEach
    public void before() {
        messages.clear();
    }

    @Test
    public void testKafkaListens() {
        DemoDocument sendMe = DemoDocument.newBuilder()
                .setBody("This is a body.")
                .setId("Identifer")
                .setTitle("Title: Sample Titles for Sample Demos")
                .putAllCustom(Map.of(
                        "a", "b",
                        "c", "d"
                )).build();
        producer.sendMessage(sendMe);
        await().atMost(10, SECONDS).until(() -> !messages.isEmpty());
        assertEquals(1, messages.size());
        Message result = messages.poll();
        assertEquals(expected, result);
    }

    @KafkaListener
    static class SampleListener {
        @Topic("sample-topic-proto")
        public void getTopic(Message message) {
            try {
                messages.add(DemoDocument.parseFrom(message.toByteArray()));
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
