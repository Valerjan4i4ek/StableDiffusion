package TextToImage;

import java.util.List;

public class Answer {
    private List<Artifacts> artifacts;

    public Answer(List<Artifacts> artifacts)
    {
        this.artifacts = artifacts;
    }

    public List<Artifacts> getArtifacts()
    {
        return artifacts;
    }
}

