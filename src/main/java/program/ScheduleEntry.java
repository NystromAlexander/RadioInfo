package program;

import java.util.Calendar;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class ScheduleEntry {
    private int episodeId;
    private String title;
    private String subTitle;
    private String description;
    private Calendar startTime;
    private Calendar endTime;
    private int programId;
    private String programName;
    private int channelId;
    private String channelName;
    private String imgUrl;
    private String imgUrlTemplate;

    /**
     * This is just a container for schedule information
     */
    public ScheduleEntry() {

    }

    /**
     *
     * @return ID for the episode
     */
    public int getEpisodeId() {
        return episodeId;
    }

    /**
     *
     * @param episodeId Set the ID for the episode
     */
    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    /**
     *
     * @return Title of the show
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title sets the title of the show to the given
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return The sub title for the show
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     *
     * @param subTitle set the sub title to the given string
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     *
     * @return The description of the program
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description Set the description for the program
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return The time the program starts
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime Set the time for the program to start
     */
    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return The time the program ends
     */
    public Calendar getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime Set the time the program ends
     */
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return The ID for the program
     */
    public int getProgramId() {
        return programId;
    }

    /**
     *
     * @param programId Set the ID for the program to the given int
     */
    public void setProgramId(int programId) {
        this.programId = programId;
    }

    /**
     *
     * @return Name of the program
     */
    public String getProgramName() {
        return programName;
    }

    /**
     *
     * @param programName Set the name for the program
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     *
     * @return The ID for the channel this program belongs to
     */
    public int getChannelId() {
        return channelId;
    }

    /**
     *
     * @param channelId Set the channel ID for this program
     */
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    /**
     *
     * @return Name of the channel this program belongs to
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     *
     * @param channelName Set the name for this programs channel
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     *
     * @return URL to a image related to this program
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     *
     * @param imgUrl Set a URL for image related to this program
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     *
     * @return Template image url to an image related to this program
     */
    public String getImgUrlTemplate() {
        return imgUrlTemplate;
    }

    /**
     *
     * @param imgUrlTemplate Set a template image url to an image related
     *                       to this program
     */
    public void setImgUrlTemplate(String imgUrlTemplate) {
        this.imgUrlTemplate = imgUrlTemplate;
    }

    /**
     *
     * @return Formatted string displaying all the information about the program
     */
    @Override
    public String toString() {
        return "ScheduleEntry{" +
                "episodeId=" + episodeId +
                ",\n title='" + title + '\'' +
                ",\n subTitle='" + subTitle + '\'' +
                ",\n description='" + description + '\'' +
                ",\n startTime=" + startTime.getTime() +
                ",\n endTime=" + endTime.getTime() +
                ",\n programId=" + programId +
                ",\n programName='" + programName + '\'' +
                ",\n channelId=" + channelId +
                ",\n channelName='" + channelName + '\'' +
                ",\n imgUrl='" + imgUrl + '\'' +
                ",\n imgUrlTemplate='" + imgUrlTemplate + '\'' +
                "\n}";
    }

    //TODO add exit(1) in the catch clauses that should exit the program
}
