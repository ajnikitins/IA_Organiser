package com.organiser.file.util;

import com.organiser.file.interfaces.IDable;
import java.util.List;

public class Search {
  private Search() {}

  public static <T extends IDable> T binaryWithID(List<? extends T> list, int ID) {
    int l = 0;
    int r = list.size() - 1;

    while (l <= r) {
      // Calculate midpoint
      int m = l + (r - l) / 2;

      // Check if x is present at mid
      if (list.get(m).getID() == ID) {
        return list.get(m);
      }

      // If x greater, ignore left half
      // If x is smaller, ignore right half
      if (list.get(m).getID() < ID) {
        l = m + 1;
      } else {
        r = m - 1;
      }
    }

    return null;
  }
}
