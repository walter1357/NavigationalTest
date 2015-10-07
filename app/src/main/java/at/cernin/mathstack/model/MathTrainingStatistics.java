package at.cernin.mathstack.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import at.cernin.navigationaltest.R;

/**
 * Created by Walter on 24.09.2015.
 */

public class MathTrainingStatistics implements Runnable {

    /**
     * Interface damit die MathTrainingStatistics-Klasse
     * eine Funktion zum Aufrufen in der Klasse hat
     * die den Listener bereitstellt
     */

    public interface MathTrainingStatiticsListener {


        public void OnTrainingStatisticsUpdate( StatisticItem statisticData );

    }

    /*
     * Statistikdatensatz, der pro Aufgabenklasse
     * verfügbar gemacht wird
     */
    public class StatisticItem {

        /**
         * Konfigurationsdaten des Übungsmenüs
         */
        int thema = 0;
        int id = 0;
        String title = null;
        int icon = 0;
        /**
         * Statistische Daten der Auswertung von Übungen
         */
        private volatile int questionCount = 0;
        private volatile int questionsAnswersOK = 0;
        private volatile int questionsAnswersBad = 0;

        /**
         * Management des Statistik-Daten Listeners,
         * der für den ScreenUpdate der Daten zuständig ist.
         */
        private MathTrainingStatiticsListener changeListener = null;
        private boolean inChange = false;


        public StatisticItem(int thema, int id, String title, int icon) {
            this.thema = thema;
            this.id = id;
            this.title = title;
            this.icon = icon;
        }

        private void onChange() {
            if (inChange) { return; }
            if (changeListener != null) {
                inChange = true;
                changeListener.OnTrainingStatisticsUpdate( this );
                inChange = false;
            }
        }

        public int getQuestionCount() {
            return questionCount;
        }

        public void setQuestionCount(int questionCount) {
            this.questionCount = questionCount;
            onChange();
        }

        public int getQuestionsAnswersBad() {
            return questionsAnswersBad;
        }

        public void setQuestionsAnswersBad(int questionsAnswersBad) {
            this.questionsAnswersBad = questionsAnswersBad;
            onChange();
        }

        public int getQuestionsAnswersOK() {
            return questionsAnswersOK;
        }

        public void setQuestionsAnswersOK(int questionsAnswersOK) {
            this.questionsAnswersOK = questionsAnswersOK;
            onChange();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public MathTrainingStatiticsListener getChangeListener() {
            return changeListener;
        }

        public void setChangeListener(MathTrainingStatiticsListener changeListener) {
            this.changeListener = changeListener;
        }


    }

    public ArrayList<StatisticItem> statisticData = new ArrayList<StatisticItem>( );

    Thread updateThread = null;
    private volatile boolean runningOK = true;


    private void readTrainingMenuJSON(Context context) {

        try {
            // Read JSON-Fil in a String
            InputStream is = context.getResources().openRawResource(R.raw.training_menuj);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jString = new String(buffer);
            buffer=null;

            // Convert String in JSON-Objects
            JSONObject jsonObject = new JSONObject(jString);
            JSONArray ja = jsonObject.getJSONArray("Daten");
            for ( int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                int thema = jo.getInt("Thema");
                int id = jo.getInt("Id");
                String title = jo.getString("Title");
                String iconName = jo.getString("Icon");
                int icon;
                // Gefundene Items verarbeiten
                if (iconName.isEmpty()) {
                    icon = 0;
                }
                else {
                    icon = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
                    if (icon == 0) {
                        icon = R.drawable.ic_action_help;
                    }
                }
//                Log.i("MathTrainingStatistics",
//                     "Thema: " + thema + ", Id: " + id + ", Titel: <" + title + ">, " +
//                             "IconName: <" + iconName + ">, Icon: " + icon + ""
//                );
                statisticData.add( new StatisticItem( thema, id, title, icon ));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    } // readTrainingMenuJSON()

    /**
     * Read the XML resource R.xml.read_training_menu.xml into an ArrayList
     */
    private void readTrainingMenuXML(Context context) {
        try {
            int thema;
            int id;
            String title;
            int icon;
            String iconName;
            XmlResourceParser xrp= context.getResources().getXml(R.xml.training_menux);
            int eventType=xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG &&
                    xrp.getName().equalsIgnoreCase("Item")) {
                    title = ""; iconName = "";
                    id = xrp.getAttributeIntValue(null,"Id", 0);
                    thema = xrp.getAttributeIntValue(null, "Thema", 0);

                    while (!(eventType == XmlPullParser.END_DOCUMENT ||
                            (eventType == XmlPullParser.END_TAG && xrp.getName().equalsIgnoreCase("Item")))) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if (xrp.getName().equalsIgnoreCase("Title")) {
                                title = xrp.nextText();
                            }
                            else if (xrp.getName().equalsIgnoreCase("Icon")) {
                                iconName = xrp.nextText();
                            }
                        }
                        eventType=xrp.next();
                    } // while Item
                    // Gefundene Items verarbeiten
                    if (iconName.isEmpty()) {
                        icon = 0;
                    }
                    else {
                        icon = context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
                        if (icon == 0) {
                            icon = R.drawable.ic_action_help;
                        }
                    }
//                     Log.i("MathTrainingStatistics",
//                             "Thema: " + thema + ", Id: " + id + ", Titel: <" + title + ">, " +
//                                     "IconName: <" + iconName + ">, Icon: " + icon + ""
//                     );
                    statisticData.add( new StatisticItem( thema, id, title, icon ));
                } // Item
                eventType=xrp.next();
            } // while Dokument
        }
        catch (  XmlPullParserException e) {
            e.printStackTrace();
        }
        catch (  IOException e) {
            e.printStackTrace();
        }

    } // readTrainingMenuXML()


    public MathTrainingStatistics( Context context) {

        //readTrainingMenuXML( context );

        readTrainingMenuJSON(context);

        updateThread = new Thread( this );
        if (updateThread != null) {
            updateThread.start();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        runningOK = false;
        if ( updateThread != null) {
            updateThread.join(400);
        }
        super.finalize();
    }

    @Override
    public void run() {
        int i = 0;
        while (runningOK) {
            if (i < statisticData.size()) {
                StatisticItem sd = statisticData.get(i);
                int maxSize = 1000;
                int redSize = (int) (Math.random()*(maxSize*0.8f));
                int greenSize = (int) (Math.random()*(maxSize*0.8f));
                if (redSize+greenSize > maxSize) {
                    redSize = maxSize-greenSize;
                }
                sd.setQuestionCount(maxSize);
                sd.setQuestionsAnswersBad(redSize);
                sd.setQuestionsAnswersOK(greenSize);
                i++;
            }
            else {
                i = 0;
            }

            // entweder 2 Sekunden warten oder auf Ablauf von runningOK warten
            int loop = 20;
            while (runningOK && loop-- > 0) {
                // kurze pause einlegen, damit nicht zu schnell
                //try {
                //Mindestens alle 100ms testen, ob die Wartezeit schon abgelaufen ist.
                //    //Thread.sleep(100); // interrupts ON
                SystemClock.sleep(100); // interrupts OFF
                //}
                //catch (InterruptedException e) {
                //    // do nothing
                //}
            }
        }
    }


    public StatisticItem get( int position ) {
        return statisticData.get( position );
    }

    public int getCount() {
        return statisticData.size();
    }


    public boolean addListener( MathTrainingStatiticsListener changeListener, int position ) {
        if ( position >= 0 && position < statisticData.size()) {
            statisticData.get(position).changeListener = changeListener;
        }
        else {
            return false;
        }
        return true;
    }


}


