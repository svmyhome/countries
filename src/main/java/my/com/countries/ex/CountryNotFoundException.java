package my.com.countries.ex;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException() {
        super();
    }

    public CountryNotFoundException(String message) {
        super(message);
    }
}
