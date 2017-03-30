/**
 * Created by Tanusha on 30/03/2017.
 */
public class RecordSorted {
    private String method;
    private int id;
    private long time;
    private boolean is_checked;

    public RecordSorted(String method, int id, long time, boolean is_checked) {
        this.method = method;
        this.id = id;
        this.time = time;
        this.is_checked = is_checked;
    }

    public String getMethod() {
        return method;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isIs_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }
}
