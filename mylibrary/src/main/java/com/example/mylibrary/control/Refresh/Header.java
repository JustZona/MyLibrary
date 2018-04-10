package com.example.mylibrary.control.Refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mylibrary.R;


/**
 * Created by zy on 2017/2/17.
 */

public class Header extends LinearLayout {
    enum Model{
        ING,
        NORMAL,
        PROMPT,
        COMPLETE
    }

    private Context context;
    private View headView;
    private TextView prompt;
    private ImageView header_arrow;
    private ProgressBar header_progressbar;
    public Header(Context context) {
        super(context);
        this.context = context;
        init();
    }
    private void init(){
        this.setOrientation(HORIZONTAL);
        headView = LayoutInflater.from(context).inflate(R.layout.xlistview_header,null);
        prompt = (TextView) headView.findViewById(R.id.prompt);
        header_arrow = (ImageView) headView.findViewById(R.id.header_arrow);
        header_progressbar = (ProgressBar) headView.findViewById(R.id.header_progressbar);
        header_progressbar.setVisibility(GONE);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(headView,layoutParams);

    }
    public void setModel(Model model){
        switch (model){
            case ING:
                prompt.setText(R.string.xlistview_header_hint_loading);
                header_progressbar.setVisibility(VISIBLE);
                header_arrow.setVisibility(GONE);
                break;
            case NORMAL:
                prompt.setText(R.string.xlistview_header_hint_normal);
                header_arrow.setVisibility(VISIBLE);
                header_progressbar.setVisibility(GONE);
                break;
            case PROMPT:
                prompt.setText(R.string.xlistview_header_hint_ready);
                header_arrow.setVisibility(VISIBLE);
                header_progressbar.setVisibility(GONE);
                break;
            case COMPLETE:
                prompt.setText(R.string.xlistview_footer_hint_finsh);
                header_arrow.setVisibility(GONE);
                header_progressbar.setVisibility(GONE);
                break;
        }
    }

    protected void setProgress(int progress){
        prompt.setText(progress+"");
    }
}
