package at.cernin.navigationaltest;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import at.cernin.mathstack.model.MathTrainingStatistics;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class TrainingStartFragment extends Fragment
        implements AbsListView.OnItemClickListener {

    /**
     * Klasse die eine Row für das Trainingsstartfragment in
     * Form eines Adapters verwaltet und für den ListView zur
     * Verfügung stellt.
     */
    private class TrainingAdapter
            extends ArrayAdapter<String> {

        private final MathTrainingStatistics mathTrainingStatistics;

         //Adapter for one row with an statisticItem
        public TrainingAdapter(Context context, MathTrainingStatistics mathTrainingStatistics) {
            super(context, 0);
            this.mathTrainingStatistics = mathTrainingStatistics;
        }


        @Override
        public int getCount() {
            return mathTrainingStatistics.getCount();
        }

        @Override
        public boolean areAllItemsEnabled() {
             // return super.areAllItemsEnabled();
             return false;
        }

        @Override
        public boolean isEnabled(int position) {
             //return super.isEnabled(position);
             return (mathTrainingStatistics.get(position).getIcon() == 0);
        }

         @Override
        public View getView( int position, View convertView, ViewGroup parent) {
             TrainingStartFragmentRowView itemView = (TrainingStartFragmentRowView) convertView;
             // reuse View
             if (itemView == null) {
                 itemView = TrainingStartFragmentRowView.inflate(parent);
             }
             itemView.setStatisticItem(mathTrainingStatistics.get(position));

             return itemView;
         }

            //    LayoutInflater inflater = (LayoutInflater) context
            //            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //    rowView = inflater.inflate(R.layout.adapter_trainingsstart_rowview, parent, false);
     } // Trainingadapter


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Statistic from Trainings Data
     */
    MathTrainingStatistics mathTrainingStatistics;
    /**
     * Communication Link to the Activity
     */
    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static TrainingStartFragment newInstance(String param1, String param2) {
        TrainingStartFragment fragment = new TrainingStartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrainingStartFragment() {

        // Hier gibt es noch keine Context, mt dem Ressourcen geladen werden können
        // Aufruf in das onCreate verlagert.
        // mathTrainingStatistics = new MathTrainingStatistics( getActivity() );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // aus dem Constructor übernommen, weil es im Constructor des Fragments
        // noch keinen Ressourcenzugriff gibt
        mathTrainingStatistics = new MathTrainingStatistics( getActivity() );

        // TODO: Change Adapter to display your content
        // mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //         android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);

        mAdapter = new TrainingAdapter(getActivity(), mathTrainingStatistics);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainingstart, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
        Toast.makeText(getActivity(), "Auswahl " + position + " geklickt", Toast.LENGTH_SHORT).show();
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**t to be communicated
     * to the activity and potentially other fragments
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragmennts contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public String getFragmentName() {
        return mParam1;
    }


} // class TrainingStartFragment
