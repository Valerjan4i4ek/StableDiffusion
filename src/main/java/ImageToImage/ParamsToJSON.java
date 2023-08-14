package ImageToImage;

import org.apache.http.HttpEntity;

public class ParamsToJSON {
    private String text;
    private int weight;

    public ParamsToJSON(String text, int weight)
    {
        this.text = text;
        this.weight = weight;
    }
}
