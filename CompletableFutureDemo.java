import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // First task: Simulate fetching data from a database
        CompletableFuture<String> fetchData = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching data from the database...");
            sleep(2); // Simulate delay
            return "Data from database";
        });

        // Second task: Process the fetched data
        CompletableFuture<String> processData = fetchData.thenCompose(data -> CompletableFuture.supplyAsync(() -> {
            System.out.println("Processing fetched data: " + data);
            sleep(1); // Simulate processing delay
            return data + " processed";
        }));

        System.out.println("Result: " + processData.get());

        // thenCombine example
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            return 20;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            return 30;
        });

        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, Integer::sum);
        System.out.println("Combined result: " + combinedFuture.get());

        // allOf example
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    sleep(1);
                    System.out.println("Task 1 completed.");
                }),
                CompletableFuture.runAsync(() -> {
                    sleep(2);
                    System.out.println("Task 2 completed.");
                }),
                CompletableFuture.runAsync(() -> {
                    sleep(3);
                    System.out.println("Task 3 completed.");
                })
        );

        allOfFuture.join(); // Wait for all tasks to complete
        System.out.println("All tasks completed.");

        // anyOf example
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> {
                    sleep(1);
                    return "First task result";
                }),
                CompletableFuture.supplyAsync(() -> {
                    sleep(2);
                    return "Second task result";
                }),
                CompletableFuture.supplyAsync(() -> {
                    sleep(3);
                    return "Third task result";
                })
        );

        System.out.println("First completed task result: " + anyOfFuture.get());
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
