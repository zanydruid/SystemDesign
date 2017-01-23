package yidan.ambassador;

/**
 * Created by xyd on 16/4/14.
 */
public class Event {
    private int id;
    private String date;
    private String time;
    private String duration;
    private String topic;
    private String desc;
    private String format;
    private String ambamount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAmbamount() {
        return ambamount;
    }

    public void setAmbamount(String ambamount) {
        this.ambamount = ambamount;
    }


}
