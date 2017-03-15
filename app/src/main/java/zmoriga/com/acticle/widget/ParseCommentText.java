package zmoriga.com.acticle.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zmoriga.com.acticle.R;


/**
 *
 */
public class ParseCommentText {


    public static void setCommentContent(String content, final TextView textView){
        if ("".equals(content)) {
            textView.setText(content);
            return;
        }

        content = replaceContent(content);

        textView.setText(Html.fromHtml(content, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                try {
                    Bitmap bm = BitmapFactory.decodeStream(textView.getContext().getAssets()
                            .open(source));
                    Drawable drawable = new BitmapDrawable(textView.getResources(), bm);
                    if (drawable != null) {
                        int w = textView.getResources().getDimensionPixelSize(
                                R.dimen.emotions_column_width);
                        drawable.setBounds(0, 0, w, drawable.getIntrinsicHeight() * w
                                / drawable.getIntrinsicWidth());
                    }
                    return drawable;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            }
        }));
    }

    public static String replaceContent(String content) {
        String reg = "\\[emot=(.*?),(.*?)\\/\\]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(content);
        while(m.find()){
            String id = m.group(2);
            String cat = m.group(1);
            int parsedId;
            try {
                parsedId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                // Invalid format text
                continue;
            }
            String replace = cat.equals("brd") || cat.equals("td") || cat.equals("tsj") || cat.equals("ac2")?
                    "<img src='emotion/"+cat+"/%02d.gif'/>" : "<img src='emotion/ac1/%02d.gif'/>";
            content = content.replace(m.group(), String.format(replace, parsedId));
        }
        reg = "\\[at\\](.*?)\\[\\/at\\]";
        m = Pattern.compile(reg).matcher(content);
        while(m.find()){
            content = content.replace(m.group(), "<font color=\"#FF9A03\" >@" + m.group(1)+"</font> ");
        }
        reg = "\\[color=(.*?)\\]";
        m = Pattern.compile(reg).matcher(content);
        while (m.find()){
            content = content.replace(m.group(), "<font color=\"" + m.group(1) + "\" >");
        }
        content = content.replace("[/color]", "</font>");
        content = content.replaceAll("\\[size=(.*?)\\]","").replace("[/size]", "");

        reg = "\\[img=(.*?)\\]";
        m = Pattern.compile(reg).matcher(content);
        while (m.find()){
            content = content.replace(m.group(), m.group(1));
        }
        content = content.replace("[img]","").replace("[/img]", "");
        content = content.replaceAll("\\[ac=\\d{5,}\\]", "").replace("[/ac]", "");
        content = content.replaceAll("\\[font[^\\]]*?\\]", "").replace("[/font]", "");
        content = content.replaceAll("\\[align[^\\]]*?\\]", "").replace("[/align]", "");
        content = content.replaceAll("\\[back[^\\]]*?\\]", "").replace("[/back]", "");
        content = content.replace("[s]", "<strike>").replace("[/s]", "</strike>");
        content = content.replace("[b]", "<b>").replace("[/b]", "</b>");
        content = content.replace("[u]", "<u>").replace("[/u]", "</u>");
        content = content.replace("[email]", "<font color=\"#FF9A03\"> ").replace("[/email]", "</font>");
        return content;
    }
}
