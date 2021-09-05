package xyz.refinedev.coins.utils.duration;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duration {

    @Getter
    private final long value;

    public Duration(long value) {
        this.value = value;
    }

    public static Duration fromString(String source) {
        if (source.equalsIgnoreCase("perm") || source.equalsIgnoreCase("permanent")) {
            return new Duration(-1L);
        }

        long totalTime = 0L;
        boolean found = false;
        Matcher matcher = Pattern.compile("\\d+\\D+").matcher(source);

        while (matcher.find()) {
            String s = matcher.group();
            Long value = Long.parseLong(s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0]);
            String type = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1];

            switch (type) {
                case "s":
                    totalTime += value;
                    found = true;
                    break;
                case "m":
                    totalTime += value * 60;
                    found = true;
                    break;
                case "h":
                    totalTime += value * 60 * 60;
                    found = true;
                    break;
                case "d":
                    totalTime += value * 60 * 60 * 24;
                    found = true;
                    break;
                case "w":
                    totalTime += value * 60 * 60 * 24 * 7;
                    found = true;
                    break;
                case "M":
                    totalTime += value * 60 * 60 * 24 * 30;
                    found = true;
                    break;
                case "y":
                    totalTime += value * 60 * 60 * 24 * 365;
                    found = true;
                    break;
            }
        }

        return new Duration(!found ? -1 : totalTime * 1000);
    }

    public static String getRounded(long value) {
        value += 1L;

        long seconds = value / 1000L, minutes = seconds / 60L, hours = minutes / 60L, days = hours / 24L, weeks = days / 7L, months = weeks / 4L, years = months / 12L;

        if (years > 0) return years + " year" + (years == 1 ? "" : "s");
        else if (months > 0) return months + " month" + (months == 1 ? "" : "s");
        else if (weeks > 0) return weeks + " week" + (weeks == 1 ? "" : "s");
        else if (days > 0) return days + " day" + (days == 1 ? "" : "s");
        else if (hours > 0) return hours + " hour" + (hours == 1 ? "" : "s");
        else if (minutes > 0) return minutes + " minute" + (minutes == 1 ? "" : "s");
        else return seconds + " second" + (seconds == 1 ? "" : "s");
    }

}
