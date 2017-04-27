package hu.uniobuda.nik.betherichest.Factories;

import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.uniobuda.nik.betherichest.GameObjects.Game;
import hu.uniobuda.nik.betherichest.GameObjects.Investment;
import hu.uniobuda.nik.betherichest.GameObjects.Leaders;
import hu.uniobuda.nik.betherichest.R;

/**
 * Created by Kristof on 2017. 04. 18..
 */

public class LeadersFactory {

    private static List<Leaders> leaders = new ArrayList<>();




    public static List<Leaders> getLeaders()  {


        /*XmlPullParserFactory  xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser myparser = xmlFactoryObject.newPullParser();
        myparser.setInput(new FileInputStream(String.valueOf(R.xml.convertcsv)),"utf-8");


        int eventType =  myparser.getEventType();
        while(eventType!= XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_TAG:
                    String name = myparser.getAttributeValue(0);
                    long money =  Long.valueOf(myparser.getAttributeValue(1));
                    leaders.add(new Leaders(name,money));

            }

                    eventType = myparser.next();

        }*/



        leaders.add(new Leaders("Bill Gates",500000000));
        leaders.add(new Leaders("Jeff Bezos",40000000));
        leaders.add(new Leaders("Amancio Ortega",300000000));
        leaders.add(new Leaders("Mark Zuckerberg",300000000));
        leaders.add(new Leaders("Carlos Slim Helu",300000000));
        leaders.add(new Leaders("Larry Ellison",300000000));
        leaders.add(new Leaders("Charles Koch",300000000));
        leaders.add(new Leaders("David Koch",300000000));
        leaders.add(new Leaders("Michael Bloomberg",300000000));
        leaders.add(new Leaders("Bernard Arnault",300000000));
        leaders.add(new Leaders("Larry Page",300000000));
        leaders.add(new Leaders("David Koch",300000000));
        leaders.add(new Leaders("Michael Bloomberg",300000000));
        leaders.add(new Leaders("Bernard Arnault",300000000));
        leaders.add(new Leaders("Larry Page",300000000));
        return leaders;
    }


}

