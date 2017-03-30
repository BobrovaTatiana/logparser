import java.util.Date;

/**
 * Created by Tanusha on 30/03/2017.
 */
public class Record implements Comparable {
    private String method;
    private int id;
    private Date timeR;
    private String type;

    public Record(Date timeR, String type, String method, int id) {
        this.timeR = timeR;
        this.type = type;
        this.method = method;
        this.id = id;
    }


    public Record() {

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeR() {
        return timeR;
    }

    public void setTimeR(Date time) {
        this.timeR = timeR;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int compareTo(Object comparsetu) {
        int compareId = ((Record)comparsetu).getId();
        return this.getId()-compareId;
    }


}
