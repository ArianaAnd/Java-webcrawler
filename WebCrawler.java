import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    public static Queue<String> queue = new LinkedList<String>();
    public static Set<String> market = new HashSet<>();
    public static String regex = "http[s]*://(\\w+\\.)(\\w+)*";


    //Define BFS Algorith method
    public static void bfsAlgorirthm(String root) throws IOException{
        queue.add(root);
        BufferedReader br = null;

        while (!queue.isEmpty()){
            String crawlerURL = queue.poll();
            System.out.println("\n=== Site crawler:" + crawlerURL +"===");


            // we limit to 100 websites here
            if(market.size() > 100)
                return;
            boolean ok = false;
            URL url = null;


            while(!ok){
                try{
                    url = new URL(crawlerURL);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;

                } catch(MalformedURLException e) {
                    System.out.println("*** Maformed URL: " + crawlerURL);
                    crawlerURL = queue.poll();
                    ok = false;

                } catch(IOException ioe){
                    System.out.println("*** Maformed URL: " + crawlerURL);
                    crawlerURL = queue.poll();
                    ok = false;


                }


            }
            StringBuilder sb = new StringBuilder();
            String tmp = null;
            while((tmp = br.readLine()) != null){
                sb.append(tmp);
            }
         while((crawlerURL = br.readLine()) != null){
             sb.append(crawlerURL);
             }
            crawlerURL = sb.toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(crawlerURL);




            while(matcher.find()){
                String w = matcher.group();
                if(!market.contains(w)){
                    System.out.println("Sited added for crawling: " + w);
                    queue.add(w);
                }

            }


        }
        if(br != null){
            br.close();
        }
    }
            public static void showResult(){
                System.out.println("\n\nResults:");
                System.out.println("Web sites: " + market.size() + "\n" );

                for(String s : market) {
                    System.out.println("*" + s);

                }
            }

    public static void main(String[] args) {
        try{
            bfsAlgorirthm("https://www.facebook.com/ariana.andrisan");
            showResult();
        }catch(IOException e){

        }
    }

}
