/*
* ChannelParser - class to parse xml containing information about
*                 radio channels
*
* Version: 1.0
*
* Created by: Alexander Nyström
*
*/


package helpers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import program.Channel;

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
     * @see Channel
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
        } catch (IOException | SAXException e) {
            JOptionPane.showMessageDialog(null,"There " +
                    "were an internal error");
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
            if (url.compareTo("") != 0){
                return new URL(url);
            }
        } catch (MalformedURLException | XPathExpressionException e) {
            JOptionPane.showMessageDialog(null,"There " +
                    "were an internal error");
            System.exit(1);
        }
        return null;
    }

    /**
     * Create a channel object for each channel found in the parsed document
     * @param channels list to put all the channels in
     * @param doc the parsed document
     * @return list with channels
     */
    private ArrayList<Channel> buildChannels(ArrayList<Channel> channels,
                                             Document doc) {

        try {
            int channelCount = Integer.parseInt(path.evaluate(
                    "count(/sr/channels/channel)", doc));
            for (int i = 0; i < channelCount; i++){
                Channel channel = new Channel();

                channel.setId(Integer.parseInt(path.evaluate(
                        "/sr/channels/channel["+(i+1)+"]/@id", doc)));

                channel.setName(path.evaluate("/sr/channels/channel["+
                        (i+1)+"]/@name", doc));

                channel.setImgUrl(path.evaluate("/sr/channels/channel["
                        +(i+1)+"]/image", doc));

                channel.setImgTemplateUrl(path.evaluate("/sr/" +
                        "channels/channel["+(i+1)+"]/imagetemplate", doc));

                channel.setColor(path.evaluate("/sr/channels/channel["+
                        (i+1)+"]/color", doc));

                channel.setTagLine(path.evaluate("/sr/channels/" +
                        "channel["+(i+1)+"]/tagline", doc));

                channel.setSiteUrl(path.evaluate("/sr/channels/" +
                        "channel["+(i+1)+"]/siteurl", doc));

                channel.setLiveUrl(path.evaluate("/sr/channels/" +
                        "channel["+(i+1)+"]/liveaudio/url", doc));

                channel.setLiveStartKey(path.evaluate("/sr/channels/" +
                        "channel["+(i+1)+"]/liveaudio/statkey", doc));

                channel.setScheduleUrl(path.evaluate("/sr/channels/" +
                        "channel["+(i+1)+"]/scheduleurl", doc));

                channel.setChannelType(path.evaluate("/sr/channels/" +
                        "channel["+(i+1)+"]/channeltype", doc));

                channels.add(channel);
            }
        } catch (XPathExpressionException e) {
            JOptionPane.showMessageDialog(null,"There " +
                    "were an internal error");
        }
        return channels;
    }
}
