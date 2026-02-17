package my.com.countries.controller.error;

import java.util.List;
import java.util.Map;

public class ApiError {
    /**
     * {
     * "apiVersion": "2.0",
     * "error": {
     * "code": 404,
     * "message": "File Not Found",
     * "errors": [{
     * "domain": "Calendar",
     * "reason": "ResourceNotFoundException",
     * "message": "File Not Found"
     * }]
     * }
     * }
     */

    private final String apiVersion;
    private final Error error;


    public ApiError(String apiVersion, Error error) {
        this.apiVersion = apiVersion;
        this.error = error;
    }

    public ApiError(String apiVersion, String code, String message, String domain, String reason) {
        this.apiVersion = apiVersion;
        this.error = new Error(
                code,
                message,
                List.of(
                        new ErrorItems(
                                domain,
                                reason,
                                message
                        )
                )
        );
    }

    public static ApiError fromAttributeMap(String apiVersion, Map<String, Object> attributesMap) {
        return new ApiError(
                apiVersion,
                ((Integer) attributesMap.get("status")).toString(),
                (String) attributesMap.getOrDefault("error", "No message found"),
                (String) attributesMap.getOrDefault("path", "No path found"),
                (String) attributesMap.getOrDefault("error", "No error found")
        );
    }

    public Map<String, Object> toAttributeMap() {
        return Map.of(
                "apiVersion", apiVersion,
                "error", error
        );
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public Error getError() {
        return error;
    }

    private record Error(String code, String message, List<ErrorItems> errors) {

    }

    private record ErrorItems(String domain, String reason, String message) {

    }
}
