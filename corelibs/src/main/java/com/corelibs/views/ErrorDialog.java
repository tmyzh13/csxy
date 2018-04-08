package com.corelibs.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.corelibs.R;

/**
 * Created by john on 2017/7/10.
 */

public class ErrorDialog extends Dialog {

    public ErrorDialog(Context context) {
        super(context, R.style.custom_dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alipay_failed_core);

        TextView tv_ok = (TextView) findViewById(R.id.tv_ok);
        TextView tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        String start = "You can now click the button ";
        String middle = "\"Back to Cheque\"\n";
        String end = "to progress next steps,e.g. create Tax Free\ncheque.";
        Spanned nums = Html.fromHtml(start + "<font color=\"" + getContext().getResources().getColor(R.color.black) + "\"><B>" + middle + "</B></font>"
                + end);
//        tv_tips.setText(nums);
    }


}
