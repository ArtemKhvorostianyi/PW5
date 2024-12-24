ТВ-23, Хворостяний Артем Кирилович, Варіант 5

Завдання 1
Створіть два асинхронних завдання. Перше завдання отримує дані
з бази даних (імітуємо затримку), а друге завдання залежить від
результату першого і обробляє отримані дані.

За допомогою CompletableFuture.supplyAsync(() імітуємо роботу з базою даних та затримку;
 CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, Integer::sum) - комбінування двох задач, де повертається сума двох задач
 CompletableFuture.runAsync(() - вивід звітності про виконання
 CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf - очікування виконання двох задач

Завдання 2
Створюємо три міста для порівняння погоди в них
CompletableFuture<Void> allWeatherData = CompletableFuture.allOf(city1Weather, city2Weather, city3Weather);
allWeatherData.join(); виконання всіх задач одночасно, блокування основного потоку до завершення всіх задач

Отримання результатів через WeatherData city1 = city1Weather.get();
Оцінка погоди за допомогою evaluateWeather(city1, city2, city3);
Отримання швидкої відповіді за допомогою CompletableFuture<Object> fastestResponse = CompletableFuture.anyOf(city1Weather, city2Weather, city3Weather);

fetchWeather - імітує затримку
evaluateWeather - перевіряє умови для кожного міста
sleep - додає затримку для імітації реального часу виконання задачі
