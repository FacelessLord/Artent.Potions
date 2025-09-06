package faceless.artent.potions.api;

import java.util.List;

public class ListUtils {

  public static boolean isNullOrEmpty(List<?> list) {
    return list == null || list.isEmpty();
  }
}
