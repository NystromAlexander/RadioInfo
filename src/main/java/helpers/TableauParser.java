package helpers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import program.Tableau;

import javax.xml.bind.DatatypeConverter;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class TableauParser {

    private DocumentBuilder parser;
    private XPath path;


    public TableauParser() {

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
    public ArrayList<Tableau> parseTableauApi(String apiUrl) {
        ArrayList<Tableau> tableaus = new ArrayList<Tableau>();
        Document todayDoc;
        Document ydayDoc;
        Document tomDoc;
        try {
            URL yesterday = new URL(apiUrl+getYesterday());
            URL tomorrow = new URL(apiUrl+getTomorrow());
            URL today = new URL(apiUrl);
            System.out.println(yesterday+"\n"+today+"\n"+tomorrow);
            InputStream todayInput = today.openStream();
            ydayDoc = parser.parse(yesterday.openStream());
            tableaus = buildTableau(tableaus,ydayDoc);
            while ((yesterday = getNextPage(ydayDoc)) != null) {
                ydayDoc = parser.parse(yesterday.openStream());
                tableaus = buildTableau(tableaus, ydayDoc);
            }
            todayDoc = parser.parse(todayInput);
            tableaus = buildTableau(tableaus,todayDoc);
            while ((today = getNextPage(todayDoc)) != null) {
                todayDoc = parser.parse(today.openStream());
                tableaus = buildTableau(tableaus, todayDoc);
            }
            tomDoc = parser.parse(tomorrow.openStream());
            tableaus = buildTableau(tableaus,tomDoc);
            while ((tomorrow = getNextPage(tomDoc)) != null) {
                tomDoc = parser.parse(tomorrow.openStream());
                tableaus = buildTableau(tableaus, tomDoc);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return tableaus;
    }

    /**
     * Create a channel object for each channel found in the parsed document
     * @param tableaus list to put all the channels in
     * @param doc the parsed document
     * @return list with channels
     */
    private ArrayList<Tableau> buildTableau(ArrayList<Tableau> tableaus, Document doc) {
        try {
            int episodeCount = Integer.parseInt(path.evaluate(
                    "count(/sr/schedule/scheduledepisode)", doc));
            Calendar rightNow = Calendar.getInstance();
//            System.out.println("channel count: "+episodeCount);
            for (int i = 0; i < episodeCount; i++){
                Tableau tableau = new Tableau();
                Calendar startTime = DatatypeConverter.parseDateTime(
                        path.evaluate("/sr/schedule/scheduledepisode["+(i+1)+"]/" +
                                "starttimeutc",doc));
//                System.out.println(startTime.get(Calendar.HOUR)+":"+startTime.get(Calendar.MINUTE)+":"+startTime.get(Calendar.SECOND));
                if (isWithinInterval(rightNow, startTime)) {
                    String id = path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                                    "]/episodeid", doc);
                    if (id.compareTo("") != 0) {
                        tableau.setEpisodeId(Integer.parseInt(id));
                    }

                    tableau.setTitle(path.evaluate("/sr/schedule/" +
                            "scheduledepisode["+(i+1)+ "]/title", doc));

                    tableau.setSubTitle(path.evaluate("/sr/schedule/" +
                                    "scheduledepisode["+(i+1)+ "]" +
                            "/subtitle", doc));

                    tableau.setDescription(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                                    "]/description", doc));

                    tableau.setStartTime(startTime);

                    tableau.setEndTime(DatatypeConverter.parseDateTime(
                            path.evaluate("/sr/schedule/" +
                                    "scheduledepisode["+(i+1)+"]/" +
                                    "endtimeutc", doc)));

                    tableau.setProgramId(Integer.parseInt(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                            "]/program/@id", doc)));

                    tableau.setProgramName(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                            "]/program/@name", doc));

                    tableau.setChannelId(Integer.parseInt(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                            "]/channel/@id", doc)));

                    tableau.setChannelName(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                            "]/channel/@name", doc));

                    tableau.setImgUrl(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                            "]/imageurl", doc));

                    tableau.setImgUrlTemplate(path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                            "]/imageurltemplate", doc));

                    tableaus.add(tableau);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return tableaus;
    }

    /**
     * Checks if the given time is within a 24h interval of current time.
     * That is if compareTo is 12h behind or before current time
     * @param rightNow current time
     * @param startTime time to compare to
     * @return true if the time is withing the interval
     */
    private boolean isWithinInterval(Calendar rightNow, Calendar startTime) {
        return Math.abs(startTime.getTimeInMillis() -
                rightNow.getTimeInMillis()) <=
                TimeUnit.HOURS.toMillis(12);
    }

    /**
     *
     * @return formatted string to attach to the url to on the format
     *          &date=YYYY-MM-DD
     */
    private String getYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR,-1);
        String yDay = "&date="+ yesterday.get(Calendar.YEAR) + "-" +
                yesterday.get(Calendar.MONTH) + "-" +
                yesterday.get(Calendar.DATE);
        return yDay;
    }

    /**
     *
     * @return formatted string to attach to the url to on the format
     *          &date=YYYY-MM-DD
     */
    private String getTomorrow(){
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR,1);
        String to = "&date=" + tomorrow.get(Calendar.YEAR) + "-" +
                tomorrow.get(Calendar.MONTH) + "-" +
                tomorrow.get(Calendar.DATE);
        return to;
    }

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

    public static void main (String[] args) {
        TableauParser parser = new TableauParser();
        ArrayList<Tableau> tableaus = parser.parseTableauApi("http://api.sr.se/api/v2/scheduledepisodes?channelid=164");

        for (Tableau tableau: tableaus
                ) {
            System.out.println(tableau);

        }
    }
}
