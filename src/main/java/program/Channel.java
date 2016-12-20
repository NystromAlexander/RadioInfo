package program;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class Channel {

    private int id;
    private String name;
    private String imgUrl;
    private String imgTemplateUrl;
    private String color;
    private String tagLine;
    private String siteUrl;
    private String liveUrl;
    private String liveStartKey;
    private String scheduleUrl;
    private String channelType;

    private Tableau tableau;

    public Channel() {

    }

    public void setTableau(Tableau tableau) {
        this.tableau = tableau;
    }

    public Tableau getTableau() {
        return tableau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgTemplateUrl() {
        return imgTemplateUrl;
    }

    public void setImgTemplateUrl(String imgTemplateUrl) {
        this.imgTemplateUrl = imgTemplateUrl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public String getLiveStartKey() {
        return liveStartKey;
    }

    public void setLiveStartKey(String liveStartKey) {
        this.liveStartKey = liveStartKey;
    }

    public String getScheduleUrl() {
        return scheduleUrl;
    }

    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ",\n name='" + name + '\'' +
                ",\n imgUrl='" + imgUrl + '\'' +
                ",\n imgTemplateUrl='" + imgTemplateUrl + '\'' +
                ",\n color='" + color + '\'' +
                ",\n tagLine='" + tagLine + '\'' +
                ",\n siteUrl='" + siteUrl + '\'' +
                ",\n liveUrl='" + liveUrl + '\'' +
                ",\n liveStartKey='" + liveStartKey + '\'' +
                ",\n scheduleUrl='" + scheduleUrl + '\'' +
                ",\n channelType='" + channelType + '\'' +
                ",\n tableau=" + tableau +
                "}\n";
    }
}
