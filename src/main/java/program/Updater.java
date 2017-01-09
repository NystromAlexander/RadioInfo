/*
* Updater - class for updating the schedules
*
* Version: 1.0
*
* Created by: Alexander Nyström
*
*/

package program;

import gui.TabBuilder;
import helpers.ChannelParser;
import helpers.ScheduleParser;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexander Nyström(dv15anm) on 21/12/2016.
 */
public class Updater implements Runnable{
    private final String channelAPI = "http://api.sr.se/api/v2/channels";
    private ArrayList<Channel> channels;
    private List<Channel> threadAccess;
    private List<Channel> finished;

    /**
     * Create a updater designed to update schedules for all channels
     * and doing so with multiple threads
     */
    public Updater() {
        threadAccess = Collections.synchronizedList(new ArrayList<Channel>());
        finished = Collections.synchronizedList(new ArrayList<Channel>());
        initialUpdate();
    }

    /**
     * The initial update parses all channels available so that it can parse
     * the schedules when asked
     */
    private void initialUpdate(){
        ChannelParser channelParser = new ChannelParser();
        channels = channelParser.parseChannelApi(channelAPI);
    }

    /**
     * Creates thread for each channel and sets it to parse the schedules
     * @return list with tabbed panels with all the schedules
     */
    public ArrayList<JTabbedPane> update() {
        finished.clear();
        threadAccess.addAll(channels);
        Thread[] threads = new Thread[channels.size()];
        for (int i = 0; i < channels.size(); i++) {
            threads[i] = new Thread(this);
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null,
                        "There were an internal error");
            }
        }
        return createPanels();
    }

    /**
     * Creates the tabbed panels for the different channel groups and
     * add the finished panels to a list
     * @return list with tabbed panels with channel schedules
     */
    private ArrayList<JTabbedPane> createPanels(){
        TabBuilder start = new TabBuilder();
        TabBuilder srE = new TabBuilder();
        TabBuilder p4 = new TabBuilder();
        //group the channels by name
        for (Channel channel: finished) {
            if (channel.getName().startsWith("P4")) {
                p4.createTab(channel);
            } else if (channel.getName().contains("SR Extra")) {
                srE.createTab(channel);
            } else {
                start.createTab(channel);
            }
        }
        JTabbedPane startPanel = start.buildTabbs();
        JTabbedPane p4Panel = p4.buildTabbs();
        JTabbedPane srEPanel = srE.buildTabbs();

        ArrayList<JTabbedPane> panels = new ArrayList<>();
        /*Important that they get added in this order for the main window
            to know which is which */
        panels.add(startPanel);
        panels.add(p4Panel);
        panels.add(srEPanel);
        return panels;
    }

    /**
     * Gets a channel from the list of channels and parse the schedule for that
     * channel and then add the completed channel to a list with finished
     * channels
     *
     * Thread safe run method since it's only two lists all threads share and
     * those lists are synchronized lists.
     *
     */
    public void run() {
        Channel channel = threadAccess.remove(0);
        if (channel.getScheduleUrl().compareTo("" ) != 0) {
            ScheduleParser scheduleParser = new ScheduleParser();
            List<ScheduleEntry> scheduleEntries =
                    scheduleParser.parseTableauApi(channel.getScheduleUrl());
            channel.setScheduleEntry(scheduleEntries);
        }

        finished.add(channel);
    }
}
