package program;

import helpers.ChannelParser;
import helpers.TableauParser;

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

    public List<Channel> update() {
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
        return finished;
    }

    public void run() {
        Channel channel = threadAccess.get(0);
        threadAccess.remove(channel);
        if (channel.getScheduleUrl().compareTo("" ) == 0) {
//            System.out.println("No shedule: "+channel.getName());
        } else {
            TableauParser tableauParser = new TableauParser();
            List<Tableau> tableaus =
                    tableauParser.parseTableauApi(channel.getScheduleUrl());
            channel.setTableau(tableaus);
        }

        finished.add(channel);
    }
}
