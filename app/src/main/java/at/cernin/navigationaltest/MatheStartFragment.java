package at.cernin.navigationaltest;


import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * Created by Walter on 16.08.2015.
 */

public class MatheStartFragment extends Fragment
        implements View.OnClickListener
{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private int mSectionNumber;
    private Activity mActivity;
    private OnFragmentInteractionListener mListener;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MatheStartFragment newInstance(int sectionNumber) {
        MatheStartFragment fragment = new MatheStartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public MatheStartFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mathe_start, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        ImageButton ib;

        super.onStart();

        if (mActivity == null)
            return;

        ib = (ImageButton) mActivity.findViewById(R.id.imbtStartTraining);
        if (ib != null) {
            ib.setOnClickListener(this);
        }
        ib = (ImageButton) mActivity.findViewById(R.id.imbtStartTesting);
        if (ib != null) {
            ib.setOnClickListener(this);
        }
        ib = (ImageButton) mActivity.findViewById(R.id.imbtStartAuswertung);
        if (ib != null) {
            ib.setOnClickListener(this);
        }
        ib = (ImageButton) mActivity.findViewById(R.id.imbtStartSocialMedia);
        if (ib != null) {
            ib.setOnClickListener(this);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mActivity = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onClickCockpitButton(int position);
    }



    public String getFragmentName() {
        return getString(R.string.title_section0);
    }


    @Override
    public void onClick(View v) {
        if (mListener != null) {
            switch (v.getId()) {
                case R.id.imbtStartTraining:
                    mListener.onClickCockpitButton(1);
                    break;
                case R.id.imbtStartTesting:
                    mListener.onClickCockpitButton(2);
                    break;
                case R.id.imbtStartAuswertung:
                    mListener.onClickCockpitButton(3);
                    break;
                case R.id.imbtStartSocialMedia:
                    mListener.onClickCockpitButton(4);
                    break;
                default:
                    throw new IndexOutOfBoundsException(getFragmentName()
                            + " Fragment for button " + v.getId() + "not implemented");
            }
        }
    }
}
