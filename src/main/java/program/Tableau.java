package program;

import java.util.Calendar;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class Tableau {
    private int episodeId;
    private String title;
    private String subTitle;
    private Calendar startTime;
    private Calendar endTime;
    private int programId;
    private String programName;
    private int channelId;
    private String channelName;
    private String imgUrl;
    private String imgUrlTemplate;

    public Tableau() {

    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrlTemplate() {
        return imgUrlTemplate;
    }

    public void setImgUrlTemplate(String imgUrlTemplate) {
        this.imgUrlTemplate = imgUrlTemplate;
    }
}
