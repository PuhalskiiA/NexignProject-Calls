import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    private static List<Call> getInformationByNumber(List<Call> calls, long number) {
        return calls.stream().filter(call -> call.getNumber() == number).toList();
    }
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                "cdr.txt"));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                "result.txt"))) {
            Call.getPositions(bufferedReader.readLine());

            String line;
            List<Call> calls = new LinkedList<>();

            while ((line = bufferedReader.readLine()) != null) {
                calls.add(new Call(line));
            }

            for (Long item : Call.getNumbers()) {
                List<Call> result = getInformationByNumber(calls, item);

                String rate = result.get(0).getRate();

                switch (rate) {
                    case "06" -> {
                        long sum_times = 0;
                        long result_minutes = 0;

                        for (Call call : result) {
                            if (result_minutes < 300) {
                                call.setCost(0.0);
                            } else {
                                call.setCost(1.0);
                            }

                            sum_times += call.getDate_end().getTime() - call.getDate_start().getTime();

                            result_minutes = TimeUnit.MILLISECONDS.convert(sum_times, TimeUnit.MINUTES);
                        }
                    }
                    case "11" -> {

                    }
                    case "03" -> {

                    }
                    default
                }
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
