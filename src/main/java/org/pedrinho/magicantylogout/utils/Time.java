package org.pedrinho.magicantylogout.utils;

import java.util.concurrent.TimeUnit;

public class Time {

    public static String formatTime(long time) {
        if (time < 1L) {
            return "< 1s";
        } else {
            StringBuilder builder = new StringBuilder();
            if (time >= TimeUnit.SECONDS.toMillis(1L)) {
                long[] units = new long[]{TimeUnit.MILLISECONDS.toDays(time), TimeUnit.MILLISECONDS.toHours(time) % 24L, TimeUnit.MILLISECONDS.toMinutes(time) % 60L, TimeUnit.MILLISECONDS.toSeconds(time) % 60L};
                if (units[0] > 0L) {
                    builder.append(units[0]).append("d").append(" ");
                }

                if (units[1] > 0L || units[0] > 0L) {
                    builder.append(units[1]).append("h").append(" ");
                }

                if (units[2] > 0L || units[0] > 0L || units[1] > 0L) {
                    builder.append(units[2]).append("m").append(" ");
                }

                if (units[3] > 0L || units[0] > 0L || units[1] > 0L || units[2] > 0L) {
                    builder.append(units[3]).append("s").append(" ");
                }
            } else {
                builder.append(time).append("ms");
            }

            return builder.toString().trim();
        }
    }
}
