package com.grab;

import com.sun.mail.util.MimeUtil;
import org.junit.Test;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

/**
 * Created by baiqunwei on 15/6/26.
 */
public class TestMimeUtility {
    @Test
    public void testDecode() throws UnsupportedEncodingException {
        String fileName = "=?gb2312?B?xbfR9LqjLbG+v8Yt0afOuy1DRVQ2LdDFz6K53MDt0+vQxc+iz7XNsy3O5Lq6?==?gb2312?B?tPPRp+fz5+zRp9S6LTEyvewtsLK71S2/vMLHx7C2yy5kb2M=?=";
        fileName = fileName.replaceAll("\\?==\\?", "?=\t\r\n=?");

        String newFileName = "";
        fileName = MimeUtility.decodeText(fileName);
        System.out.print(fileName);

    }


}
