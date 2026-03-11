import java.util.*;

public class ChallengerQueue {
    private final Map<String, Queue<String>> leaderQueues = new HashMap<>(); // leaderUuid -> queue of challenger UUIDs
    private final Map<String, Boolean> gymOpenState = new HashMap<>(); // leaderUuid -> open/closed
    private final Map<String, Boolean> gymBusyState = new HashMap<>(); // leaderUuid -> busy/available

    public void openGym(String leaderUuid) {
        gymOpenState.put(leaderUuid, true);
    }
    public void closeGym(String leaderUuid) {
        gymOpenState.put(leaderUuid, false);
    }
    public boolean isGymOpen(String leaderUuid) {
        return gymOpenState.getOrDefault(leaderUuid, false);
    }
    public void setGymBusy(String leaderUuid, boolean busy) {
        gymBusyState.put(leaderUuid, busy);
    }
    public boolean isGymBusy(String leaderUuid) {
        return gymBusyState.getOrDefault(leaderUuid, false);
    }
    public void enqueueChallenger(String leaderUuid, String challengerUuid) {
        leaderQueues.putIfAbsent(leaderUuid, new LinkedList<>());
        leaderQueues.get(leaderUuid).add(challengerUuid);
    }
    public String dequeueChallenger(String leaderUuid) {
        Queue<String> queue = leaderQueues.get(leaderUuid);
        if (queue == null || queue.isEmpty()) return null;
        return queue.poll();
    }
    public List<String> getQueue(String leaderUuid) {
        return new ArrayList<>(leaderQueues.getOrDefault(leaderUuid, new LinkedList<>()));
    }
}