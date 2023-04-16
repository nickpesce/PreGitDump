package net.minecraft.src;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

class HtmlRequester
{
    private String data;

    public HtmlRequester(String s)
    {
        data = "";

        try
        {
            URL url = new URL(s);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setReadTimeout(5000);
            urlconnection.setDoOutput(true);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
            String s1;

            while ((s1 = bufferedreader.readLine()) != null)
            {
                data = (new StringBuilder()).append(data).append(s1).toString();
            }

            bufferedreader.close();
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }
    }

    public String getHTML()
    {
        return data;
    }
}
