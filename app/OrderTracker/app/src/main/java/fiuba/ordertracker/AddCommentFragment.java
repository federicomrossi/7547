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
import android.widget.Toast;

import java.util.List;

import fiuba.ordertracker.pojo.Agenda;
import fiuba.ordertracker.pojo.Client;
import fiuba.ordertracker.services.ClientService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by scampa on 27/5/2016.
 */
public class AddCommentFragment extends DialogFragment {

    private static Client client;

    public static AddCommentFragment newInstance(Client c) {
        AddCommentFragment fragment = new AddCommentFragment();
        client = c;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_comment, null);

        TextView textCommentLabel = (TextView) v.findViewById(R.id.textCommentLabel);
        final EditText textComment = (EditText) v.findViewById(R.id.textComment);
        final View _view = v;
        final ClientDetailActivity _activity = (ClientDetailActivity) this.getActivity();

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Motivo de visita")

                // Confirm button
                .setPositiveButton(R.string.accept,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                String comment = textComment.getText().toString();

                                if(comment.equals("")) {
                                    Toast.makeText(_activity.getApplicationContext(), "La visita no ha sido registrada. El comentario no puede estar vacío.", Toast.LENGTH_LONG).show();
                                    getDialog().dismiss();
                                    return;
                                }

                                _activity.saveComment(textComment.getText().toString());
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