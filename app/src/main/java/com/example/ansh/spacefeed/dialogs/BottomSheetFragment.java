package com.example.ansh.spacefeed.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ansh.spacefeed.R;

/**
 * This class is used to make a bottom sheet dialog fragment
 * It needs to be extended as this is a fragment and it'll
 * appear & disappear from the view. So should behave as a fragment.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet_dialog, container, false);

        /**
         * Rather handling click events like this we will create an interface and handle it inside calling
         * activity/fragment so that we can use their methods with context.
          */
//        LinearLayout linearLayout = view.findViewById(R.id.fragment_history_bottom_sheet_edit);
//        linearLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "LOL", Toast.LENGTH_SHORT).show();
//            }
//        });

        // get the views inside the dialog fragment to set click listener to them.
        LinearLayout linearLayout1 = view.findViewById(R.id.fragment_history_bottom_sheet_edit);
        LinearLayout linearLayout2 = view.findViewById(R.id.fragment_history_bottom_sheet_delete);

        // passing the Id's of the button
        linearLayout1.setOnClickListener(v -> {
            mListener.onButtonClicked(v.getId());
            dismiss();
        });
        linearLayout2.setOnClickListener(v -> {
            mListener.onButtonClicked(v.getId());
            dismiss();
        });

        return view;
    }

    /**
     * Interface to listen events for views inside the bottom sheet dialog fragment
     * So then we can pass it to the calling activity for further use.
     */
    public interface BottomSheetListener {
        void onButtonClicked(int id);
    }

    /**
     * Making sure the activity which instantiate this fragment implements this
     * "BottomSheetListener" interface.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Casting the calling activity context to interface.
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BottomSheetFragment");
        }
    }

    /**
     * Overriding the theme of the activity/fragment (in this case - fragment)
     * to apply our own custom theme from the "style"
     * @return
     */
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}
