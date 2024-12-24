import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWeather {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Fetch weather data for three cities
        CompletableFuture<WeatherData> city1Weather = fetchWeather("City1");
        CompletableFuture<WeatherData> city2Weather = fetchWeather("City2");
        CompletableFuture<WeatherData> city3Weather = fetchWeather("City3");

        // Combine all results
        CompletableFuture<Void> allWeatherData = CompletableFuture.allOf(city1Weather, city2Weather, city3Weather);

        allWeatherData.join();

        // Get individual results
        WeatherData city1 = city1Weather.get();
        WeatherData city2 = city2Weather.get();
        WeatherData city3 = city3Weather.get();

        // Compare and decide
        System.out.println("Comparing weather data...");
        evaluateWeather(city1, city2, city3);

        // Example of anyOf to get the fastest response
        CompletableFuture<Object> fastestResponse = CompletableFuture.anyOf(city1Weather, city2Weather, city3Weather);
        System.out.println("Fastest weather data response: " + fastestResponse.get());
    }

    private static CompletableFuture<WeatherData> fetchWeather(String city) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching weather data for " + city + "...");
            sleep(ThreadLocalRandom.current().nextInt(1, 4)); // Simulate variable delay
            WeatherData data = new WeatherData(city,
                    ThreadLocalRandom.current().nextInt(20, 40), // Temperature
                    ThreadLocalRandom.current().nextInt(30, 70), // Humidity
                    ThreadLocalRandom.current().nextInt(5, 15)); // Wind speed
            System.out.println("Fetched data for " + city + ": " + data);
            return data;
        });
    }

    private static void evaluateWeather(WeatherData... cities) {
        for (WeatherData city : cities) {
            if (city.temperature > 25 && city.humidity < 60 && city.windSpeed < 10) {
                System.out.println(city.cityName + " is good for a beach day.");
            } else {
                System.out.println(city.cityName + " is not ideal for a beach day. Dress warmly.");
            }
        }
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    static class WeatherData {
        String cityName;
        int temperature;
        int humidity;
        int windSpeed;

        public WeatherData(String cityName, int temperature, int humidity, int windSpeed) {
            this.cityName = cityName;
            this.temperature = temperature;
            this.humidity = humidity;
            this.windSpeed = windSpeed;
        }

        @Override
        public String toString() {
            return "WeatherData{" +
                    "cityName='" + cityName + '\'' +
                    ", temperature=" + temperature +
                    ", humidity=" + humidity +
                    ", windSpeed=" + windSpeed +
                    '}';
        }
    }
}
