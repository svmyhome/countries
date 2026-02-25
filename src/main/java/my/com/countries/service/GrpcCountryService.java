package my.com.countries.service;

import io.grpc.stub.StreamObserver;
import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.domain.graphql.CountryInputGql;
import my.com.grpc.countries.CounterRequest;
import my.com.grpc.countries.CountryRequest;
import my.com.grpc.countries.CountryResponse;
import my.com.grpc.countries.CountryServiceGrpc;
import my.com.grpc.countries.CountryUpdate;
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
    public void updateCountry(CountryUpdate request, StreamObserver<CountryResponse> responseObserver) {
        super.updateCountry(request, responseObserver);
    }
}
