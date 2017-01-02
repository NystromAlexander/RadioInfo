package helpers;

import gui.InfoWindow;
import gui.MainWindow;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import program.Channel;
import program.Tableau;
import program.Updater;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class ChannelParser {
    private DocumentBuilder parser;
    private XPath path;
    private String find;
    public ChannelParser() {

        DocumentBuilderFactory dbfactory =
                DocumentBuilderFactory.newInstance();
        try {
            parser = dbfactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        XPathFactory xpfactory = XPathFactory.newInstance();
        path = xpfactory.newXPath();
    }

    /**
     * Takes the given api url and will parse the xml returned and create a
     * channel object for each parsed channel, then return a list with all
     * channels found
     * @param apiUrl url to the channel api
     * @return List with all channels that was parsed
     */
    public ArrayList<Channel> parseChannelApi(String apiUrl) {
        ArrayList<Channel> channels = new ArrayList<>();
        Document doc;

        try {
            URL url = new URL(apiUrl);
            InputStream input = url.openStream();
            find = url.toString();
            doc = parser.parse(input);
            buildChannels(channels,doc);
            while ((url = getNextPage(doc)) != null) {
                find = url.toString();
                doc = parser.parse(url.openStream());
                buildChannels(channels,doc);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return channels;
    }

    /**
     * Gets the next page from the parsed document
     * @param doc the document with the parsed xml
     * @return the url to the next xml page
     */
    private URL getNextPage(Document doc) {
        try {
            String url = path.evaluate("/sr/pagination/nextpage",doc);
//            System.out.println("Next page: "+url);
            if (url.compareTo("") != 0){
                return new URL(url);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a channel object for each channel found in the parsed document
     * @param channels list to put all the channels in
     * @param doc the parsed document
     * @return list with channels
     */
    private ArrayList<Channel> buildChannels(ArrayList<Channel> channels, Document doc) {

        try {
            int channelCount = Integer.parseInt(path.evaluate("count(/sr/channels/channel)", doc));
            for (int i = 0; i < channelCount; i++){
                Channel channel = new Channel();

                channel.setId(Integer.parseInt(path.evaluate("/sr/channels/channel["+(i+1)+"]/@id", doc)));
                channel.setName(path.evaluate("/sr/channels/channel["+(i+1)+"]/@name", doc));
                channel.setImgUrl(path.evaluate("/sr/channels/channel["+(i+1)+"]/image", doc));
                channel.setImgTemplateUrl(path.evaluate("/sr/channels/channel["+(i+1)+"]/imagetemplate", doc));
                channel.setColor(path.evaluate("/sr/channels/channel["+(i+1)+"]/color", doc));
                channel.setTagLine(path.evaluate("/sr/channels/channel["+(i+1)+"]/tagline", doc));
                channel.setSiteUrl(path.evaluate("/sr/channels/channel["+(i+1)+"]/siteurl", doc));
                channel.setLiveUrl(path.evaluate("/sr/channels/channel["+(i+1)+"]/liveaudio/url", doc));
                channel.setLiveStartKey(path.evaluate("/sr/channels/channel["+(i+1)+"]/liveaudio/statkey", doc));
                channel.setScheduleUrl(path.evaluate("/sr/channels/channel["+(i+1)+"]/scheduleurl", doc));
                channel.setChannelType(path.evaluate("/sr/channels/channel["+(i+1)+"]/channeltype", doc));

                channels.add(channel);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return channels;
    }

    public static void main (String[] args) {
//        ChannelParser parser = new ChannelParser();
//        ArrayList<Channel> channels = parser.parseChannelApi("http://api.sr.se/api/v2/channels");
//        TableauParser tableauParser = new TableauParser();
//        List<Tableau> tableaus = tableauParser.parseTableauApi("http://api.sr.se/api/v2/scheduledepisodes?channelid=164");
        Updater updater = new Updater();
        List<JTabbedPane> channels = updater.update();
        JTabbedPane startPanel = channels.get(0);
        JTabbedPane p4Panel = channels.get(1);
        JTabbedPane srEPanel = channels.get(2);

        MainWindow window = new MainWindow(startPanel,p4Panel,srEPanel);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setUpGUI();
            }
        });
//        window.setVisible(true);

//        Calendar rightNow = Calendar.getInstance();
//        Calendar yesterday = Calendar.getInstance();
//        yesterday.add(Calendar.DAY_OF_YEAR,-1);
//        System.out.println(rightNow.get(Calendar.YEAR)+"-"+rightNow.get(Calendar.MONTH)+"-"+rightNow.get(Calendar.DATE));
//        System.out.println(yesterday.get(Calendar.YEAR)+"-"+yesterday.get(Calendar.MONTH)+"-"+yesterday.get(Calendar.DATE));
//        System.out.println(rightNow.getTime());
//        for (Channel channel: channels
//             ) {
//            System.out.println(channel);
//
//        }
    }
}
