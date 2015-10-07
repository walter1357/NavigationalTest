package at.cernin.navigationaltest;

//import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import at.cernin.mathstack.model.MathTrainingStatistics;

/**
 * Created by Walter on 03.10.2015.
 *
 * View der vom Trainingsadapter verwaltet wird
 */
public class TrainingStartFragmentRowView
        extends RelativeLayout
        implements MathTrainingStatistics.MathTrainingStatiticsListener {

    // Link to StatisticItem-Data
    private MathTrainingStatistics.StatisticItem statisticItem = null;

    // Controlls im View
    ImageView imageViewTheme;
    TextView textViewTheme;
    ImageViewTrainingStatistics imageViewStatistic;
    TextView textViewAction1;
    TextView textViewAction2;
    ImageView imageViewAction;

    public static TrainingStartFragmentRowView inflate( ViewGroup parent ) {

        TrainingStartFragmentRowView trainingView =
                (TrainingStartFragmentRowView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_trainingsstart_rowview, parent, false);
        return trainingView;
    }

    public TrainingStartFragmentRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.adapter_trainingsstart_rowview_children, this, true);
        setupChildren();    }

    /*
    Not Used
     */
    public TrainingStartFragmentRowView(Context context, AttributeSet attrs, int defStyleAttr, ImageView imageViewAction) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.adapter_trainingsstart_rowview_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        imageViewTheme = (ImageView) findViewById(R.id.trainingsadapter_theme_icon);
        textViewTheme = (TextView) findViewById(R.id.trainingsadapter_theme_label);
        imageViewStatistic = (ImageViewTrainingStatistics) findViewById(R.id.trainingsadapter_action_statistic);
        textViewAction1 = (TextView) findViewById(R.id.trainingsadapter_action_label1);
        textViewAction2 = (TextView) findViewById(R.id.trainingsadapter_action_label2);
        imageViewAction = (ImageView) findViewById(R.id.trainingsadapter_action_icon);
    }

    public MathTrainingStatistics.StatisticItem getStatisticItem() {
        return statisticItem;
    }

    public void setStatisticItem(MathTrainingStatistics.StatisticItem statisticItem) {
        clearStatisticItem();
        this.statisticItem = statisticItem;
        this.statisticItem.setChangeListener(this);
        updateChildren();
    }

    private void clearStatisticItem() {
        if (statisticItem != null) {
            if (statisticItem.getChangeListener() == this ) {
                statisticItem.setChangeListener(null);
            }
            statisticItem = null;
        }
    }

    private void updateChildren(){
        // fill Data
        if (statisticItem == null) { // undefinierte Zeile
            imageViewTheme.setVisibility(View.GONE);
            textViewTheme.setVisibility(View.GONE);
            imageViewStatistic.setVisibility(View.GONE);
            textViewAction1.setVisibility(View.GONE);
            textViewAction2.setVisibility(View.GONE);
            imageViewAction.setVisibility(View.GONE);
        }
        else if (statisticItem.getIcon() != 0) { // Überschriftenzeile
            imageViewTheme.setImageResource(statisticItem.getIcon());
            textViewTheme.setText(statisticItem.getTitle());

            imageViewTheme.setVisibility(View.VISIBLE);
            textViewTheme.setVisibility(View.VISIBLE);
            imageViewStatistic.setVisibility(View.GONE);
            textViewAction1.setVisibility(View.GONE);
            textViewAction2.setVisibility(View.GONE);
            imageViewAction.setVisibility(View.GONE);
        }
        else { // Datenanzeigezeile/Schwierigkeitsauswahl
            textViewAction1.setText(statisticItem.getTitle());
            // Anzahl der Fragen die in dieser Session noch nicht gestellt wurden
            textViewAction2.setText(
                    Integer.toString(statisticItem.getQuestionCount()-statisticItem.getQuestionsAnswersOK()) +
                            getContext().getString(R.string.trainings_questions)
            );
            imageViewStatistic.setMaxSize(statisticItem.getQuestionCount());
            imageViewStatistic.setGreenSize(statisticItem.getQuestionsAnswersOK());
            imageViewStatistic.setRedSize(statisticItem.getQuestionsAnswersBad());
            textViewTheme.setVisibility(View.GONE);
            imageViewTheme.setVisibility(View.GONE);
            imageViewStatistic.setVisibility(View.VISIBLE);
            textViewAction1.setVisibility(View.VISIBLE);
            textViewAction2.setVisibility(View.VISIBLE);
            imageViewAction.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        clearStatisticItem();
        super.finalize();
    }


    /**
     * Is called, when new Statistic-Data are available or
     * any statistic data has been changed
     */
    @Override
    public void OnTrainingStatisticsUpdate(  MathTrainingStatistics.StatisticItem statisticData ) {
        ((AppCompatActivity) getContext()).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        updateChildren();
                                                    }

                                                }
        );
    }



} // TrainingStartFragmentRowView
