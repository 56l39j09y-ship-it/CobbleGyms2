public class BattleRecord {
    public String recordId;
    public String challengerUuid;
    public String opponentUuid;
    public String result;
    public String date;
    public BattleRecord(String recordId, String challengerUuid, String opponentUuid, String result, String date) {
        this.recordId = recordId;
        this.challengerUuid = challengerUuid;
        this.opponentUuid = opponentUuid;
        this.result = result;
        this.date = date;
    }
}