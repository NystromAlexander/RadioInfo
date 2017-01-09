package program;

import java.util.List;

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

    private List<ScheduleEntry> scheduleEntry;

    /**
     * A container for channel information is empty until defined
     */
    public Channel() {

    }

    /**
     *
     * @param scheduleEntry The channels scheduleEntry
     */
    public void setScheduleEntry(List<ScheduleEntry> scheduleEntry) {
        this.scheduleEntry = scheduleEntry;
    }

    /**
     *
     * @return scheduleEntry for the channel
     */
    public List<ScheduleEntry> getScheduleEntry() {
        return scheduleEntry;
    }

    /**
     *
     * @return ID for the channel
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id set the ID for the channel to this value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return Name of the channel
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name Name of the channel
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return URL to eventual image not all channels have one
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     *
     * @param imgUrl Sets the channels img url to given string
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     *
     * @return URL to image template not all channels have one
     */
    public String getImgTemplateUrl() {
        return imgTemplateUrl;
    }

    /**
     *
     * @param imgTemplateUrl set the channels template image url to given string
     */
    public void setImgTemplateUrl(String imgTemplateUrl) {
        this.imgTemplateUrl = imgTemplateUrl;
    }

    /**
     *
     * @return the color for the channel
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color sets the channels color to the given string
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return Tag line for the channel
     */
    public String getTagLine() {
        return tagLine;
    }

    /**
     *
     * @param tagLine set the channels tag line to the given string
     */
    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    /**
     *
     * @return URL to the webpage
     */
    public String getSiteUrl() {
        return siteUrl;
    }

    /**
     *
     * @param siteUrl sets the channels webpage URL to given string
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    /**
     *
     * @return URL to the website where it's possible to listen to the radio
     */
    public String getLiveUrl() {
        return liveUrl;
    }

    /**
     *
     * @param liveUrl URL to where you can listen to the radio
     */
    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    /**
     *
     * @return Key to start the live radio
     */
    public String getLiveStartKey() {
        return liveStartKey;
    }

    /**
     *
     * @param liveStartKey the key to the live radio
     */
    public void setLiveStartKey(String liveStartKey) {
        this.liveStartKey = liveStartKey;
    }

    /**
     *
     * @return URL to the scheduleEntry
     */
    public String getScheduleUrl() {
        return scheduleUrl;
    }

    /**
     *
     * @param scheduleUrl set the scheduleEntry url
     */
    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }

    /**
     *
     * @return what type of channel it is
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     *
     * @param channelType set what channel type it is
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    /**
     *
     * @return Formatted string with all the channel contents
     */
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
                ",\n scheduleEntry=" + scheduleEntry +
                "}\n";
    }
}
