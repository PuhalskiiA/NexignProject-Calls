import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Call {
    private String call;
    private long number;
    private Date date_start;
    private Date date_end;
    private String rate;

    private Period duration;

    private double cost;

    private static int position_call = 0;
    private static int position_rate = 0;
    private static int position_date_start = 0;
    private static int position_date_end = 0;
    private static int position_number = 0;

    private static Set<Long> numbers = new HashSet<>();


    public static Date getDate(String str) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        return formatter.parse(str);
    }

    public static void getPositions(String str) throws ParseException {
        String[] strings = str.split(", ");

        Date date_start = new Date();
        Date date_end = new Date();

        for (int i = 0; i < strings.length; i++) {
            switch (strings[i].length()) {
                case 2 -> {
                    if (strings[i].equals("01") || strings[i].equals("02")) {
                        position_call = i;
                    } else {
                        position_rate = i;
                    }
                }
                case 11 -> position_number = i;
                case 14 -> {
                    if (position_date_start == 0) {
                        position_date_start = i;
                        date_start = Call.getDate(strings[i]);
                    } else {
                        date_end = Call.getDate(strings[i]);
                        position_date_end = i;

                        if (date_start.after(date_end)) {
                            int save = position_date_start;
                            position_date_start = position_date_end;
                            position_date_end = save;
                        }
                    }
                }
            }
        }
    }

    public Call(String str) throws ParseException {
        String[] strings = str.split(", ");

        call = strings[position_call];
        number = Long.parseLong(strings[position_number]);
        numbers.add(Long.parseLong(strings[position_number]));
        date_start = getDate(strings[position_date_start]);
        date_end = getDate(strings[position_date_end]);
        duration = Period.between(date_end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                date_start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        rate = strings[position_rate];
    }

    public long getNumber() {
        return number;
    }

    public static Set<Long> getNumbers() {
        return numbers;
    }

    public String getRate() {
        return rate;
    }

    public Period getDuration() {
        return duration;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public static void setPosition_call(int position_call) {
        Call.position_call = position_call;
    }

    public static void setPosition_rate(int position_rate) {
        Call.position_rate = position_rate;
    }

    public static void setPosition_date_start(int position_date_start) {
        Call.position_date_start = position_date_start;
    }

    public static void setPosition_date_end(int position_date_end) {
        Call.position_date_end = position_date_end;
    }

    public static void setPosition_number(int position_number) {
        Call.position_number = position_number;
    }

    public static void setNumbers(Set<Long> numbers) {
        Call.numbers = numbers;
    }

    @Override
    public String toString() {
        return call + " " + number + " " + date_start + " " + date_end + " " + rate;
    }
}
