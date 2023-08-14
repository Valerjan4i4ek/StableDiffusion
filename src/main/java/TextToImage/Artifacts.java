package TextToImage;

public class Artifacts
{
    private String base64;
    private long seed;
    private String finishReason;

    public Artifacts(String base64, long seed, String finishReason)
    {
        this.base64 = base64;
        this.seed = seed;
        this.finishReason = finishReason;
    }

    public String getBase64()
    {
        return base64;
    }

    public long getSeed()
    {
        return seed;
    }

    public String getFinishReason()
    {
        return finishReason;
    }
}
