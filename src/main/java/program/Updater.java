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

    public Updater() {
        threadAccess = Collections.synchronizedList(new ArrayList<Channel>());
        finished = Collections.synchronizedList(new ArrayList<Channel>());
        initialUpdate();
    }

    private void initialUpdate(){
        ChannelParser channelParser = new ChannelParser();
        channels = channelParser.parseChannelApi(channelAPI);
    }

    public ArrayList<JTabbedPane> update() {
        finished.clear();
        threadAccess.addAll(channels);
        Thread[] threads = new Thread[channels.size()];
        for (int i = 0; i < channels.size(); i++) {
            threads[i] = new Thread(this);
            threads[i].start();
        }
        for (int j = 0; j < threads.length; j++){
            try {
                threads[j].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return createPanels();
    }

    private ArrayList<JTabbedPane> createPanels(){
        TabBuilder start = new TabBuilder();
        TabBuilder srE = new TabBuilder();
        TabBuilder p4 = new TabBuilder();
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
        panels.add(startPanel);
        panels.add(p4Panel);
        panels.add(srEPanel);
        return panels;
    }

    public void run() {
        Channel channel = threadAccess.remove(0);
//        threadAccess.remove(channel);
        if (channel.getScheduleUrl().compareTo("" ) == 0) {
//            System.out.println("No shedule: "+channel.getName());
        } else {
            ScheduleParser scheduleParser = new ScheduleParser();
            List<Schedule> schedules =
                    scheduleParser.parseTableauApi(channel.getScheduleUrl());
            channel.setSchedule(schedules);
        }

        finished.add(channel);
    }
}
