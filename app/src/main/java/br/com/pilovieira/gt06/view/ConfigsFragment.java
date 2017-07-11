package br.com.pilovieira.gt06.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.ListenerProvider;
import br.com.pilovieira.gt06.business.GT06Commands;
import br.com.pilovieira.gt06.comm.SMSEmitter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigsFragment extends Fragment {

    private GT06Commands commands;
    private SMSEmitter emitter;

    @Bind(R.id.btnChangePassword) Button btnChangePassword;
    @Bind(R.id.btnAuthorize) Button btnAuthorize;
    @Bind(R.id.btnRemoveAuth) Button btnRemoveAuth;
    @Bind(R.id.btnTimeZone) Button btnTimeZone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new GT06Commands(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnChangePassword)
    public void btnChangePasswordClicked() {
        ListenerProvider.openDialogTwoParam(this, btnChangePassword, R.string.old_password, R.string.new_password, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String oldPass, String newPass) {
                emitter.emit(btnChangePassword.getText().toString(), commands.changePassword(oldPass, newPass));
            }
        });
    }

    @OnClick(R.id.btnAuthorize)
    public void btnAuthorizeClicked() {
        ListenerProvider.openDialogOneParam(this, btnAuthorize, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnAuthorize.getText().toString(), commands.authorizeNumber(number));
            }
        });
    }

    @OnClick(R.id.btnRemoveAuth)
    public void mountBtnDeleteNumber() {
        ListenerProvider.openDialogOneParam(this, btnRemoveAuth, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnRemoveAuth.getText().toString(), commands.deleteNumber(number));
            }
        });
    }

    @OnClick(R.id.btnTimeZone)
    public void btnTimeZoneClicked() {
        ListenerProvider.openDialogOneParam(this, btnTimeZone, R.string.time_zone, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String timezone) {
                emitter.emit(btnTimeZone.getText().toString(), commands.timeZone(timezone));
            }
        });
    }

    @OnClick(R.id.btnRestart)
    public void restartAction() {
        emitter.emit(getString(R.string.restart_tracker), commands.reset());
    }

    @OnClick(R.id.btnBegin)
    public void beginAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.are_you_sure);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                emitter.emit(getString(R.string.factory_reset_begin), commands.begin());
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

}