package br.paulomenezes.ufrpe.utils;

import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

/**
 * Created by paulo on 01/11/2016.
 */

public class ULTagHandler implements Html.TagHandler {
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equals("ul") && !opening) output.append("\n");
        if (tag.equals("li") && opening) output.append("\n\t•");
    }
}
