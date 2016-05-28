package fiuba.ordertracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by scampa on 27/5/2016.
 */
public class AddCommentFragment extends DialogFragment {

    public static AddCommentFragment newInstance() {
        AddCommentFragment fragment = new AddCommentFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_comment, null);

        TextView textCommentLabel = (TextView) v.findViewById(R.id.textCommentLabel);
        final EditText textComment = (EditText) v.findViewById(R.id.textComment);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.add_product)

                // Confirm button
                .setPositiveButton(R.string.accept,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                System.out.println("******* Comentario ingresado: " + textComment.getText());
                                // TODO Store comment!!
                            }
                        })

                // Cancel button
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                System.out.println("******* AddCommentFragment - Cancel Button");
                                getDialog().dismiss();
                            }
                        }
                )
                .create();
    }

}