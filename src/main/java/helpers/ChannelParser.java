package helpers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import program.Channel;

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
        ArrayList<Channel> channels = new ArrayList<Channel>();
        Document doc;
        try {
            URL urlObject = new URL(apiUrl);
            InputStream input = urlObject.openStream();
            doc = parser.parse(input);
            if (doc != null) {
                buildChannels(channels,doc);
            } else {
                System.out.println("Could not parse: "+ input);
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
        ChannelParser parser = new ChannelParser();
        ArrayList<Channel> channels = parser.parseChannelApi("http://api.sr.se/api/v2/channels");

        for (Channel channel: channels
             ) {
            System.out.println(channel);

        }
    }
}
