package my.com.countries.service;

import io.grpc.stub.StreamObserver;
import java.util.List;
import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.domain.graphql.CountryInputGql;
import my.com.grpc.countries.CounterRequest;
import my.com.grpc.countries.CountryRequest;
import my.com.grpc.countries.CountryResponse;
import my.com.grpc.countries.CountryServiceGrpc;
import my.com.grpc.countries.CountryUpdate;
import my.com.grpc.countries.StreamNewCountryRequest;
import my.com.grpc.countries.idRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {

    private final CountryService countryService;

    @Autowired
    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void country(idRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql = countryService.countyGqlById(request.getId());
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(countryGql.id().toString())
                        .setName(countryGql.name())
                        .setCode(countryGql.code())
                        .setCoordinates(countryGql.coordinates())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void countries(CounterRequest request, StreamObserver<CountryResponse> responseObserver) {
        List<CountryGql> countries = countryService.allCountriesGql();
        for (int i = 0; i < request.getCounter(); i++) {
            responseObserver.onNext(
                    CountryResponse.newBuilder()
                            .setId(countries.get(i).id().toString())
                            .setName(countries.get(i).name())
                            .setCode(countries.get(i).code())
                            .setCoordinates(countries.get(i).coordinates())
                            .build()
            );
        }
        responseObserver.onCompleted();
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql = countryService.addCountryGql(new CountryInputGql(
                request.getName(),
                request.getCode(),
                request.getCoordinates()
        ));
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(countryGql.id().toString())
                        .setName(countryGql.name())
                        .setCode(countryGql.code())
                        .setCoordinates(countryGql.coordinates())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StreamNewCountryRequest> addStreamCountry(StreamObserver<CounterRequest> responseObserver) {
        return new StreamObserver<StreamNewCountryRequest>() {
            private int count = 0;

            @Override
            public void onNext(StreamNewCountryRequest request) {
                CountryRequest countryRequest = request.getInput();

                try {
                    countryService.addCountryGql(new CountryInputGql(
                            countryRequest.getName(),
                            countryRequest.getCode(),
                            countryRequest.getCoordinates()
                    ));
                    count++;

                    System.out.println("Added country: " + countryRequest.getName() +
                            ", total so far: " + count);

                } catch (Exception e) {
                    System.err.println("Failed to add country: " + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in stream: " + t.getMessage());
                t.printStackTrace();
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed. Total countries added: " + count);
                CounterRequest response = CounterRequest.newBuilder()
                        .setCounter(count)
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void updateCountry(CountryUpdate request, StreamObserver<CountryResponse> responseObserver) {
        final CountryInputGql countryInputGql = new CountryInputGql(
                request.getInput().getName(), request.getInput().getCode(), request.getInput().getCoordinates());
        CountryGql updateCountryGql = countryService.updateCountryGql(request.getCode(), countryInputGql);
        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setId(updateCountryGql.id().toString())
                        .setName(updateCountryGql.name())
                        .setCode(updateCountryGql.code())
                        .setCoordinates(updateCountryGql.coordinates())
                        .build()
        );
        responseObserver.onCompleted();
    }
}
