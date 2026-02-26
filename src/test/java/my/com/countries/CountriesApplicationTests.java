package my.com.countries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import my.com.grpc.countries.CountryRequest;
import my.com.grpc.countries.CountryResponse;
import my.com.grpc.countries.CountryServiceGrpc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.grpc.server.port=9091"  // используем другой порт для тестов
})
class CountriesApplicationTests {

    @Test
    void testAddCountry() {
//        ManagedChannel channel = ManagedChannelBuilder
//                .forAddress("localhost", 9090)
//                .usePlaintext()
//                .build();
//
//        CountryServiceGrpc.CountryServiceBlockingStub stub =
//                CountryServiceGrpc.newBlockingStub(channel);
//
//        CountryRequest request = CountryRequest.newBuilder()
//                .setName("Test Country")
//                .setCode("TST")
//                .setCoordinates("test")
//                .build();
//
//        CountryResponse response = stub.addCountry(request);
//
//        assertNotNull(response.getId());
//        assertEquals("Test Country", response.getName());
//        assertEquals("TST", response.getCode());
    }
}
