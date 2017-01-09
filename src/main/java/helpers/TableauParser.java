package helpers;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import program.Tableau;

import javax.swing.*;
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
import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexander Nyström(dv15anm) on 20/12/2016.
 */
public class TableauParser implements Runnable{

    private List<URL> urls;
    private List<Tableau> tableaus;

    /**
     * Parser to parse and create tableau's for radio channels
     */
    public TableauParser() {
        tableaus = Collections.synchronizedList(new ArrayList<Tableau>());
        urls = Collections.synchronizedList(new ArrayList<URL>());
    }

    /**
     * Takes the given api url and gets the url for yesterday and tomorrow
     * and then parses the pages of the xml's with one thread for each day.
     * @param apiUrl url to the schedule api
     * @return List with all tableau's that was parsed
     */
    public List<Tableau> parseTableauApi(String apiUrl) {

        try {
            //Create the urls for yesterday, today and tomorrow
            URL yesterday = new URL(apiUrl + getYesterday());
            URL tomorrow = new URL(apiUrl + getTomorrow());
            urls.add(yesterday);
            urls.add(new URL(apiUrl));
            urls.add(tomorrow);
            Thread[] threads = new Thread[3];
            for (int i = 0; i < 2; i++){
                threads[i] = new Thread(this);
                threads[i].start();
            }
            run();

            for (int i = 0; i < 2; i++){
                threads[i].join();
            }

        } catch (InterruptedException | IOException e) {
            JOptionPane.showMessageDialog(null,"There " +
                    "were an internal error");
        }
        //Sorts the list by the start time
        tableaus.sort(Comparator.comparing(Tableau::getStartTime));
        return tableaus;
    }

     /**
     * Create a channel object for each channel found in the parsed document
      * and adds it to a list of tableau's
     * @param path path to traverse the parsed tree
     * @param doc the parsed document
     */
    private void buildTableau(XPath path, Document doc) {
        try {
            int episodeCount = Integer.parseInt(path.evaluate(
                    "count(/sr/schedule/scheduledepisode)", doc));
            Calendar rightNow = Calendar.getInstance();
            for (int i = 0; i < episodeCount; i++){
                Calendar startTime = DatatypeConverter.parseDateTime(
                        path.evaluate("/sr/schedule/scheduledepisode["+(i+1)+"]/" +
                                "starttimeutc",doc));
                if (isWithinInterval(rightNow, startTime)) {
                    Tableau tableau = new Tableau();
                    String id = path.evaluate(
                            "/sr/schedule/scheduledepisode["+(i+1)+
                                    "]/episodeid", doc);
                    if (id.compareTo("") != 0) {
                        tableau.setEpisodeId(Integer.parseInt(id));
                    }

                    tableau.setTitle(path.evaluate("/sr/schedule/" +
                            "scheduledepisode["+(i+1)+ "]/title", doc));

                    tableau.setSubTitle(path.evaluate("/sr/schedule/" +
                                    "scheduledepisode["+(i+1)+"]" +
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
    }

    /**
     * Will parse each page for each url it's thread safe since they only share
     * list with urls which is synchronized
     */
    public void run() {
        DocumentBuilder parser;
        XPath path;
        URL url = urls.remove(0);
        try {
            Document doc;
            DocumentBuilderFactory dbfactory =
                    DocumentBuilderFactory.newInstance();
            parser = dbfactory.newDocumentBuilder();
            XPathFactory xpfactory = XPathFactory.newInstance();
            path = xpfactory.newXPath();
            doc = parser.parse(url.openStream());

            while ((url = getNextPage(path, doc)) != null) {
                doc = parser.parse(url.openStream());
                buildTableau(path, doc);
            }
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            JOptionPane.showMessageDialog(null,"There " +
                    "were an internal error");
        }
    }

    /**
     * Checks if the given time is within a 24h interval of current time.
     * That is if startTime is 12h behind or before current time
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
     *          &date=YYYY-MM-DD with the date from yesterday
     */
    private String getYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE,-1);
        String yDay;
        if (yesterday.get(Calendar.MONTH) == 0) {
            yDay = "&date="+ yesterday.get(Calendar.YEAR) + "-" +
                    "1" + "-" +
                    yesterday.get(Calendar.DATE);
        } else {
            yDay = "&date="+ yesterday.get(Calendar.YEAR) + "-" +
                    yesterday.get(Calendar.MONTH) + "-" +
                    yesterday.get(Calendar.DATE);
        }

        return yDay;
    }

    /**
     *
     * @return formatted string to attach to the url with the format
     *          &date=YYYY-MM-DD for the date tomorrow
     */
    private String getTomorrow(){
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE,1);
        String to;
        if (tomorrow.get(Calendar.MONTH) == 0) {
            to = "&date=" + tomorrow.get(Calendar.YEAR) + "-" +
                    "1" + "-" +
                    tomorrow.get(Calendar.DATE);
        } else {
            to = "&date=" + tomorrow.get(Calendar.YEAR) + "-" +
                    tomorrow.get(Calendar.MONTH) + "-" +
                    tomorrow.get(Calendar.DATE);
        }

        return to;
    }

    /**
     * Gets the next page from the parsed document
     * @param path path to traverse the tree
     * @param doc the document with the parsed xml
     * @return the url to the next xml page
     */
    private URL getNextPage(XPath path, Document doc) {
        try {
            String url = path.evaluate("/sr/pagination/nextpage",doc);
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

}
