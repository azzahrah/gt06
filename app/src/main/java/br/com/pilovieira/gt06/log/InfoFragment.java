package br.com.pilovieira.gt06.log;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.pilovieira.gt06.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoFragment extends Fragment {

    private View view;
    @Bind(R.id.listLog) ListView logList;
    @Bind(R.id.btnLogClear) Button btnClear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);

        mountLogsList();

        return view;
    }

    private void mountLogsList() {
        List<ServerLog> logs = new ServerLogManager(getContext()).getLogs();
        logList.setAdapter(new LogListAdapter(getContext(), logs));
    }

    @OnClick(R.id.btnLogClear)
    public void logClearClick() {
        new ServerLogManager(view.getContext()).clearLogs();
        mountLogsList();
    }

}
