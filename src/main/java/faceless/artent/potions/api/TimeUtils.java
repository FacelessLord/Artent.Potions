package faceless.artent.potions.api;

import faceless.artent.potions.api.time.Time;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class TimeUtils {

  public static Time ticksToTime(int ticks) {
    return ticksToTime(ticks, 20);
  }

  public static Time ticksToTime(int ticks, int tps) {
    var ticksRemainder = ticks % tps;
    var seconds = ticks / tps;
    var secondsRemainder = seconds % 60;
    var minutes = seconds / 60;
    var minutesRemainder = minutes % 60;
    var hours = minutes / 60;

    return new Time(ticksRemainder, secondsRemainder, minutesRemainder, hours);
  }

  public static Text timeToText(Time time) {
    var parts = Arrays.stream(new String[]{
        time.hours() == 0 ? null : time.hours() + "hr",
        time.minutes() == 0 ? null : time.minutes() + "m",
        time.seconds() == 0 ? null : time.seconds() + "s"
    }).filter(Objects::nonNull).toList();

    return Text.literal(String.join(" ", parts));
  }
}
