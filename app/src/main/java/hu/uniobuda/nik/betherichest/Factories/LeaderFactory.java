package hu.uniobuda.nik.betherichest.Factories;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Leader;

/**
 * Created by Kristof on 2017. 04. 18..
 */

public class LeaderFactory {

    private static List<Leader> leaders;
    Game game = Game.Get();

    public LeaderFactory() {
        leaders = new ArrayList<>();
    }

    public List<Leader> getLeaders() {
        return leaders;
    }

    Leader leader;
    String text;

    public String ReadText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    public List<Leader> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        List<String> names = null;
        List<Long> moneys = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(is, null);

            names = new ArrayList<>();
            moneys = new ArrayList<>();

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                boolean mehet = false;
                if (XmlPullParser.START_TAG == eventType && tagname.equalsIgnoreCase("Name")) {
                    String name = ReadText(parser);
                    names.add(name);
                } else if (XmlPullParser.START_TAG == eventType && tagname.equalsIgnoreCase("Money")) {
                    long money = Long.parseLong(ReadText(parser));
                    moneys.add(money);
                }


                eventType = parser.next();

            }
            for (int i = 1; i <= moneys.size(); i++) {
                leaders.add(new Leader(names.get(i), moneys.get(i)));
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long userMoney = Math.round(game.getCurrentMoney());
            leaders.add(new Leader("Játékos", userMoney, true));
            Collections.sort(leaders, new Comparator<Leader>() {
                @Override
                public int compare(Leader o1, Leader o2) {
                    return Long.valueOf(o2.getMoney()).compareTo(o1.getMoney());
                }
            });
        }


        return leaders;


    }

}

