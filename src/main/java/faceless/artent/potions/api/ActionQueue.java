package faceless.artent.potions.api;

import java.util.ArrayList;
import java.util.List;

public class ActionQueue {
  private final List<ActionEntry> queue = new ArrayList<>();
  private int timer;

  public ActionQueue(int startTime) {
    this.timer = startTime;
  }

  public void enqueueAction(Action action, int timeout) {
    this.queue.add(new ActionEntry(action, this.timer, timeout));
  }

  public void tickQueue() {
    var deletionList = new ArrayList<ActionEntry>();
    for (var entry : this.queue) {
      if (entry.enqueuedTime + entry.timeout <= this.timer) {
        entry.action.call();
        deletionList.add(entry);
      }
    }
    for (var entry : deletionList) {
      this.queue.remove(entry);
    }
    this.timer++;
  }

  public record ActionEntry(
      Action action,
      int enqueuedTime,
      int timeout
  ) {
  }

  public interface Action {
    void call();
  }
}
