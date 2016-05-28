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

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Motivo de visita")

                // Confirm button
                .setPositiveButton(R.string.accept,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                // Store comment
                                ClientService clientService = ClientService.getInstance();
                                Call<Agenda> call = clientService.comment.AddComment(client.getAgendaId(),textComment.getText().toString());

                                call.enqueue(new Callback<Agenda>() {
                                    @Override
                                    public void onResponse(Call<Agenda> call, Response<Agenda> response) {
                                        Agenda agenda = response.body();
                                        System.out.println("**** Stored comment: " + agenda.getComment());
                                    }

                                    @Override
                                    public void onFailure(Call<Agenda> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                                Toast.makeText(_view.getContext(), "El comentario ha sido ingresado correctamente", Toast.LENGTH_LONG).show();
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